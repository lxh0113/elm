<template>
<div class="fixedFooter">
  <div class="leftF">
    <div class="cart">
      <div class="iconDiv">
        <i class="bi bi-cart3" ></i>
        <span>{{shopListStore.getSum()}}</span>
      </div>
      <h2>￥{{shopListStore.getTotalPrice()}}</h2>
    </div>
  </div>
  <div class="rightF">
    <button data-bs-toggle="offcanvas" data-bs-target="#offcanvasBottom" aria-controls="offcanvasBottom">去结算</button>
  </div>
</div>



</template>

<script setup>
// import IconDocumentation from "@/components/icons/IconDocumentation.vue";
import {useShopListStore} from "@/stores/shopListStore";

const shopListStore=useShopListStore();
</script>

<style scoped>
.fixedFooter
{
  position: fixed;
  bottom: 0;
  height: 120px;
  display: flex;
  justify-content: space-around;
  width: 100%;
  /*min-width: 1240px;*/
  background: white;
}
.leftF
{
  margin-left: 50px;
}
.rightF
{
  margin-right: 50px;
}
.cart
{
  display: flex;
  line-height: 120px;
}
.iconDiv
{
  width: 120px;
  height: 120px;
  /*background: #01b6fd;*/
  position: relative;
}
.cart h2
{
  color: red;
  font-size: 30px;
  line-height: 120px;
}
.rightF button
{
  padding: 10px 40px;
  margin-top: 40px;
  border-radius: 20px;
  font-size: 20px;
  outline: none;
  background: #4e9fe7;
  color: white;
  border: 2px solid #eeeeee;
}

.iconDiv i
{
  font-size: 80px;
  color: #4e9fe7;
  /*font-weight: bold;*/
}
.iconDiv span
{
  box-sizing: border-box;
  /*padding-left: 4px;*/
  text-align: center;
  position: absolute;
  top:20px;
  right: 30px;
  background: red;
  border-radius: 50%;
  line-height: 30px;
  color: white;
  height: 30px;
  width: 30px;
}
</style>