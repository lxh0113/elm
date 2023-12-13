<template>
<div class="addressBigBox">
  <div class="addressBigBoxHeader">
    <div style="height: 2vh"></div>
    <div class="detailAddress">
      <div class="detailsLeft">
        <div v-if="hasDefaultAddress" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
          <h2>{{defaultAddress.detail}}</h2>
          <span style="margin-right: 5vw;">{{defaultAddress.receiverName}}</span>
          <span style="margin-right: 3vw;">{{defaultAddress.telephone}}</span>
          <i class="bi bi-pencil-square"></i>
        </div>
        <div v-else data-bs-toggle="modal" data-bs-target="#staticBackdrop">
          <span style="line-height: 7.5vh;">请选择地址</span>
        </div>
      </div>
      <div class="detailsRight">
        <i style="margin-right: 4vw;line-height: 7.5vh" class="bi bi-chevron-right"></i>
      </div>
    </div>
  </div>
  <div class="addressBigBoxBody">
    <span>立即送出</span>
    <div>
      <span style="color: #01b6fd">预计12:10送达</span>
      <i style="margin-right: 4vw;" class="bi bi-chevron-right"></i>
    </div>
  </div>
</div>


</template>

<script setup>
import {ref,onMounted} from "vue";
import {useUserStore} from "@/stores/userStore";
import {getUserAddressAPI} from "@/apis/address";

// const defaultAddress=ref({})
const hasDefaultAddress=ref(true)
const userStore=useUserStore()

const props=defineProps({
  defaultAddress:{
    type:Object
  }
})

// 获取到默认地址
const getDefaultAddress=async ()=>{
  //获取默认地址显示 如果无就显示 请获取地址
  const res=await getUserAddressAPI(userStore.user.id)

  if(res.data.code===1)
  {
    defaultAddress.value=res.data.data
    hasDefaultAddress.value=true
  }
  else
  {
    defaultAddress.value={}
  }
}



onMounted(()=>{
  // getDefaultAddress()
})
</script>

<style scoped>
.addressBigBox
{
  height: 20vh;
  width: 95vw;
  /*background: #fed6e3;*/
  margin: 2vh auto;
  font-size: 5vw;
  border-radius: 4vw;
  background: white;
  box-shadow: 7px 7px 17px rgba(52, 56, 66, 0.5);
}
.addressBigBoxHeader
{
  margin-left: 3vw;
}
.addressBigBoxHeader
{
  color:#6c757d;
}
.addressBigBoxHeader h2
{
  font-size: 6vw;
  font-weight: bold;
  color: black;
  /*margin-top: 3vh;*/
}

.detailAddress
{
  display: flex;
  justify-content: space-between;
}
.addressBigBoxBody
{
  display: flex;
  justify-content: space-between;
  margin-top: 4vh;
  margin-left: 3vw;
}
.addressBigBoxBody
{
  color: black;
  font-weight: bold;
}


</style>