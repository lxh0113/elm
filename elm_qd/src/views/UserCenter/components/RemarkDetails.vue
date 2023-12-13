<template>
<div class="remarkBackBox">
  <div v-if="flag==0" class="noneRemarkBox">
    <ul v-if="noRemarkListData.length">
      <li v-for="(item,index) in noRemarkListData" :key="item">
        <div class="waitRemarkHeader">
          <span>{{ item.storeName }}</span>
          <span>{{formatDate(item.time)}}</span>
        </div>
        <div class="waitRemarkBody">
          <div class="leftGoods">
            <ul>
              <li v-for="goodsItem in item.itemsLists" :key="goodsItem">
                <div class="goodsDetails">
                  <img :src="goodsItem.url" alt="" :title="goodsItem.name+' '+goodsItem.flavors">
                  <span :title="goodsItem.name+' '+goodsItem.flavors">{{goodsItem.name}}&nbsp;{{goodsItem.flavors}}</span>
                </div>
              </li>
            </ul>
          </div>
          <div class="rightCount">
            {{ item.number }}
          </div>
        </div>
        <div class="buttonFooter">
          <button @click="setRemarkModal(index)" data-bs-toggle="modal" data-bs-target="#staticBackdrop">评价</button>
        </div>
      </li>
    </ul>
    <template v-else>
      <el-empty description="无数据" />
    </template>
  </div>
  <div v-else class="remarkBox">
    <ul v-if="remarkedListData.length">
      <li v-for="item in 5" :key="item">
        <div class="remarkedItem">
          <div class="remarkedHead">
            <div class="headLeft">
              <img src="http://localhost:8081/img/471179893472143920-b7eb-4431-af66-7bc2aa28c4c2.jpeg" alt="">
              <h6>茶白道 ></h6>
            </div>
            <span>2023/9/18</span>
          </div>
          <div class="remarkedStar">
            <span>星级：&nbsp;&nbsp;</span>
            <i class="bi bi-star-fill"></i>
            <i class="bi bi-star-fill"></i>
            <i class="bi bi-star-fill"></i>
            <i class="bi bi-star-fill"></i>
            <i class="bi bi-star-fill"></i>
          </div>
          <div class="remarkContent">
            <p>终于收到我需要的宝贝了，东西很好，价美物廉，谢谢掌柜的!说实在，这是我淘宝购物来让我最满意的一次购物。无论是掌柜的态度还是对物品，我都非常满意的。掌柜态度很专业热情，有问必答，回复也很快，我问了不少问题，他都不觉得烦，都会认真回答我，这点我向掌柜表示由衷的敬意，这样的好掌柜可不多。</p>
          </div>
          <div class="remarkedPic">
            <img v-for="item in 4" src="http://localhost:8081/img/471179893472143920-b7eb-4431-af66-7bc2aa28c4c2.jpeg" alt="">
          </div>
          <div class="remarkRespond">
            <p>网购了这么多年，这是我第一次这么认真的对待。我怀着忐忑的心情拿着手机盯着屏幕，迟迟不敢下手。我怕我一旦说出来，老板会觉得我在拍马屁，是不是想为了那几块钱的红包，又怕我的评价会误导后来的网友。为了写这次评价，我鼓起勇气喝了3瓶啤酒才有信心。为了不辜负老板对我的期望，我一定会客观，公正，如实的写下自己的评价:这是我这开心的一次网购，谢谢!</p>
          </div>
        </div>
      </li>
    </ul>
    <template v-else>
      <el-empty description="无数据" />
    </template>
  </div>
</div>

  <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="staticBackdropLabel">评论</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body" style="padding: 0 20px">
          <div class="storeInfoHeader">
            <img :src="remarkModalData.storeUrl" alt="">
            <span>{{ remarkModalData.storeName }}</span>
          </div>
          <div class="stars">
            <span>满意度</span>
              <span v-for="(item,index) in 5" :key="item">
                <i @click="changeStar(index+1)" v-if="index+1>remarkModalData.star" class="bi bi-star"></i>
                <i @click="changeStar(index+1)" v-else class="bi bi-star-fill"></i>
              </span>
          </div>
          <div class="remarkTextarea" >
            <label for="dishDescri" style="line-height: 55px;font-weight: bold;font-size: 16px">评价</label>
            <div>
              <textarea id="dishDescri" style="margin-top: 15px;height: 200px;resize: none;" type="text" class="form-control"
              placeholder="满意你就夸一夸，点击这里输入评价" v-model="remarkModalData.content"></textarea>
            </div>
          </div>

          <div class="remarkPicBox">
            <label for="remarkPicBox" style="line-height: 55px;font-weight: bold;font-size: 16px">图片</label>
            <ul>
              <li v-for="(item,index) in remarkModalData.images">
                <div class="remarkedImages">
                  <img @click="chooseImg(index)" :src="item" alt="">
                  <i @click="deleteImg(index)" class="bi bi-x-circle-fill"></i>
                </div>
              </li>
              <li>
                <i @click="chooseImg(remarkModalData.images.length)" class="bi bi-plus-square-dotted"></i>
                <input type="file" ref="remarkedImg" @change="toUploadImg" style="display: none" class="form-control" id="remarkPicBox" placeholder="">
              </li>
            </ul>
          </div>

        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
          <button @click="publishRemark" type="button" class="btn btn-primary" data-bs-dismiss="modal">发布</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted, watch} from 'vue'
import {useRouter,useRoute} from "vue-router";
import {getRemarksOrdersAPI} from '@/apis/orders.js'
import {useUserStore} from "@/stores/userStore";
import {ElMessage} from "element-plus";
import {getRemarkedListAPI} from '@/apis/comments.js'
import {uploadImgAPI} from "@/apis/upload.js";
import {useWsStore} from "@/stores/wsStore";
import {Constant} from "@/utils";

const router=useRouter()
const route=useRoute()
const remarkedImg=ref()
const flag=ref(route.params.status)
const stars=ref(1)
const noRemarkListData=ref([])
const remarkedListData=ref([])
const userStore=useUserStore()
const pageData=ref({})
const wsStore=useWsStore()
const remarkModalData=ref({
  star:5,
  storeId:'',
  ordersId:'',
  content:'',
  senderId:'',
  receiverId:'',
  storeUrl:'',
  images:[],
  storeName:''
})

let curImageIndex=0

const changeStar=(index)=>{
  remarkModalData.value.star=index
  // stars.value=index
}

watch(()=>router.currentRoute,()=>{
  flag.value=route.params.status
  console.log(flag.value)
},{
  deep:true
})

const init=async ()=>{
  console.log("flag"+flag.value)
  if(flag.value==0)
  {
    //未评价
    const res=await getRemarksOrdersAPI(userStore.user.id,4,1);
    if(res.data.code===1)
    {
      console.log(res)
      pageData.value=res.data.data
      noRemarkListData.value=res.data.data.records
    }
    else
    {
      ElMessage.error("请检查网络连接")
    }
  }
  else
  {
    // 已经评价
    const res=await getRemarkedListAPI({userId:'',current:1})
    pageData.value=res.data.data
    remarkedListData.value=res.data.data.records
  }
}

const setRemarkModal=(index)=>{
  remarkModalData.value={
    star:5,
    storeId:noRemarkListData.value[index].storeId,
    ordersId:noRemarkListData.value[index].id,
    content:'',
    senderId:userStore.user.id,
    receiverId:noRemarkListData.value[index].storeId,
    storeUrl:noRemarkListData.value[index].storeUrl,
    storeName:noRemarkListData.value[index].storeName,
    images:[]
  }
}

const uploadImage=async (file)=>{
  var imageRegex = /\.(jpeg|jpg|png|gif)$/;

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

const toUploadImg=async ()=>{
  //上传店内坏境
  let file=remarkedImg.value.files[0];
  const res=await uploadImage(file)

  if(res!==0)
  {
    //判断是那个变了
    if(remarkModalData.value.images.length===curImageIndex) remarkModalData.value.images.push(res);
    else remarkModalData.value.images[curImageIndex]=res

    ElMessage.success("上传成功")
  }
  else ElMessage.error("上传失败")
}

const chooseImg=(index)=>{
  curImageIndex=index
  remarkedImg.value.click()
}

const deleteImg=(index)=>{
  let i=index
  remarkModalData.value.images=remarkModalData.value.images.filter((item,index)=>{
    if(i!==index) return item
  })
}

const publishRemark=async ()=>{
  remarkModalData.value.images=JSON.stringify(remarkModalData.value.images)

  let data={
    storeId:remarkModalData.value.storeId,
    ordersId:remarkModalData.value.ordersId,
    content:remarkModalData.value.content,
    senderId:remarkModalData.value.senderId,
    receiverId:remarkModalData.value.receiverId,
    star:remarkModalData.value.star,
    images:remarkModalData.value.images
  }

  wsStore.sendMessage(Constant.People_Remark,data.receiverId,data)
}

const formatDate=(time)=>{
  const date=new Date(time)
  const year = date.getFullYear();
  let month = date.getMonth() + 1;
  let day = date.getDate();
  let hours = date.getHours();
  let minutes = date.getMinutes();
  let seconds = date.getSeconds();

  // 在月、日、小时、分钟、秒数小于10时，在前面补0
  month = month < 10 ? '0' + month : month;
  day = day < 10 ? '0' + day : day;
  hours = hours < 10 ? '0' + hours : hours;
  minutes = minutes < 10 ? '0' + minutes : minutes;
  seconds = seconds < 10 ? '0' + seconds : seconds;

  const formattedDate = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
  return formattedDate;
}

watch(()=>wsStore.message,()=>{
  console.log(wsStore.message)

  if(wsStore.message?.messageType===Constant.People_Remark)
  {
    //收到了发布评论的消息
    const message=wsStore.message.t

    if(message.senderId===userStore.user.id)
    {
      //说明是自己发布的
      ElMessage.success("评论发布成功")
    }

  }

},{
  deep:true
})

onMounted(()=>{
  wsStore.wsInit()

  console.log(route.params.status)

  init()
})
</script>

<style scoped>
.noneRemarkBox
{
  /*height: 600px;*/
  width: 100%;
  /*background-color: #fed6e3;*/
}
ul
{
  padding-left: 0;
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  align-items: flex-start;
  padding-bottom: 30px;
}
ul li
{
  /*flex:45%;*/
  /*width: 660px;*/
  flex:80%;
  /*border:1px dotted #6c757d;*/
  box-shadow: 0 3px 8px rgb(0,0,0,0.2);
  border-radius: 20px;
  /*background-color: #fed6e3;*/
  margin-top: 30px;
}
ul li
{
  margin: 0 50px;
  margin-top: 30px;
}
/*ul li:nth-child(even)*/
/*{*/
/*  margin-right: 50px;*/
/*}*/
/*ul li:nth-child(odd)*/
/*{*/
/*  margin-left: 50px;*/
/*}*/

.noneRemarkBox ul li
{
  /*width: 1;*/
}
.waitRemarkHeader
{
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
  font-weight: bold;
}
.waitRemarkHeader span
{
  font-size: 17px;
  margin-left: 30px;
  margin-right: 30px;
}
.waitRemarkBody
{
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
  color:#8a8a8a;
}
.waitRemarkBody .leftGoods
{
  /*flex:1;*/
  margin-right: 50px;
}
.waitRemarkBody .leftGoods ul
{
  display: flex;
  flex-wrap: nowrap;
  /*flex-direction: row;*/
  justify-content: left;
  padding-left: 0;
  margin-left: 30px;
  /*background-color: #01b6fd;*/
  max-width: 1000px;
  height: 140px;
  overflow-y: hidden;
  overflow-x: scroll;
  scroll-behavior: smooth;
  /*justify-content: left;*/
}
.waitRemarkBody .leftGoods ul li
{
  box-shadow: none;
  border-radius: 0px;
  margin-top: 0px;
  margin-right: 10px;
  margin-left: 0px;
  color:black;
  /*background-color: #6c757d;*/
}
.waitRemarkBody .leftGoods ul li .goodsDetails
{
  width: 100px;
  height: 120px;
  /*background: #01b6fd;*/
}
.goodsDetails span
{
  /*text-overflow: ellipsis;*/
  /*overflow: hidden;*/
  /*word-break: break-all;*/
  /*white-space: nowrap;*/
}
.waitRemarkBody .leftGoods ul li img
{
  width: 100px;
  height: 100px;
}
.waitRemarkBody .rightCount
{
  margin-right: 30px;
  line-height: 120px;
}
.buttonFooter
{
  display: flex;
  justify-content: right;
}
.buttonFooter button
{
  margin-bottom: 30px;
  margin-right: 30px;
  width: 100px;
  height: 40px;
  border-radius: 20px;
  border: 2px solid #01b6fd;
  background-color: white;
  color:#01b6fd;
  font-weight: bold;
}
.buttonFooter button:hover
{
  background-color: #f5f5f5;
}

.remarkBox ul li
{
  /*height: 300px;*/
}
.remarkedHead
{
  /*background-color: #01b6fd;*/
  margin-top: 20px;
  margin-left:50px;
  display: flex;
  height: 60px;
  justify-content: space-between;
  /*vertical-align: center;*/
}
.remarkedHead .headLeft
{
  display: flex;
  justify-content: left;
}
.remarkedHead h6
{
  margin-left: 20px;
  line-height: 60px;
  font-weight: bold;
  /*vertical-align: center;*/

}
.remarkedHead img
{
  width: 60px;
  height: 60px;
  border-radius: 10px;
}
.remarkedHead span
{
  line-height: 60px;
  margin-right: 50px;
  color:gray;
}
.remarkedStar
{
  display: flex;
  margin-left: 50px;
  height: 30px;
  /*background-color: #509ee8;*/
  line-height: 30px;
  font-weight: bold;
}
.remarkedStar i
{
  margin-right: 5px;
  color:#509ee8;
}
.remarkContent
{
  margin-left: 50px;
  text-indent: 2em;
  margin-right: 50px;
}
.remarkedPic
{
  display: flex;
  margin-left: 50px;
  margin-bottom: 10px;
}
.remarkedPic img
{
  width: 200px;
  height: 200px;
  margin-right: 10px;
}
.remarkRespond
{
  box-sizing: border-box;
  padding: 10px;
  margin-left: 50px;
  margin-right: 50px;
  text-indent: 2em;
  background: #f5f5f5;
  margin-bottom: 20px;
  border-radius: 20px;
}

.storeInfoHeader
{
  margin-top: 20px;
  display: flex;
  justify-content: left;
}
.storeInfoHeader img
{
  height: 70px;
  width: 70px;
}
.storeInfoHeader span
{
  margin-left: 20px;
  line-height: 70px;
  font-weight: bold;
  font-size: 16px;

}
.stars
{
  height: 80px;
  line-height: 80px;
}
.stars span{
  font-size: 18px;
  font-weight: bold;
  margin-right: 20px;
}
.stars i
{
  margin-left: 20px;
  font-size: 1.3rem;
  color:#01b6fd;
}
.remarkTextarea
{
  display: flex;
}
.remarkTextarea div
{
  margin-left: 20px;
  flex:1;
}
.remarkPicBox
{
  display: flex;
}
.remarkedPic label{
  min-width: 40px;
}
.remarkPicBox ul
{
  flex:1;
  display: flex;
  margin-top: 20px;
  justify-content: left;
  /*margin-left: 20px;*/
  flex-wrap: wrap;
}
.remarkPicBox ul li>i
{
  font-size: 8rem;
}
.remarkPicBox ul li>i:hover
{
  color: #01b6fd;
}
.remarkPicBox ul
{
  display: flex;
  flex-wrap: wrap;
  justify-content: left;
}
.remarkPicBox ul li
{
  /*flex:1;*/
  display: flex;
  box-shadow: none;
  width: 150px;
  height: 150px;
  margin-right: 20px;
  margin-bottom: 20px;
  border-radius: 0px;
  margin-top: 0px;
}
.remarkPicBox ul li .remarkedImages
{
  /*flex:1;*/
  position: relative;
  background-color: #01b6fd;
  width: 150px;
  height: 150px;
}
.remarkPicBox ul li .remarkedImages img
{
  width: 150px;
  height: 150px;
}
.remarkPicBox ul li .remarkedImages i
{
  position: absolute;
  display: none;
  right: -13px;
  top:-10px;
  color: #01b6fd;
  font-size: 20px;
}
.remarkPicBox ul li .remarkedImages:hover i
{
  display: block;
}
.remarkPicBox ul li .remarkedImages i:hover
{
  color: #6c757d;
}
</style>