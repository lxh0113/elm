<template>

  <div class="userMessageBigBox">
    <div class="messageLeft">
      <div class="title">聊天</div>
      <hr>
      <ul>
        <li v-for="(item,index) in leftFriends" :key="item.id" @click="toChat(index)">
          <img :src="item.avatar" alt="">
          <div>
            <div class="storeName">{{item.nickname}}</div>
            <!--            <div class="message">等你回来</div>-->
          </div>
        </li>
      </ul>
    </div>
    <div class="messageRight">
      <div class="title">聊天</div>
      <hr>
      <div v-if="flag" class="chatBigBox">

      </div>
      <div v-else class="chatBigBox">
        <div class="overYBox">
          <div class="appearMoreBox">
            <span v-if="messagesListData?.isEnd===1">显示更多消息</span>
            <span v-else>没有更多消息了</span>
          </div>
          <ul>
            <li v-for="item in messagesListData?.messagesList">
              <div class="time">
                <span>{{formatDate(item.time)}}</span>
              </div>

              <div v-if="item.senderId!==userStore.user.id" class="leftMessageBox">
                <img :src="currentChatPeople.avatar" alt="">
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
import {getLeftFriendsAPI} from "@/apis/message";

const userStore=useUserStore()
const storeStore=useStoreStore()
const wsStore=useWsStore()
const messagesStore=useMessagesStore()
const leftFriends=ref([])
const flag=ref(true)
const currentChatPeople=ref({})

const messagesListData=ref([ ])

const chatData=ref({
  senderId:userStore.user.id,
  receiverId:'',
  content:''
})

watch(()=>wsStore.message,async ()=>{
  console.log(wsStore.message)
  // console.log(wsStore.message.value)

  if(wsStore.message?.messageType===Constant.Private_Chat) {
    const message = wsStore.message.t
    console.log("收到消息了" + message.content)

    console.log("前" + messagesListData.value.messagesList.length)

    await messagesStore.addNewOneMessageById(message)

    console.log("后" + messagesListData.value.messagesList.length)

    messagesListData.value =await messagesStore.getMessagesListById(currentChatPeople.value.id)
  }
},{
  deep:true
})

const chatStore=()=>{
  chatData.value.receiverId=currentChatPeople.value.id
  wsStore.sendMessage(Constant.Private_Chat,currentChatPeople.value.id,chatData.value)
  chatData.value.content=""
}

const getMessages=async (id)=>{
  // let id=storeStore.store.id
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

const getLeftFriends=async ()=>{
  const res=await getLeftFriendsAPI(userStore.user.id);

  if(res.data.code===1)
  {
    leftFriends.value=res.data.data
  }
  else
  {
    ElMessage.error("获取出错")
  }
}

const toChat=async (index)=>{
  //
  chatData.value.content=''
  currentChatPeople.value=leftFriends.value[index]
  flag.value=false
  let receiverId=leftFriends.value[index].id;

  await getMessages(receiverId)
}

onMounted(async ()=>{
  wsStore.wsInit()

  //获取左边的数据来渲染
  getLeftFriends()

})

onUnmounted(()=>{
})
</script>

<style scoped>
.userMessageBigBox
{
  display: flex;
  box-sizing: border-box;
  /*padding: 30px 30px;*/
}
.messageLeft
{
  width: 400px;
  background: #f5f5f5;
  overflow-y: scroll;

}
.messageLeft ul
{
  padding-left: 0;
}
.messageLeft ul li
{
  /*background-color: #fed6e3;*/
  height: 100px;
  border-bottom: 1px solid #bfc0c1;
}
.messageLeft ul li img
{
  width: 70px;
  height: 70px;
  border-radius: 50%;
  margin-top: 15px;
  margin-left: 10px;
}
.messageRight
{
  flex:1;
  background: #f5f5f5;
}
.messageRight .title
{
  height: 50px;
  line-height: 60px;
  font-weight: bold;
  font-size: 20px;
  margin-left: 30px;
  background: #f5f5f5;
}
.messageLeft .title
{
  height: 50px;
  line-height: 60px;
  font-weight: bold;
  font-size: 20px;
  margin-left: 30px;
  background: #f5f5f5;
}
.messageLeft ul li
{
  display: flex;
}
.messageLeft ul li .storeName
{
  margin-top: 15px;
  line-height: 35px;
  margin-left: 10px;
  font-size: 17px;
  font-weight: bold;
}
.messageLeft ul li .message
{
  line-height: 35px;
  margin-left: 10px;
  color:gray;
}

.chatBigBox
{
  /*margin: 0 px;*/
  background: #f5f5f5;
  height: 700px;
  border-radius: 20px;
}
.overYBox
{
  height: 550px;
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
  /*width: 100%;*/
  /*margin: 0 auto;*/

  /*transform: translateX(-50%);*/
}
.time span
{
  margin-left: 50%;
  transform: translateX(-50%);
}

.textarea
{
  resize: none;
  box-sizing: border-box;
  margin-top: 0;
  /*padding-top: 0;*/
  width: 100%;
  height: 150px;
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