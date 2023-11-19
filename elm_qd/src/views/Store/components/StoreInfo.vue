<template>
  <div class="becomeStoreBack">
    <h1>店家信息</h1>
    <div class="form">
      <ul>
        <li>
          <div class="mb-3">
            <label for="storeName" class="form-label">店铺名称</label>
            <input type="email" style="width: 300px" class="form-control" v-model="storeData.store.name" id="storeName" placeholder="吉大奶茶店">
          </div>
        </li>
        <li>
          <div class="mb-3">
            <label for="storeLogo" class="form-label">店铺logo</label>
            <div class="storeLogo">
              <img @click="chooseStoreLogo" :src="storeData.store.logoUrl" alt="">
            </div>
            <input type="file" style="display: none" ref="storeLogoInput" @change="uploadStoreLogo" class="form-control" id="storeLogo" placeholder="">
            <button style="margin-top: 20px" @click="chooseStoreLogo" class="btn btn-primary">修改店铺logo</button>
          </div>
        </li>
        <li>
          <div class="mb-3">
            <label for="storeBack" class="form-label">店铺背景</label>
            <div class="storeEnv">
              <img @click="chooseStoreUrl" :src="storeData.store.storeUrl" alt="">
            </div>
            <input type="file" ref="storeUrlInput" @change="uploadStoreUrl" style="display:none;" class="form-control" id="storeBack" placeholder="">
            <button @click="chooseStoreUrl" style="margin-top: 20px" class="btn btn-primary">修改店铺背景</button>
          </div>
        </li>
        <li>
          <div class="mb-3">
            <label for="storeDesc" class="form-label">店家描述</label>
            <textarea type="email" class="form-control" v-model="storeData.store.description" id="storeDesc" placeholder="今天你喝奶茶了吗"></textarea>
          </div>
        </li>
        <li>
          <div class="mb-3">
            <label for="storeTel" class="form-label">电话号码</label>
            <input type="email" style="width: 300px" class="form-control" v-model="storeData.store.telephone" id="storeTel" placeholder="133xxxxxxxx">
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
              <select ref="provinceRef" @change="changeProvince" class="form-select" aria-label="Default select example" v-model="storeData.address.province">
                <option value="0" selected>省</option>
                <option v-for="item in provinceArry" :key="item.districtId" :value="item.districtId">{{item.district}}</option>
              </select>
              <select ref="cityRef" @change="changeCity" class="form-select" aria-label="Default select example" v-model="storeData.address.city">
                <option value="0" selected>市</option>
                <option v-for="item in cityArry" :key="item.districtId" :value="item.districtId">{{item.district}}</option>
              </select>
              <select ref="districtRef" class="form-select" aria-label="Default select example" v-model="storeData.address.district">
                <option value="0" selected>区</option>
                <option v-for="item in districtArry" :key="item.districtId" :value="item.districtId">{{item.district}}</option>
              </select>
            </div>
            <label style="font-weight: normal;">详细地址</label>
            <input type="email" class="form-control" v-model="storeData.address.detail" id="storeAddress" placeholder="吉首大学张家界校区">
          </div>
        </li>
        <li>
          <div class="mb-3">
            <label for="packageFee" class="form-label">打包费用</label>
            <input type="email" style="width: 300px" class="form-control" v-model="storeData.store.packageFee" id="packageFee" placeholder="2">
          </div>
        </li>
        <li>
          <div class="mb-3">
            <label @click="chooseStoreEnv(storeData.storeEnv.length)" class="form-label">店内环境(最多四张照片，最少一张)</label>
            <div class="storeEnv">
              <div class="pic" v-for="(item,index) in storeData.storeEnv">
                <img :src="item.url" @click="chooseStoreEnv(index)" alt="">
                <span class="delePic" @click="deleteStoreEnv(index)">&nbsp;X&nbsp;</span>
              </div>
            </div>
            <input type="file" ref="storeEnvInput" @change="uploadStoreEnv" style="display: none" class="form-control" id="storeEnv" placeholder="">
            <button @click="chooseStoreEnv(storeData.storeEnv.length)" class="btn btn-primary">修改店内环境</button>
          </div>
        </li>
      </ul>
      <button @click="changeStoreInfo" class="submitApp btn btn-primary">修改</button>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted,toRaw, computed,watch} from 'vue'
import {uploadImgAPI} from "@/apis/upload";
import {ElMessage} from "element-plus";
import {useUserStore} from "@/stores/userStore.js";
import {getDistrictAPI, getUserAddressAPI} from "@/apis/address";
// import {becomeStoreAPI} from "@/apis/store.js";
import {useStoreStore} from "@/stores/storeStore.js";
import {changeFlavorsAPI} from "@/apis/flavors";
import {changeStoreAPI} from "@/apis/store";

const userStore=useUserStore();
const storeLogoInput=ref(null)
const storeUrlInput=ref(null)
const storeEnvInput=ref(null)
const storeStore=useStoreStore();


let storeData=ref({
  store:{
    id:userStore.user.id,
    name:'123',
    logoUrl:'http://localhost:8081/img/elmlogo.jpg',
    storeUrl:'http://localhost:8081/img/e34dea44-b090-47de-a6a4-79743c8b398c.jpg',
    description:'',
    telephone:'',
    packageFee:'',
    category:'',
    status:0,
  },
  address:{
    province:0,
    city:0,
    district:0,
    detail:'',
  },
  storeEnv:[

  ]
})

// 分别改变省市区 的 代码
const changeProvince=async ()=>{
  //对应的城市是需要换掉的
  //得到了相对于的 省的id
  storeData.value.address.city=0
  storeData.value.address.district=0
  if(storeData.value.address.province===0) return
  const res=await getDistrict({pid:storeData.value.address.province,level:2})
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
  storeData.value.address.district=0
  if(storeData.value.address.city===0) return
  const res=await getDistrict({pid:storeData.value.address.city,level:3})
  if(res.data.code===1)
  {
    districtArry.value=res.data.data
  }
  else
  {
    ElMessage.info(res.data.msg)
  }
}

const getCity=async ()=>{

  const res=await getDistrictAPI({pid:storeData.value.address.province,level:2});
  console.log(res)
  if(res.data.code===1)
  {
    cityArry.value=res.data.data;
  }
  else
  {
    ElMessage.info(res.data.msg)
  }
}

const getDistrictNow=async ()=>{

  const res=await getDistrictAPI({pid:storeData.value.address.city,level:3});
  console.log(res)
  if(res.data.code===1)
  {
    districtArry.value=res.data.data;
  }
  else
  {
    ElMessage.info(res.data.msg)
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
    storeData.value.store.logoUrl=res
    ElMessage.success("上传成功")
  }
  else ElMessage.error("上传失败")
}

const uploadStoreUrl=async ()=>{
  let file=storeUrlInput.value.files[0];
  const res=await uploadImage(file);

  if(res!==0)
  {
    storeData.value.store.storeUrl=res;
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


// //省市区数组
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

  getCity()

  getDistrictNow()
}


const changeStoreInfo=async ()=>{

  // 首先判断是否已经修改了
  let data=await storeStore.getUserInfo();

  console.log(data)
  let changeData=JSON.parse(JSON.stringify(storeData.value))
  console.log(changeData)

  //判断data是否喝storeData的value里面数据相同
  if(JSON.stringify(data)===JSON.stringify(changeData))
  {
    ElMessage.error("您未做出改变！");
    return
  }

  if(changeData.store.name.trim()==='')
  {
    ElMessage.error("名称不能为空")
    return
  }

  if(changeData.store.storeUrl.trim()==='')
  {
    ElMessage.error("背景不能为空")
    return
  }

  if(changeData.store.logoUrl.trim()==='')
  {
    ElMessage.error("logo不能为空")
    return
  }

  if(changeData.store.description.trim()==='')
  {
    ElMessage.error("描述不能为空")
    return
  }

  if(changeData.store.telephone.trim()===''||
  changeData.store.telephone.trim().length!==11)
  {
    ElMessage.error("电话号码格式不对")
    return
  }

  if(!( /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(changeData.store.telephone.trim()))){
    ElMessage.error("手机号码有误，请重填");
    return
  }

  if(changeData.store.packageFee<0)
  {
    ElMessage.error("打包费用出错")
    return
  }

  if(changeData.address.province==="0"||
    changeData.address.city==="0"||
  changeData.address.district==="0"||
  changeData.address.detail.trim()==="")
  {
    ElMessage.error("地址有误")
    return
  }

  if(changeData.storeEnv.length<0||
     changeData.storeEnv.length>4)
  {
    ElMessage.error("店内环境图片只能是1-4张哦")
    return
  }

  console.log(changeData)
  // 修改信息
  const res=await changeStoreAPI(changeData);

  if(res.data.code===0)
  {
    ElMessage.error(res.data.msg);
  }
  else
  {
    await getStoreInfo()
    ElMessage.success("修改成功");
  }
}

//获取信息
const getStoreInfo=async ()=>{
  console.log("出错了")
  let data=await storeStore.setUserStore();
  storeData.value=JSON.parse(JSON.stringify(data))
}


onMounted(async ()=>{
  //获取到商家的信息
  console.log("123")
  await getStoreInfo()

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