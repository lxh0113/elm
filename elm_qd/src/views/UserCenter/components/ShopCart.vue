<template>
  <h2>我的购物车</h2>
  <div class="jiesuan">
    <div class="form-check">
      <input class="form-check-input" type="checkbox" @click="changeAllSelect" v-model="isAllSelect" id="flexCheckDefault">
      <label class="form-check-label" for="flexCheckDefault">
        全选
      </label>
    </div>
    <div>
      <button @click="deleteList">批量删除</button>
    </div>
    <div>
      <button @click="handleComputed">去结算</button>
    </div>
  </div>
  <div class="storeList">
    <ul v-if="shopCartListData.length">
      <li v-for="(item,index) in shopCartListData">
        <div class="storeBox">
          <div class="storeBoxHeader">
            <div class="form-check">
              <input class="form-check-input" @click="setIsSelectedRow(index)" v-model="isSelectedRow[index]" type="checkbox" id="flexCheckDefault">
              <img :src="item.store.storeUrl" alt="">
              <label class="form-check-label" for="flexCheckDefault">
                {{item.store.name}}
              </label>
            </div>
          </div>
          <ul>
            <li v-for="(cartItem,cartIndex) in item.shopCarts">
              <div class="goodsSelected">
                <div class="form-check">
                  <input @click="selectedOne(index,cartIndex)" v-model="isSelectedArry[index][cartIndex]" class="form-check-input" type="checkbox" id="flexCheckDefault">
                </div>
              </div>
              <div class="goodsDetails">
                <img :src="cartItem.goods.url" alt="">
              </div>
              <div class="goodsTextDesc">
                <div class="textHeader">
                  <h5>{{cartItem.goods.name}}</h5>
                </div>
                <div class="textBody">
                  {{cartItem.flavors.map((i)=>i.options).join('/')}}
                </div>
                <div class="textPrice">
                  <span>￥{{cartItem.goods.price+getGoodsTotalPrice(cartItem)}}元</span>
                  <div>
                    <button @click="openModal(cartItem)" type="button" class="btn btn-primary chooseCartSize">选规格</button>
                    <button @click="handleMinusNumber(cartItem)"><i class="bi bi-dash-lg"></i></button>
                    <input v-model="cartItem.number" type="number">
                    <button @click="()=>cartItem.number++"><i class="bi bi-plus-lg"></i></button>
                  </div>
                </div>
              </div>
              <i @click="handleDeleteCartOne(cartItem.id)" class="bi bi-x-circle-fill"></i>
            </li>
          </ul>
        </div>
      </li>
    </ul>
    <template v-else>
      <el-empty description="无数据" />
    </template>
  </div>

  <button ref="chooseSizeModal" style="display: none" data-bs-toggle="modal" data-bs-target="#chooseSize"></button>

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
              <img :src="currentGoodsData?.goods?.url" alt="">
            </div>
            <div class="dishRight">
              <h3>{{currentGoodsData.goods.name}}</h3>
              <p>{{currentGoodsData.goods.description}}</p>
              <!--              <span class="">￥12</span>-->
              <span class="">￥{{currentGoodsData.goods.price+getGoodsTotalPrice(currentGoodsData)}}元</span>
            </div>
          </div>
          <div class="dishCount">
            <div class="countLeft">
              数量
            </div>
            <div class="countRight">
              <button @click="handleMinusNumber(currentGoodsData)">-</button>
              <input v-model="currentGoodsData.number" type="number">
              <button @click="()=>currentGoodsData.number++">+</button>
            </div>
          </div>
          <div class="dishBody">
<!--                        这个地方写的是规格-->
            <div class="flavors" v-for="(item,goodsIndex) in flavorsData">
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
<!--          <button @click="addToShopCart()" type="button" class="btn btn-primary" data-bs-dismiss="modal">加入购物车</button>-->
        </div>
        <div class="modal-footer">
          <button ref="closeModal" type="button" class="btn btn-secondary" data-bs-dismiss="modal">关闭</button>
          <button @click="dealUpdateGoods" type="button" class="btn btn-primary" data-bs-dismiss="modal">确定</button>
        </div>
      </div>
    </div>
  </div>

</template>

<script setup>
import {ref, onMounted, computed} from "vue";
import {getShopCartAPI, deleteCartOneAPI, deleteListAPI} from "@/apis/shopCart";
import {useUserStore} from "@/stores/userStore";
import {ElMessage} from "element-plus";
import {getAllFlavorsAPI} from '@/apis/flavors.js'
import {updateShopCartAPI} from "@/apis/shopCart";

let shopCartListData=ref({})
const userStore=useUserStore()
const chooseSizeModal=ref()
const originGoodsData=ref({})
const deleteListArry=ref([])
const isSelectedArry=ref({})
const isSelectedRow=ref([])
const isAllSelect=ref(false)
const closeModal=ref()

let pageData=ref({
  current:1
})
let currentGoodsData=ref({
  goods:{}
})
let flavorsData=ref({})

const getShopCartList=async ()=>{
  const res=await getShopCartAPI({current:pageData.value.current,userId:userStore.user.id})
  if(res.data.code===1)
  {
    shopCartListData.value=res.data.map.res
    console.log(shopCartListData.value)
    pageData.value=res.data.data

    isSelectedArry.value=shopCartListData.value.map(item=>{
      return [...item.shopCarts.map(i=>{
        return 0
      })]
    })

    isSelectedRow.value=shopCartListData.value.map(item=>{
      return 0
    })

    console.log(isSelectedArry.value)
  }
  else
  {
    ElMessage.error(res.data.msg)
  }
}

const openModal=async (data)=>{
  //得到
  currentGoodsData.value=data
  console.log(currentGoodsData.value)

  //再重新赋值给这个数据
  originGoodsData.value=JSON.parse(JSON.stringify(currentGoodsData.value))

  const res=await getAllFlavorsAPI(currentGoodsData.value.goods.id);

  if(res.data.code===1)
  {
    console.log(res)
    flavorsData.value=res.data.data
  }
  else
  {
    ElMessage.error("获取出错")
  }

  chooseSizeModal.value.click()
}

const setId=(goodIndex,index)=>{
  //设置当前加一个类

  // console.log(goodIndex,index)
  // console.log(currentGoodsData.value.flavors[goodIndex])
  // console.log(flavorsData.value[goodIndex])
  // console.log(flavorsData.value[goodIndex].flavors)
  // console.log(flavorsData.value[goodIndex].flavors[index])
  currentGoodsData.value.flavors[goodIndex]=flavorsData.value[goodIndex].flavors[index];
  // currentGoodsData.value.flavors[goodIndex].id=flavorsData.value[goodIndex].flavors[index].id

}

const handleMinusNumber=(data)=>{
  //处理减去的逻辑
  console.log(data)
  if(data.number-1===0)
  {
    //
    let r=confirm("你确定要把当前商品从购物车拿出去吗")
    if(r===true)
    {
      handleDeleteCartOne(data.id)
    }
    else {
      return
    }
  }
  else
  {
    currentGoodsData.value.number--;
  }
}

const handleDeleteCartOne=async (id)=>{
  //处理删除的逻辑
  // alert("你看看我进来了吗"+id)
  closeModal.value.click()
  const res=await deleteCartOneAPI(id)

  if(res.data.code===1)
  {
    getShopCartList()
    ElMessage.success("删除成功")
  }
  else
  {
    ElMessage.error(res.data.msg)
  }

}

const dealUpdateGoods=async ()=>{
  //处理逻辑  比较一下

  if(JSON.stringify(currentGoodsData.value.flavors)===JSON.stringify(originGoodsData.value.flavors)
  &&currentGoodsData.value.number===originGoodsData.value.number)
  {
    //并没有做出改变
    return
  }
  else
  {
    currentGoodsData.value.goodsInfo=JSON.stringify(currentGoodsData.value.flavors.map(item=>{
      return {id:item.id}
    }))

    // console.log(currentGoodsData.value.goodsInfo)

    console.log(currentGoodsData.value)

    //发送给后端接收
    const res=await updateShopCartAPI(currentGoodsData.value)

    if(res.data.code===1)
    {
      getShopCartList()
      ElMessage.success("更新成功")
    }
    else {
      ElMessage.error(res.data.msg)
    }
  }
}

const changeAllSelect=()=>{
  isAllSelect.value=!isAllSelect.value;

  for (let i = 0; i < isSelectedRow.value.length; i++) {
    isSelectedRow.value[i]=isAllSelect.value;
  }

  for (let i = 0; i < isSelectedArry.value.length; i++) {
    for (let j = 0; j < isSelectedArry.value[i].length; j++) {
      isSelectedArry.value[i][j]=isAllSelect.value;
    }
  }
}

const setIsSelectedRow=(index)=>{

  isSelectedRow.value[index]=!isSelectedRow.value[index]

  for (let i = 0; i < isSelectedArry.value[index].length; i++) {
    isSelectedArry.value[index][i]=isSelectedRow.value[index]
  }

}

const selectedOne=(i,j)=>{

  isSelectedArry.value[i][j]=!isSelectedArry.value[i][j]

  isSelectedRow.value[i]=isSelectedArry.value[i].reduce((sum,item)=>sum&&item,1)

  isAllSelect.value=isSelectedRow.value.reduce((sum,item)=>item&&sum,1)
}

const deleteList=async ()=>{
  //
  let data=[]
  for (let i = 0; i < isSelectedArry.value.length; i++) {
    for (let j = 0; j < isSelectedArry.value[i].length; j++) {
      if(isSelectedArry.value[i][j])
      {
        //如果是选中状态
        data.push(shopCartListData.value[i].shopCarts[j].id)
      }
    }
  }
  if(JSON.stringify(data)===JSON.stringify([]))
  {
    ElMessage.error("您没有选中任何数据")
    return
  }
  console.log(data)

  const res=await deleteListAPI(data);

  if(res.data.code===1)
  {
    getShopCartList()
    ElMessage.success("删除成功")
  }
  else
  {
    ElMessage.error(res.data.msg)
  }
}

const getGoodsTotalPrice=(data)=>{
  let sum=0
  for (let i = 0; i < data?.flavors?.length; i++) {
    for (let j = 0; j < flavorsData.value[i]?.flavors?.length; j++) {
      if(data?.flavors[i]?.id===flavorsData.value[i]?.flavors[j]?.id)
      {
        sum+=flavorsData.value[i]?.flavors[j]?.price;
      }
    }
  }

  return sum
}

const handleComputed=()=>{
  //拿出选中的商品 放入订单中

  console.log(shopCartListData.value)
  let data=[]
  for (let i = 0; i < isSelectedArry.value.length; i++) {
    let shopList=[]
    for (let j = 0; j < isSelectedArry.value[i].length; j++) {
      //获取到store
      shopList.push({count:shopCartListData.value[i].shopCarts[j].number,
        goods:shopCartListData.value[i].shopCarts[j].goods,
        flavors:shopCartListData.value[i].shopCarts[j].flavors,
      })

      // data.push({shopList:currentGoodsData.value,})
    }
    data.push({store:shopCartListData.value[i].store,shopList:shopList})
  }

  console.log(data)
}

onMounted(()=>{
  getShopCartList()
})
</script>

<style scoped>
h2
{
  margin-left: 50px;
  line-height: 80px;
}
.jiesuan
{
  display: flex;
}
.jiesuan
{
  margin-left: 50px;
  height: 70px;
  /*background-color: #fed6e3;*/
  line-height: 70px;
}
.jiesuan .form-check
{
  font-size: 18px;
  margin-right: 40px;
}
.jiesuan .form-check input
{
  margin-top: 26px;
}
.jiesuan button
{
  margin-top: 10px;
  border:1px solid #01b6fd;
  background-color: #01b6fd;
  color:white;
  height: 50px;
  width: 120px;
  line-height: 50px;
  border-radius: 20px;
  font-size: 18px;
  box-shadow: 0 3px 8px rgb(0,0,0,0.2);
  margin-left: 50px;
}
.storeList
{
  /*background-color: #fed6e3;*/
}
.storeList ul
{
  /*background-color: #01b6fd;*/
  padding-left: 0;
  width: 93%;
  margin: 20px auto;
  /*display: flex;*/
}

.storeList>ul>li
{
  display: flex;
  /*border:1px dotted #6c757d;*/
  box-shadow: 0 3px 8px rgb(0,0,0,0.2);
  border-radius: 20px;
  border:1px solid #7a8289;
  /*background-color: #fed6e3;*/
  margin-top: 30px;
}
.storeBoxHeader
{
  height: 70px;
  line-height: 70px;
  font-size: 17px;
  font-weight: bold;
}
.storeBoxHeader .form-check
{
  margin-left: 30px;
  /*margin-top: 30px;*/
}
.storeBoxHeader .form-check input
{
  margin-top: 26px;
}
.storeBoxHeader .form-check img
{
  width: 50px;
  height: 50px;
  margin:0 20px;
}
.storeBox>ul
{
  margin-left: 30px;
  margin-right: 30px;
  width: 100%;
  display: flex;
  flex-wrap: wrap;
}
.storeBox>ul>li
{
  position:relative;;
  /*box-sizing: border-box;*/
  padding:20px;
  width: 650px;
  display: flex;
  margin-right: 30px;
  /*background-color: #01b6fd;*/
  margin-bottom: 20px;
  /*border:1px solid #7a8289;*/
  box-shadow: 0 3px 8px rgb(0,0,0,0.2);
  max-width: 650px;
  border-radius: 20px;
  /*flex:1;*/
  /*width: 100%;*/
}
.storeBox>ul>li>i
{
  display: none;
  position: absolute;
  color:#01b6fd;
  top:-10px;
  right: -5px;
  font-size: 1.4rem;
}
.storeBox>ul>li:hover>i
{
  display: block;
}
.storeBox>ul>li>i:hover
{
  color:#7a8289;
}
.goodsDetails img
{
  width: 100px;
  height: 100px;
}
.goodsSelected .form-check
{
  line-height: 100px;
  font-size: 15px;
}
.goodsSelected .form-check input
{
  margin-top: 44px;
}
.goodsDetails
{
  margin-left: 30px;
}
.goodsTextDesc
{
  margin-left: 20px;
  flex:1;
  max-width: 477px;
}
.goodsTextDesc .textHeader
{
  height: 30px;
  /*line-height: 40px;*/
}
.goodsTextDesc .textHeader h5
{
  line-height: 30px;
  margin-bottom: 0;
}
.goodsTextDesc .textBody
{
  height: 30px;
  line-height: 30px;
  color:#7a8289;
}
.goodsTextDesc .textPrice
{
  /*flex:1;*/
  /*background-color: #fed6e3;*/
  display: flex;
  height: 40px;
  line-height: 40px;
  font-size: 17px;
  justify-content: space-between;
}
.goodsTextDesc .textPrice button
{
  box-sizing: border-box;
  height: 30px;
  width: 30px;
  line-height: 30px;
  border-radius: 50%;
  border:1px solid #01b6fd;
  color:white;
  font-weight: bold;
  background-color: #01b6fd;
  /*margin-right: 50px;*/
}
.goodsTextDesc .textPrice button:hover
{
  background-color: #7a8289;
  border:1px solid #7a8289;
}
.goodsTextDesc .textPrice input
{
  text-align: center;
  width: 40px;
  height: 30px;
  margin: 0 10px;
  border:1px solid #01b6fd;
}
.storeBox>ul>li .textPrice div .chooseCartSize
{
  width: 80px;
  border-radius: 10px;
  margin-right: 10px;
  line-height: 18px;
  font-weight: normal;
}
.storeBox>ul>li .textPrice div .chooseCartSize:hover
{
  background-color: #01b6fd;
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