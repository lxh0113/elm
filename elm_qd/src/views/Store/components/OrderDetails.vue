<template>
<div>
  <div class="headChoose">
    <label>订单号:&nbsp;&nbsp;<input v-model="searchData.orderId" @keyup.enter="getOrders" type="text" placeholder="请填写订单号"></label>
    <label>用户名字:&nbsp;&nbsp;<input v-model="searchData.userName" @keyup.enter="getOrders" type="text" placeholder="请填写用户名称"></label>
    <label>手机号:&nbsp;&nbsp;<input v-model="searchData.telephone" @keyup.enter="getOrders" type="text" placeholder="请写手机号"></label>
    <label>下单日期:&nbsp;&nbsp;<input v-model="searchData.startTime" @input="getOrders" type="datetime-local" placeholder="开始日期"></label>
    <label>至:&nbsp;&nbsp;<input v-model="searchData.endTime" @input="getOrders" type="datetime-local" placeholder="结束日期"></label>
  </div>
  <table>
    <thead>
      <tr>
        <td>订单号</td>
        <td>订单菜品</td>
        <td>地址</td>
<!--        <td>口味</td>-->
<!--        <td>预计送达时间</td>-->
        <td>备注</td>
        <td>价格</td>
<!--        <td>餐具数量</td>-->
        <td>操作</td>
      </tr>
    </thead>
    <tbody v-if="ordersLists.length>0">
      <tr v-for="(item,index) in ordersLists" :key="item.id">
        <td>
          {{item.id}}
        </td>
        <td>
          <h6 style="min-width: 270px;font-size:16px;" v-for="goodsItem in item.itemsLists" :key="goodsItem">
            {{goodsItem.name}}&nbsp;×{{goodsItem.count}}
          </h6>
        </td>
        <td>
          {{item.addressText}}
        </td>
        <td>
          {{item.remark}}
        </td>
<!--        <td></td>-->
        <td>
          <span class="priceText">￥{{item.price}}</span>
        </td>
        <td>
          <span v-if="getRefuseText(item.status)" @click="toHandleReceive(index)" class="greenText">{{getReceivedText(item.status)}}</span>
          <span v-if="getReceivedText(item.status)" @click="toHandleRefuse(index)" class="redText">{{getRefuseText(item.status)}}</span>
          <span @click="setCurrentData(index)" class="grayText" data-bs-toggle="modal" data-bs-target="#staticBackdrop">查看</span>
        </td>
      </tr>
    </tbody>
    <template v-else>
      <el-empty description="无数据" />
    </template>

    <nav style="margin-top: 40px" aria-label="Page navigation example">
      <ul class="pagination justify-content-center">
        <li class="page-item" :class="{disabled:pageData.current===1}" @click="choosePages(1)">
          <a class="page-link">首页</a>
        </li>
        <li v-for="(item,index) in pageData.pages>3?3:pageData.pages" @click="choosePages(index+1)" class="page-item"><a class="page-link" href="#">{{index+1}}</a></li>
        <li class="page-item" :class="{disabled:pageData.current===pageData.pages}" @click="choosePages(pageData.pages)">
          <a class="page-link" href="#">末尾</a>
        </li>
      </ul>
    </nav>
  </table>
</div>

  <!-- Modal -->
  <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="staticBackdropLabel">订单信息</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body" style="padding: 0 20px">
          <div class="orderHeader">
            <div class="orderHeaderLeft">
              <span class="gray">订单号：&nbsp;</span>
              <span class="Btext">{{currentOrderData.id}}</span>
              <span class="statusText">{{getStatus(currentOrderData.status)}}</span>
            </div>
            <div class="orderHeaderRight">
              <span class="gray">下单时间：&nbsp;{{formatDate(currentOrderData.time)}}</span>
            </div>
          </div>
          <div class="orderBody">
            <div class="orderBodyInfo">
              <div>用户名：&nbsp;{{currentOrderData.buyerName}}</div>
              <div>手机号：&nbsp;{{currentOrderData.buyerTel}}</div>
              <div>地址：&nbsp;{{currentOrderData.pcd}}{{currentOrderData.addressText}}</div>
            </div>
          </div>
          <div class="orderBodyRemark">
            <div class="remarkBox">
              <div class="remarkFront">备注</div>
              <span class="remarkRear">{{currentOrderData.remark}}</span>
            </div>
          </div>
          <div class="orderBodyDetails">
            <div class="detailsLeft">
              <span class="gray" style="line-height: 40px">菜品</span>
            </div>
            <div class="detailsRight">
              <ul>
                <li v-for="item in currentOrderData.itemsLists" :key="item">
                  <span>{{item.name}}×{{item.count}}</span>
                  <span>{{item.flavors}}</span>
                  <span>￥{{item.price}}元</span>
                </li>
              </ul>
              <span class="dishSum">菜品小计:&nbsp;</span>
              <span class="dishSumPrice">￥{{currentOrderData.price-currentOrderData.packageFee}}元</span>
            </div>
          </div>
          <div class="orderBodyDetails">
            <div class="detailsLeft">
              <span class="gray" style="line-height: 40px">费用</span>
            </div>
            <div class="detailsPriceRight">
              <ul>
                <li>
                  <span>菜品小计:&nbsp;</span>
                  <span>￥{{currentOrderData.price-currentOrderData.packageFee}}元</span>
                </li>
                <li>
                  <span>打包费:&nbsp;</span>
                  <span>￥{{currentOrderData.packageFee}}元</span>
                </li>
                <li>
                  <span>合计:&nbsp;</span>
                  <span>￥{{currentOrderData.price}}元</span>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">{{getRefuseButtonText(currentOrderData.status)}}</button>
          <button type="button" class="btn btn-primary" data-bs-dismiss="modal">{{getReceivedButtonText(currentOrderData.status)}}</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref,onMounted,watch} from 'vue'
import {ElMessage} from "element-plus";
import {useRoute,useRouter} from "vue-router";
import {useUserStore} from "@/stores/userStore";
import {getOrdersAPI,changeOrdersStatusAPI} from '@/apis/orders.js'
import {getSingleDistrictAPI} from "@/apis/address";
import {messageTitle,storeOrdersButtonText,storeListText} from "@/utils/MessageTitle";
import {useWsStore} from "@/stores/wsStore";
import {Refuse_Refund} from "@/utils/constant";
import {Constant} from "@/utils";

const userStore=useUserStore();
const router=useRouter()
const route=useRoute()
const ordersLists=ref([])
const wsStore=useWsStore();

const currentOrderData=ref({
  status:0
})
const searchData=ref({
  status:route.params.choose,
  current:1,
  storeId:userStore.user.id,
  telephone:'',
  userName:'',
  orderId:'',
  startTime:'',
  endTime:''
})

const pageData=ref({
  countId:0,
  current:1,
  pages:1,
  size:7,
  total:0
})


const getOrders=async ()=>{
  searchData.value.status=route.params.choose;

  if(searchData.value.endTime<searchData.value.startTime)
  {
    ElMessage.error("日期设置错误")
    return
  }
  console.log(searchData)
  const res=await getOrdersAPI(searchData.value);

  if(res.data.code===1)
  {
    console.log(res)
    ordersLists.value=res.data.data.records
    pageData.value=res.data.data
  }
  else
  {
    ElMessage.error("获取出错")
  }
}

const getStatus=(status)=>{
  if(status===0)
  {
    return "未支付"
  }

  if(status===1)
  {
    return "已支付"
  }

  if(status===2)
  {
    return "已取消"
  }

  if(status===3)
  {
    return "已接单"
  }

  if(status===4)
  {
    return "已完成"
  }

  if(status===5)
  {
    return "已评论"
  }

  if(status===6)
  {
    return "已取消"
  }

  if(status===7)
  {
    return "已退款"
  }
}

const setCurrentData=(index)=>{
  currentOrderData.value=ordersLists.value[index]
}

const formatDate=(time)=>{
  const date=new Date(time)
  const year = date.getFullYear();
  let month = date.getMonth() + 1;
  let day = date.getDate();
  let hours = date.getHours();
  let minutes = date.getMinutes();
  let seconds = date.getSeconds();

  // 在月、日、小时、分钟、秒数小于10时，在前面补0
  month = month < 10 ? '0' + month : month;
  day = day < 10 ? '0' + day : day;
  hours = hours < 10 ? '0' + hours : hours;
  minutes = minutes < 10 ? '0' + minutes : minutes;
  seconds = seconds < 10 ? '0' + seconds : seconds;

  const formattedDate = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
  return formattedDate;
}

const toHandleReceive=async (index)=>{
  //处理订单
  let data=ordersLists.value[index];

  if(getReceivedText(data.status)==="同意退款")
  {
    data.status=7
    // wsStore.sendMessage(Constant.)
  }
  else if(getReceivedText(data.status)==="完成订单")
  {
    data.status=4
    wsStore.sendMessage(Constant.Complete_Orders,data.buyerId,data)
  }
  else if(getReceivedText(data.status)==="接单")
  {
    data.status=3
    wsStore.sendMessage(Constant.Receive_Orders,data.buyerId,data)
  }

}

const toHandleRefuse=async (index)=>{
  //取消订单
  let data=ordersLists.value[index];

  if(getRefuseText(data.status)==="拒绝退款")
  {
    // data.status=7
    wsStore.sendMessage(Constant.Refuse_Refund,currentOrderData.value.buyerId,data)
  }
  else if(getRefuseText(data.status)==="取消订单")
  {
    data.status=2
    wsStore.sendMessage(Constant.Cancel_Orders,currentOrderData.value.buyerId,data)
  }
  else if(getRefuseText(data.status)==="拒单")
  {
    data.status=2
    wsStore.sendMessage(Constant.Refuse_Orders,data.buyerId,data)
  }

  nextTick(()=>{
    getOrders()
  })
  // const res=await changeOrdersStatusAPI(data);
}

const choosePages = (currentPage) => {
  searchData.value.current = currentPage;
  getOrders();

  // 移除所有带有 "active" 类的元素的 "active" 类
  document.querySelectorAll(".page-item").forEach((item) => {
    item.removeAttribute("aria-current");
    item.classList.remove("active");
  });

  // 为当前页面的元素添加 "active" 类，并设置 aria-current 属性
  const activePageItem = document.querySelector(`.page-item:nth-child(${currentPage+1})`);
  if (activePageItem) {
    activePageItem.classList.add("active");
    activePageItem.setAttribute("aria-current", "page");
  }
};

const getRefuseText=(status)=>{
  // console.log(storeListText[`${status}`].refuse)
  return storeListText[`${status}`].refuse
}

const getReceivedText=(status)=>{

  return storeListText[`${status}`].receive
}

const getRefuseButtonText=(status)=>{
  console.log(status)
  // console.log(storeListText[`${status}`].refuse)
  return storeOrdersButtonText[`${status}`].refuse
}

const getReceivedButtonText=(status)=>{

  return storeOrdersButtonText[`${status}`].receive
}

watch(()=>wsStore.message,async ()=>{
  console.log(wsStore.message)
  // console.log(wsStore.message.value)

  if(wsStore.message?.messageType===Constant.Operation_Success) {
    const message = wsStore.message.t
    //更新一下列表
    getOrders()
  }
},{
  deep:true
})

watch(() => router.currentRoute, () => {
  // This will be called whenever the route changes
  getOrders();
},{
  deep:true
});


onMounted(()=>{
    getOrders();
    wsStore.wsInit()
    // let i=1;
  // console.log(storeOrdersButtonText[`${i}`].refuse)
})
</script>

<style scoped>
table
{
  margin-left: 30px;
}
td
{
  flex:1;
  text-align: center;
  display: flex; /* 使用 flex 布局 */
  flex-wrap: wrap;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
}
thead tr td
{
  font-weight: bold;
  font-size: 18px;
}
tr
{
  display: flex;
  padding-top: 10px;
  width: 1400px;
  text-align: center;
  justify-content: space-evenly;
  border-bottom: 1px solid #eeeeee;
  padding-bottom: 10px;
}
tbody
{
  min-height: 500px;
  margin-top: 10px;
}

.headChoose
{
  display: flex;
}
label
{
  height: 60px;
  line-height: 60px;
  text-align: center;
  margin-left: 40px;
}
label input
{
  width: 150px;
  height: 30px;
  /*line-height: 0;*/
  padding-left: 10px;
  outline: none;
  border: 1px solid lightblue;
}

.grayText,
.greenText,
.redText
{
  /*font-weight: bold;*/
  font-style: italic;
  /*font-weight: bold;*/
  /*cursor: grab;*/
  font-size: 16px;
  margin: 0 15px;
}
.greenText
{
  color: green;
}
.redText
{
  color: red;
}
.grayText
{
  color: gray;
}
.priceText
{
  color: red;
  font-weight: bold;
  font-size: larger;
}

ul
{
  padding-left: 0;
}
.orderHeader
{
  display: flex;
  justify-content: space-between;
  height: 60px;
  width: 100%;
  border-bottom: 1px solid #dee2e6;
  line-height: 60px;
  /*background: #01b6fd;*/
}
.orderBody
{
  /*background: #fed6e3;*/
}
.orderBodyInfo
{
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}
.orderBodyInfo div
{
  flex:50%;
  height: 50px;
  line-height: 50px;
}
.remarkBox
{
  display: flex;
  height: 50px;
  line-height: 50px;
  border:2px solid #01b6fd;
  background: lightblue;
  border-radius: 10px;
}
.remarkBox .remarkFront
{
  padding:3px 10px;
  background: #509ee8;
  margin: 5px;
  line-height: 35px;
  color: white;
  border-radius: 10px;
}
.remarkBox .remarkRear
{

}
.orderBodyDetails
{
  display: flex;
  margin-top: 15px;
}
.orderBodyDetails .detailsLeft
{
  width: 100px;
  /*background: #509ee8;*/
}
.orderBodyDetails .detailsRight
{
  flex:1;
}
.orderBodyDetails .detailsRight ul li
{
  display: flex;
  width: 100%;
  height: 40px;
  line-height: 40px;
  justify-content: space-between;
}
.orderBodyDetails .detailsRight ul li span
{
  margin-right: 10px;
}
.Btext
{
  font-weight: bold;
}
.statusText
{
  padding:3px 5px;
  background: #2f2f2f;
  color:white;
  border-radius: 10px;
  margin-left: 10px;
}
.gray
{
  color:grey;
}
.dishSum
{
  font-weight: bold;
  font-size: 15px;
}
.dishSumPrice
{
  font-weight: bold;
  font-size: 15px;
  margin-left: 10px;
  color:red;
}
.detailsPriceRight ul li
{
  height: 40px;
  line-height: 40px;
}
.detailsPriceRight ul li:nth-child(3) span:nth-child(1)
{
  font-weight: bold;
  font-size: 16px;
}
.detailsPriceRight ul li span:nth-child(2)
{
  margin-left: 20px;
  /*color:#509ee8;*/
  font-weight: bold;
}
.detailsPriceRight ul li:nth-child(3) span:nth-child(2)
{
  font-weight: bold;
  font-size: 16px;
  color:red;
  margin-left: 20px;
}
</style>