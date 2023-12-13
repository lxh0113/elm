<template>
<div class="contentBack" @click="$router.push(`/menu/${store.store.id}`)">
  <div style="height: 20px"></div>
  <div class="contentHeader">
    <div class="leftAvatar">
      <img :src="store.store.storeUrl" alt="图片未加载">
    </div>
    <div class="rightText">
      <h3 style="height: 40px">{{store.store.name}}</h3>
      <div class="contentDetails">
        <span>{{store.store.star}}分</span>
        <span>月售{{store.store.sales}}+</span>
        <span>{{store.store.category}}</span>
        <span>起送：{{store.store.deliverStartFee}}</span>
      </div>
      <div class="contentDesc">
        <p>{{store.store.description}}</p>
      </div>
    </div>
  </div>
  <div class="contentFooter">
    <ul>
      <li v-for="item in store.list">
        <div class="childrenItem" @click.stop="$router.push(`/menu/${store.store.id}/order/details/${item.categoryId}`)">
          <img :src="item.url" alt="图片未加载">
          <h6>{{item.name}}</h6>
          <h6 style="color: red">￥{{item.price}}元</h6>
        </div>
      </li>
    </ul>
  </div>
</div>
</template>

<script setup>
defineProps({
  store:{
    type:Object
  }
})
</script>

<style scoped>
.contentBack
{
  width: 530px;
  height: 350px;
  /*background: #01b6fd;*/
  box-shadow: 0 3px 8px rgb(0,0,0,0.3);
  border-radius: 20px;
  transition: all 0.5s;
}
h3
{
  font-weight: bold;
  margin-bottom: 0px;
}
.contentBack:hover
{
  transform: translateY(-8px);
}
.contentHeader
{
  margin-left: 20px;
  display: flex;;
  height: 145px;
  /*background: #fed6e3;*/
}
.leftAvatar img
{
  width: 130px;
  height: 130px;
  border-radius: 15px;
}
.rightText
{
  margin-left: 15px;
  flex: 1;
  margin-right: 15px;
}
.contentDetails
{
  width: 100%;
  height: 50px;
  display: flex;
  justify-content: space-around;

  /*background: #01b6fd;*/
}
.rightText span
{
  font-weight: bold;
  line-height: 50px;
  color:red;
}
.contentDesc
{
  height: 55px;

}
.contentFooter
{
  background: #eeeeee;
  margin-left: 20px;
  margin-right: 20px;
  border-radius: 20px;
}
.contentFooter ul
{
  padding-left: 0px;
  display: flex;
  justify-content: space-evenly;
}
.contentFooter ul li
{
  list-style: none;
  text-align: center;
}
.contentFooter ul li img
{
  margin-top: 10px;
  width: 100px;
  height: 100px;
  border-radius: 10px;
}
.contentFooter ul li h6
{
  font-weight: bold;
  margin-top: 5px;

}
</style>