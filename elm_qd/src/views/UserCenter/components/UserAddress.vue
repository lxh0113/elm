<template>
<div class="addressBack">
  <div class="userAddress">
    <ul>
      <li v-for="(item,index) in addressList" :key="item.id">
        <AddressItem @changeAddressData="(i)=>changeAddressData(i)" @changeAddressList="(list)=>changeAddressList(list)" :index="index" :address="item">
        </AddressItem>
      </li>
      <li>
        <div class="addNewAddress" data-bs-toggle="modal" data-bs-target="#addressModal">
          <i class="bi bi-building-add"></i>
        </div>
      </li>
    </ul>
  </div>
</div>

  <!-- Modal -->
  <div class="modal fade" id="addressModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="addressModalTitle" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="addressModalTitle">{{modalTitle}}</h1>
          <button @click="setOriginModal" type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <div class="addressInfo">
            <div class="mb-3" style="margin-left: 15px;">
              <label for="addressName" class="form-label">名称</label>
              <input v-model="addressForm.receiverName" type="email" class="form-control" id="addressName" placeholder="姓名">
            </div>

            <div style="margin-left: 20px">称呼</div>
            <div style="margin-left: 15px;margin-top: 15px" class="form-check form-check-inline">
              <input v-model="addressForm.gender" class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="1">
              <label class="form-check-label" for="inlineRadio1">先生</label>
            </div>
            <div class="form-check form-check-inline">
              <input v-model="addressForm.gender" class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2" value="0">
              <label class="form-check-label" for="inlineRadio2">女士</label>
            </div>
            <div style="height: 15px"></div>
            <div class="mb-3" style="margin-left: 15px;">
              <label for="addressTelephone" class="form-label">电话</label>
              <input v-model="addressForm.telephone" type="email" class="form-control" id="addressTelephone" placeholder="13xxxxxxxxxx">
            </div>

          </div>
          <div style="margin-left: 20px;margin-top: 20px">地址</div>
          <div class="pcd">

            <select ref="provinceRef" class="form-select" aria-label="Default select example" v-model="addressForm.province" @change="changeProvince">
              <option :value="0" selected>省</option>
              <option v-for="item in provinceArry" :key="item.districtId" :value="item.districtId">{{item.district}}</option>
            </select>
            <select ref="cityRef" class="form-select" aria-label="Default select example" v-model="addressForm.city" @change="changeCity">
              <option :value="0">市</option>
              <option v-for="item in cityArry" :key="item.districtId" :value="item.districtId">{{item.district}}</option>
            </select>

            <select ref="districtRef" style="width: 438px" class="form-select" aria-label="Default select example" v-model="addressForm.district">
              <option :value="0">区</option>
              <option v-for="item in districtArry" :key="item.districtId" :value="item.districtId">{{item.district}}</option>
            </select>

            <div class="mb-3" style="margin-left: 15px;">
              <label for="exampleFormControlInput1" class="form-label">详细地址</label>
              <input v-model="addressForm.detail" type="email" class="form-control" id="exampleFormControlInput1" placeholder="吉首大学张家界校区">
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button @click="setOriginModal" type="button" class="btn btn-secondary" data-bs-dismiss="modal">关闭</button>
          <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="ensureAddress">{{modalButton}}</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import AddressItem from '@/views/UserCenter/components/AddressItem.vue'
import {ref,onMounted} from "vue";
import {deleteAddressAPI, getSingleDistrictAPI, getUserDefaultAddressAPI} from '@/apis/address.js'
import {useUserStore} from "@/stores/userStore";
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";
import {changeAddressAPI,getUserAddressAPI,getDistrictAPI,addUserAddressAPI} from "@/apis/address.js";

const addressList=ref([])
const userStore=useUserStore();
const modalTitle=ref("新建地址")
const modalButton=ref("新建")
let curIndex=0

//下面这个需要传递数据过来 有可能是新建 也有可能是编辑


const addressForm=ref({
  userId:userStore.user.id,
  receiverName:'',
  gender:0,
  identity:0,
  telephone:'',
  province:0,
  city:0,
  district:0,
  detail:'',
  isDefault:0
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

const ensureAddress=async ()=>{

  if(addressForm.value.receiverName.trim()==='')
  {
    ElMessage.error("你的名字为空")
    return
  }

  if(addressForm.value.province===0||addressForm.value.city===0||
  addressForm.value.district===0)
  {
    ElMessage.error("您的地址有错误")
    return;
  }

  if(addressForm.value.detail.trim()==='')
  {
    ElMessage.error("您的地址有错误")
    return;
  }

  if(addressForm.value.telephone.trim()===''||addressForm.value.telephone.trim().length!==11)
  {
    ElMessage.error("电话号码格式不正确")
    return;
  }

  if(!( /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(addressForm.value.telephone)))
  {
    ElMessage.error("电话号码格式不正确")
    return;
  }


  if(modalButton.value==="新建")
  {
    const res=await addUserAddressAPI(addressForm.value)

    if(res.data.code===1)
    {
      ElMessage.success("添加成功")
      addressList.value=res.data.data

    }
    else
    {
      ElMessage.error(res.data.msg)
    }

    setOriginModal()

    return
  }

  //修改的代码
  if((JSON.stringify(addressForm.value))===(JSON.stringify(addressList.value[curIndex])))
  {
    //如果这俩个对象相等
    ElMessage.error("您没有做出任何更改！")

    setOriginModal()
    return
  }

  const res=await changeAddressAPI(addressForm.value)

  if(res.data.code===1)
  {
    addressList.value=res.data.data
    ElMessage.success("修改成功")
  }
  else
  {
    ElMessage.error(res.data.msg)

  }

  setOriginModal()
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

const getCity=async ()=>{
  const res=await getDistrict({pid:addressForm.value.province,level:2})

  if(res.data.code===1)
  {
    cityArry.value=res.data.data
  }
  else
  {
    ElMessage.error(res.data.msg)
  }
}

const getDistrictNow=async ()=>{
  const res=await getDistrict({pid:addressForm.value.city,level:3})

  if(res.data.code===1)
  {
    districtArry.value=res.data.data
  }
  else
  {
    ElMessage.error(res.data.msg)
  }
}

const getAddress=async ()=>{
  const res=await getUserDefaultAddressAPI(userStore.user.id)

  if(res.data.code===1)
  {
    console.log(res)
    addressList.value=res.data.data
  }
  else
  {
    ElMessage.error(res.data.msg)
  }
}

const setOriginModal=()=>{
  modalTitle.value="新建地址"
  modalButton.value="新建"
}

const changeAddressList=(list)=>{
  addressList.value=list
}

const changeAddressData=async (index)=>{

  console.log(addressList.value[index])
  addressForm.value=addressList.value[index]
  await getCity()

  await getDistrictNow()

  addressForm.value=JSON.parse(JSON.stringify(addressList.value[index]))

  //点的是编辑按钮需要 写一个修改标题 和 文字
  modalTitle.value="修改地址"
  modalButton.value="修改"

  curIndex=index

}

onMounted(()=>{
  getAddress()
  getProvince()
})
</script>

<style scoped>
li
{
  list-style:none;
}
.addressBack
{
  margin-top: 70px;
  margin-left: 100px;
}
.userAddress ul
{
  display: flex;
  flex-wrap: wrap;
  justify-content: left;
  padding-left: 0px;
}
.userAddress ul li
{
  margin-right:50px;
  margin-bottom: 50px;
}
.addNewAddress
{
  position: relative;
  width: 600px;
  height: 300px;
  border:5px dotted #01b6fd;

  /*background: #fed6e3;*/
  /*box-shadow: 0 3px 8px rgb(0 0 0 / 20%);*/
  border-radius: 20px;
  transition: all 0.5s;
  /*text-align: center;*/
}
.addNewAddress:hover
{
  background: #eeeeee;
  color: #4e9fe7;
}
.addNewAddress:hover i
{
  font-size: 120px;
  transform: translateX(-20px) translateY(-20px);
}
.addNewAddress i
{
  font-size: 100px;
  color: #01b6fd;
  position: absolute;
  margin-top: 70px;
  margin-left: 250px;
  transition: all 0.5s;
}

.pcd
{
  margin-top: 20px;
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

.pcd select:nth-last-child
{
  width: 200px;
}

.mb-3 input
{
  width: 438px;
}
.mb-5
{
  margin-bottom: 0!important;
}

</style>