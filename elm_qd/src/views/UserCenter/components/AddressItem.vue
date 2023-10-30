<template>
<div class="addressItemBackground">
  <div class="empty" style="height: 5px"></div>
  <div class="userInfoHeader">
    <div class="info">
      <span>{{address.receiverName}}</span>
      <span>{{address.gender?"先生":"女士"}}</span>
    </div>
    <div class="tele">
      {{address.telephone}}
    </div>
  </div>
  <div class="addressBody">
    <div class="left">
      <div style="line-height: 50px;height: 50px">地址</div>
      <div style="height: 50px;line-height: 50px">详细地址</div>
    </div>
    <div class="right">
      <div style="line-height: 50px">{{address.province+address.city+address.district}}</div>
      <div style="line-height: 50px">{{address.detail}}</div>
    </div>
  </div>
  <div class="editFooter">
    <button>编辑</button>
    <button>删除</button>
  </div>
</div>
</template>

<script setup>
defineProps({
  address:{
    type:Object
  }
})

</script>

<style scoped>
.addressItemBackground
{
  /*background: pink;*/
  /*position: relative;*/
  height: 250px;
  width: 450px;
  box-shadow: 0 3px 8px rgb(0 0 0 / 20%);
  border-radius: 20px;
  transition: all 0.5s;
}
.addressItemBackground:hover
{
  transform: translateY(-10px);

}
.userInfoHeader
{
  margin-top: 10px;
  display: flex;
  font-size: 20px;
  line-height: 60px;
  height: 60px;
}
.info
{
  flex:7;
  margin-left: 20px;
}
.tele
{
  flex: 3;
  margin-right: 20px;
}
.addressBody
{
  display: flex;
  font-size: 20px;
}
.addressBody .left
{
  margin-left: 20px;
  flex: 3;
  height: 100px;
}
.addressBody .right
{
  margin-right: 20px;
  flex: 7;
  height: 100px;
}
.editFooter
{
  margin-top: 10px;
  display: flex;
  justify-content: right;
  height: 60px;
}
.editFooter button
{
  margin-right: 30px;
  height: 40px;
  width: 100px;
  margin-top: 10px;
  outline: none;
  color: white;
  background: #01b6fd;
  border-radius: 10px;
  border:2px solid #eeeeee;
}
</style>