<template>
  <div class="backBox">
    <div class="OrderHeader">
      <ul class="chooseStatus">
        <li>
          <router-link to="/user/order/-1">全部订单</router-link>
        </li>
        <li><router-link to="/user/order/1">待接单</router-link></li>
        <li><router-link to="/user/order/2">已取消</router-link></li>
        <li><router-link to="/user/order/4">已完成</router-link></li>
        <li><router-link to="/user/order/5">已评论</router-link></li>
        <li><router-link to="/user/order/7">已退款</router-link></li>
      </ul>
    </div>
    <div class="showView">
      <router-view></router-view>
    </div>
  </div>
</template>

<script setup>

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
  min-height: 700px;
  /*margin-right: 30px;*/
  /*height: 400px;*/
  /*background: pink;*/
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
  border: 1px solid #eeeeee;
}
</style>