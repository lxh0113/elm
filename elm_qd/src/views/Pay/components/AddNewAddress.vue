<template>
<div class="addBackBox">
  <div style="height: 1vh"></div>
  <span class="alertText">提交订单后,该收获地址将自动保存修改</span>
  <div style="height: 0.5vh"></div>
  <div class="addDetails">
    <div class="addLeft">
      <div style="height: 5vh;line-height: 5vh">地址</div>
      <div style="height: 5vh;line-height: 5vh">详细地址</div>
      <div style="height: 5vh;line-height: 5vh">收货人</div>
      <div style="height: 5vh;line-height: 5vh">性别</div>
      <div style="height: 5vh;line-height: 5vh">手机号</div>
    </div>
    <div class="addRight">
      <div class="PCD">
        <select ref="provinceRef" class="form-select" aria-label="Default select example" v-model="addressData.province" @change="changeProvince">
          <option :value="0" selected>省</option>
          <option v-for="item in provinceArry" :key="item.districtId" :value="item.districtId">{{item.district}}</option>
        </select>
        <select ref="cityRef" class="form-select" aria-label="Default select example" v-model="addressData.city" @change="changeCity">
          <option :value="0">市</option>
          <option v-for="item in cityArry" :key="item.districtId" :value="item.districtId">{{item.district}}</option>
        </select>

        <select ref="districtRef" class="form-select" aria-label="Default select example" v-model="addressData.district">
          <option :value="0">区</option>
          <option v-for="item in districtArry" :key="item.districtId" :value="item.districtId">{{item.district}}</option>
        </select>
      </div>

      <div class="mb-3" style="margin-left: 15px;width: 60vw;height: 5vh">
        <input v-model="addressData.detail" type="text" class="form-control" placeholder="吉首大学张家界校区">
      </div>

      <div class="mb-3" style="margin-left: 15px;width: 60vw;height: 5vh">
        <input v-model="addressData.receiverName" type="text" class="form-control" placeholder="李">
      </div>

      <div class="mb-3" style="margin-left: 15px;width: 60vw;height: 5vh;display: flex">
        <div class="form-check">
          <label for="woman">女士</label>
          <input class="form-check-input" type="radio" name="flexRadioDefault" id="woman">
        </div>
        <div class="form-check" style="margin-left: 3vw">
          <label for="man">先生</label>
          <input class="form-check-input" type="radio" name="flexRadioDefault" id="man">
        </div>
      </div>

      <div class="mb-3" style="margin-left: 15px;width: 60vw;height: 5vh">
        <input v-model="addressData.telephone" type="text" class="form-control">
      </div>
    </div>
  </div>
  <div style="height: 1vh"></div>
  <div class="bottomText" @click="toShowOtherAddress" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
    <span data-bs-toggle="modal" data-bs-target="#staticBackdrop">
      其他收货地址
    </span>
    <i data-bs-toggle="modal" data-bs-target="#staticBackdrop" class="bi bi-chevron-compact-right"></i>
  </div>
  <div style="height: 1vh"></div>
</div>
</template>

<script setup>
import {ref,onMounted,watch} from "vue";
import {getDistrictAPI, getSingleDistrictAPI} from "@/apis/address";
import {useUserStore} from "@/stores/userStore";
import {ElMessage} from "element-plus";

const emit=defineEmits([''])
const userStore=useUserStore()

const {addressForm} = defineProps(['addressForm'])

const addressData=ref({})


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
  return res
}

//分别改变省市区 的 代码
const changeProvince=async ()=>{
  //对应的城市是需要换掉的
  //得到了相对于的 省的id
  addressData.value.city=0
  if(addressData.value.province===0) return
  const res=await getDistrict({pid:addressData.value.province,level:2})
  if(res.data.code===1)
  {
    //
    cityArry.value=res.data.data
  }
  else
  {
    // ElMessage.info("出错了")

  }
}

const changeCity=async ()=>{
  //改变市
  addressData.value.district=0
  if(addressData.value.city===0) return
  const res=await getDistrict({pid:addressData.value.city,level:3})
  if(res.data.code===1)
  {
    districtArry.value=res.data.data
  }
  else
  {
    // ElMessage.info("出错了")
  }
}

const getProvince=async ()=>{
  //获取到相关数据并且显示出来
  const res=await getDistrict({pid:1,level:1})
  console.log(res)
  if(res.data.code===1)
  {
    //获取成功
    provinceArry.value=res.data.data
    await changeProvince()
  }
  else
  {
    ElMessage.info("出错了！");
  }
}

const getCity=async (province)=>{
  const res=await getDistrict({pid:province,level:2})
  console.log("此时的省",province)
  if(res.data.code===1)
  {
    cityArry.value=res.data.data
  }
  else
  {
    ElMessage.error(res.data.msg)
  }
}

const getDistrictNow=async (city)=>{

  console.log("此时的市",city)
  const res=await getDistrict({pid:city,level:3})

  if(res.data.code===1)
  {
    districtArry.value=res.data.data
  }
  else
  {
    ElMessage.error(res.data.msg)
  }
}

onMounted(async ()=>{

  // console.log("看看我是否又执行了重新开了一个组件")

  if(addressForm.id!=='')
  {
    await getProvince()

    await getCity(addressForm.province)

    await getDistrictNow(addressForm.city)

    addressData.value=addressForm
  }
  else
  {
    getProvince();
    addressData.value=addressForm
  }
})

</script>

<style scoped>
.addBackBox
{
  /*height: 30vh;*/
  width: 95vw;
  /*background: #fed6e3;*/
  margin: 2vh auto;
  font-size: 5vw;
  border-radius: 4vw;
  background: white;
  box-shadow: 7px 7px 17px rgba(52, 56, 66, 0.5);
}
.alertText
{
  font-size: 4vw;
  background: -webkit-linear-gradient(left,#f9f9f9,white);
  color:#b4b4b4;
  margin-left: 4vw;
  padding: 1vh 3vw;
  border-radius: 3vw;
  /*margin-top: 1vh;*/
}
.addDetails
{
  display: flex;
}
.addLeft
{
  margin-left: 3vw;
  font-size: 4vw;
}
.addRight
{
  flex:1;
  /*background: #fed6e3;*/
}
.PCD
{
  display: flex;
  height: 5vh;
  /*background: #01b6fd;*/
  justify-content: space-evenly;
}
.PCD select
{
  width: 20vw;
  height: 4.5vh;
  /*font-size: 2.5vw;*/
}
.bottomText
{
  color:#6c757d;
  font-size: 4vw;
  text-align: center;
}
.mb-3
{
  margin-bottom: 0!important;
}
.form-check
{
  color:#b4b4b4;
  margin-top: 0.5vh;
}
</style>