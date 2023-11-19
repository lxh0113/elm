<template>
  <div class="detailsBox">
    <ul>
      <li v-for="item in goodsList" :key="item.id">
        <ProductsItem @chooseSize="(id)=>chooseSize(id)" :good="item"></ProductsItem>
      </li>
    </ul>
  </div>

  <!-- Button trigger modal -->
<!--  <button ref="toChooseSize" style="display: none" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#chooseSize">-->
<!--    打开-->
<!--  </button>-->

  <!-- Button trigger modal -->
  <button ref="toChooseSize" style="display: none"  type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#chooseSize">
  </button>

</template>

<script setup>
import ProductsItem from '@/views/Menu/components/ProductsItem.vue'
import {ref,onMounted} from "vue";
import {useRoute,useRouter} from "vue-router";
import {getGoodsByCategoryAPI} from '@/apis/dish.js'
import {ElMessage} from "element-plus";
import {useGoodsStore} from "@/stores/goodsStore";

const router=useRouter()
const route=useRoute()
const goodsStore=useGoodsStore();
const toChooseSize=ref(null)

const goodsList=ref([])

const chooseSize=async (id)=>{
  console.log("点击了"+id)

  //获取到数据存储到
  const res=await goodsStore.setGoods(id);

  console.log("数据已经设置完毕")

  toChooseSize.value.click()
}

const getGoods=async ()=>{
  let storeId=route.params.id;
  let categoryId=route.params.categoryId;

  console.log(storeId,categoryId)

  const res=await getGoodsByCategoryAPI({storeId,categoryId})

  if(res.data.code===1)
  {
    goodsList.value=res.data.data
  }
  else
  {
    ElMessage.error("获取失败")
  }
}

onMounted(()=>{

  getGoods()

})
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