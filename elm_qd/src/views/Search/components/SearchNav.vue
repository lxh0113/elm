<template>
  <div class="searchNavBackground">
    <div class="searchNav">
      <div class="logo">
        <img @click="$router.push('/')" src="@/img/logo.png" alt="">
      </div>
      <div class="searchInput">
<!--        @input="fn($event.target.value)"-->
        <input ref="input" @keyup.enter="fn($event.target.value)" :value="searchText" type="text" placeholder="请输入搜索内容">
        <button @click="deal">搜索</button>
      </div>
      <div class="changeAddress">
        <el-icon style="color: #01b6fd;margin-left: 30px;font-size: 30px"><LocationInformation /></el-icon>
        <span class="address">切换地址</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref} from 'vue'

const text=ref('')
const input=ref(null)

const emit=defineEmits(['toSearch'])

const deal=()=>{
  console.log(input.value.value)
  fn(input.value.value)
}

const fn=(value)=>{
  // console.log(value.event)
  emit('toSearch',value)
}


const props=defineProps({
  searchText:String
})

</script>

<style scoped>
.searchNavBackground
{
  background: white;
  width: 100%;
  min-width: 1240px;
}
.searchNav
{
  width: 1240px;
  margin: 0 auto;
  height: 150px;
  display: flex;
  justify-content: space-between;
  background: white;
}
.searchNav img
{
  width: 150px;
  height: 150px;
}
.searchInput
{
  box-sizing: border-box;
  display: flex;
  text-align: center;
}
.searchInput input
{
  height: 40px;
  padding-left: 10px;
  width: 310px;
  margin-top: 55px;
  outline: none;
  border: 2px solid #01b6fd;
  border-bottom-left-radius: 20px;
  border-top-left-radius: 20px;
}
.searchInput button
{
  height: 40px;
  width: 100px;
  margin-top: 55px;
  outline: none;
  color: white;
  background-color: #01b6fd;
  border: 2px solid #01b6fd;
  border-bottom-right-radius: 20px;
  border-top-right-radius: 20px;
}
.changeAddress span
{
  line-height: 150px;
}
</style>