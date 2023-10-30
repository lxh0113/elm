<template>
  <CategoryContainer :title="title" :sub-title="sbuTitle">
    <ul>
      <li v-for="item in 4" :key="item">
        <GoodsItem :good="good"></GoodsItem>
      </li>
    </ul>
  </CategoryContainer>
  <CategoryContainer :title="title" :sub-title="sbuTitle">
    <ul>
      <li v-for="item in 4" :key="item">
        <GoodsItem :good="good"></GoodsItem>
      </li>
    </ul>
  </CategoryContainer>
</template>

<script setup>
import {ref} from 'vue'
import CategoryContainer from '@/views/Layout/components/CategoryContainer.vue'
import GoodsItem from '@/views/Layout/components/GoodsItem.vue'

const good=ref({
    id:'12',
    logo_url:'./src/img/slider/1.png',
    name:'KFC',
    star:4.8,
    sales:3000,
    deliver_start_fee:20,
    description:'非常好吃好吃',
    category:'西餐'
})

const title=ref("附近")
const sbuTitle=ref("你好")
</script>

<style scoped>

ul
{
  background: white;
  display: flex;
  flex-wrap: wrap;
  padding-left: 0px;
}
li
{
  background: white;
  list-style: none;
  margin:0 10px;
}
</style>