<template>
  <div class="backgroundBox">
    <div class="loginBox">
      <div class="preBox" :class="{toRight:isMove}">
        <h1 class="welcomeText">欢迎你</h1>
        <h3 class="englishText">welcome to here</h3>
        <div class="image">
          <img src="src/img/5.jpeg" alt="">
        </div>
      </div>
      <div class="left">
        <h1>注册</h1>
        <input type="text" @blur="verifyRegisterEmail" v-model="registerData.email" placeholder="请输入邮箱号">
        <div class="msg">{{errorMsg.reEmailMsg}}</div>
        <input type="text" @blur="verifyRegisterCaptcha" v-model="registerData.captcha" class="captchaInput" placeholder="请输入验证码">
        <button class="captcha" ref="reCaptchaButton" @click="getRegisterCaptcha">{{reCaptchaText}}</button>
        <div class="msg">{{errorMsg.reCaptchaMsg}}</div>
        <input type="password" @blur="verifyRegisterPassword" v-model="registerData.password" placeholder="请输入密码">
        <div class="msg">{{errorMsg.rePwdMsg}}</div>
        <input type="password" @blur="verifyRegisterEnsurePwd" v-model="registerData.ensurePwd" placeholder="确认密码">
        <div class="msg">{{errorMsg.reEnsurePwdMsg}}</div>
        <button class="register" @click="register">注册</button>
        <p @click="MoveLeft">已有账号？去登录</p>
      </div>
      <div class="right" v-if="isForget">
        <h1>登录</h1>
        <input type="text" @blur="verifyLoginAccount" v-model="loginData.account" placeholder="请输入账号">
        <div class="msg">{{errorMsg.LoAccountMsg}}</div>
        <input type="password" @blur="verifyLoginPassword" v-model="loginData.password" placeholder="请输入密码">
        <div class="msg">{{errorMsg.LoPwdMsg}}</div>
        <button class="loginButton" @click="login">登录</button>
        <div class="text">
          <span @click="MoveRight">没有账号？去注册</span>
          <span @click="toForget">忘记密码？</span>
        </div>
      </div>
      <div class="forgetPassword" v-else>
        <h1>忘记密码</h1>
        <input type="text" @blur="verifyForgetEmail" v-model="forgetData.email" placeholder="请输入邮箱">
        <div class="msg">{{errorMsg.FoEmailMsg}}</div>
        <input class="captchaInput" @blur="verifyForgetCaptcha" v-model="forgetData.captcha" type="text" placeholder="请输入验证码">
        <button class="captcha" ref="foCaptchaButton" @click="getForgetCaptcha">{{foCaptchaText}}</button>
        <div class="msg">{{errorMsg.FoCaptchaMsg}}</div>
        <button class="loginButton" @click="forgetPassword">登录</button>
        <p @click="toLogin">去登录</p>
      </div>
    </div>
  </div>

</template>

<script setup>
import {ref} from 'vue'
import {forgetPasswordAPI, getCaptchaAPI, loginAPI, registerAPI} from "@/apis/login.js";
import {ElMessage} from "element-plus";
import 'element-plus/theme-chalk/el-message.css'
import md5 from "md5";
import {useRouter} from 'vue-router'
import {useUserStore} from "@/stores/userStore.js";

const isMove=ref(0)
const isForget=ref(1)

const reCaptchaButton=ref(null)
const foCaptchaButton=ref(null)

const reCaptchaText=ref("获取验证码")
const foCaptchaText=ref("获取验证码")
const router=useRouter();

const userStore=useUserStore();

const errorMsg=ref({
  //这里是注册的提示
  reEmailMsg:"",
  reCaptchaMsg:"",
  rePwdMsg:"",
  reEnsurePwdMsg:"",
  //下面是登录的一些提示
  LoAccountMsg:"",
  LoPwdMsg:"",
  //注册的提示
  FoEmailMsg:"",
  FoCaptchaMsg:""
})

const loginData=ref({
  account:"4711798934",
  password:""
})

const registerData=ref({
  email:"",
  captcha:"",
  password:"",
  ensurePwd:""
})

const forgetData=ref({
  email:"",
  captcha:""
})

//下面是控制界面现实登录注册 以及 忘记密码
const MoveLeft=()=>{
  isMove.value=0;
}
const MoveRight=()=>{
  isMove.value=1;
}
const toForget=()=>{
  isForget.value=0;
}
const toLogin=()=>{
  isForget.value=1;
}


//登录 注册 忘记密码 获取验证码的方法
const login=async ()=>{
  //先判断是否正确
  if(verifyLoginAccount()===false)
  {
    ElMessage.error(errorMsg.value.LoAccountMsg)
    return ;
  }
  if(verifyLoginPassword()===false)
  {
    ElMessage.error(errorMsg.value.LoPwdMsg)
    return ;
  }

  const res=await userStore.getUser(loginData.value.account,md5(loginData.value.password))

  if(res.success)
  {
    ElMessage.success("登录成功")
    router.push("/")
  }
  else
  {
    console.log("到这里了")
    ElMessage.error(res.message)
  }
  // console.log(res)
  //
  // if(res.data.code===1)
  // {
  //   ElMessage.success("登录成功")
  //   //存储到userStore
  //
  //   router.push("/")
  // }
  // else ElMessage.error(res.data.msg);
  //
  // console.log(res)
}

const register=async ()=>{

  if(verifyRegisterEmail()===false)
  {
    ElMessage.error(errorMsg.value.reEmailMsg);
    return ;
  }
  if(verifyRegisterCaptcha()===false)
  {
    ElMessage.error(errorMsg.value.reCaptchaMsg);
    return ;
  }
  if(verifyRegisterPassword()===false)
  {
    ElMessage.error(errorMsg.value.rePwdMsg);
    return ;
  }
  if(verifyRegisterEnsurePwd()===false)
  {
    ElMessage.error(errorMsg.value.reEnsurePwdMsg)
    return ;
  }

  const res=await registerAPI({
    email:registerData.value.email,
    password:md5(registerData.value.password),
    captcha:registerData.value.captcha
  })

  //
  if(res.data.code===1)
  {
    ElMessage.success("注册成功！");
  }
  else ElMessage.error(res.data.msg);

}

const forgetPassword=async ()=>{

  if(verifyForgetEmail()===false)
  {
    ElMessage.error(errorMsg.value.FoEmailMsg);
    return ;
  }
  if(verifyForgetCaptcha()===false)
  {
    ElMessage.error(errorMsg.value.FoCaptchaMsg);
    return ;
  }

  const res=await forgetPasswordAPI({email:forgetData.value.email,captcha:forgetData.value.captcha})

  console.log(res)
  //
  if(res.data.code===1)
  {
    ElMessage.success("登录成功")
    router.push("/")
  }
  else ElMessage.error(res.data.msg);
}

const getRegisterCaptcha=async ()=>{

  if(verifyRegisterEmail()===false)
  {
    ElMessage.error("请填写正确格式的邮箱")
    return ;
  }

  //一分钟内不能重复获取
  if(reCaptchaButton.disable===true)
  {
    ElMessage.error("已在获取验证码！")
    return ;
  }
  reCaptchaButton.disable=true

  let i=60;
  let k=setInterval(()=>{
    reCaptchaText.value='还剩'+i+"秒"
    i--
    if(i===0)
    {
      clearInterval(k);
      reCaptchaText.value="获取验证码"
      reCaptchaButton.disable=false;
    }
  },1000)

  const res=await getCaptchaAPI(registerData.value.email)

  ElMessage.success("验证码获取成功")
}

const getForgetCaptcha=async ()=>{

  if(verifyForgetEmail()===false)
  {
    ElMessage.error("请填写正确格式的邮箱")
    return ;
  }

  //一分钟内不能重复获取
  if(foCaptchaButton.disable===true)
  {
    ElMessage.error("已在获取验证码！")
    return ;
  }
  reCaptchaButton.disable=true

  let i=60;
  let k=setInterval(()=>{
    foCaptchaText.value='还剩'+i+"秒"
    i--
    if(i===0)
    {
      clearInterval(k);
      foCaptchaText.value="获取验证码"
      foCaptchaButton.disable=false;
    }
  },1000)

  const res=await getCaptchaAPI(forgetData.value.email)

  if(res.data.code) ElMessage.success("验证码获取成功")

}

//下面是 校验的方法

//登录 模块
const verifyLoginAccount=()=>{
  //验证账号
  if(loginData.value.account.trim()==="")
  {
    errorMsg.value.LoAccountMsg="账号不能为空"
    return false;
  }
  else if(loginData.value.account.trim().length!==10)
  {
    errorMsg.value.LoAccountMsg="账号是10位"
    return false
  }
  else errorMsg.value.LoAccountMsg=""
  return true;
}

const verifyLoginPassword=()=>{
  //验证密码
  if(loginData.value.password.trim()==="")
  {
    errorMsg.value.LoPwdMsg="密码不能为空"
    return false;
  }
  else if(loginData.value.password.trim().length<8||loginData.value.password.trim().length>16)
  {
    errorMsg.value.LoPwdMsg="密码需要是8-16位";
    return false
  }
  else
  {
    const password = loginData.value.password;
    const hasLetter = /[a-zA-Z]/.test(password);
    const hasNumber = /[0-9]/.test(password);
    const hasSpecialChar = /[!@#$%^&*()_+{}\[\]:;<>,.?~\\-]/.test(password);

    if (!hasLetter) {
      errorMsg.value.LoPwdMsg = "密码必须包含至少一个字母";
      return false;
    } else if (!hasNumber) {
      errorMsg.value.LoPwdMsg = "密码必须包含至少一个数字";
      return false;
    } else if (!hasSpecialChar) {
      errorMsg.value.LoPwdMsg = "密码必须包含至少一个特殊字符";
    } else {
      // 密码满足所有要求
      errorMsg.value.LoPwdMsg = ""; // 清除错误消息
      return true;
    }
  }

}

//注册 模块
const verifyRegisterEmail=()=>{
  //验证注册的邮箱号
  const email = registerData.value.email.trim();
  const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

  if (email === "") {
    errorMsg.value.reEmailMsg = "邮箱不能为空";
    return false;
  } else if (!emailPattern.test(email)) {
    errorMsg.value.reEmailMsg = "请输入有效的邮箱地址";
    return false;
  } else {
    // 邮箱格式合法
    errorMsg.value.reEmailMsg = ""; // 清除错误消息
    return true;
  }
}

const verifyRegisterCaptcha=()=>{
  if(registerData.value.captcha.trim().length!==6)
  {
    errorMsg.value.reCaptchaMsg="验证码不正确"
    return false;
  }
  errorMsg.value.reCaptchaMsg=""
  return true
}

const verifyRegisterPassword=()=>{
  if(registerData.value.password.trim()==="")
  {
    errorMsg.value.rePwdMsg="密码不能为空"
    return false;
  }
  else if(registerData.value.password.trim().length<8||registerData.value.password.trim().length>16)
  {
    errorMsg.value.rePwdMsg="密码需要是8-16位";
    return false
  }
  else
  {
    const password = registerData.value.password;
    const hasLetter = /[a-zA-Z]/.test(password);
    const hasNumber = /[0-9]/.test(password);
    const hasSpecialChar = /[!@#$%^&*()_+{}\[\]:;<>,.?~\\-]/.test(password);

    if (!hasLetter) {
      errorMsg.value.rePwdMsg = "密码必须包含至少一个字母";
      return false;
    } else if (!hasNumber) {
      errorMsg.value.rePwdMsg = "密码必须包含至少一个数字";
      return false;
    } else if (!hasSpecialChar) {
      errorMsg.value.rePwdMsg = "密码必须包含至少一个特殊字符";
    } else {
      // 密码满足所有要求
      errorMsg.value.rePwdMsg = ""; // 清除错误消息
      return true;
    }
  }
}

const verifyRegisterEnsurePwd=()=>{
  if(registerData.value.ensurePwd===registerData.value.password)
  {
    errorMsg.value.reEnsurePwdMsg=""
    return true;
  }
  else
  {
    errorMsg.value.reEnsurePwdMsg="确认密码错误"
    return false;
  }
}

// 验证 忘记密码 这块
const verifyForgetEmail=()=>{
  const email = forgetData.value.email.trim();
  const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

  if (email === "") {
    errorMsg.value.FoEmailMsg = "邮箱不能为空";
    return false;
  } else if (!emailPattern.test(email)) {
    errorMsg.value.FoEmailMsg = "请输入有效的邮箱地址";
    return false;
  } else {
    // 邮箱格式合法
    errorMsg.value.FoEmailMsg = ""; // 清除错误消息
    return true;
  }
}

const verifyForgetCaptcha=()=>{
  if(forgetData.value.captcha.trim().length!==6)
  {
    errorMsg.value.FoCaptchaMsg="验证码不正确"
    return false;
  }
  errorMsg.value.FoCaptchaMsg=""
  return true
}

</script>

<style scoped>
p,
h1,
h3
{
  user-select: none;
}
*
{
  margin: 0;
  padding: 0;
}
.msg
{
  width: 350px;
  height: 30px;
  line-height: 30px;
  margin-left: 55px;
  color:red;
}
.backgroundBox
{
  background: linear-gradient(to right,#e8edea, #fed6e3);
  width:100%;
  height:100%;
  position:fixed;
  background-size:100% 100%;
  display: flex;
}
.loginBox
{
  margin:0 auto;
  margin-top: 200px;
  /*border-radius: 40px;*/
  width: 1000px;
  height: 500px;
  background-color: rgba(75, 81, 95, 0.1);
  box-shadow: 7px 7px 17px rgba(52, 56, 66, 0.5);
  display: flex;
  justify-content: space-evenly;
  overflow: hidden;
}
.preBox
{
  position: absolute;
  top:200px;
  width: 500px;
  transform: translateX(-50%);
  background: #fed6e3;
  transition: 0.5s ease-in-out;
}
.toRight
{
  transform:translateX(50%);
}
.left,
.right,
.forgetPassword
{
  flex: 1;
  height: 100%;
}
.welcomeText
{
  text-align: center;
  line-height: 100px;
  color: #b09fa8;
}
.englishText
{
  text-align: center;
  line-height: 100px;
  color: #fff;
}
.image
{
  height: 300px;
  text-align: center;
}
.image img
{
  height: 200px;
  width: 200px;
  margin-top: 30px;
}
.left h1
{
  color: #b09fa8;
  text-align: center;
  line-height: 100px;
}
.right h1
{
  color: #b09fa8;
  text-align: center;
  line-height: 200px;
}
.left input
{
  margin-left: 50px;
  width: 350px;
  height: 40px;

  padding-left: 20px;
  outline: none;
  border-radius: 10px;
  border: 2px solid #eee;
}
.left .captchaInput
{
  width: 200px;
}
.left .captcha
{
  margin-left: 30px;
  height: 40px;
  width: 120px;
  outline: none;
  border: 0px;
  border-radius: 10px;
  background: #413833;
  color: #b09fa8;
  font-size: 16px;
}
.left .register
{
  margin-left: 50px;
  height: 40px;
  width: 375px;
  outline: none;
  border: 0;
  border-radius: 10px;
  background: #413833;
  color: #b09fa8;
  font-size: 16px;
}
.left p
{
  text-align: center;
  margin-top: 15px;
  color: #b09fa8;
  cursor: pointer;
}
.right input
{
  margin-left: 50px;
  width: 350px;
  height: 40px;

  padding-left: 20px;
  outline: none;
  border-radius: 10px;
  border: 2px solid #eee;
}
.loginButton
{
  margin-top: 20px;
  margin-left: 50px;
  height: 40px;
  width: 375px;
  outline: none;
  border: 0;
  border-radius: 10px;
  background: #413833;
  color: #b09fa8;
  font-size: 16px;
}
.right .text
{
  margin-top: 15px;
  display: flex;
  justify-content: space-around;
}
.right .text span
{
  text-align: center;
  margin-top: 15px;
  color: #b09fa8;
  cursor: pointer;
}
.forgetPassword input
{
  margin-left: 50px;
  width: 350px;
  height: 40px;

  padding-left: 20px;
  outline: none;
  border-radius: 10px;
  border: 2px solid #eee;
}
.forgetPassword h1
{
  color: #b09fa8;
  text-align: center;
  line-height: 200px;
}
.forgetPassword .captchaInput
{
  width: 200px;
}
.forgetPassword .captcha
{
  margin-left: 30px;
  height: 40px;
  width: 120px;
  outline: none;
  border: 0;
  border-radius: 10px;
  /*background: #fed6e3;*/
  background: #413833;
  color: #b09fa8;
  font-size: 16px;
}
.forgetPassword p
{
  margin-left: 360px;
  margin-top: 25px;
  color: #b09fa8;
  cursor: pointer;
}

.invalid
{
  background: #eba6a3;
}
</style>