<template>
<div>
  <div class="orderBox">
    <div class="leftCategory">
      <ul>
        <li v-for="item in categoryDataList">
          <router-link :to="'/menu/' + $route.params.id + '/order/details'+'/'+item.id">
            {{item.name}}
          </router-link>
        </li>
      </ul>
    </div>
    <div class="rightOrder">
      <keep-alive>
        <router-view :key="$route.fullPath" />
      </keep-alive>
<!--      <RouterView>-->
<!--        <MenuDetails :key="$route.params.categoryId" />-->
<!--      </RouterView>-->
    </div>
  </div>
</div>
</template>

<script setup>
import {ref,onMounted} from "vue";
import {getAllCategoryAPI} from '@/apis/category.js'
import {useRoute,useRouter} from "vue-router";
import {ElMessage} from "element-plus";
import MenuDetails from "@/views/Menu/components/MenuDetails.vue"

const router=useRouter()
const route=useRoute()
const categoryDataList=ref([])

const getCategory=async ()=>{
  let storeId=route.params.id;
  const res=await getAllCategoryAPI({storeId})

  if(res.data.code===1)
  {
    categoryDataList.value=res.data.data
  }
  else
  {
    ElMessage.error(res.data.msg)
  }
}

onMounted(()=>{
  getCategory()

  router.push("")
})
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