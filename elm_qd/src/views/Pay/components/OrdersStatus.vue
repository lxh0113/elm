<template>
  <div style="height: 3vh"></div>
  <div class="returnBox" >
    <span>
      <i @click="$router.push(`menu/${storeStore.store.id}/order`)" class="bi bi-arrow-return-left"></i>
      返回
    </span>
  </div>
<!--  <div class="handInBox">-->
<!--&lt;!&ndash;    订单提交状态&ndash;&gt;-->
<!--    <h3><i class="bi bi-check2-circle"></i>&nbsp;&nbsp;已经提交...</h3>-->
<!--  </div>-->
<!--  v-else-if="ordersStore.orderData.status===1"-->
  <div class="waitPayBox" v-if="ordersStore.orderData.status===0">
    <h3>{{nowTime}}</h3>

    <h3><i class="bi bi-currency-exchange"></i>&nbsp;&nbsp;等待付款...</h3>
  </div>
  <div class="finishPayBox" v-else-if="ordersStore.orderData.status===1">
    <h3><i class="bi bi-cart-check"></i>&nbsp;&nbsp;已经支付，等待接单</h3>
  </div>

  <div class="cancelBox" v-else-if="ordersStore.orderData.status===2">
    <h3><i class="bi bi-x-circle"></i>&nbsp;&nbsp;订单已取消</h3>
  </div>


</template>

<script setup>
import {ref} from 'vue'
import {ElMessage} from "element-plus";
import {useOrdersStore} from "@/stores/ordersStore";
import {getUpdatedOrdersAPI} from "@/apis/orders";
import {useRouter,useRoute} from "vue-router";
import {useStoreStore} from "@/stores/storeStore";

const router=useRouter()
const route=useRoute()
const ordersStore=useOrdersStore()
const storeStore=useStoreStore()
// 获取HTML中的元素
// 将订单时间转换为日期对象

const nowTime=ref("15:00")

const targetTime = new Date(ordersStore.orderData.time);

const updateOrders=async ()=>{
  const orderId=ordersStore.getOrderData().id;
  console.log(""+orderId)
  const res=await getUpdatedOrdersAPI(orderId);
  if(res.data.code===1)
  {
    //获取成功
    ordersStore.setOrderData(res.data.data)

    if(ordersStore.getOrderData().status===0)
    {
      ElMessage.info("您的订单未支付")
    }
    else if(ordersStore.getOrderData().status===1)
    {
      ElMessage.success("已支付")
    }
    else if(ordersStore.getOrderData().status===2)
    {
      ElMessage.info("已取消")
      //清除订单数据
      ordersStore.setOrderData(null)
      router.push(`menu/${storeStore.store.id}/order`)
    }
    else if(ordersStore.getOrderData().status===3)
    {
      ElMessage.success("已接单")
    }
    else if(ordersStore.getOrderData().status===4)
    {
      ElMessage.success("已完成")
    }
    else if(ordersStore.getOrderData.status===7)
    {
      ElMessage.info("已退款")
    }

  }
  else
  {
    ElMessage.error(res.data.msg)
  }
}

function updateCountdown() {
  const currentTime = new Date(); // 获取当前时间的日期对象

  // 计算距离目标时间的剩余时间（毫秒）
  const timeDiff = targetTime.getTime() + 15 * 60 * 1000 - currentTime.getTime();

  // 将时间差转换为分钟和秒数
  const minutes = Math.floor((timeDiff % (1000 * 60 * 60)) / (1000 * 60));
  const seconds = Math.floor((timeDiff % (1000 * 60)) / 1000);

  if(minutes<=0&&seconds<=0)
  {
    //请求订单状态
    updateOrders()

    clearInterval(k)
    return
  }

  // 更新 ref 的值以显示倒计时
  nowTime.value = `${minutes} 分钟 ${seconds} 秒`;
}

// 更新倒计时
let k=setInterval(updateCountdown, 1000);

</script>

<style scoped>
.handInBox
{
  width: 95vw;
  height: 30vh;
  background: white;
  margin: 0 auto;
  border-radius: 5vw;
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
}
.returnBox
{
  height: 5vh;
  margin-left: 5vw;
  font-size: 20px;
  color: white;
  font-weight: bold;
}

h3
{
  font-size: 25px;
  color: green;
  font-weight: bold;
}

.waitPayBox
{
  width: 95vw;
  height: 30vh;
  background: white;
  margin: 0 auto;
  border-radius: 5vw;
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
}
.waitPayBox h3
{
  color:red;
  width: 100%;
}
.finishPayBox
{
  width: 95vw;
  height: 30vh;
  background: white;
  margin: 0 auto;
  border-radius: 5vw;
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
}
.finishPayBox h3
{
  color:#01b6fd;
}
.cancelBox
{
  width: 95vw;
  height: 30vh;
  background: white;
  margin: 0 auto;
  border-radius: 5vw;
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
}
.cancelBox h3
{
  color:gray;
}
</style>