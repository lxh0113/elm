<template>
<div class="SearchBodyBack">
  <ul>
    <li v-for="item in 4">
      <ContentItems :store="store">

      </ContentItems>
    </li>
  </ul>
</div>
</template>

<script setup>
import ContentItems from '@/views/Search/components/ContentItems.vue'
import {ref} from 'vue'

const store=ref({
  id:'1',
  logoUrl:'../../../img/123.jpg',
  storeUrl:'../../../img/123.jpg',
  description:'很好吃',
  telephone:'1111',
  deliverStartFee:12,
  packageFee:1,
  addressId:'123',
  category:'美食',
  star:4.8,
  sales:200,
  name:'KFC',
  children:[
    {url:'../../../img/123.jpg',name:'芋泥奶茶',price:12},
    {url:'../../../img/123.jpg',name:'芋泥奶茶',price:12},
    {url:'../../../img/123.jpg',name:'芋泥奶茶',price:12}
  ]
})

</script>

<style scoped>
.SearchBodyBack
{
  width: 1160px;
  margin: 0 auto;
  height: 1000px;
  /*background-color: #fed6e3;*/
}
ul
{
  padding-left: 0;
  /*margin-left: 30px;*/
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
}
li
{
  list-style: none;
  margin-bottom: 40px;
}
</style>