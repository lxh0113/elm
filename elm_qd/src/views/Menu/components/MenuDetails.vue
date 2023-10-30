<template>
  <div class="detailsBox">
    <ul>
      <li v-for="item in 8">
        <ProductsItem></ProductsItem>
      </li>
    </ul>
  </div>
</template>

<script setup>
import ProductsItem from '@/views/Menu/components/ProductsItem.vue'
</script>

<style scoped>
.detailsBox
{
  width: 830px;
  margin: 0 auto;
}
ul
{
  display: flex;
  flex-wrap: wrap;
  padding-left: 0px;
}
li
{
  list-style: none;
}

</style>