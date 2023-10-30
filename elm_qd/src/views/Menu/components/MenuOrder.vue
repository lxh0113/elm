<template>
<div>
  <div class="orderBox">
    <div class="leftCategory">
      <ul>
        <li v-for="item in 6">
          <router-link :to="'/menu/' + $route.params.id + '/order/details'">
            老板推荐
          </router-link>
        </li>
      </ul>
    </div>
    <div class="rightOrder">
      <RouterView></RouterView>
    </div>
  </div>
</div>
</template>

<script setup>

</script>

<style scoped>
.orderBox
{
  display: flex;
}
.leftCategory
{
  flex: 2;
  /*height: 1000px;*/
  background: #f1f1f1;
  border-radius: 30px;
}
ul
{
  padding-left: 0px;
}
ul li
{
  list-style: none;
  height: 100px;
  text-align: center;
  /*border-bottom: 1px solid #ccc;*/
}
a
{
  color:#666666;
  text-decoration: none;
  line-height: 100px;
  font-weight: bold;
}
.rightOrder
{
  flex: 7;
  /*height: 1000px;*/
  /*background: #01b6fd;*/

  margin-left: 30px;
}
</style>