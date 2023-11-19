<template>
<div>
  <div class="CategoryHead">
    <div class="categoryLeft">
      <label>分类名称：&nbsp;<input @keyup.enter="searchCategory" v-model="categoryData.name" type="text" placeholder="请输入分类名称"></label>
      <button @click="searchCategory">查询</button>
    </div>
    <div class="categoryRight">
      <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
        +新增分类
      </button>
    </div>
  </div>
  <div class="categoryTable">
    <table>
      <thead>
        <tr>
          <td>分类名称</td>
          <td>排序</td>
          <td>状态</td>
          <td>操作</td>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item,index) in categoryList" :key="item">
          <td>
            {{item.name}}
          </td>
          <td>
            <i @click="upSort(index)" style="font-size: 2rem" v-show="index!==0" class="greenText bi bi-arrow-up-circle-fill"></i>
            <i @click="downSort(index)" style="font-size: 2rem" v-show="index!==categoryList.length-1" class="redText bi bi-arrow-down-circle-fill"></i>
          </td>
          <td>
            <span class="greenText" v-if="!item.status" @click="changeCategoryStatus(index)">启用</span>
            <span class="grayText" v-else @click="changeCategoryStatus(index)">禁用</span>
          </td>
          <td>
            <span class="greenText" @click="toClickCategory(index)">修改</span>
            <span class="redText" @click="deleteCategory(index)">删除</span>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</div>


  <!-- Modal -->
  <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="staticBackdropLabel">新建分类</h5>
          <button type="button" class="btn-close" style="background: white;color:black;border:0;"  data-bs-dismiss="modal" aria-label="Close">X</button>
        </div>
        <div class="modal-body">
          <div class="mb-3 row">
            <label for="inputPassword" style="line-height: 55px;margin-left: 20px;" class="col-sm-3 col-form-label">分类名称</label>
            <div class="col-sm-7">
              <input v-model="categoryData.name" style="margin-top: 15px" type="text" class="form-control" id="inputPassword">
            </div>
          </div>
        <div class="modal-footer">
          <button type="button" ref="closeAddButton" style="background: #6c757d;border: 0;" class="btn btn-secondary" data-bs-dismiss="modal">关闭</button>
          <button type="button" class="btn btn-primary" @click="addNewCategory">添加</button>
        </div>
      </div>
    </div>
  </div>
  </div>

  <!-- Button trigger modal -->
  <button type="button" ref="modifyCategoryButton" style="display: none;" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#changeStatus">
  </button>

  <!-- Modal -->
  <div class="modal fade" id="changeStatus" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">修改分类</h5>
          <button type="button" class="btn-close" style="background: white;color:black;border:0;"  data-bs-dismiss="modal" aria-label="Close">X</button>
        </div>
        <div class="modal-body">
          <div class="mb-3 row">
            <label for="inputPassword" style="line-height: 55px;margin-left: 20px;" class="col-sm-3 col-form-label">分类名称</label>
            <div class="col-sm-7">
              <input v-model="categoryData.name" style="margin-top: 15px" type="text" class="form-control" id="inputPassword">
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button ref="closeModifyButton" type="button" style="background: #6c757d;border: 0;" class="btn btn-secondary" data-bs-dismiss="modal">关闭</button>
          <button @click="modifyCategoryName" type="button" class="btn btn-primary">确定</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import 'bootstrap-icons/font/bootstrap-icons.min.css'
import {ref} from 'vue'
import {useUserStore} from "@/stores/userStore.js";
import {getAllCategoryAPI,addNewCategoryAPI,deleteCategoryAPI,changeCategoryAPI} from "@/apis/category";
import {ElMessage} from "element-plus";
import {exchangeSortAPI,searchCategoryAPI} from "@/apis/category";

const userStore=useUserStore();

let categoryData=ref({
  id:'',
  storeId:userStore.user.id,
  sort:0,
  name:'',
  status:1
})

const categoryList=ref([])
const modifyCategoryButton=ref(null)
const closeAddButton=ref(null)
const closeModifyButton=ref(null)
let curIndex=0;

const addNewCategory=async ()=>{

  console.log(categoryData.value)

  for (let i = 0; i < categoryList.value.length; i++) {
    if(categoryData.value===categoryList.value[i].name)
    {
      ElMessage.error("当前名称已经存在！请换一个名称")
      return
    }
  }

  //给排序字段赋值
  categoryData.value.sort=categoryList.value.length

  const res=await addNewCategoryAPI(categoryData.value);

  if(res.data.code===1)
  {
    categoryList.value=res.data.data;
    ElMessage.success("添加成功")
    //置空 + 赋值
    categoryData.value.name=''
  }
  else ElMessage.error(res.data.msg)

  closeAddButton.value.click()
}

const getAllCategory=async (index)=>{
  const res=await getAllCategoryAPI({storeId:userStore.user.id})

  if(res.data.code===1)
  {
    categoryList.value=res.data.data
  }
  else ElMessage.error(res.data.msg)
}

const deleteCategory=async (index)=>{

  let r=confirm("您确认要删除当前这条数据吗")
  if(r===false) return

  let data=categoryList.value[index]
  const res=await deleteCategoryAPI(data)

  if(res.data.code===1)
  {
    categoryList.value=res.data.data
  }
  else ElMessage.error(res.data.msg)
}

//更改 分类状态的
const changeCategoryStatus=async (index)=>{

  let res;

  if(categoryList.value[index].status===1)
  {
    let data=categoryList.value[index];
    data.status=0;
    res=await changeCategoryAPI(data)
  }
  else if(categoryList.value[index].status===0)
  {
    let data=categoryList.value[index];
    data.status=1;
    res=await changeCategoryAPI(data)
  }

  if(res.data.code===1)
  {
    categoryList.value=res.data.data
  }
  else ElMessage.error(res.data.msg)
}


const upSort=async (index)=>{
  if(index===0)
  {
    ElMessage.error("不能往上移动了哦")
  }
  else
  {
    //交换
    let data=[categoryList.value[index-1],categoryList.value[index]]
    const res=await exchangeSortAPI(data)

    if(res.data.code===1)
    {
      categoryList.value=res.data.data
      ElMessage.success("更改成功")
    }
    else ElMessage.error(res.data.msg)
  }
}

const downSort=async (index)=>{
  if(index===categoryList.value.length-1)
  {
    ElMessage.error("不能往下移动了哦")
  }
  else
  {
    let data=[categoryList.value[index],categoryList.value[index+1]]
    const res=await exchangeSortAPI(data);

    if(res.data.code===1)
    {
      categoryList.value=res.data.data
      ElMessage.success("更改成功")
    }
    else ElMessage.error(res.data.msg)
  }
}

const toClickCategory=(index)=>{
  curIndex=index
  categoryData.value.name=categoryList.value[curIndex].name;
  modifyCategoryButton.value.click();
}

const modifyCategoryName=async ()=>{
  //修改名字
  if(categoryData.value.name===categoryList.value[curIndex].name)
  {
    //说明相等
    ElMessage.error("你没有做出更改")
  }
  else
  {
    for (let i = 0; i < categoryList.value.length; i++) {
      if(categoryData.value.name===categoryList.value[i].name)
      {
        ElMessage.error("您输入了重复的分类")
        return
      }
    }

    let data=categoryList.value[curIndex];
    data.name=categoryData.value.name;

    const res=await changeCategoryAPI(data)
    if(res.data.code===1)
    {
      categoryList.value=res.data.data
      ElMessage.success("更改成功")
    }
    else
    {
      ElMessage.error("更改失败")
    }
    categoryData.value.name=''
  }

  closeModifyButton.value.click()
}


const searchCategory=async ()=>{
  categoryData.value.name=categoryData.value.name.trim();

  const res=await searchCategoryAPI(categoryData.value);

  if(res.data.code===1)
  {
    //查询成功
    categoryList.value=res.data.data
  }
  else
  {
    ElMessage.error(res.data.msg)
  }

  // categoryData.value.name=''
}

onMounted(()=>{
  getAllCategory()
})
</script>

<style scoped>
.CategoryHead
{
  margin-left: 30px;
  margin-top: 40px;
  height: 60px;
  width: 97%;
  line-height: 60px;
  /*background: #fed6e3;*/
  display: flex;
  justify-content: space-between;
}
label
{
  height: 60px;
  line-height: 60px;
  text-align: center;
  margin-left: 40px;
}
label input
{
  width: 150px;
  height: 42px;
  /*line-height: 0;*/
  /*margin-top: 0px;*/
  padding-left: 10px;
  outline: none;
  border: 1px solid #eeeeee;
}
button {
  padding: 8px 20px;
  outline: none;
  margin-left: 30px;
  color: white;
  background: #01b6fd;
  border-radius: 10px;
  border: 1px solid #01b6fd;
  font-weight: bold;
  font-size: 18px;
  line-height: 25px;
}
table
{
  margin-left: 60px;
  margin-top: 30px;
  border-collapse: collapse;
  /*box-shadow: 0 3px 8px rgb(0,0,0,0.2);*/
}
thead
{
  /*background: #01b6fd;*/
  /*color: white;*/
}
thead tr td
{
  font-weight: bold;
  font-size: 20px;
  height: 70px;
  line-height: 70px;
}
tr
{
  display: flex;
  width: 1400px;
  justify-content: space-evenly;
  border-bottom: 2px solid #f1f1f1;
}
td
{
  flex:1;
  text-align: center;
  height: 60px;
  line-height: 60px;
  display: flex;
  justify-content: center;
  /*border:1px solid #eee;*/
}
/*tbody tr:nth-child(odd){*/
/*  background-color: #eee;*/
/*}*/
/*tbody tr:nth-child(odd) td*/
/*{*/
/*  border-left:1px solid white;*/
/*  border-right:1px solid white;*/
/*}*/
.categoryRight button:last-child
{
  margin-right: 40px;
  background: #eeeeee;
  color: #01b6fd;
}
.grayText,
.greenText,
.redText
{
  /*font-weight: bold;*/
  font-style: italic;
  /*font-weight: bold;*/
  /*cursor: grab;*/
  font-size: 16px;
  margin: 0 15px;
}
.greenText
{
  color: green;
}
.redText
{
  color: red;
}
.grayText
{
  color: gray;
}
</style>