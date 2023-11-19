<template>
<div class="userHeader">
  <img src="../../../../src/img/logo.png" alt="">
  <div class="avatar">
    <img @click="$router.push('/')" :src="userStore?.user?.avatar" alt="">
    <span>{{userStore.user.nickname}}</span>
  </div>
</div>
</template>

<script setup>
import {useUserStore} from "@/stores/userStore.js";

const userStore=useUserStore()

// const userInfoData=userStore.getUserInfo();
</script>

<style scoped>
.userHeader
{
  margin-right: 30px;
  width: 100%;
  min-width: 1857px;
  height: 180px;
  background: white;
  display: flex;
  justify-content: space-between;
}
.userHeader img
{
  width: 250px;
  margin-top: 10px;
  margin-left: 50px;
}
.avatar
{
  display: flex;
}
.avatar img
{
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-top:50px;
}
.avatar span
{
  line-height: 150px;
  font-weight: bold;
  color: black;
  font-size: 20px;
  margin-right: 50px;
  margin-left: 50px;
}
</style>