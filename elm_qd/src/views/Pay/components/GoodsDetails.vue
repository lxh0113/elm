<template>
<div class="detailsDishBox">
<!--  <div style="height: 2vh"></div>-->
  <h3>吉大奶茶店</h3>
  <ul>
    <li v-for="(item,index) in shopListStore.shopList">
      <div class="itemBox">
        <div class="leftImage">
          <img :src="item.goods.url" alt="">
        </div>
        <div class="centerInfo">
          <h2>{{item.goods.name}}</h2>
          <span>{{ getFlavors(index) }}</span>
          <br>
          <span>×{{item.count}}</span>
        </div>
        <div class="rightPrice">
          <span>￥{{item.goods.price*item.count}}</span>
        </div>
      </div>
    </li>
  </ul>
  <div class="pakageFee">
    <span>打包费</span>
    <span>￥{{shopListStore.getAllPackageFee()}}</span>
  </div>
  <div class="totalPrice">
    <span>小计</span>
    <span>￥{{shopListStore.getTotalPrice()}}</span>
  </div>
</div>
</template>

<script setup>
import {useShopListStore} from "@/stores/shopListStore";

const shopListStore=useShopListStore();

//显示规格
const getFlavors=(index)=>{
  //当前
  let flavorsText="";
  let goodsItem=shopListStore.getShopList()[index]

  for (let i = 0; i < goodsItem.flavorsListData.length; i++) {
    //找到对应的 goodsItem 的 flavors
    for (let j = 0; j < goodsItem.flavorsListData[i].flavors.length; j++) {
      if(goodsItem.flavorsListData[i].flavors[j].id===goodsItem.flavors[i].id)
      {
        flavorsText=flavorsText+"/"+goodsItem.flavorsListData[i].flavors[j].options;
      }
    }
  }

  return flavorsText.substring(1);
}



</script>

<style scoped>
.detailsDishBox
{
  /*height: 20vh;*/
  width: 95vw;
  /*background: #fed6e3;*/
  margin: 2vh auto;
  font-size: 5vw;
  border-radius: 4vw;
  background: white;
  box-shadow: 7px 7px 17px rgba(52, 56, 66, 0.5);
}
.detailsDishBox h3
{
  color: #6c757d;
  font-size: 4vw;
  margin-left: 3vw;
  line-height: 5vh;
}
ul
{
  padding-left: 0;
}
ul li
{
  list-style: none;
}
.itemBox
{
  display: flex;
  justify-content: space-between;
  height: 12vh;
  /*background: pink;*/
}
.itemBox .leftImage
{
  margin-top: 1vh;
  margin-left: 3vw;
}
.itemBox .leftImage img
{
  width: 20vw;
  height: 10vh;
}
.centerInfo
{
  text-align: left;
  margin-left: 2vw;
  flex:1;
}
.centerInfo h2
{
  margin-top: 1vh;
  font-size: 4vw;
  font-weight: bold;
  /*background: #01b6fd;*/
}
.centerInfo span
{
  color: #6c757d;
  font-size: 4vw;
}
.rightPrice
{
  margin-right: 3vw;
  margin-top: 1vh;
  line-height: 10vh;
  font-size: 4vw;
  font-weight: bold;
}
.pakageFee
{
  display: flex;
  justify-content: space-between;
}
.pakageFee span
{
  margin-left: 3vw;
  margin-right: 2vw;
  font-size: 4vw;
  font-weight: bold;;
}
.totalPrice
{
  display: flex;
  justify-content: space-between;
  font-size: 4vw;
  line-height: 6vh;
}
.totalPrice span:nth-child(1)
{
  margin-left: 3vw;
}
.totalPrice span:nth-child(2)
{
  margin-right: 2vw;
  font-size: 5vw;
  color: red;
  font-weight: bold;
}
</style>