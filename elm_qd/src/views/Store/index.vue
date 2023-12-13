<template>
  <StoreHeader></StoreHeader>
  <div class="back">
    <div class="bigBox">
      <div class="left">
        <ul>
          <li><RouterLink to="/store/workplace">工作台</RouterLink></li>
          <li><RouterLink to="/store/data">数据统计</RouterLink></li>

          <li><RouterLink to="/store/message">消息管理</RouterLink></li>
          <li><RouterLink to="/store/order">订单管理</RouterLink></li>

<!--          <li><RouterLink to="/store/combo">套餐管理</RouterLink></li>-->
          <li><RouterLink to="/store/dish">菜品管理</RouterLink></li>
          <li><RouterLink to="/store/category">分类管理</RouterLink></li>
          <li><RouterLink to="/store/info">商家信息</RouterLink></li>
        </ul>
      </div>
      <div class="right">
        <RouterView></RouterView>
      </div>
    </div>
  </div>
  <!-- Button trigger modal -->
<!--  <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop">-->
<!--    Launch static backdrop modal-->
<!--  </button>-->


  <StoreFooter></StoreFooter>

</template>

<script setup>
import StoreHeader from '@/views/Store/components/StoreHeader.vue'
import StoreFooter from '@/views/Store/components/StoreFooter.vue'
</script>

<style scoped>
.back
{
  width: 100%;
  min-width: 1857px;
  height: 100%;
  background: #e8edea;
}
.bigBox
{
  width: 100%;
  min-width: 1857px;
  height: 100%;
  margin: 0 auto;
  display: flex;
  align-items: stretch;
}

.bigBox .left
{
  margin-top: 10px;
  background: #01b6fd;
  width: 400px;
}
ul
{
  padding-left: 0;
}
.bigBox .left li
{
  box-sizing: border-box;
  color: white;
  height:120px;
  text-align: center;
  list-style: none;
  line-height: 120px;
  font-size: 20px;
}
a
{
  text-decoration: none;
  color: white;
}
a.router-link-active
{
  /*width: 90%;*/
  /*height: 110px;*/
  /*margin:0 auto;*/
  /*margin-top: 10px;*/
  /*background: white;*/
  /*color: #01b6fd;*/
}

.right
{
  width: 100%;
  /*flex:1;*/
  margin:10px;
  background: white;
}


</style>