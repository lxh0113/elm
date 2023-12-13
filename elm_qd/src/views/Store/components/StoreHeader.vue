<template>
<div class="bigBox">
  <div class="logo">
    <img @click="$router.push('/')" src="@/img/logo.png" alt="图片未加载">
    <span v-if="storeStore.getStore().status===0" @click="changeStoreStatus(1)" class="on"><i class="bi bi-toggle-on"></i>营业中</span>
    <span v-else @click="changeStoreStatus(0)" class="off"><i class="bi bi-toggle-off"></i>关门中</span>
  </div>
  <div class="info">
    <img src="@/img/fox.png" alt="">
    <h3>KFC</h3>
  </div>
</div>
</template>

<script setup>
import {ref,onMounted} from 'vue'
import {changeStoreStatusAPI,getStoreInfoAPI} from "@/apis/store";
import {useUserStore} from "@/stores/userStore";
import {ElMessage} from "element-plus";
import {useStoreStore} from "@/stores/storeStore";

const isOpen=ref(true)
const storeStore=useStoreStore();
const userStore=useUserStore();
const changeStoreStatus=async (status)=>{
  let data=await storeStore.getStore()
  data.status=status
  const res=await changeStoreStatusAPI(data)
  if(res.data.code===1)
  {
    storeStore.setStore(res.data.data)
    // isOpen.value=status
    ElMessage.success("修改店铺状态成功")
  }
  else
  {
    ElMessage.error(res.data.msg)
  }
}

onMounted(()=>{
  isOpen.value=storeStore.store.status===1?0:1
})
</script>

<style scoped>
.bigBox
{
  width: 100%;
  min-width: 1240px;
  height: 150px;
  background: white;
  display: flex;
  justify-content: space-between;
}
.logo
{
  display: flex;
  margin-left: 50px;
}
.logo img
{
  height: 150px;

}
.on
{
  /*line-height: 150px;*/
  background: #01b6fd;
  margin-top: 60px;
  height: 30px;
  color: white;
  padding:5px;
}
.off
{
  background: #7a7a7a;
  margin-top: 60px;
  height: 30px;
  color: white;
  padding:5px;
}
.info
{
  display: flex;
  margin-right: 50px;
}
.info img
{
  height: 50px;
  width: 50px;
  border-radius: 50%;
  margin-top: 50px;
}
.info h3
{
  line-height: 150px;
  margin-left: 20px;
}
</style>