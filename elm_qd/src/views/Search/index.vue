<template>
<div class="searchBackground">
  <LayoutNav></LayoutNav>
  <SearchNav/>
  <div class="searchTitle">
    <div class="TitleHead">
      <ul>
        <li>综合排序</li>
        <li>销量优先</li>
        <li>距离优先</li>
        <li>价格优先</li>
      </ul>
    </div>
    <SearchBody></SearchBody>
  </div>
  <LayoutFooter></LayoutFooter>
</div>
</template>

<script setup>

import LayoutHeader from '@/views/Layout/components/LayoutHeader.vue'
import LayoutNav from '@/views/Layout/components/LayoutNav.vue'
import LayoutFooter from '@/views/Layout/components/LayoutFooter.vue'
import SearchNav from "@/views/Search/components/SearchNav.vue";
import SearchBody from "@/views/Search/components/SearchBody.vue"
</script>

<style scoped>
.searchBackground
{
  background: #eeeeee;
}
.searchTitle
{
  background: white;
  width: 1240px;
  margin: 0 auto;
  margin-top: 30px;
  margin-bottom: 30px;
  border-radius: 30px;
  /*box-shadow: 0 3px 8px rgb(0,0,0,0.2);*/
}
.searchTitle ul
{
  display: flex;
  margin-left: 40px;
}
.searchTitle ul li
{
  list-style: none;
  width: 200px;
  height: 60px;
  /*background: #01b6fd;*/
  line-height: 60px;
  text-align: center;
  font-weight: bold;
}
</style>