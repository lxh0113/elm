<template>
<div class="fixedFooter">
  <div class="leftF">
    <div class="cart">
      <div class="iconDiv">
        这里是一个图标
      </div>
      <h2>￥12</h2>
    </div>
  </div>
  <div class="rightF">
    <button>去结算</button>
  </div>
</div>
</template>

<script setup>

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
.cart .iconDiv
{

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
</style>