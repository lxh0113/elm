<template>
<div class="SearchBodyBack">
  <ul v-if="list?.length">
    <li v-for="item in list">
      <ContentItems :store="item">
      </ContentItems>
    </li>
  </ul>
  <template v-else>
    <el-empty description="无数据" />
  </template>

</div>
</template>

<script setup>
import ContentItems from '@/views/Search/components/ContentItems.vue'
import {ref,onMounted} from 'vue'

const props=defineProps({
  list:Array
})

onMounted(()=>{
  console.log(props.list)
})

</script>

<style scoped>
.SearchBodyBack
{
  width: 1160px;
  margin: 0 auto;
  /*height: 1000px;*/

  /*background-color: #fed6e3;*/
}
ul
{
  padding-left: 0;
  /*margin-left: 30px;*/
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
}
li
{
  list-style: none;
  margin-left: 20px;
  margin-bottom: 40px;
}
</style>