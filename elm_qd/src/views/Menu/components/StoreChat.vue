<template>
  <div class="chatBigBox">
    <div class="overYBox">
        <div class="appearMoreBox">
          <span v-if="messagesListData.isEnd===1">显示更多消息</span>
          <span v-else>没有更多消息了</span>
        </div>
        <ul>
          <li v-for="item in messagesListData.messagesList">
            <div class="time">
              <span>{{formatDate(item.time)}}</span>
            </div>

            <div v-if="item.senderId!==userStore.user.id" class="leftMessageBox">
              <img :src="storeStore.store.storeUrl" alt="">
              <div class="centerBox">
                <p>{{item.content}}</p>
              </div>
            </div>

            <div v-else class="rightMessageBox">
              <div class="centerBox">
                <p>{{item.content}}</p>
              </div>
              <img :src="userStore.user.avatar" alt="">
            </div>
          </li>
        </ul>
    </div>
<!--    <hr>-->
    <div class="textareaBox">
      <textarea v-model="chatData.content" class="textarea">
      </textarea>
      <i class="bi bi-image"></i>
      <button @click="chatStore">发送</button>
    </div>
  </div>
</template>

<script setup>
import {ref, watch, onUnmounted, onMounted, h} from "vue";
// import {useWsStore} from "@/stores/wsStore";
import {useUserStore} from "@/stores/userStore";
import {ElMessage, ElNotification} from "element-plus";
import {useStoreStore} from "@/stores/storeStore";
import {useWsStore} from "@/stores/wsStore";
import {Constant} from '@/utils/index.js'
import {messageTitle} from "@/utils/MessageTitle";
import {useMessagesStore} from "@/stores/messagesStore";

const userStore=useUserStore()
const storeStore=useStoreStore()
const wsStore=useWsStore()
const messagesStore=useMessagesStore()

const messagesListData=ref({})

const chatData=ref({
  senderId:userStore.user.id,
  receiverId:storeStore.store.id,
  content:''
})

watch(()=>wsStore.message,()=>{
  console.log(wsStore.message)
  // console.log(wsStore.message.value)

  if(wsStore.message?.messageType===Constant.Private_Chat)
  {
    const message=wsStore.message.t
    console.log("收到消息了"+message.content)
    //存入进去
    messagesStore.addNewOneMessageById(message)

    getMessages()

    //滚动到底部

  }

},{
  deep:true
})

const chatStore=()=>{
  wsStore.sendMessage(Constant.Private_Chat,storeStore.store.id,chatData.value)
  chatData.value.content=""
}

const getMessages=async ()=>{
  let id=storeStore.store.id
  messagesListData.value=await messagesStore.getMessagesListById(id)
  await console.log(messagesListData.value)

  // 获取要滚动的DOM元素
  let container = document.querySelector('.overYBox');
  console.log(container.scrollHeight)
  // 将滚动条滚动到底部
  container.scrollTop = container.scrollHeight;

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

onMounted(async ()=>{
  wsStore.wsInit()

  //获取到消息来渲染
  await getMessages();



})

onUnmounted(()=>{
})
</script>

<style scoped>
.chatBigBox
{
  margin: 0 50px;
  background: #f5f5f5;
  height: 600px;
  border-radius: 20px;
}
.overYBox
{
  height: 400px;
  overflow-y: scroll;
}
.leftMessageBox,
.rightMessageBox
{
  display: flex;
  margin-bottom: 10px;
  /*vertical-align: center;*/
}
.leftMessageBox img,
.rightMessageBox img
{
  width: 70px;
  height: 70px;
  border-radius: 50%;
}
.leftMessageBox
{
  justify-content: left;
  margin-left: 10px;
}
.rightMessageBox
{
  justify-content: right;
  margin-right: 10px;
}
.rightMessageBox .centerBox,
.leftMessageBox .centerBox
{
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: left;
}
.leftMessageBox .centerBox
{
  /*margin-top: 10px;*/
  /*margin-top: 6px;*/
  /*margin-bottom: 6px;*/
  background: white;
  margin-left: 10px;
  padding: 0 20px;
  border-radius: 10px;
  margin-right: 100px;
}
.leftMessageBox .centerBox p
{
  margin-top: 10px;
  /*transform: translateY(50%);*/
}

.rightMessageBox .centerBox
{
  /*margin-top: 10px;*/
  /*margin-top: 6px;*/
  /*margin-bottom: 6px;*/
  background: lightskyblue;
  margin-right: 10px;
  padding: 0 20px;
  border-radius: 10px;
  margin-left: 100px;
  /*color:white;*/
}
.rightMessageBox .centerBox p
{
  margin-top: 10px;
}
.time
{
  height: 30px;
  line-height: 30px;
  color: #7a8289;
  display: flex;
  justify-content: center;
  /*width: 100%;*/
  /*margin: 0 auto;*/

  /*transform: translateX(-50%);*/
}

.textarea
{
  resize: none;
  box-sizing: border-box;
  margin-top: 0;
  /*padding-top: 0;*/
  width: 100%;
  height: 200px;
  border: 0;
  border-top: 1px solid #7a8289;
  background: #f5f5f5;
  padding: 40px 20px;
}
.textareaBox
{
  position: relative;
}
.textareaBox i
{
  position: absolute;
  top: 10px;
  left: 20px;
  font-size: 20px;
}
.textareaBox button
{
  position: absolute;
  right: 20px;
  bottom: 20px;
  padding: 10px 20px;
  border-radius: 10px;
  border: #e9e9e9;
  background: #e9e9e9;
  color: #4e9fe7;
  font-weight: bold;
  font-size: 15px;
}
.appearMoreBox
{
  display: flex;
  justify-content: center;
}
.appearMoreBox span
{
  line-height: 40px;
  color:#01b6fd;
  font-weight: bold;
  font-size: 16px;
  /*text-decoration: #509ee8;*/
}
ul
{
  padding-left: 0px;
}
</style>