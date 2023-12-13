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
        <span @click="completeOrders(index)" class="greenText">完成</span>
        <span @click="cancelOrders(index)" class="redText">取消</span>
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
</template>

<script setup>
import {ref, onMounted, watch,onUnmounted} from "vue";
import {useUserStore} from "@/stores/userStore";
import {useRoute, useRouter} from "vue-router";
import {ElMessage} from "element-plus";
import {getOrdersAPI} from "@/apis/orders";

const userStore=useUserStore();
const ordersLists=ref([])
const router=useRouter()
const route=useRoute()

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
  searchData.value.status=route.params.status;

  if(searchData.value.endTime<searchData.value.startTime)
  {
    ElMessage.error("日期设置错误")
    return
  }
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

const stopwatch=watch(() => router.currentRoute, () => {
  console.log("进来了")
  getOrders();
},{
  deep:true
});

onMounted(()=>{
  getOrders()
})

onUnmounted(()=>{
  stopwatch()
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

</style>