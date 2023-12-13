<template>
<div class="fixedFooter">
  <div class="leftF">
    <div class="cart">
      <div class="iconDiv">
        <i class="bi bi-cart3" @click="()=>isShowShopList=!isShowShopList"></i>
        <span>{{shopListStore.getSum()}}</span>
      </div>
      <h2>￥{{shopListStore.getTotalPrice()}}</h2>
    </div>
  </div>
  <div class="rightF">
    <button @click="toPay">去结算</button>
  </div>
</div>

<div v-show="isShowShopList===true" :class="{shopList:isShowShopList===true}">
  <div class="shopListHeader">
    <span @click="()=>isShowShopList=false">X</span>
  </div>
  <template v-if="shopListStore.shopList.length===0">
    <el-empty description="无数据" />
  </template>
  <div v-else class="shopListBody">
    <div class="operation">
      <div class="" style="margin-left: 40px">
        <input @click="bigCheck" class="firstCheck form-check-input" type="checkbox" v-model="isSelected">
      </div>
      <div class="twoButton">
        <button @click="toArryDel"><i class="bi bi-trash3-fill"></i>&nbsp;&nbsp;批量删除</button>
        <button @click="shopListStore.clearShopList()"><i class="bi bi-x-circle-fill"></i>&nbsp;&nbsp;清除所有</button>
      </div>
    </div>
    <ul>
      <li v-for="(item,index) in shopListStore.getShopList()" :key="item.goods.id">
        <div class="shopListItem">
          <div class="form-check">
            <input class="form-check-input" type="checkbox" v-model="item.isSelected">
          </div>
          <div class="image">
            <img :src="item.goods.url" alt="图片未加载">
          </div>
          <div class="details">
            <h4>{{item.goods.name}}</h4>
            <div class="itemFlavors">{{getFlavors(index)}}</div>
            <div class="numAndCount">
              <span class="price">￥{{item.goods.price}}</span>
              <div class="detailsCount">
                <button @click="subCount(index)">-</button>
                <input type="number" v-model="item.count">
                <button @click="addCount(index)">+</button>
              </div>
            </div>
          </div>
        </div>
      </li>
    </ul>
  </div>
</div>

</template>

<script setup>
// import IconDocumentation from "@/components/icons/IconDocumentation.vue";
import {useShopListStore} from "@/stores/shopListStore.js";
import {computed, ref} from 'vue'
import {useRouter,useRoute} from "vue-router";

const isShowShopList=ref(false)
const shopListStore=useShopListStore();
const route=useRoute()
const router=useRouter()

const addCount=(index)=>{
  //添加 数量操作
  let data=shopListStore.getShopList();
  data=data[index];

  data.count++;
  shopListStore.changeGoods(data.goods.id,data)
}

const subCount=(index)=>{
  let data=shopListStore.getShopList()
  data=data[index]

  if(data.count)
  {
    data.count--
    //TODO
    console.log()
    shopListStore.changeGoods(data.goods.id,data)
  }
}

const isSelected = computed({
  get() {
    return shopListStore.getShopList().every(item => item.isSelected);
  },
  set: (newValue) => {
    shopListStore.getShopList().forEach(item => item.isSelected = newValue);
  }
});


const bigCheck=()=>{
  console.log(isSelected.value)
  isSelected.value = !isSelected.value;
}

const toArryDel=()=>{
  shopListStore.clearSelected()
}

const toPay=()=>{
  router.push("/pay")
}

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
.shopList
{
  /*box-shadow: 0 3px 8px rgb(0,0,0,0.2);*/
  position: fixed;
  /*min-width: 1240px;*/
  bottom: 120px;
  left: 0;
  z-index: 50;
  width: 100%;
  /*height: 500px;*/
  background: white;
  transition: all 0.5s;
  /*border:1px solid #7a7a7a;*/
}
.shopListHeader
{
  display: flex;

  /*background-color: #01b6fd;*/
  /*min-width: 1240px;*/
  width: 100%;
  justify-content: right;
}
.shopListHeader span
{
  /*position: fixed;*/
  margin-top: 10px;
  margin-right: 30px;
  font-weight: bold;
  font-size: 40px;
}
.shopListHeader span:hover
{
  color: #6c757d;
}
.shopListBody ul
{
  padding-left: 0;
}
.shopListBody .operation
{
  display: flex;
  height: 60px;
  justify-content: space-between;
}
.shopListBody .operation button
{
  margin-right: 60px;
  padding: 10px 30px;
  color:white;
  background: #4e9fe7;
  outline: none;
  border:0;
  border-radius: 10px;
}
.shopListBody .operation button:hover
{
  background: #6c757d;
}
ul
{
  background: #eeeeee;

}
ul li
{
  list-style: none;
  /*background: #;*/
}
.shopListItem
{
  height: 120px;
  margin-left: 40px;
  margin-bottom: 10px;
  display: flex;
  justify-content: space-evenly;
  /*background-color: #01b6fd;*/
}
.shopListItem .image img
{
  margin-top: 10px;
  width: 100px;
  height: 100px;
  margin-right: 20px;
  margin-left: 30px;
}
.shopListItem .details
{
  /*background-color: purple;*/
  margin-right: 50px;
  flex:1;
  margin-top: 10px;
}
.shopListItem .details .price
{
  color: red;
  font-size: 20px;
  font-weight: bold;
}
.numAndCount
{
  display: flex;
  justify-content: space-between;
}
.numAndCount button
{
  width: 30px;
  height: 30px;
  text-align: center;
  border-radius: 50%;
  background:#4e9fe7;
  color: white;
  font-weight: bold;
  font-size: 20px;
  outline: none;
  border:0px;
}
.numAndCount button:hover
{
  background: #6c757d;
}
.numAndCount input
{
  box-sizing: border-box;
  width: 40px;
  height: 30px;
  margin: 0 20px;
  padding-left: 10px;
}
.itemFlavors
{
  height: 40px;
  line-height: 40px;
  color: #6c757d;
}
.detailsCount
{
  margin-right: 30px;
}
.form-check:nth-child(n+1)
{
  width: 100px;
  height: 100px;
  /*background: #01b6fd;*/
}
.form-check .form-check-input
{
  margin-top: 45px;
  margin-left: 20px;
}
.firstCheck
{
  margin-top: 10px;
  margin-left: 40px;
}
</style>