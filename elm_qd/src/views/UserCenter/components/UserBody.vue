<template>
<div class="userBodyBack">
  <div class="userLeft">
    <ul>
      <li>
        <router-link :to="'/user/info'">个人资料</router-link>
      </li>
      <li>
        <router-link :to="'/user/order'">订单管理</router-link>
      </li>
      <li>
        <router-link :to="'/user/message'">消息中心</router-link>
      </li>
      <li>
        <router-link :to="'/user/remark'">评价中心</router-link>
      </li>
      <li>
        <router-link :to="'/user/store'">成为商家</router-link>
      </li>
      <li>
        <router-link :to="'/user/address'">地址管理</router-link>
      </li>
    </ul>
  </div>
  <div class="userRight">
    <router-view></router-view>
  </div>
</div>
</template>

<script setup>

</script>

<style scoped>
.userBodyBack
{
  width: 100%;
  min-width: 1857px;
  background: #e8edea;
  /*height: 1000px;*/
  display: flex;
}
.userLeft
{
  margin-top: 10px;
  background: #01b6fd;
  width: 400px;
}
.userLeft ul
{
  padding-left: 0px;
}
.userLeft ul li
{
  box-sizing: border-box;
  color: white;
  height:120px;
  text-align: center;
  list-style: none;
  line-height: 120px;
  font-size: 20px;
}
a
{
  text-decoration: none;
  color: white;
}
.userRight
{
  width: 100%;
  margin:10px;
  background: white;
}

</style>