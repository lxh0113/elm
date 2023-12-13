<template>
<div class="remarkBox">
  <div style="height: 1vh"></div>
  <div class="note" data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-whatever="@mdo">
    <span>备注</span>
    <i class="bi bi-chevron-right"></i>
  </div>
  <div class="cookerNum">
    <span>餐具数量</span>
    <span>您已经默认需要餐具&nbsp;&nbsp;<i class="bi bi-chevron-right"></i></span>
  </div>

</div>
</template>

<script setup>

</script>

<style scoped>
.remarkBox
{
  /*height: 80vh;*/
  width: 95vw;
  /*background: #fed6e3;*/
  margin: 2vh auto;
  font-size: 5vw;
  border-radius: 4vw;
  background: white;
  margin-bottom: 12vh;
  box-shadow: 7px 7px 17px rgba(52, 56, 66, 0.5);
}
.note
{
  display: flex;
  justify-content: space-between;
  font-size: 4vw;
  line-height: 5vh;
}
.note span
{
  margin-left: 3vw;
}
.note i
{
  margin-right: 2vw;
}
.cookerNum
{
  display: flex;
  justify-content: space-between;
  font-size: 4vw;
  line-height: 5vh;
}
.cookerNum span:nth-child(1)
{
  margin-left: 3vw;
}
.cookerNum span:nth-child(2)
{
  margin-right: 2vw;
}
</style>