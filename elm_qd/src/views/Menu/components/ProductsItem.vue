<template>
<div class="productBac">
  <div class="leftImg">
    <img :src="good.url" alt="">
  </div>
  <div class="rightText">
    <h3 :title="good.name">{{good.name}}</h3>
    <span :title="good.description">{{good.description}}</span>
    <br>
    <span style="color: #ff636b;margin-top:10px;font-weight: bold;">月售200+ 4.9分</span>
    <br>
    <div class="priceText">
      <h5>￥{{good.price}}起</h5>
      <button @click="$emit('chooseSize',good.id)" class="chooseSize">
        选规格
      </button>
    </div>
  </div>

</div>

</template>

<script setup>
import 'bootstrap/js/src/modal.js'
import {ref,onMounted} from "vue";

defineProps({
  good:{
    type:Object
  }
})


onMounted(()=>{

})
</script>

<style scoped>
h3,h5
{
  font-weight: bold;
  margin-bottom: 0px;
}
.productBac
{
  width: 380px;
  height: 200px;
  margin-left: 25px;
  margin-bottom: 30px;
  background: white;
  display: flex;
  border-radius: 20px;
  box-shadow: 0 3px 8px rgb(0,0,0,0.2);
}
.leftImg img
{
  width: 150px;
  height: 150px;
  margin-top: 25px;
  margin-left: 25px;
}
.rightText
{
  margin-left: 15px;
  width: 180px;
  /*background: pink;*/
  height: 200px;
}
.rightText h3
{
  margin-top: 25px;
  line-height: 40px;
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
  font-size: 25px;
}
.rightText span
{
  color: #6c757d;
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
  line-height: 30px;
}
.priceText
{
  margin-top: 8px;
  height: 60px;
  display: flex;
  justify-content: space-between;
}
.priceText h5
{
  line-height: 60px;
  font-size: 20px;
  color: red;
}
.chooseSize
{
  font-weight: bold;
  font-size: 14px;
  padding: 2px 5px;
  height: 30px;
  margin-top: 15px;
  background: #4e9fe7;
  color: white;
  outline: none;
  border-radius: 10px;
  border: 0px;
}
</style>