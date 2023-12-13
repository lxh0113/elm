<template>
 <div class="payBackground">
 </div>
  <div v-if="ordersStore?.orderData?.id">
<!--    设置显示状态-->
    <OrdersStatus></OrdersStatus>
  </div>
  <div v-else>
    <div class="bigBox">
      <div style="height: 3vh"></div>
      <h3>确认订单</h3>
      <PayAddress :defaultAddress="currentAddressData" :key="currentAddressData" v-if="!isAddNewAddress"></PayAddress>
      <AddNewAddress v-else :addressForm="addressForm" :key="addressForm"></AddNewAddress>
      <GoodsDetails></GoodsDetails>
      <GoodsRemark></GoodsRemark>
    </div>
    <ToPay @toHandleAllOperation="toHandleAllOperation"></ToPay>
  </div>

  <!-- 地址 Modal -->
  <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="staticBackdropLabel">选择地址</h1>
          <button ref="closeAddressModal" type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
<!--          <h3>默认地址</h3>-->
          <div v-if="addressList.length!==0">
            <div class="addressList" v-for="(item,index) in addressList">
              <div class="form-check">
                <input class="form-check-input" type="radio" name="flexRadioDefault" id="addressRadio" :value="item.id" @click="changeModalData(index)">
              </div>
              <div class="addressText">
                <span>{{item.detail}}</span>
                <div style="display: flex;justify-content: left;color:#6c757d;font-weight: normal">
                  <span style="margin-right: 4vw;">{{item.receiverName}}</span>
                  <span>{{item.telephone}}</span>
                </div>
              </div>
              <div class="addressEditIcon">
                <i @click="editAddress(index)" class="bi bi-pencil-square"></i>
              </div>
            </div>
            <div @click="toAddNewAddress" style="text-align: center;color:#7a8289;margin-top: 1vh">新增地址<i class="bi bi-building-add"></i></div>
          </div>

          <template v-else>
            <el-empty @click="toAddNewAddress" description="无地址,去新增地址" />
          </template>

        </div>
      </div>
    </div>
  </div>

<!--  备注 Modal-->
  <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="exampleModalLabel">备注</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <form>
            <div class="mb-3">
              <label for="message-text" class="col-form-label">备注:</label>
              <textarea v-model="orderData.remark" class="form-control" id="message-text"></textarea>
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">关闭</button>
          <button type="button" class="btn btn-primary" data-bs-dismiss="modal">确定</button>
        </div>
      </div>
    </div>
  </div>


</template>

<script setup>
import PayAddress from "@/views/Pay/components/PayAddress.vue";
import GoodsDetails from "@/views/Pay/components/GoodsDetails.vue";
import GoodsRemark from "@/views/Pay/components/GoodsRemark.vue";
import ToPay from "@/views/Pay/components/ToPay.vue";
import AddNewAddress from "@/views/Pay/components/AddNewAddress.vue";
import OrdersStatus from '@/views/Pay/components/OrdersStatus.vue'
import {ElMessage} from "element-plus";
import {ref,onMounted} from "vue";
import {addNewAddressForOrderAPI, getSingleDistrictAPI, getUserDefaultAddressAPI} from '@/apis/address.js'
import {useUserStore} from "@/stores/userStore";
import {useStoreStore} from "@/stores/storeStore";
import {useShopListStore} from "@/stores/shopListStore";
import {makeOrdersAPI,toPayAPI} from '@/apis/pay.js'
import {useUserStoreStore} from '@/stores/userStoreStore'
import {useOrdersStore} from "@/stores/ordersStore";
// import

const userStore=useUserStore()
const addressList=ref([])
const isAddNewAddress=ref(false)
const closeAddressModal=ref()
const storeStore=useStoreStore();
let currentAddressData=ref({})
const shopListStore=useShopListStore();
const userStoreStore=useUserStoreStore()
const ordersStore=useOrdersStore()

const addressForm=ref({
  id:'',
  userId:userStore.user.id,
  receiverName:'',
  gender:0,
  identity:0,
  telephone:'',
  province:0,
  city:0,
  district:0,
  detail:'',
  isDefault:0
})

const orderData=ref({
  buyerId:userStore.user.id,
  storeId:'',
  number:'',
  price:'',
  itemsLists:[],
  remark:'',
  addressText:'',
  pcd:'',
  buyerName:'',
  buyerTel:'',
  packageFee:shopListStore.getAllPackageFee()
})

//支付数据
const alipayData=ref({
  traceNo:'',
  totalAmount:'',
  subject:'',
  alipayTraceNo:''
})

//准备一个数据 用来存储订单信息

const getAddress=async ()=>{
  const res=await getUserDefaultAddressAPI(userStore.user.id);
  if(res.data.code===1)
  {
    addressList.value=res.data.data
    //获取到地址后
    currentAddressData.value=addressList.value[0]

    addressForm.value=addressList.value[0]
  }
  else
  {
    ElMessage.error("获取地址出错")
  }

}

const toAddNewAddress=()=>{
  //去新建地址
  isAddNewAddress.value=true
  //这是新增
  addressForm.value=JSON.parse(JSON.stringify({
    id:'',
    userId:userStore.user.id,
    receiverName:'',
    gender:0,
    identity:0,
    telephone:'',
    province:0,
    city:0,
    district:0,
    detail:'',
    isDefault:0
  }))

  closeAddressModal.value.click()
}

const editAddress=(index)=>{
  isAddNewAddress.value=false;

  //设置数据
  addressForm.value=JSON.parse(JSON.stringify(addressList.value[index]))
  console.log(addressForm.value)
  console.log("当前下标为"+index)
  isAddNewAddress.value=true;
  closeAddressModal.value.click()

}

const verifyAddressData=()=>{
  //验证
  console.log('在验证了')
  console.log(addressForm.value)
  if(addressForm.value.receiverName==='')
  {
    ElMessage.error("接收人不能为空")
    return false
  }
  addressForm.value.receiverName=addressForm.value.receiverName.trim()

  if(addressForm.value.telephone.trim()==='')
  {
    ElMessage.error("电话号码为空")
    return false;
  }
  addressForm.value.telephone=addressForm.value.telephone.trim()

  if(addressForm.value.province===0)
  {
    ElMessage.error("您还未选中地址")
    return false;
  }

  if(addressForm.value.city===0)
  {
    ElMessage.error("您还未选中地址")
    return false;
  }

  if(addressForm.value.district===0)
  {
    ElMessage.error("您还未选中地址")
    return false;
  }

  if(addressForm.value.detail.trim()==='')
  {
    ElMessage.error("您还填写地址")
    return false;
  }
  addressForm.value.detail=addressForm.value.detail.trim();
  return true

}

const getPcdText=async (province,city,district)=>{
  //先获取到省
  let provinceData=ref()
  let cityData=ref()
  let districtData=ref()

  let res=await getSingleDistrictAPI(province);
  if(res.data.code===1)
  {
    provinceData.value=res.data.data
  }
  else
  {
    ElMessage.error("获取失败")
    return
  }
  res=await getSingleDistrictAPI(city);
  if(res.data.code===1)
  {
    cityData.value=res.data.data
  }
  else
  {
    ElMessage.error("获取失败")
    return
  }

  res=await getSingleDistrictAPI(district);

  if(res.data.code===1)
  {
    districtData.value=res.data.data
  }
  else
  {
    ElMessage.error("获取失败")
    return
  }
  console.log(provinceData.value.district+cityData.value.district+districtData.value.district)
  let str = "" + provinceData.value.district + cityData.value.district + districtData.value.district;
  return str// 直接返回值
}

const toHandAddress=async ()=>{

  console.log('是不是在处理地址了')
  //设置名字
  console.log(storeStore.getUserInfo())
  orderData.value.storeId=storeStore.getUserInfo().id;

  //先把地址发送过去
  if(addressForm.value.id==='')
  {
    //说明当前是新建地址 设置一个新地址

    //需要验证是不是空的
    const flag=verifyAddressData();
    if(flag===false) return false

    const res=await addNewAddressForOrderAPI(addressForm.value)
    if(res.data.code===1)
    {
      //成功得到了 id 通过id获取到文本
      ElMessage.success("增加成功")
      orderData.value.addressText=res.data.data.detail
      orderData.value.pcd=await getPcdText(res.data.data.province,res.data.data.city,res.data.data.district)
      orderData.value.buyerName=res.data.data.receiverName
      orderData.value.buyerTel=res.data.data.telephone
      return true
    }
    else
    {
      ElMessage.error(res.data.msg)
      return false
    }
  }
  else
  {
    //这里需要比较  数据是响应式的可以之间发送
    console.log(addressForm.value)

    //如果修改了 就发送修改后的数据过去
    for (let i = 0; i < addressList.value.length; i++) {
      if(addressList.value[i].id===addressForm.value.id)
      {
        if((JSON.stringify(addressList.value[i]))===JSON.stringify(addressForm.value))
        {
          //当前对象是相等的状态  获取到地址文本
          orderData.value.addressText=addressList.value[i].detail
          //设置orderData的省市区状态
          orderData.value.pcd=await getPcdText(addressList.value[i].province,addressList.value[i].city,addressList.value[i].district)
          orderData.value.buyerName=addressList.value[i].receiverName
          orderData.value.buyerTel=addressList.value[i].telephone
          return true
        }
        else
        {
          //需要新建一个地址
          const res=await addNewAddressForOrderAPI(addressForm.value)
          if(res.data.code===1)
          {
            //成功得到了 id 通过id获取到文本
            orderData.value.addressText=res.data.data.detail
            orderData.value.pcd=await getPcdText(res.data.data.province,res.data.data.city,res.data.data.district)
            orderData.value.buyerName=res.data.data.receiverName
            orderData.value.buyerTel=res.data.data.telephone
            return true
          }
          else
          {
            ElMessage.error(res.data.msg)
            return false
          }
        }
      }
    }
  }

  return true
}

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

const toHandleOrder=async ()=>{
  //处理商品信息  已经有了商家id 地址
  orderData.value.number=shopListStore.getSum()
  orderData.value.price=shopListStore.getTotalPrice();

  orderData.value.storeId=userStoreStore.getUserStoreId()

  //需要处理一下这个 goodsLists  一个菜品需要

  let itemsLists=[...shopListStore.getShopList().map((item,index)=>{
    return {name:item.goods.name,url:item.goods.url,flavors:getFlavors(index),count:item.count,price:item.goods.price}
  })]

  console.log(itemsLists)
  orderData.value.itemsLists=itemsLists
  //发送请求过去

}


const changeModalData=(index)=>{
  //改变地址
  console.log("改变地址",index)
  currentAddressData.value=JSON.parse(JSON.stringify(addressList.value[index]))
  addressForm.value=JSON.parse(JSON.stringify(addressList.value[index]))
  console.log(currentAddressData.value)
}


const toHandleAllOperation=async ()=>{
  console.log("看看我是否到处理所有操作的地方来了")
  //处理所有的事情

  //处理地址
  const flag=await toHandAddress();
  console.log('flag',flag)
  if(flag===false) return

  //处理订单
  await toHandleOrder()

  console.log("查看pcd的值")
  console.log(orderData.value.pcd)

  //处理完毕发送请求过去
  const res=await makeOrdersAPI(orderData.value)

  if(res.data.code===0)
  {
    ElMessage.error(res.data.msg)
    return
  }
  console.log(res)
  ordersStore.setOrderData(res.data.data)
  alipayData.value=res.data.map.alipay


  console.log("到支付环节了")

  window.open("http://localhost:8081/api/alipay/pay?subject="+alipayData.value.subject+"&traceNo="+alipayData.value.traceNo+"&totalAmount="+alipayData.value.totalAmount)


}

const clearOrders=()=>{
  ordersStore.clearOrdersData()
}

onMounted(()=>{
  //判断订单的状态 然后来显示

  //请求地址
  getAddress()
  // clearOrders()
})
</script>

<style scoped>
.payBackground
{
  position:fixed;
  /*background-size:100% 100%;*/
  width:100vw;
  height:100vh;
  z-index:-1;
  background: -webkit-linear-gradient(top,#509ee8,#f6f6f6);
}
.bigBox
{
  width: 100vw;
  /*height: 100vh;*/
}
h3
{
  /*margin-top: 3vh;*/
  text-align: center;
  color: white;
  font-size: 4vw;
}

.modal-body h3
{
  color: black;
  text-align: left;
}
.addressText
{
  color: black;
  font-weight: bold;
}
.addressList
{
  display: flex;
  justify-content: space-between;
  /*background: #fed6e3;*/
  height: 8vh;
  line-height: 8vh;
}
.addressList .form-check
{
  /*background: #01b6fd;*/
}
.addressList .form-check input
{
  margin-top: 3vh;
  font-size: 5vw;
  /*margin-left: 1vw;*/
  margin-left:-2vw;
}
.addressList .addressText
{
  line-height: 4vh;
  flex: 1;
  margin-left: 5vw;
  margin-right: 7vw;
}
.addressList .addressEditIcon
{
  font-size: 5vw;
}
</style>