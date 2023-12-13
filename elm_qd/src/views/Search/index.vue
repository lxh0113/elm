<template>
<div class="searchBackground">
  <LayoutNav></LayoutNav>
  <SearchNav :search-text="searchText" @toSearch="(value)=>toSearch(value)" />
  <div class="searchTitle">
    <div class="chooseCategory">
      <ul>
        <li @click="()=>status=true" :class="{activeStatus:status}">
          店铺
        </li>
        <li :class="{activeStatus:!status}" @click="()=>status=false">
          商品
        </li>
      </ul>
    </div>
    <div class="TitleHead">
      <ul>
        <li @click="()=>choose=1" :class="{activeChoose:choose===1}">综合排序</li>
        <li @click="()=>choose=2" :class="{activeChoose:choose===2}">销量优先</li>
        <li @click="()=>choose=3" :class="{activeChoose:choose===3}">距离优先</li>
        <li @click="()=>choose=4" :class="{activeChoose:choose===4}">价格优先</li>
      </ul>
    </div>
    <SearchBody :list="searchListData"></SearchBody>
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
import {ref,onMounted,watch} from 'vue'
import {useRouter,useRoute} from 'vue-router'
import {searchAPI} from "@/apis/search.js";
import {ElMessage} from "element-plus";

const searchText=ref('')
const route=useRoute()
const router=useRouter()
const status=ref(true)
const choose=ref(1)
const searchListData=ref()
const pageData=ref()

const toSearch=async (text)=>{
  // alert(text)
  if(text.trim()==='') ElMessage.error("你没有输入任何内容")
  const res=await searchAPI(text,status.value,choose.value);
  if(res.data.code===1)
  {
    // console.log(res.data)
    pageData.value=res.data.map.page
    searchListData.value=res.data.data
  }
  else ElMessage.error(res.data.msg)
}

const initSearchText=()=>{
  if(route.params.searchText!==null)
  {
    searchText.value=route.params.search
    console.log(searchText.value)
  }
}

// const fn=(value)=>{
//   searchText.value=value
// }


onMounted(()=>{
  initSearchText()
  toSearch(searchText.value)
})
</script>

<style scoped>
.searchBackground
{
  background: #eeeeee;
}
ul
{
  padding-left: 0;
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
.chooseCategory ul
{
  display: flex;
  margin-right: 50px;
}
.chooseCategory ul li
{
  flex:1;
  font-size: 15px;
}
.activeChoose
{
  /*background-color: #01b6fd;*/
  color:#02b7fb;
  font-size: 16px;
}
.activeStatus
{
  background-color: #02b7fb;
  color:white;
  font-size: 17px;
}
</style>