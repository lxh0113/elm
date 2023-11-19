<template>
<div class="addressItemBackground">
  <div class="empty" style="height: 30px">
    <span v-show="address.isDefault===1">默认</span>
  </div>
  <div class="addressBody">
    <div class="left">
      <div>称呼：&nbsp;</div>
      <div>电话：&nbsp;</div>
    </div>
    <div class="right">
      <span>{{address.receiverName}}&nbsp;&nbsp;{{address.gender?"先生":"女士"}}</span>
      <br>
      <span>{{address.telephone}}</span>
    </div>
  </div>
  <div class="addressBody">
    <div class="left">
      <div>地址：&nbsp;</div>
      <div>详细地址：&nbsp;</div>
    </div>
    <div class="right">
      <span>{{addressText}}</span>
      <br>
      <span>{{address.detail}}</span>
    </div>
  </div>
  <div class="editFooter">
    <button @click="setDefaultAddress" v-show="address.isDefault===0" style="width: 150px">设置为默认地址</button>
    <button data-bs-toggle="modal" data-bs-target="#addressModal" @click="changeAddressData">编辑</button>
    <button @click="deleteAddress">删除</button>
  </div>
</div>
</template>

<script setup>
import {ref} from 'vue'
import {defineEmits} from 'vue'
import {deleteAddressAPI,changeToDefaultAPI, getSingleDistrictAPI} from "@/apis/address";
import {ElMessage} from "element-plus";

const { address,index } = defineProps(['address','index']);
const addressText=ref("")
const emits=defineEmits(['changeAddressList','changeAddressData'])

const getAddressText=async (province,city,district)=>{
  //先获取到省
  let provinceData=ref()
  let cityData=ref()
  let districtData=ref()

  let res=await getSingleDistrictAPI(province);
  if(res.data.code===1)
  {
    provinceData.value=res.data.data
  }
  else
  {
    ElMessage.error("获取失败")
    return
  }
  res=await getSingleDistrictAPI(city);
  if(res.data.code===1)
  {
    cityData.value=res.data.data
  }
  else
  {
    ElMessage.error("获取失败")
    return
  }

  res=await getSingleDistrictAPI(district);

  if(res.data.code===1)
  {
    districtData.value=res.data.data
  }
  else
  {
    ElMessage.error("获取失败")
    return
  }
  addressText.value=provinceData.value.district+cityData.value.district+districtData.value.district
  return addressText.value
}

const setDefaultAddress=async ()=>{
  //设置当前地址为默认地址
  const res=await changeToDefaultAPI(address);

  if(res.data.code===1)
  {
    emits('changeAddressList',res.data.data)
    ElMessage.success("修改成功")
  }
  else
  {
    ElMessage.error(res.data.msg)
  }
}

const deleteAddress=async ()=>{
  //删除 当前地址
  const res=await deleteAddressAPI(address);
  if(res.data.code===1)
  {
    emits('changeAddressList',res.data.data)
    ElMessage.success("删除成功")
  }
  else
  {
    ElMessage.error(res.data.msg)
  }
}

const changeAddressData=()=>{
  console.log("下标为"+index)
  //先是设置数据 需要得到省市区的 让父组件打开弹窗 并且显示
  emits('changeAddressData',index)
}

onMounted(()=>{
  getAddressText(address.province, address.city, address.district);
})
</script>

<style scoped>
.addressItemBackground
{
  /*background: pink;*/
  /*position: relative;*/
  height: 300px;
  width: 600px;
  box-shadow: 0 3px 8px rgb(0 0 0 / 20%);
  border-radius: 20px;
  transition: all 0.5s;
}
.addressItemBackground:hover
{
  transform: translateY(-10px);
}
.empty
{
  display: flex;
  justify-content: right;
}
.empty span
{
  margin-right: 20px;
  margin-top: 10px;
  font-weight: bold;
  font-size: 25px;
  transform: rotate(-320deg) translateX(14px);
  /*color: red;*/
  /*background: #01b6fd;*/
  color: greenyellow;
  /*padding: 20px 80px;*/
}
.addressBody
{
  display: flex;
  font-size: 20px;
  margin-left: 20px;
}
.addressBody .left
{
  margin-left: 20px;
  flex: 3;
  height: 100px;
}
.addressBody .left div
{
  line-height: 50px;
  height: 50px;
  font-weight: bold;
  /*color: #01b6fd;*/
}
.addressBody .right
{
  margin-right: 20px;
  flex: 7;
  height: 100px;
  width: 273px;
  text-overflow: ellipsis;
  overflow: hidden;
  word-break: break-all;
  white-space: nowrap;
}
.addressBody .right span
{
  line-height: 50px;
  font-weight: lighter;
}
.editFooter
{
  margin-top: 10px;
  display: flex;
  justify-content: right;
  height: 60px;
}
.editFooter button
{
  margin-right: 30px;
  height: 40px;
  width: 100px;
  /*margin-top: 10px;*/
  outline: none;
  color: white;
  background: #01b6fd;
  border-radius: 10px;
  border:2px solid #eeeeee;
}
</style>