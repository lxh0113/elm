<template>
<div class="toPayBox">
  <span>合计￥</span>
  <span>{{shopListStore.getTotalPrice()}}</span>
  <button @click="toPay">提交订单</button>
</div>
</template>

<script setup>
import {useShopListStore} from "@/stores/shopListStore";
import {ElMessage} from "element-plus";
import {useUserStore} from "@/stores/userStore";

const shopListStore=useShopListStore()
const userStore=useUserStore()
// const emit=defineEmits(['toHandAddress','toHandleOrder'])
const emit=defineEmits(['toHandleAllOperation'])

const toPay=()=>{
  //去支付
  console.log("支付")
  emit('toHandleAllOperation')
}
</script>

<style scoped>
.toPayBox
{
  position: fixed;
  height: 10vh;
  bottom: 0;
  z-index: 100;
  background: white;
  width: 100vw;
  display: flex;
  justify-content: right;
  line-height: 10vh;
  /*text-align: center;*/
}
.toPayBox span:nth-child(1)
{
  font-size: 4vw;
  margin-right: 2vw;
}
.toPayBox span:nth-child(2)
{
  font-size: 6vw;
  color: red;
  margin-right: 2vw;
  font-weight: bold;
}
.toPayBox button
{
  width: 30vw;
  font-size: 4.5vw;
  height: 6vh;
  margin-top: 2vh;
  margin-right: 4vw;
  border-radius: 5vw;
  line-height: 6vh;
  border:0;
  /*line-height: 5vh;*/
  color: white;
  background: #01b6fd;
  outline: none;
}
</style>