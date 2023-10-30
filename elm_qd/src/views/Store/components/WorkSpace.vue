<template>
<div>
  <div>
  <div class="today">
    <div class="header">
      <h2>今日数据 <small>2022/9/22</small></h2>
      <span>详细数据&gt;&gt;</span>
    </div>
    <div class="mainbody">
      <WorkItem v-for="item in 5" style="flex:1" :title="Text">
        <h1 class="h1title">￥</h1>
      </WorkItem>
    </div>
  </div>

  </div>
    <div class="today">
      <div class="header">
        <h2> 订单管理<small>2022/9/22</small></h2>
        <span>订单明细&gt;&gt;</span>
      </div>
      <div class="mainbody">
          <Item v-for="item in 5" style="flex:1;">
            <p>你好</p>
          </Item>
      </div>

    </div>

</div>
  <div class="today">
    <div class="header">
      <h2> 菜品总览<small>2022/9/22</small></h2>
      <span>订单明细&gt;&gt;</span>
    </div>
    <div class="mainbody">
      <div class="left">
        <Item v-for="item in 2" style="flex: 2"><p>已停售</p></Item>
        <AddNew><p>新增+</p></AddNew>
      </div>
      <div class="right">
        <Item v-for="item in 2" style="flex: 2"><p>已经起售</p></Item>
        <AddNew><p>新增+</p></AddNew>
      </div>

    </div>

  </div>

  <div class="today">
    <div class="header">
      <h2> 订单信息<small>2022/9/22</small></h2>
      <div class="button">
        <button>待接单</button>
        <button>待派送</button>
      </div>
    </div>
    <div class="mainbody">

    </div>

  </div>


</template>

<script setup>
import WorkItem from '@/views/Store/components/WorkItem.vue'
import Item from '@/views/Store/components/Item.vue'
import AddNew from '@/views/Store/components/AddNew.vue'
const Text=ref("营业额")
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

.button
{
  display: flex;
  margin-right: 20px;
}
.button button
{
  padding: 3px 30px;
  background: #01b6fd;
  outline: none;
  border: 1px solid #eeeeee;
  color: white;
}
</style>