<template>
  <div class="nav">
<!--    <el-icon><LocationInformation /></el-icon>-->
    <div class="container">
      <el-icon style="color: white;margin-left: 30px;"><LocationInformation /></el-icon>
      <span class="address" @click="chooseAddress">{{addressText}}</span>
    </div>

    <ul>
      <li @mouseenter="showPerson" class="avatar" v-if="userStore?.user?.id">
        <img :src="userStore?.user?.avatar" alt="">
        <span>{{userStore?.user?.nickname}}</span>
        <div @mouseleave="disappearShow" :class="{show:isShow}" class="choose">
          <ul>
            <li @click="toUserCenter">个人中心</li>
            <li @click="toStore">{{ userStore?.user?.identity === 1 ? "成为商家" : "进入商家端" }}</li>
          </ul>
        </div>
      </li>
      <li v-else @click="$router.push('/login')">请先登录</li>
      <li><span>帮助中心</span></li>
      <li><span>关于我们</span></li>
    </ul>
  </div>

  <!-- Button trigger modal -->
  <button ref="addressModal" type="button" style="display: none" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
    Launch static backdrop modal
  </button>

  <!-- Modal -->
  <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="staticBackdropLabel">设置地址</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
<!--          这里写的是下拉按钮-->
          <div class="pcd">
            <select ref="provinceRef" class="form-select" aria-label="Default select example" v-model="addressForm.province" @change="changeProvince">
              <option :value="0" selected>省</option>
              <option v-for="item in provinceArry" :key="item.districtId" :value="item.districtId">{{item.district}}</option>
            </select>
            <select ref="cityRef" class="form-select" aria-label="Default select example" v-model="addressForm.city" @change="changeCity">
              <option :value="0" selected>市</option>
              <option v-for="item in cityArry" :key="item.districtId" :value="item.districtId">{{item.district}}</option>
            </select>

            <select ref="districtRef" style="width: 438px" class="form-select" aria-label="Default select example" v-model="addressForm.district">
              <option :value="0" selected>区</option>
              <option v-for="item in districtArry" :key="item.districtId" :value="item.districtId">{{item.district}}</option>
            </select>

            <div class="mb-3" style="margin-left: 15px;">
              <label for="exampleFormControlInput1" class="form-label">详细地址</label>
              <input type="email" class="form-control" id="exampleFormControlInput1" placeholder="吉首大学张家界校区">
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">关闭</button>
          <button type="button" class="btn btn-primary" @click="ensureAddress" style="background-color: #01b6fd">{{userStore?.user?.id?"保存":"使用"}}</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import 'bootstrap/dist/css/bootstrap.min.css'

// import 'bootstrap/dist/js/bootstrap.min.js
import 'bootstrap/js/src/modal.js'

import {onMounted,ref,computed} from "vue";
import {useUserStore} from "@/stores/userStore.js";
import {useRouter} from "vue-router";
import {getUserAddressAPI,getDistrictAPI} from "@/apis/address.js";
import {ElMessage} from "element-plus";



//获取地址和个人信息
const isShow=ref(0)
const Router=useRouter();

const detailAddress=ref("");

const showPerson=()=>{
  isShow.value=1;
}

const disappearShow=()=>{
  isShow.value=0;
}

const addressModal=ref(null)

const toStore=()=>{
  if(userStore?.user?.identity===2)
  {
    Router.push("/store/workplace")
  }
}
const toUserCenter=()=>{
  if(userStore?.user?.id)
  {
    Router.push("/user")
  }
  else Router.push("login")
}

const userStore=useUserStore();

const addressForm=ref({
  province:0,
  city:0,
  district:0,
  detail:''
})


const getAddress=async ()=>{
  if(localStorage.getItem("localAddress")!==null)
  {
    detailAddress.value=localStorage.getItem("localAddress")
    return detailAddress;
  }

  if(!userStore?.user?.id)
  {
    //说明没登录设置地址手动
  }
  else
  {
    //发送请求过去获得地址
    const res=await getUserAddressAPI(userStore.user.id)
    if(res.data.code===1)
    {
      //获取成功设置
      addressForm.value=res.data.data;
    }
    else
    {
      addressForm.value.province="还没有地址，添加地址"
    }
  }
}

const addressText=computed(()=>{
  if(detailAddress.value.trim()==="") return "设置地址"
  else return detailAddress;
},{
  deep:true
})

//省市区数组
const provinceArry=ref([])
const cityArry=ref([])
const districtArry=ref([])

const provinceRef=ref(null)
const cityRef=ref(null)
const districtRef=ref(null)

async function getDistrict(data)
{
  const res=await getDistrictAPI(data);
  console.log(res)
  return res
}

//分别改变省市区 的 代码
const changeProvince=async ()=>{
  //对应的城市是需要换掉的
  //得到了相对于的 省的id
  addressForm.value.city=0
  if(addressForm.value.province===0) return
  const res=await getDistrict({pid:addressForm.value.province,level:2})
  if(res.data.code===1)
  {
    //
    cityArry.value=res.data.data
  }
  else
  {
    ElMessage.info("出错了")

  }
}

const changeCity=async ()=>{
  //改变市
  addressForm.value.district=0
  if(addressForm.value.city===0) return
  const res=await getDistrict({pid:addressForm.value.city,level:3})
  if(res.data.code===1)
  {
    districtArry.value=res.data.data
  }
  else
  {
    ElMessage.info("出错了")
  }
}

const ensureAddress=()=>{
  //确认地址
  //如果本地有user 向后端发送请求去 需要写入到数据库
  // console.log(provinceRef.value.)

  if(userStore?.user?.id)
  {
    //TODO
  }
  //如果没有就存入到localstorage里面
  else
  {
    localStorage.setItem("localAddress",JSON.stringify(detailAddress.value))

  }
}

const chooseAddress=async ()=>{
  if(addressText.value==="设置地址"||addressText.value==="还没有地址，添加地址")
  {
    //获取到相关数据并且显示出来
    const res=await getDistrict({pid:1,level:1})
    console.log(res)
    if(res.data.code===1)
    {
      //获取成功
      provinceArry.value=res.data.data
    }
    else
    {
      ElMessage.info("出错了！");
      return ;
    }
    addressModal.value.click();

  }
}

onMounted(()=>{
  //获取到 地址 和 个人信息
  // setAddress();
  getAddress()


})
</script>

<style scoped>
/*@import 'node_modules/bootstrap/dist/css/bootstrap.css';*/

.nav
{
  height: 50px;
  background: #333333;
  display: flex;
  justify-content: space-between;
  min-width: 1240px;
}
.address
{
  color:white;
  margin-left: 15px;
  line-height: 50px;
  font-size: 18px;
}
ul
{
  display: flex;
  margin-right: 20px;
  padding-left: 0px;
}
li
{
  list-style: none;
  font-size: 15px;
  color: white;
  height: 20px;
  margin-top: 15px;
  padding-left: 25px;
  margin-right: 25px;
}
.avatar
{
  margin-top: 0px;
  display: flex;
  line-height: 50px;
  vertical-align: center;
}
.avatar img
{
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-top: 5px;
}
.avatar span
{
  margin-left: 20px;
}

li:nth-child(n+2)
{
  border-left: 1px solid white;
}
.choose
{
  height: 200px;
  width: 200px;
  background: white;
  position: absolute;
  z-index: 100;
  display: none;
  border-radius: 30px;
  box-shadow: 0 3px 8px rgb(0,0,0,0.2);
  /*transform: translateY(45px);*/
  transition: all 0.5s;
}
.choose ul
{
  display: inline;
}
.choose ul li
{
  color: black;

}
.show
{
  transform: translateY(45px);
  display: block;
}
.pcd
{
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
}
.pcd select
{
  width: 200px;
  margin: 0 auto;
  margin-bottom: 40px;
}

.mb-3 input
{
  width: 438px;
}
</style>