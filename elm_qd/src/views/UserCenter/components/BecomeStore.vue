<template>
<div class="becomeStoreBack">
  <h1>成为店家</h1>
  <div class="form">
    <ul>
      <li>
        <div class="mb-3">
          <label for="storeName" class="form-label">店铺名称</label>
          <input type="email" style="width: 300px" class="form-control" v-model="storeData.name" id="storeName" placeholder="吉大奶茶店">
        </div>
      </li>
      <li>
        <div class="mb-3">
          <label for="storeLogo" class="form-label">店铺logo</label>
          <div class="storeLogo">
            <img @click="chooseStoreLogo" :src="storeData.logoUrl" alt="">
          </div>
          <input type="file" style="display: none" ref="storeLogoInput" @change="uploadStoreLogo" class="form-control" id="storeLogo" placeholder="">
          <button style="margin-top: 20px" @click="chooseStoreLogo" class="btn btn-primary">上传店铺logo</button>
        </div>
      </li>
      <li>
        <div class="mb-3">
          <label for="storeBack" class="form-label">店铺背景</label>
          <div class="storeEnv">
            <img @click="chooseStoreUrl" :src="storeData.storeUrl" alt="">
          </div>
          <input type="file" ref="storeUrlInput" @change="uploadStoreUrl" style="display:none;" class="form-control" id="storeBack" placeholder="">
          <button @click="chooseStoreUrl" style="margin-top: 20px" class="btn btn-primary">上传店铺背景</button>
        </div>
      </li>
      <li>
        <div class="mb-3">
          <label for="storeDesc" class="form-label">店家描述</label>
          <textarea type="email" class="form-control" v-model="storeData.description" id="storeDesc" placeholder="今天你喝奶茶了吗"></textarea>
        </div>
      </li>
      <li>
        <div class="mb-3">
          <label for="storeTel" class="form-label">电话号码</label>
          <input type="email" style="width: 300px" class="form-control" v-model="storeData.telephone" id="storeTel" placeholder="133xxxxxxxx">
        </div>
      </li>
      <li>
        <div class="mb-3">
          <label for="storeCategory" class="form-label">店家分类</label>
          <select style="width: 200px" id="storeCategory" class="form-select" aria-label="Default select example">
            <option value="0" selected>分类</option>
          </select>
        </div>
      </li>
<!--      <div class="mb-3">-->
<!--        <label for="exampleFormControlInput1" class="form-label">配送费</label>-->
<!--        <input type="email" class="form-control" id="exampleFormControlInput1" placeholder="name@example.com">-->
<!--      </div>-->
      <li>
        <div class="mb-3">
          <label for="storeAddress" class="form-label">店家地址</label>
          <div class="address">
            <select ref="provinceRef" @change="changeProvince" class="form-select" aria-label="Default select example" v-model="storeData.province">
              <option value="0" selected>省</option>
              <option v-for="item in provinceArry" :key="item.districtId" :value="item.districtId">{{item.district}}</option>
            </select>
            <select ref="cityRef" @change="changeCity" class="form-select" aria-label="Default select example" v-model="storeData.city">
              <option value="0" selected>市</option>
              <option v-for="item in cityArry" :key="item.districtId" :value="item.districtId">{{item.district}}</option>
            </select>
            <select ref="districtRef" class="form-select" aria-label="Default select example" v-model="storeData.district">
              <option value="0" selected>区</option>
              <option v-for="item in districtArry" :key="item.districtId" :value="item.districtId">{{item.district}}</option>
            </select>
          </div>
          <label style="font-weight: normal;">详细地址</label>
          <input type="email" class="form-control" v-model="storeData.detail" id="storeAddress" placeholder="吉首大学张家界校区">
        </div>
      </li>
      <li>
        <div class="mb-3">
          <label for="packageFee" class="form-label">打包费用</label>
          <input type="email" style="width: 300px" class="form-control" v-model="storeData.packageFee" id="packageFee" placeholder="2">
        </div>
      </li>
      <li>
        <div class="mb-3">
          <label @click="chooseStoreEnv(storeData.storeEnv.length)" class="form-label">店内环境(最多四张照片，最少一张)</label>
          <div class="storeEnv">
            <div class="pic" v-for="(item,index) in storeData.storeEnv">
              <img :src="item" @click="chooseStoreEnv(index)" alt="">
              <span class="delePic" @click="deleteStoreEnv(index)">&nbsp;X&nbsp;</span>
            </div>
          </div>
          <input type="file" ref="storeEnvInput" @change="uploadStoreEnv" style="display: none" class="form-control" id="storeEnv" placeholder="">
          <button @click="chooseStoreEnv(storeData.storeEnv.length)" class="btn btn-primary">上传店内环境</button>
        </div>
      </li>
    </ul>
    <button @click="applyForBecomeStore" class="submitApp btn btn-primary">提交申请</button>
  </div>
</div>
</template>

<script setup>
import {ref, onMounted, computed,watch} from 'vue'
import {uploadImgAPI} from "@/apis/upload";
import {ElMessage} from "element-plus";
import {useUserStore} from "@/stores/userStore.js";
import {getDistrictAPI, getUserAddressAPI} from "@/apis/address";
import {becomeStoreAPI} from "@/apis/store.js";

const userStore=useUserStore();
const storeLogoInput=ref(null)
const storeUrlInput=ref(null)
const storeEnvInput=ref(null)

let storeData=ref({
  id:userStore.user.id,
  name:'',
  logoUrl:'http://localhost:8081/img/elmlogo.jpg',
  storeUrl:'http://localhost:8081/img/e34dea44-b090-47de-a6a4-79743c8b398c.jpg',
  description:'',
  telephone:'',
  packageFee:'',
  category:'',
  province:0,
  city:0,
  district:0,
  status:0,
  detail:'',
  storeEnv:[]
})

//分别改变省市区 的 代码
const changeProvince=async ()=>{
  //对应的城市是需要换掉的
  //得到了相对于的 省的id
  storeData.value.city=0
  storeData.value.district=0
  if(storeData.value.province===0) return
  const res=await getDistrict({pid:storeData.value.province,level:2})
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
  storeData.value.district=0
  if(storeData.value.city===0) return
  const res=await getDistrict({pid:storeData.value.city,level:3})
  if(res.data.code===1)
  {
    districtArry.value=res.data.data
  }
  else
  {
    ElMessage.info("出错了")
  }
}

const getCity=async ()=>{
  const res=await getDistrictAPI({pid:storeData.value.province,level:2});

  if(res.data.code===1)
  {
    cityArry.value=res.data.data;
  }
  else
  {
    ElMessage.info("出错了")
  }
}

const getDistrictNow=async ()=>{
  const res=await getDistrictAPI({pid:storeData.value.city,level:3});

  if(res.data.code===1)
  {
    districtArry.value=res.data.data;
  }
  else
  {
    ElMessage.info("出错了")
  }
}

if(localStorage.getItem("storeData"))
{
  storeData.value=JSON.parse(localStorage.getItem("storeData"))
  //设置select
  if(storeData.value.city!==0)
  {
    getCity();
  }
  if(storeData.value.city!==0)
  {
    getDistrictNow()
  }
}

let curStoreEnvIndex=0;

const chooseStoreLogo=()=>{
  storeLogoInput.value.click();
}

const chooseStoreUrl=()=>{
  storeUrlInput.value.click();
}

const chooseStoreEnv=(index)=>{
  if(index>=4)
  {
    ElMessage.error("图片不能提交超过4张！")
    return
  }
  curStoreEnvIndex=index;
  storeEnvInput.value.click()
}

const uploadImage=async (file)=>{
  var imageRegex = /\.(jpeg|jpg|png|gif)$/i;

  console.log(file)

  if(imageRegex.test(file.name))
  {
    var imageUrl = URL.createObjectURL(file);
    console.log("这是一个图片")

    let data = new FormData(); //创建form对象
    data.append('file',file);//通过append向form对象添加数
    const res=await uploadImgAPI(userStore.user.id,data)

    console.log(res)

    if(res.data.code===1)
    {
      return res.data.data;
    }
    else
    {
      ElMessage.error(res.data.msg)
      return 0;
    }
  }
  else ElMessage.error('您选择的不是一个图片！')
}

const uploadStoreLogo=async ()=>{
  //
  let file = storeLogoInput.value.files[0];
  const res=await uploadImage(file)
  console.log(res)
  if(res!==0)
  {
    storeData.value.logoUrl=res
    ElMessage.success("上传成功")
  }
  else ElMessage.error("上传失败")
}

const uploadStoreUrl=async ()=>{
  let file=storeUrlInput.value.files[0];
  const res=await uploadImage(file);

  if(res!==0)
  {
    storeData.value.storeUrl=res;
    ElMessage.success("上传成功");
  }
  else ElMessage.error("上传失败")
}

const uploadStoreEnv=async ()=>{
  //上传店内坏境
  let file=storeEnvInput.value.files[0];
  const res=await uploadImage(file)

  if(res!==0)
  {
    //判断是那个变了
    storeData.value.storeEnv[curStoreEnvIndex]=res;
    ElMessage.success("上传成功")
  }
  else ElMessage.error("上传失败")
}

const deleteStoreEnv=(index)=>{
  //删除数组
  console.log('点击了')
  let i=index
  storeData.value.storeEnv=storeData.value.storeEnv.filter((item,index)=>{
    if(i!==index) return item
  })
  // console.log(storeData.value.storeEnv)
}


//获取城市列表
const addressForm=ref({
  province:0,
  city:0,
  district:0,
  detail:''
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

const getProvince=async ()=>{
  const res=await getDistrict({pid:1,level:1})
  if(res.data.code===0)
  {
    ElMessage.error("服务器繁忙出错了！");
    return 0;
  }
  provinceArry.value=res.data.data;
}


const applyForBecomeStore=async ()=>{

  //先做出预判
  for (let key in storeData) {
    if (storeData.hasOwnProperty(key)) {
      let value = storeData[key];
      if (typeof value === 'string' && value.trim() === '')
      {
        return false; // 字符串为空
      }
      else if (typeof value === 'number' && value === 0)
      {
        return false; // 数字为0
      }
      else if (Array.isArray(value) && value.length === 0&&value.length<=4)
      {
        return false; // 数组为空
      }
    }
  }

  //申请成为商家
  console.log(storeData.value)
  const res=await becomeStoreAPI(storeData.value);

  if(res.data.code===0)
  {

    ElMessage.error(res.data.msg);
  }
  else
  {
    ElMessage.success("申请成功，待审核中...");
  }
}


watch(
    ()=>storeData.value,
    value => {
      localStorage.setItem("storeData",JSON.stringify(value))
    },
    {
      deep:true,immediate:true
    }
)

onMounted(()=>{
  //获取省市列表
  getProvince()
})


</script>

<style scoped>
.becomeStoreBack
{
  margin-top: 50px;
  margin-left: 80px;
  margin-right: 600px;
}
.becomeStoreBack h1
{
  font-weight: bold;
}
label
{
  font-weight: bold;
  font-size: larger;
}
.form ul
{
  padding-left: 0;
}
.form ul li
{
  list-style: none;
  margin-top: 40px;
}
.form ul li h3
{
  font-weight: bold;
}
.storeLogo img
{
  width: 100px;
  height: 100px;
}
.storeEnv img
{
  width: 300px;
  height: 200px;
}
.address
{
  display: flex;
  justify-content: space-between;
  margin-bottom: 30px;
}
.address select
{
  width: 200px;
}
.storeEnv
{
  display: flex;
  flex-wrap: wrap;
}
.pic
{
  position: relative;
  width: 300px;
  height: 200px;
  margin-right: 30px;
  margin-bottom: 30px;
}
.pic img
{
  width: 300px;
  height: 200px;
}
/*.del*/
/*{*/
/*  position: absolute;*/
/*  top:10px;*/
/*  right: 10px;*/
/*  font-size: 20px;*/
/*  color:#01b6fd;*/
/*  z-index: 2;*/
/*  width: 20px;*/
/*  height: 20px;*/
/*}*/
.storeEnv
{
  /*width: 1400px;*/
}
.delePic
{
  position: absolute;
  top:10px;
  right: 10px;
  font-weight: bold;
}
.delePic:hover
{
  color:black;
  background: white;
}
.submitApp
{
  margin-top: 30px;
  width: 100%;
  height: 50px;
  margin-bottom: 50px;
}
</style>