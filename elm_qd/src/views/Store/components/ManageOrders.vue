<template>
<div class="backBox">
  <div class="OrderHeader">
    <ul class="chooseStatus">
      <li>
        <router-link to="/store/order/details">全部订单</router-link>
      </li>
      <li><router-link to="/store/order/details">待接单</router-link></li>
      <li><router-link to="/store/order/details">待派送</router-link></li>
      <li><router-link to="/store/order/details">派送中</router-link></li>
      <li><router-link to="/store/order/details">已完成</router-link></li>
      <li><router-link to="/store/order/details">已取消</router-link></li>
    </ul>
  </div>
  <div class="showView">
    <div class="headChoose">
      <label>订单号:&nbsp;&nbsp;<input type="text" placeholder="请填写订单号"></label>
      <label>手机号:&nbsp;&nbsp;<input type="text" placeholder="请填写手机号"></label>
      <label>下单日期:&nbsp;&nbsp;<input type="datetime-local" placeholder="开始日期"></label>
      <label>至:&nbsp;&nbsp;<input type="datetime-local" placeholder="结束日期"></label>
    </div>
    <router-view></router-view>
  </div>
</div>
</template>

<script>

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