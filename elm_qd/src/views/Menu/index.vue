<template>

<LayoutNav></LayoutNav>

  <LayoutHeader></LayoutHeader>
  <div class="backcolor">
    <div class="menuBox">
      <div class="backurl">
        <img :src="storeData.storeUrl" alt="">
      </div>
      <div class="trans">
        <div class="details">
          <div class="header">
            <div class="left">
              <h1>{{storeData.name}}</h1>
              <span class="star">4.8分</span>
              <span>月售3000+</span>
            </div>
            <div class="right">
              <img :src="storeData.logoUrl" alt="">
            </div>
          </div>
          <div class="footer">
            <p>{{storeData.description}}</p>
          </div>
        </div>
        <div class="mainBox">
          <div class="choose">
            <ul>
              <li>
                <RouterLink :to="'/menu/' + $route.params.id + '/order'">点餐</RouterLink>
              </li>
              <li>
                <RouterLink :to="'/menu/' + $route.params.id + '/remark'">评价</RouterLink>
              </li>
              <li>
                <RouterLink :to="'/menu/' + $route.params.id + '/chat'">聊天</RouterLink>
              </li>
            </ul>
          </div>
          <div class="orderArea">
            <RouterView></RouterView>
          </div>
        </div>
      </div>
    </div>
  </div>


  <!-- Modal -->
  <div class="modal fade" id="chooseSize" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="chooseSizeLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="chooseSizeLabel">选择规格</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">

          <div class="dishHeader">
            <div class="dishLeft">
              <img :src="goodsStore.getGoods().goods.url" alt="">
            </div>
            <div class="dishRight">
              <h3>{{goodsStore.getGoods().goods.name}}</h3>
              <p>已选</p>
              <!--              <span class="">￥12</span>-->
              <span class="">￥{{getGoodsTotalPrice()}}</span>
            </div>
          </div>
          <div class="dishCount">
            <div class="countLeft">
              数量
            </div>
            <div class="countRight">
              <button @click="()=>currentGoodsData.count--">-</button>
              <input type="number" v-model="currentGoodsData.count">
              <button @click="()=>currentGoodsData.count++">+</button>
            </div>
          </div>
          <div class="dishBody">
            <!--            这个地方写的是规格-->
            <div class="flavors" v-for="(item,goodsIndex) in goodsStore.getGoods()?.flavorsListData">
              <h5>{{item?.name}}</h5>
              <div class="optionFlavor">
                <span v-for="(optionItem,index) in item?.flavors" :key="optionItem?.id" @click="setId(goodsIndex,index)" :class="{activeFlavors:optionItem?.id===currentGoodsData?.flavors[goodsIndex]?.id}">
                  {{optionItem?.options}}{{optionItem?.price===0?"":" ￥"+optionItem.price}}
                </span>
              </div>
            </div>
          </div>
        </div>
        <div style="display: flex;justify-content: right;margin-right: 14px;margin-bottom: 20px">
          <button @click="addToShopCart()" type="button" class="btn btn-primary" data-bs-dismiss="modal">加入购物车</button>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">关闭</button>
          <button @click="toShopCart" type="button" class="btn btn-primary" data-bs-dismiss="modal">确定</button>
        </div>
      </div>
    </div>
  </div>

  <ComputeFooter></ComputeFooter>

</template>

<script setup>
import {ref,onMounted,watch} from "vue";

import LayoutNav from '@/views/Layout/components/LayoutNav.vue'
import LayoutHeader from '@/views/Layout/components/LayoutHeader.vue'
import ComputeFooter from '@/views/Menu/components/ComputeFooter.vue'
import 'bootstrap/js/src/modal.js'
import 'bootstrap/dist/css/bootstrap.min.css'
import {useRoute,useRouter} from 'vue-router'
import {getStoreOrigin} from '@/apis/store.js'
import {ElMessage} from "element-plus";
import {useGoodsStore} from '@/stores/goodsStore.js'
import {useShopListStore} from "@/stores/shopListStore";
import {useUserStoreStore} from "@/stores/userStoreStore";
import {useUserStore} from "@/stores/userStore";
import {useStoreStore} from "@/stores/storeStore";
import {addToShopListAPI} from "@/apis/shopCart";
// import {useUserStoreS}

const route=useRoute();
const router=useRouter();
const goodsStore=useGoodsStore();
const shopListStore=useShopListStore()
const userStoreStore=useUserStoreStore()
const userStore=useUserStore()
const storeStore=useStoreStore()

const storeData=ref({})

//需要有数量 和 当前选中的 类别 而且是必选项
// const currentGoodsData=ref({
//   count:1,
//   flavors: [
//     ...goodsStore.getGoods()?.flavorsListData?.map((item) => {
//       return { name: item?.flavors[0]?.name, id: item?.flavors[0]?.id };
//     })]
// })

const currentGoodsData=ref({
  count:1,
  flavors: [
    ...goodsStore.getGoods()?.flavorsListData?.map((item) => {
      return { ...item.flavors[0] };
    })]
})

// currentGoodsData.value={
//   count:1,
//   flavors: [
//     ...goodsStore.getGoods().flavorsListData?.map((item) => {
//       return { name: item.goods.name, id: item.flavors[0].id };
//     })]
// }

const getStore=async ()=>{
  let storeId=route.params.id;
  // userStoreStore.
  userStoreStore.setUserStoreId(storeId)
  storeStore.clearStore()
  storeStore.getUserInfoByUserStore()

  shopListStore.clearShopList()

  const res=await getStoreOrigin(storeId);
  if(res.data.code===1)
  {
    console.log(res)
    storeData.value=res.data.data
    userStoreStore.setStorePackageFee(storeData.value.packageFee)
  }
  else
  {
    ElMessage.error(res.data.msg)
  }
}

const getGoods=()=>{
  //得到需要显示的数据

}

const setId=(goodIndex,index)=>{
  //设置当前加一个类
  const goodsList=goodsStore.getGoods()
  console.log(goodIndex,index)
  // console.log(currentGoodsData.value)
  // console.log(currentGoodsData.value.flavors[goodIndex].id)
  // console.log(goodsList.flavorsListData[goodIndex].flavors[index].id)

  // currentGoodsData.value.flavors[goodIndex].id=goodsList.flavorsListData[goodIndex].flavors[index].id;
  currentGoodsData.value.flavors[goodIndex]=goodsList.flavorsListData[goodIndex].flavors[index];

}

const getGoodsTotalPrice=()=>{
  let sum=0
  console.log(currentGoodsData.value.flavors)
  for (let i = 0; i < currentGoodsData.value.flavors.length; i++) {
    sum+=currentGoodsData.value.flavors[i].price
  }

  console.log(sum)
  console.log(goodsStore.goods.price)

  return goodsStore.getGoods().goods.price+sum
}

const toShopCart=()=>{
  //加入到 购物车

  let data = { ...goodsStore.getGoods(), ...currentGoodsData.value,isSelected:0 };
  console.log("data")
  console.log(data)
  //这是新的 商品 传递到 购物车里面
  shopListStore.addToList(data)

}

const addToShopCart=async ()=>{

  const data={
    userId:userStore.user.id,
    goodsId:goodsStore.getGoods().goods.id,
    storeId:userStoreStore.getUserStoreId(),
    number:currentGoodsData.value.count,
    flavors:[
        ...currentGoodsData.value.flavors.map((item)=>{
          return {id:item.id}
        })
    ]
  }

  console.log("看看需要加入购物车的商品内容")
  console.log(data)

  // 加入购物车
  const res=await addToShopListAPI(data);
  if(res.data.code===1)
  {
    ElMessage.success("添加成功")
  }
  else
  {
    ElMessage.error(res.data.msg)
  }
}



watch(() => goodsStore.goods,()=>{
  currentGoodsData.value={
    count:1,
    flavors: [
      ...goodsStore.getGoods()?.flavorsListData?.map((item) => {
        return { ...item.flavors[0] };
      })]
  }
},{
  deep:true
})

onMounted(async ()=>{
  await getStore()

})

</script>

<style scoped>
.menuBox
{
  width: 1240px;
  /*height: 3000px;*/
  background: white;
  margin: 0 auto;

}
.backcolor
{
  width: 100%;
  height: 100%;
  background: #eeeeee;
}
.menuBox .backurl
{
  width: 100%;
  height: 500px;
}
.backurl img
{
  height: 500px;
  width: 1240px;
}
.trans
{
  width: 1180px;
  margin: 0 auto;
  transform: translateY(-100px);
}
.details
{
  width: 1180px;
  margin: 0 auto;

  height: 300px;
  border-radius: 30px;
  background: white;
  box-shadow: 0 3px 8px rgb(0,0,0,0.2);
}
.details .header {
  width: 1180px;
  margin: 0 auto;
  /*background: pink;*/
  display: flex;
  justify-content: space-between;
  height: 180px;
}
.details .footer
{
  height: 120px;
}
.details .header .left
{
  margin-top: 30px;
  margin-left: 30px;
}
.details .header .left h1
{
  line-height: 80px;
}
.details .header .left span
{
  line-height: 20px;
  font-weight: bold;
  font-size: 20px;
  color: #ff636b;
  margin-right: 30px;
}

.details .header .right
{
  margin-top: 30px;
  margin-right: 30px;
}

.details .header .right img
{
  width: 120px;
  height: 120px;
  border-radius: 20px;
}

.details .footer p
{
  margin: 0 30px;
}

.mainBox
{
  width: 1180px;
  margin: 0 auto;
  /*height: 2000px;*/
  background: white;
  padding-bottom: 30px;
  margin-top: 30px;
  border-radius: 30px;
  box-shadow: 0 3px 8px rgb(0,0,0,0.2);
}
.choose
{
  width: 1120px;
  height: 100px;
  /*background: palegoldenrod;*/
  margin:0 auto;
  margin-bottom: 30px;
  line-height: 100px;
}
a
{
  font-size: 20px;
  text-decoration: none;
  color: #01b6fd;
  padding:0 30px;
  font-weight: bold;
}
a.router-link-active
{
  color: #ff636b;
}
.choose ul
{
  display: flex;
  justify-content: space-evenly;
}
.choose ul li
{
  list-style: none;
}
.orderArea
{
  width: 1120px;
  margin: 0 auto;
  /*height: 1000px;*/
  /*margin-bottom: 30px;*/
  /*background: #01b6fd;*/
}
.dishHeader
{
  display: flex;
  margin-left: 15px;
  margin-top: 15px;
  margin-right: 15px;
}
.dishLeft img
{
  width: 150px;
  height: 150px;
  border-radius: 20px;
}
.dishRight
{
  margin-left: 15px;
}
.dishRight h3
{
  font-weight: bold;
  height: 30px;
  margin-bottom: 0;
  line-height: 30px;
}
.dishRight p
{
  color: #6c757d;
  height: 70px;
  margin-top: 10px;
  margin-bottom: 10px;
}
.dishRight span
{
  color: red;
  font-size: 25px;
  font-weight: bold;
  height: 20px;
}
.dishCount
{
  display: flex;
  justify-content: space-between;
  margin-left: 15px;
  margin-right: 15px;
  margin-top: 10px;
}

.countRight button
{
  width: 30px;
  height: 30px;
  outline: none;
  border:1px solid #666;
}
.countRight input
{
  box-sizing: border-box;
  padding-left: 5px;
  width: 40px;
  margin: 0 10px;
}
.dishBody
{
  margin-left: 15px;
  margin-right: 15px;
  margin-top: 15px;
  /*border-top:1px solid #6c757d;*/
}

.optionFlavor
{
  display: flex;
  flex-wrap: wrap;
}
.optionFlavor span
{
  padding:6px 20px;
  background: #eeeeee;
  margin-right: 20px;
  margin-bottom: 15px;
  /*border-radius:10px*/
}
.activeFlavors
{
  border:1px solid #01b6fd;
  color:#01b6fd;
}
</style>