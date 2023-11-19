<template>
<div class="bigBox">
  <div class="logo">
    <img @click="$router.push('/')" src="@/img/logo.png" alt="图片未加载">
    <span v-if="true" class="on">营业中</span>
    <span v-else class="off">关门中</span>
  </div>
  <div class="info">
    <img src="@/img/fox.png" alt="">
    <h3>KFC</h3>
  </div>
</div>
</template>

<script setup>

</script>

<style scoped>
.bigBox
{
  width: 100%;
  min-width: 1240px;
  height: 150px;
  background: white;
  display: flex;
  justify-content: space-between;
}
.logo
{
  display: flex;
  margin-left: 50px;
}
.logo img
{
  height: 150px;

}
.on
{
  /*line-height: 150px;*/
  background: #01b6fd;
  margin-top: 60px;
  height: 20px;
  color: white;
  padding:5px;
}
.off
{
  background: #7a7a7a;
  margin-top: 60px;
  height: 20px;
  color: white;
  padding:5px;
}
.info
{
  display: flex;
  margin-right: 50px;
}
.info img
{
  height: 50px;
  width: 50px;
  border-radius: 50%;
  margin-top: 50px;
}
.info h3
{
  line-height: 150px;
  margin-left: 20px;
}
</style>