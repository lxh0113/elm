<template>
<div class="bigBox">
    <div class="sliderBox">
      <el-carousel height="500px">
        <el-carousel-item v-for="item in imgUrl" :key="item.id">
          <img :src="item.url" alt="" />
        </el-carousel-item>
      </el-carousel>
    </div>
</div>
</template>

<script setup>
import {ref} from 'vue'

const imgUrl=ref([
  {id:1,url:'./src/img/slider/7.gif'},
  {id:2,url:'./src/img/slider/1.png'},
  {id:3,url:'./src/img/slider/2.png'},
  {id:4,url:'./src/img/slider/8.gif'},
  {id:7,url:'./src/img/slider/3.png'},
  {id:8,url:'./src/img/slider/6.jpg'},
  {id:10,url:'./src/img/slider/9.gif'}
])
const url=ref('./src/img/background.png');
</script>

<style scoped>
.bigBox
{
  background: #f5f5f5;
  width: 100%;
  height: 500px;
}
.sliderBox
{
  width: 1240px;
  height: 500px;
  margin: 0 auto;
}
img {
  width: 100%;
  height: 500px;
}
</style>