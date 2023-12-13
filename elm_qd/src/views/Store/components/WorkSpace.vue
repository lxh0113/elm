<template>
<div>
  <div>
  <div class="today">
    <div class="header">
      <h2>今日数据 <small>{{getToday()}}</small></h2>
      <span>详细数据&gt;&gt;</span>
    </div>
    <div class="mainbody">
      <WorkItem v-for="item in todayData" style="flex:1" :title="item.title">
        <h1 class="h1title">{{item.text}}</h1>
      </WorkItem>
    </div>
  </div>

  </div>
    <div class="today">
      <div class="header">
        <h2>订单管理<small>2022/9/22</small></h2>
        <span>订单明细&gt;&gt;</span>
      </div>
      <div class="mainbody">
         <Item style="flex:1;">
           <p @click="$router.push('/store/order/details/1')" style="font-size: 20px"><i class="bi bi-file-earmark-plus"></i>&nbsp;&nbsp;待接单</p>
         </Item>
        <Item style="flex:1;">
          <p @click="$router.push('/store/order/details/3')" style="font-size: 20px"><i class="bi bi-hourglass-split"></i>&nbsp;&nbsp;待完成</p>
        </Item>
        <Item style="flex:1;">
          <p @click="$router.push('/store/order/details/4')" style="font-size: 20px"><i class="bi bi-file-check"></i>&nbsp;&nbsp;已完成</p>
        </Item>
        <Item style="flex:1;">
          <p @click="$router.push('/store/order/details/2')" style="font-size: 20px"><i class="bi bi-x-circle"></i>&nbsp;&nbsp;已取消</p>
        </Item>
        <Item style="flex:1;">
          <p @click="$router.push('/store/order/details/-1')" style="font-size: 20px"><i class="bi bi-file-text"></i>&nbsp;&nbsp;全部订单</p>
        </Item>
      </div>

    </div>

</div>
  <div style="display:flex">
    <div class="today" style="margin-right: 0px;margin-left: 15px">
      <div class="header">
        <h2> 菜品总览<small>2022/9/22</small></h2>
        <span>订单明细&gt;&gt;</span>
      </div>
      <div class="mainbody" style="margin-right: 0;">
        <div class="left">
          <Item style="flex: 2" @click="$router.push()"><p style="font-size: 20px"><i class="bi bi-x-circle"></i>&nbsp;&nbsp;已停售</p></Item>
          <Item style="flex: 2" @click="$router.push()"><p style="font-size: 20px"><i class="bi bi-check-circle"></i>&nbsp;&nbsp;已起售</p></Item>
          <AddNew><p style="font-size: 20px">新增&nbsp;<i class="bi bi-plus-circle"></i></p></AddNew>
        </div>
      </div>
    </div>
    <div class="today" style="margin-left: 0;margin-right: 15px">
      <div class="header">
        <h2> 分类总览<small>2022/9/22</small></h2>
        <span>分类明细&gt;&gt;</span>
      </div>
      <div class="mainbody">
        <div class="left">
          <Item style="flex: 2" @click="$router.push()"><p style="font-size: 20px"><i class="bi bi-x-circle"></i>&nbsp;&nbsp;已停售</p></Item>
          <Item style="flex: 2" @click="$router.push()"><p style="font-size: 20px"><i class="bi bi-check-circle"></i>&nbsp;&nbsp;已起售</p></Item>
          <AddNew><p style="font-size: 20px">新增&nbsp;<i class="bi bi-plus-circle"></i></p></AddNew>
        </div>

      </div>
    </div>
  </div>

  <div class="today orderTitle">
    <div class="header">
      <h2>订单信息<small>2022/9/22</small></h2>
<!--        <button>待接单</button>-->
<!--        <button>待派送</button>-->
        <ul class="chooseStatus">
          <li><router-link to="/store/workplace/1">待接单</router-link></li>
          <li><router-link to="/store/workplace/3">待派送</router-link></li>
        </ul>
    </div>
  </div>

  <div class="mainbody orderListBox">
    <router-view></router-view>
  </div>

<!--  新建菜品-->

<!--  新建分类-->

</template>

<script setup>
import WorkItem from '@/views/Store/components/WorkItem.vue'
import Item from '@/views/Store/components/Item.vue'
import AddNew from '@/views/Store/components/AddNew.vue'

const todayData=ref([
  {title:"营业额",text:"￥元"},
  {title:"有效订单",text:2},
  {title:"订单完成率",text:"12%"},
  {title:"平均客单价",text:"￥"},
  {title:"新增用户",text:1}
])
const getToday=()=>{
  const today = new Date();
  const year = today.getFullYear();
  const month = today.getMonth() + 1; // 月份从 0 开始，所以要加 1
  const day = today.getDate();

  // console.log(`${year}-${month}-${day}`);
  return `${year}-${month}-${day}`
}
</script>

<style scoped>
h2
{
  font-weight: bold;
}
.today
{
  width: 98%;
  height: 200px;
  margin:0 auto;
  /*background: #fed6e3;*/
}
.today .header
{
  display: flex;
  justify-content: space-between;
  margin-top: 15px;
  height: 50px;
}
.today .header h2
{
  line-height: 50px;
  margin-left: 20px;
}
.today .header span
{
  margin-right: 20px;
  line-height: 50px;
}
.orderTitle
{
  height: 100px;
}

.mainbody
{
  margin-bottom: 30px;
  margin-left: 20px;
  margin-right: 20px;
  display: flex;
}


.h1title
{
  line-height: 70px;
  height: 70px;
}

.disBox
{
  display: flex;
}
.left,.right
{
  flex: 1;
  display: flex;
}
.left
{
  margin-right: 20px;
}

/*.button*/
/*{*/
/*  display: flex;*/
/*  margin-right: 20px;*/
/*}*/
/*.button button*/
/*{*/
/*  padding: 3px 30px;*/
/*  background: #01b6fd;*/
/*  outline: none;*/
/*  border: 1px solid #eeeeee;*/
/*  color: white;*/
/*}*/

.today .header span
{
  margin-right: 50px;
}

.chooseStatus
{
  /*flex:1;*/
  display: flex;
  /*justify-content: left;*/
  margin-top: 40px;
  margin-left: 30px;
}
.chooseStatus li
{
  /*height: 30px;*/
  flex:1;
  line-height: 40px;
  list-style: none;
  width: 150px;
  height: 40px;
  text-align: center;
  /*background: #01b6fd;*/
  color: black;
  border:2px solid #eeeeee;
  font-weight: bold;
}
a
{
  text-decoration: none;
  color: black;
}
a.router-link-active
{
  display: block;
  background: #01b6fd;
  color: white;
}
.orderListBox
{
  min-height:500px;
}
</style>