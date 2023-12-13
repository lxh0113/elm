<template>
  <div class="header">
    <img src="@/img/logo.png" alt="">
    <ul>
      <li>
        <RouterLink active-class="active" :to="`/search`">水果</RouterLink>
      </li>
      <li>
        <RouterLink active-class="active" :to="`/search`">鲜花</RouterLink>
      </li>
      <li><RouterLink active-class="active" :to="`/search`">买药</RouterLink></li>
      <li><RouterLink active-class="active" :to="`/search`">奶茶</RouterLink></li>
      <li><RouterLink active-class="active" :to="`/search`">西餐</RouterLink></li>
      <li><RouterLink active-class="active" :to="`/search`">甜品</RouterLink></li>
      <li><RouterLink active-class="active" :to="`/search`">养生</RouterLink></li>
    </ul>

    <input v-model="seearchText" @keydown="toSearch($event)" type="text" placeholder="搜一搜" />

    <el-icon style="margin-top: 50px;font-size: 50px;margin-left: 30px;color: #02b7fb"><ShoppingCart /></el-icon>
  </div>
</template>

<script setup>
import {ref} from 'vue'
import {useRouter} from "vue-router";

const router=useRouter();
const seearchText=ref('')

const toSearch=(e)=>{
  console.log(e)
  if(e.code==='Enter')
  {
    //如果是等于回车事件
    router.push('/search'+'/'+seearchText.value)
  }
}
</script>

<style scoped>
.header
{
  width: 1240px;
  margin: 0 auto;
  height: 150px;
  display: flex;
  justify-content: space-evenly;
  background: white;
}
ul
{
  display: flex;
  margin-left: 30px;
}
li
{
  list-style: none;
  text-decoration: none;
  line-height: 150px;
  padding: 0 30px;
}
.header img
{
  width: 150px;
  height: 150px;
}
li a
{
  color: black;
  text-decoration: none;
}
li a:hover
{
  color: #02b7fb;
  border-bottom:1px solid #02b7fb;
}

input
{
  box-sizing: border-box;
  height: 30px;
  margin-top: 60px;
  outline: none;
  padding-left: 15px;
  border: 1px solid #02b7fb;
  border-radius: 10px;
  margin-left: 30px;

}

.active-class
{
  /*color: #fed6e3;*/
  color: #02b7fb;
  border-bottom:1px solid #02b7fb;
}
</style>