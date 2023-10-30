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
      <li>
        <div>地址+</div>
      </li>
    </ul>
  </div>
  <div class="userInfoRight">
    <ul>
      <li>
        <div class="userId" style="height: 100px;line-height: 100px">
          <span style="font-size: 20px">{{userStore?.user?.id}}</span>
        </div>
      </li>
      <li>
        <div class="userName" style="height: 100px;">
          <input type="text" v-model="userStore.user.nickname">
        </div>
      </li>
      <li>
        <div class="userGender" style="height: 100px;line-height: 100px;">
          <label><input type="radio" name="sex" value="1" v-model="userStore.user.gender">&nbsp;&nbsp;男</label>
          <label style="margin-left: 100px;"><input v-model="userStore.user.gender" type="radio" name="sex" value="0">&nbsp;&nbsp;女</label>
        </div>
      </li>
      <li>
        <div class="userAge" style="height: 100px;">
          <input type="number" max="100" min="0" v-model="userStore.user.age">
        </div>
      </li>
      <li>
        <div class="telephone" style="height: 100px">
          <input type="number" minlength="11" v-model="userStore.user.telephone">
        </div>
      </li>
      <li>
        <div class="userEmail" style="height: 100px">
          <input type="text" v-model="userStore.user.email">
        </div>
      </li>
      <li>
        <div class="avatar" style="height: 200px">
          <img :src="userStore.user.avatar" alt="图片未加载">
        </div>
      </li>
      <li>
        <div class="buttonBox">
          <input type="file" name="fileUpload"/>
          <button>修改信息</button>
        </div>
      </li>
      <li>
        <div class="userAddress">
          <ul>
            <li v-for="item in 4" :key="item">
              <AddressItem :address="address">
              </AddressItem>
            </li>
          </ul>
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
//获取到个人信息

const userStore=useUserStore();


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