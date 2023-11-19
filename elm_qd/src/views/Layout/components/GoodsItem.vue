<template>
  <div class="bigBox" @click="to(good.id)">
    <div class="logoUrl">
      <img :src="good.logoUrl" alt="图片未加载">
    </div>
    <div class="rightText">
      <div class="title">
        <h1>{{good.name}}</h1>
      </div>
      <div class="details">
        <span class="star">4.8分</span>
        <span class="sales">月售100+</span>
      </div>
      <div class="category">
        <span class="categoryText">西餐</span>
        <span class="deliver">起送费：{{good.deliverStartFee}}</span>
      </div>
      <div class="desc">
        <span>{{good.description}}</span>
      </div>
    </div>
  </div>
</template>

<script setup>
//这里是传递参数
import {useRouter} from "vue-router";

const Router=useRouter();

defineProps({
  good:{
    type:Object
  }
})


const to=(id)=>{
  Router.push(`/menu/${id}`);
}
</script>

<style scoped>
.bigBox
{
  margin-bottom: 30px;
  display: flex;
  height: 200px;
  width: 600px;
  background: white;
  transform: translate3d(0, -3px, 0);
  box-shadow: 0 3px 8px rgb(0 0 0 / 20%);
  /*border-radius: 20px;*/
  overflow: hidden;
}
.bigBox:hover
{
  transform: translate3d(0, -6px, 0);
  box-shadow: 0 3px 8px rgb(0 0 0 / 40%);
}
.bigBox .logoUrl
{
  margin-left: 10px;
  margin-top: 10px;
  width: 180px;
  height: 180px;
  /*border-radius: 20px;*/
  overflow: hidden;
}
.bigBox .logoUrl img
{
  width: 180px;
  height: 180px;
  border-radius: 20px;
}
.rightText
{
  margin-left: 20px;
}
.rightText .title
{
  line-height: 60px;
  height: 60px;
  width: 100%;
}
.rightText .title h1
{
  margin-top: 0px;
  margin-bottom: 0px;
  font-weight: 700;
  line-height: 60px;
  font-size: 2em;
}
.details
{
  height: 30px;
}
.star
{
  color: #ff636b;
  font-weight: bold;
  font-size: 16px;
}
.sales
{
  margin-left:15px;
  font-size: 15px;
  color: #7a7a7a;
}
.category
{
  height: 30px;
}
.category .categoryText
{
  font-size: 15px;
  font-weight:bold;
  color: #ff636b;
}
.category .deliver
{
  margin-left: 15px;
  color: #ff770b;
}


.desc
{
  font-size: 15px;
  color: #7a7a7a;
  height: 80px;
}
</style>