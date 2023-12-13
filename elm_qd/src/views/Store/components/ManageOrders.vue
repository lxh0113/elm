<template>
<div class="backBox">
  <div class="OrderHeader">
    <ul class="chooseStatus">
      <li>
        <router-link to="/store/order/details/-1">全部订单</router-link>
      </li>
      <li><router-link to="/store/order/details/1">待接单</router-link></li>
<!--      <li><router-link to="/store/order/details">待派送</router-link></li>-->
      <li><router-link to="/store/order/details/2">已取消</router-link></li>
      <li><router-link to="/store/order/details/3">已接单</router-link></li>
      <li><router-link to="/store/order/details/4">已完成</router-link></li>
      <li><router-link to="/store/order/details/5">已评论</router-link></li>
      <li><router-link to="/store/order/details/6">退款处理</router-link></li>
      <li><router-link to="/store/order/details/7">已退款</router-link></li>
    </ul>
  </div>
  <div class="showView">
    <router-view></router-view>
  </div>
</div>
</template>

<script setup>
import {useRouter,useRoute} from "vue-router";
import {ref,onMounted} from 'vue'

const router=useRouter();
const route=useRoute()

onMounted(()=>{

  router.push("/store/order/details/-1")
})
</script>

<style scoped>
.chooseStatus
{
  display: flex;
  margin-top: 40px;
  margin-left: 30px;
}
.chooseStatus li
{
  /*height: 30px;*/
  line-height: 40px;
  list-style: none;
  width: 150px;
  height: 40px;
  text-align: center;
  /*background: #01b6fd;*/
  color: black;
  border:2px solid #eeeeee;
  font-weight: bold;
}
a
{
  text-decoration: none;
  color: black;
}
a.router-link-active
{
  display: block;
  background: #01b6fd;
  color: white;
}
.showView
{
  margin-top: 30px;
  margin-left: 30px;
  width: 96%;
  box-shadow: 0 3px 8px rgb(0,0,0,0.2);
  border-radius: 20px;
  margin-right: 30px;
  min-height: 700px;
  /*background: pink;*/
}



</style>