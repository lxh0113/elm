<template>
<div class="productBac">
  <div class="leftImg">
    <img src="@/img/123.jpg" alt="">
  </div>
  <div class="rightText">
    <h3>薄荷奶绿</h3>
    <span>很好吃很好吃</span>
    <br>
    <span style="color: #ff636b;font-weight: bold;">月售200+ 4.9分</span>
    <br>
    <div class="priceText">
      <h5>￥14起</h5>
      <button class="chooseSize">
        选规格
      </button>
    </div>
  </div>
</div>
</template>

<script setup>

</script>

<style scoped>
h3,h5
{
  font-weight: bold;
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

  font-size: 23px;
}
.rightText span
{

  line-height: 30px;
}
.priceText
{
  height: 60px;
  display: flex;
  justify-content: space-between;
}
.priceText h5
{
  line-height: 60px;
  font-size: 16px;
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