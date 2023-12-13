<template>
<div>
  <div class="headChoose">
    <label>订单号:&nbsp;&nbsp;<input @keyup.enter="getOrdersList" v-model="searchData.orderId" type="text" placeholder="请输入订单号"></label>
    <label>搜索:&nbsp;&nbsp;<input @keyup.enter="getOrdersList" v-model="searchData.text" type="text" placeholder="请搜索关键字"></label>
    <label>下单日期:&nbsp;&nbsp;<input @input="getOrdersList" v-model="searchData.startTime" type="datetime-local" placeholder="开始日期"></label>
    <label>至:&nbsp;&nbsp;<input @input="getOrdersList" v-model="searchData.endTime" type="datetime-local" placeholder="结束日期"></label>
  </div>
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
                  <div>
                    <img class="itemImage" :src="item.url">
                    <span style="margin-left: 10px">{{item.name}}×{{item.count}}</span>
                  </div>
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
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">关闭</button>
          <button type="button" class="btn btn-primary" data-bs-dismiss="modal">确认</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted, watch,onUnmounted} from 'vue'
import {useRouter,useRoute} from "vue-router";
import {getUserOrdersAPI} from "@/apis/orders";
import {useUserStore} from "@/stores/userStore.js";

const router=useRouter()
const route=useRoute()
const userStore=useUserStore()
const currentOrderData=ref({})

const searchData=ref({
  status:route.params.status,
  userId:userStore.user.id,
  current:1,
  text:'',
  orderId:0,
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

const ordersLists=ref({})

const getOrdersList=async ()=>{

  searchData.value.status=route.params.status

  const res=await getUserOrdersAPI(searchData.value)
  ordersLists.value=res.data.data.records;
  pageData.value=res.data.data
}

const choosePages = (currentPage) => {
  searchData.value.current = currentPage;
  getOrdersList();

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
    return "退款处理中"
  }

  if (status===7)
  {
    return "已退款"
  }

}

const stopwatch=watch(() => router.currentRoute, () => {
  console.log("进来了")
  if(route.params.status) getOrdersList();
},{
  deep:true
});

onMounted(()=>{
  getOrdersList()
})

onUnmounted(()=>{
  stopwatch()
})

</script>

<style scoped>
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
  border: 1px solid #eeeeee;
}


table
{
  margin-left: 30px;
}
td
{
  flex:1;
  /*display: flex;*/
  /*justify-content: center;*/
  text-align: center;
  display: flex; /* 使用 flex 布局 */
  flex-wrap: wrap;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */


  /*vertical-align: center;*/
  /*height: 40px;*/
  /*line-height: 40px;*/
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
  height: 100px;
  line-height: 100px;
  margin-bottom: 10px;
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
.itemImage
{
  width: 100px;
  height: 100px;
}

</style>