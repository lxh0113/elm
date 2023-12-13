<template>
<div class="userInfoBack">
  <div class="userInfoLeft">
    <ul>
      <li>
        <div>用户id</div>
      </li>
      <li>
        <div>用户昵称</div>
      </li>
      <li>
        <div>性别</div>
      </li>
      <li>
        <div>年龄</div>
      </li>
      <li>
        <div>电话</div>
      </li>
      <li>
        <div>邮箱</div>
      </li>
      <li>
        <div style="height: 200px">头像</div>
      </li>
      <div style="height: 100px;"></div>
    </ul>
  </div>
  <div class="userInfoRight">
    <ul>
      <li>
        <div class="userId" style="height: 100px;line-height: 100px">
          <span style="font-size: 20px">{{user.id}}</span>
        </div>
      </li>
      <li>
        <div class="userName" style="height: 100px;">
          <input type="text" v-model="user.nickname">
        </div>
      </li>
      <li>
        <div class="userGender" style="height: 100px;line-height: 100px;">
          <label><input type="radio" name="sex" value="1" v-model="user.gender">&nbsp;&nbsp;男</label>
          <label style="margin-left: 100px;"><input v-model="user.gender" type="radio" name="sex" value="0">&nbsp;&nbsp;女</label>
        </div>
      </li>
      <li>
        <div class="userAge" style="height: 100px;">
          <input type="number" max="100" min="0" v-model="user.age">
        </div>
      </li>
      <li>
        <div class="telephone" style="height: 100px">
          <input type="number" minlength="11" v-model="user.telephone">
        </div>
      </li>
      <li>
        <div class="userEmail" style="height: 100px">
          <input type="text" v-model="user.email">
        </div>
      </li>
      <li>
        <div class="avatar" style="height: 200px">
          <img :src="user.avatar"  alt="图片未加载">
        </div>
      </li>
      <li>
        <div class="buttonBox">
          <input type="file" ref="uploadInput" enctype="multipart/form-data" @change="uploadAvatar" name="fileUpload" style="display: none"/>
          <button @click="toUpload">上传头像</button>
          <button @click="changeInfo">修改信息</button>
        </div>
      </li>
    </ul>
  </div>
</div>
</template>

<script setup>
import AddressItem from '@/views/UserCenter/components/AddressItem.vue'
import {ref} from 'vue'
import {useUserStore} from "@/stores/userStore.js";
import {uploadImgAPI,uploadAvatarAPI} from "@/apis/upload.js";
import {storeToRefs} from 'pinia'
import {getUserInfoAPI} from '@/apis/login.js'
import {ElMessage} from "element-plus";
import {changeUserInfoAPI} from "@/apis/user.js";

//获取到个人信息

const uploadInput=ref(null)

const userStore=useUserStore();
const user=ref(JSON.parse(JSON.stringify(userStore.user)))

// let user={...userStore.user}

const address=ref({
  receiverName:'lxh0113',
  gender:0,
  telephone:'12345678901',
  province:'湖南省',
  city:'张家界市',
  district:'永定区',
  detail:'吉首大学张家界校区',
  isDefault:0
})

const toUpload=()=>{
  console.log(uploadInput)
  uploadInput.value.click()
}

const uploadAvatar=async ()=>{
  var file = uploadInput.value.files[0];
  var imageRegex = /\.(jpeg|jpg|png|gif)$/i;

  console.log(file)

  if(imageRegex.test(file.name))
  {
    var imageUrl = URL.createObjectURL(file);
    console.log("这是一个图片")

    let data = new FormData(); //创建form对象
    // data.append("userId",userStore.user.id)
    console.log('userId'+userStore.user.id)
    data.append("file",file)
    const res=await uploadAvatarAPI(userStore.user.id,data)

    console.log(res)

    if(res.data.code===1)
    {
      const res=await userStore.getUserById(userStore.user.id);

      if(res.success===1)
      {
        ElMessage.success("更新成功")
      }

    }
    else ElMessage.error(res.data.msg)

  }
  else ElMessage.error('您选择的不是一个图片！')
}

const changeInfo=async ()=>{
  //先判断是否有改变
  let areEqual = true;

  for (const key in user) {
    if (user[key] !== userStore.user[key]) {
      areEqual = false;
      break;
    }
  }

  if(areEqual===true)
  {
    ElMessage.error("您并没有做出任何修改！！");
  }
  else
  {
    //发送请求修改
    const res= await changeUserInfoAPI(user.value);

    if(res.data.code===1)
    {
      //正确的设置新的值
      const resData=await userStore.getUserById(user.value.id);
      if(resData.success)
      {
        //重新解构赋值
        // user={...userStore.user}
        user.value=JSON.parse(JSON.stringify(userStore.user))
        ElMessage.success("修改成功！");
      }
      else
      {
        ElMessage.error(res.data.msg)
      }
    }
    else {
      ElMessage.error("修改失败")
    }

  }
}
</script>

<style scoped>
.userInfoBack
{
  display: flex;
}
.userInfoLeft
{
  margin-top: 30px;
  margin-left: 30px;
  width: 300px;
  /*height: 1000px;*/
  border-right:2px solid #eee;
  /*background:pink;*/
}
.userInfoLeft ul
{
  padding-left: 0;
}

.userInfoLeft ul li
{
  list-style: none;
  text-align: center;
  font-size: 20px;
}
.userInfoLeft ul li div
{
  height: 100px;
  line-height: 100px;
  font-weight: 700;
}
.userInfoRight
{
  width: 100%;
  /*height: 1000px;*/
  margin-top: 30px;
  margin-left: 40px;
  /*background: #01b6fd;*/
}
.userInfoRight ul li
{
  list-style: none;
}
.userInfoRight input[type='text'],
.userInfoRight input[type='number']
{
  margin-top: 25px;
  outline: none;
  border: 2px solid #eee;
  padding-left: 30px;
  width:500px;
  height: 50px;
}
.userInfoRight img
{
  width: 150px;
  height: 150px;
  margin-top: 25px;
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
.buttonBox
{
  height: 100px;
}
.buttonBox button
{
  width: 150px;
  height: 50px;
  margin-right: 50px;
  margin-top: 25px;
  outline: none;
  background: #01b6fd;
  color: white;
  border-radius: 20px;
  border: 2px solid #eeeeee;
}
</style>