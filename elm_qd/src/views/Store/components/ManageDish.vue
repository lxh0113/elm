<template>
  <div>
    <div class="dishHead">
      <div class="dishLeft">
        <label>菜品名称：&nbsp;<input @keyup.enter="toSearch" v-model="searchTextData.name" type="text" placeholder="请输入分类名称"></label>
        <label>菜品分类：&nbsp;
          <select style="width: 150px" class="myselect" v-model="searchTextData.categoryId" @change="toSearch">
            <option selected value="0">全部</option>
            <option v-for="item in categoryList" :value="item.id">{{item.name}}</option>
          </select>
        </label>
        <label>售卖状态：&nbsp;
          <select style="width: 150px" class="myselect" v-model="searchTextData.status" @change="toSearch">
            <option selected value="2">全部</option>
            <option value="1">正常</option>
            <option value="0">停售</option>
          </select>
        </label>

        <button @click="toSearch">查询</button>
      </div>
      <div class="dishRight">
        <button @click="multiDel">+批量删除</button>
        <!-- Button trigger modal -->
        <button type="button" class="btn btn-primary" ref="openModal" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
          新建菜品+
        </button>
      </div>
    </div>
    <div class="dishFooter">
      <table>
        <thead>
        <tr>
          <td class="checkBox">
            <div class="form-check">
              <input @click="bigCheck" class="form-check-input" type="checkbox" v-model="isSelected">
            </div>
          </td>
          <td>菜品名称</td>
          <td>图片</td>
          <td>菜品分类</td>
          <td>售价</td>
          <td>售卖状态</td>
          <td>操作</td>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(item,index) in dishesList" :key="item.id">
          <td class="checkBox">
            <div class="form-check">
              <input class="form-check-input" type="checkbox" v-model="item.isSelected">
            </div>
          </td>
          <td>
            {{item.name}}
          </td>
          <td class="dishImg">
            <img :src="item.url" alt="">
          </td>
          <td>
            {{getCategoryName(index)}}
          </td>
          <td>
            <span class="price">￥{{item.price}}</span>
          </td>
          <td>
            <span v-if="item.status" @click="changeGoodsStatus(index)" class="greenText">正常</span>
            <span v-else @click="changeGoodsStatus(index)" class="grayText">停售</span>
          </td>
          <td>
            <span class="greenText" @click="toModifyModal(index)">修改</span>
            <span class="redText" @click="deleteGoods(index)">删除</span>
          </td>
        </tr>
        </tbody>
      </table>
    </div>

    <nav style="margin-top: 40px" aria-label="Page navigation example">
      <ul class="pagination justify-content-center">
        <li class="page-item" :class="{disabled:pageData.current===1}" @click="choosePages(1)">
          <a class="page-link">首页</a>
        </li>
        <li v-for="(item,index) in pageData.pages>3?3:pageData.pages" @click="choosePages(index+1)" class="page-item"><a class="page-link" href="#">{{index+1}}</a></li>
        <li class="page-item" :class="{disabled:pageData.current===pageData.pages}" @click="choosePages(pageData.pages)">
          <a class="page-link" href="#">末尾</a>
        </li>
      </ul>
    </nav>
  </div>

  <!-- Modal -->
  <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="staticBackdropLabel">{{modalTitle}}</h5>
          <button @click="setOriginData" type="button" class="btn-close" style="background: white;color:black;border:0;"  data-bs-dismiss="modal" aria-label="Close">X</button>
        </div>
        <div class="modal-body">
          <div class="mb-3 row">
            <label for="dishName" style="line-height: 55px;margin-left: 20px;" class="col-sm-3 col-form-label">菜品名称</label>
            <div class="col-sm-7">
              <input v-model="dishData.name" style="margin-top: 15px" type="text" class="form-control" id="dishName">
            </div>
          </div>
          <div class="mb-3 row">
            <label for="dishPrice" style="line-height: 55px;margin-left: 20px;" class="col-sm-3 col-form-label">菜品价格</label>
            <div class="col-sm-7">
              <input v-model="dishData.price" style="margin-top: 15px" type="number" class="form-control" id="dishPrice">
            </div>
          </div>
          <div class="mb-3 row">
            <label for="dishCategory" style="line-height: 55px;margin-left: 20px;" class="col-sm-3 col-form-label">菜品分类</label>
            <div class="col-sm-7">
              <select v-model="dishData.categoryId" style="margin-top: 15px" class="form-control" id="dishCategory">
                <option v-for="item in categoryList" :value="item.id">{{item.name}}</option>
              </select>
            </div>
          </div>

          <div class="mb-3 row">
            <label for="dishImage" style="line-height: 150px;margin-left: 20px;" class="col-sm-3 col-form-label">菜品图片</label>
            <div class="col-sm-7">
              <img class="dishImage" ref="dishImage" @click="toChoose" :src="dishData.url" alt="图片未加载">
              <input type="file" id="dishImage" @change="uploadImage" ref="dishInput" style="display: none">
            </div>
          </div>

          <div class="mb-3 row">
            <label style="margin-left: 20px;" class="col-sm-3 col-form-label">菜品口味</label>
            <div class="col-sm-7">
              <ul>
                <li v-for="item in flavorsList" :key="item">
                  <label class="flavorTitle">{{item.name}}</label>

                  <i @click="()=>{isShowModifyName=!isShowModifyName}" title="编辑" style="margin-left: 20px;color: #01b6fd;font-weight: bold;font-size: 20px;" class="bi bi-pencil-square"></i>
                  <i title="删除" @click="delFlavorsName(item.name)" style="margin-left: 20px;color: #01b6fd;font-weight: bold;font-size: 20px;" class="bi bi-trash3"></i>
                  <div v-show="isShowModifyName" class="editFlavorsName">
                    <input type="text" placeholder="请输入名字">
                    <button @click="()=>{isShowModifyName=false}">确认</button>
                  </div>
                  <div class="total">
                    <div class="flavorItem" v-for="(optionsItem,index) in item.flavors" :key="optionsItem">
                      <span>{{optionsItem.options}}</span>
                      <i @click="modifyFlavors(optionsItem,index)" title="编辑" class="editFlavors bi bi-pencil-square" data-bs-target="#exampleModalToggle2" data-bs-toggle="modal" data-bs-dismiss="modal"></i>
                      <i title="删除" @click="deleteFlavors(optionsItem)" class="deleteFlavors bi bi-x-circle-fill"></i>
<!--                      <span @click="deleteFlavors(optionsItem)" class="closeFlavors">X</span>-->
                    </div>
                    <div class="flavorItemADD">
                      <i @click="addOptions(item.name)" data-bs-target="#exampleModalToggle2" data-bs-toggle="modal" data-bs-dismiss="modal" class="bi bi-plus-square-dotted"></i>
                    </div>
                  </div>
                </li>
              </ul>
              <button class="addNewFlavors btn btn-primary" data-bs-target="#exampleModalToggle2" data-bs-toggle="modal" data-bs-dismiss="modal">新增选项</button>
            </div>
          </div>

          <div class="mb-5 row" >
            <label for="dishDescri" style="line-height: 55px;margin-left: 20px;" class="col-sm-3 col-form-label">菜品描述</label>
            <div class="col-sm-7">
              <textarea id="dishDescri" v-model="dishData.description" style="margin-top: 15px;height: 200px;resize: none;" type="text" class="form-control"></textarea>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button @click="setOriginData" type="button" ref="closeAddDish" style="background: #6c757d;border: 0;" class="btn btn-secondary" data-bs-dismiss="modal">关闭</button>
          <button type="button" class="btn btn-primary" @click="chooseToModal">{{modalButtonText}}</button>
        </div>
      </div>
    </div>
  </div>
  <div class="modal fade" id="exampleModalToggle2" aria-hidden="true" aria-labelledby="exampleModalToggleLabel2" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalToggleLabel2">{{secondModlTitle}}</h5>
          <button type="button" class="btn-close" style="background: white;color:black;border:0;"  data-bs-dismiss="modal" aria-label="Close">X</button>
        </div>
        <div class="modal-body">

          <div class="mb-3 row">
            <label for="flavorsName" style="line-height: 55px;margin-left: 20px;" class="col-sm-3 col-form-label">口味名称</label>
            <div class="col-sm-7">
              <input v-model="flavorsData.name" style="margin-top: 15px" type="text" class="form-control" placeholder="甜度(不能超过10个词哦)" id="flavorsName">
            </div>
          </div>

          <div class="mb-3 row">
            <label for="flavorsOptions" style="line-height: 55px;margin-left: 20px;" class="col-sm-3 col-form-label">具体口味</label>
            <div class="col-sm-7">
              <input v-model="flavorsData.options" style="margin-top: 15px" type="text" class="form-control" placeholder="五分糖(不能超过12个词哦)" id="flavorsOptions">
            </div>
          </div>

          <div class="mb-3 row">
            <label for="flavorsPrice" style="line-height: 55px;margin-left: 20px;" class="col-sm-3 col-form-label">口味价格</label>
            <div class="col-sm-7">
              <input v-model="flavorsData.price" style="margin-top: 15px" type="text" class="form-control" placeholder="" id="flavorsPrice">
            </div>
          </div>

        </div>
        <div class="modal-footer">
          <button @click="clearData" type="button" class="btn btn-secondary" style="background: #6c757d;border: 0;" data-bs-target="#staticBackdrop" data-bs-toggle="modal" data-bs-dismiss="modal">取消</button>
          <button @click="addNewFlavors" class="btn btn-primary" data-bs-target="#staticBackdrop" data-bs-toggle="modal" data-bs-dismiss="modal">{{secondModalButtonText}}</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {computed, onMounted, ref} from 'vue'
import {uploadImgAPI} from "@/apis/upload";
import {ElMessage} from "element-plus";
import {useUserStore} from "@/stores/userStore";
import {addNewDishAPI, deleteDishAPI, getAllDishesAPI, modifyDishAPI, mulDeleteDishAPI,searchDishAPI} from "@/apis/dish";
import {getAllCategoryAPI} from "@/apis/category";
import {addNewFlavorsAPI,getAllFlavorsAPI,changeFlavorsAPI,deleteFlavorsAPI,deleteAllFlavorsByNameAPI} from "@/apis/flavors";

const dishInput=ref(null)
const dishImage=ref(null)
const closeAddDish=ref(null)
const openModal=ref(null)
const userStore=useUserStore();
const modalTitle=ref("新建菜品")
const modalButtonText=ref("添加")
const secondModlTitle=ref("新建口味")
const secondModalButtonText=ref("新增")
const isShowModifyName=ref(false)

const toChoose=async ()=>{
  console.log(dishInput)
  dishInput.value.click();
}

const dishData=ref({
  id:'',
  name:'',
  url:'http://localhost:8081/img/121.jpg',
  status:2,
  description:'',
  categoryId:0,
  price:0,
  current:1,
  storeId:userStore.user.id
})

let searchTextData=ref({
  name:'',
  categoryId:0,
  current:1,
  status:2,
  storeId:userStore.user.id
})

const categoryList=ref([])

const dishesList=ref([])

const flavorsData=ref({
  name:'',
  options:'',
  price:0,
  id:0,
  goodsId:0
})

const flavorsList=ref([

])

const getCategoryList=async ()=>{
  const res=await getAllCategoryAPI({storeId:userStore.user.id});

  if(res.data.code===1)
  {
    categoryList.value=res.data.data
  }
  else ElMessage.error(res.data.msg)
}

const pageData=ref({
  countId:0,
  current:1,
  pages:1,
  size:7,
  total:0
})

const getCategoryName=(index)=>{
  let id=dishesList.value[index].categoryId;
  const thisItem = categoryList.value.find((item)=>{
    if(item.id===id) return item
  })
  return thisItem.name
}

const setOriginPages=()=> {
  const activePageItem = document.querySelector(`.page-item:nth-child(2)`);
  if (activePageItem) {
    activePageItem.classList.add("active");
    activePageItem.setAttribute("aria-current", "page");
  }
}

const uploadImage=async ()=>{
  var file = dishInput.value.files[0];
  var imageRegex = /\.(jpeg|jpg|png|gif)$/i;

  console.log(file)

  if(imageRegex.test(file.name))
  {
    var imageUrl = URL.createObjectURL(file);
    console.log("这是一个图片")

    let data = new FormData(); //创建form对象
    data.append('file',file);//通过append向form对象添加数
    const res=await uploadImgAPI(userStore.user.id,data)

    console.log(res)

    if(res.data.code===1)
    {
      ElMessage.success("上传成功！")
      dishImage.value.src=res.data.data

      dishData.value.url=dishImage.value.src
    }
    else ElMessage.error(res.data.msg)

  }
  else ElMessage.error('您选择的不是一个图片！')
}

const setOriginData=()=>{
  dishData.value={
    id:'',
    name:'',
    url:'http://localhost:8081/img/121.jpg',
    status:2,
    description:'',
    categoryId:0,
    price:'',
    current:1,
    storeId:userStore.user.id
  }
  flavorsList.value=[]

  modalTitle.value="新建菜品"
  modalButtonText.value="添加"
}

const getAllDish=async ()=>{
  //获取到所有的菜品  根据 store_id
  const res=await getAllDishesAPI({storeId:userStore.user.id})

  if(res.data.code===1)
  {
    console.log(res)
    dishesList.value=res.data.data.records
    setOriginPages()
    pageData.value=res.data.data;
  }
  else ElMessage.error(res.data.msg)
}

const addNewDish=async ()=>{

  //新加菜单
  let data={
    goods:{
      url:dishImage.value.src,
      status:1,
      description:dishData.value.description,
      categoryId:dishData.value.categoryId,
      name:dishData.value.name,
      price:dishData.value.price,
      storeId:userStore.user.id
    },
    flavorsListData:flavorsList.value
  }

  if(data.price<0)
  {
    ElMessage.error("价格错误")
    return
  }

  const res=await addNewDishAPI(data)

  if(res.data.code===1)
  {
    console.log(res)
    dishesList.value=res.data.data.records
    setOriginPages()
    pageData.value=res.data.data

    ElMessage.success("添加成功")
  }
  else
  {
    ElMessage.error(res.data.msg)
  }

  dishData.value={
    id:'',
    name:'',
    url:'http://localhost:8081/img/121.jpg',
    status:2,
    description:'',
    categoryId:0,
    price:'',
    current:1,
    storeId:userStore.user.id
  }
  closeAddDish.value.click();
}

const deleteGoods=async (index)=>{
  let flag=confirm("您确认要删除吗？")

  if(flag===false) return null

  const res=await deleteDishAPI({id:dishesList.value[index].id,storeId:userStore.user.id})

  if(res.data.code===1)
  {
    dishesList.value=res.data.data.records
    setOriginPages()
    pageData.value=res.data.data

    ElMessage.success("删除成功")
  }
  else ElMessage.error(res.data.msg)
}

const changeGoodsStatus=async (index)=>{

  let data=dishesList.value[index];
  if(data.status===1) data.status=0;
  else data.status=1;

  const res=await modifyDishAPI(data);

  if(res.data.code===1)
  {
    dishesList.value=res.data.data.records
    setOriginPages()
    pageData.value=res.data.data

    ElMessage.success("修改状态成功")
  }
  else ElMessage.error(res.data.msg)
}

const changeGoods=async ()=>{
  //这是需要修改 信息 先判断一下是否和之前的信息是一样的

  //先找到下标
  let index=0;
  for (let i = 0; i < dishesList.value.length; i++) {
    if(dishesList.value[i].id===dishData.value.id)
    {
      index=i;break;
    }
  }

  let areEqual=true
  for (const key in dishData)
  {
    if(dishData[key]!==dishesList.value[index][key])
    {
      areEqual=false
      break
    }
  }

  if(areEqual===true)
  {
    ElMessage.error("您没有做出任何修改")
    return
  }

  console.log(dishData.value)

  //说明需要修改
  const res=await modifyDishAPI(dishData.value)

  if(res.data.code===1)
  {
    dishesList.value=res.data.data.records

    setOriginPages()
    pageData.value=res.data.data

    ElMessage.success("修改成功")
  }
  else
  {
    ElMessage.error(res.data.msg)
  }

  closeAddDish.value.click();

  dishData.value={
    id:'',
    name:'',
    url:'http://localhost:8081/img/121.jpg',
    status:2,
    description:'',
    categoryId:0,
    price:'',
    current:1,
    storeId:userStore.user.id
  }
  modalTitle.value="新建菜品"
  modalButtonText.value="添加"
}

const choosePages = (currentPage) => {
  searchTextData.value.current = currentPage;
  toSearch();

  // 移除所有带有 "active" 类的元素的 "active" 类
  document.querySelectorAll(".page-item").forEach((item) => {
    item.removeAttribute("aria-current");
    item.classList.remove("active");
  });

  // 为当前页面的元素添加 "active" 类，并设置 aria-current 属性
  const activePageItem = document.querySelector(`.page-item:nth-child(${currentPage+1})`);
  if (activePageItem) {
    activePageItem.classList.add("active");
    activePageItem.setAttribute("aria-current", "page");
  }
};

const toSearch=async ()=>{

  console.log(searchTextData.value)
  const res=await searchDishAPI(searchTextData.value);

  if(res.data.code===1)
  {
    dishesList.value=res.data.data.records
    pageData.value=res.data.data
    // ElMessage.success("查询成功")
  }
  else ElMessage.error(res.data.msg)
}

const multiDel=async ()=>{
  //批量删除

  let r=confirm("您确认要删除这些数据吗")
  if(r===false) return

  let data =[]
  for (let i = 0; i < dishesList.value.length; i++) {
    if(dishesList.value[i].isSelected)
    {
      data=[dishesList.value[i],...data]
    }
  }

  const res=await mulDeleteDishAPI(data)

  if(res.data.code===1)
  {
    dishesList.value=res.data.data.records
    pageData.value=res.data.data
    setOriginPages()
    ElMessage.success("删除成功")
  }
  else
  {
    ElMessage.error(res.data.msg)
  }
}

const isSelected = computed({
  get() {
    return dishesList.value.every(item => item.isSelected);
  },
  set: (newValue) => {
    dishesList.value.forEach(item => item.isSelected = newValue);
  }
});

const bigCheck=()=>{
  console.log(isSelected.value)
  isSelected.value = !isSelected.value;
}

const toModifyModal=(index)=>{

  //要给dishData赋值
  dishData.value ={...dishesList.value[index]}

  const {isSelected,...rest} = dishData.value
  dishData.value={...rest}

  flavorsList.value=[]
  //打开窗口 设置
  openModal.value.click();

  //在这里写 如果 sessionStorage里面有 就不用获取 直接取出来
  //否则就需要
  getAllFlavors()

  modalTitle.value="修改商品"
  modalButtonText.value="修改"
}

const chooseToModal=()=>{
  if(modalButtonText.value==="添加")
  {
    //如果按下的是添加按钮
    addNewDish()

  }
  else
  {
    //按下的是修改按钮
    changeGoods()
  }
}

//这一部分是对 口味的管理
const getAllFlavors=async ()=>{

  console.log("获取到所以的flavors")

  const res=await getAllFlavorsAPI(dishData.value.id);

  console.log(res)

  if(res.data.code===1)
  {
    flavorsList.value=res.data.data
    //存储到sessionStorage里面
    sessionStorage.setItem(`flavors_${dishData.value.id}`,JSON.stringify(flavorsList));
  }
  else
  {
    ElMessage.error(res.data.msg)
  }
}

const addNewFlavors=async ()=>{

  flavorsData.value.name=flavorsData.value.name.trim();
  flavorsData.value.options=flavorsData.value.options.trim();

  if(secondModalButtonText.value==="修改")
  {
    changeFlavors()
    return
  }

  flavorsData.value.goodsId=dishData.value.id;

  if(flavorsData.value.name===''||flavorsData.value.options==='')
  {
    ElMessage.error("您未正确输入")
    return
  }

  if(flavorsData.value.name.length<=0||flavorsData.value.name.length>10)
  {
    ElMessage.error("名称必须存在，且不能超过10个词")
    return
  }

  if(flavorsData.value.options.length<=0||flavorsData.value.options.length>12)
  {
    ElMessage.error("具体口味必须存在，且不能超过10个词")
    return
  }

  if(flavorsData.value.price<0)
  {
    ElMessage.error("价格不能为负数")
    return
  }


  //TODO
  if(modalButtonText.value==="添加")
  {
    //如果是添加就先记录 然后批量添加
    let data=JSON.parse(JSON.stringify(flavorsData.value));

    let flag=0;
    for (let i = 0; i < flavorsList.value.length; i++) {
      if(flavorsList.value[i].name===flavorsData.value.name)
      {
        //如果在里面
        flag=1;
        flavorsList.value[i].flavors.push(data);
      }
    }

    if(flag===0)
    {
      flavorsList.value.push({name:flavorsData.value.name,
                              flavors:[data]})
    }

    ElMessage.success("新增成功")

    clearData()
    return
  }


  const res=await addNewFlavorsAPI(flavorsData.value);
  if(res.data.code===1)
  {
    flavorsList.value=res.data.data;
    ElMessage.success("新增成功");
  }
  else ElMessage.error(res.data.msg)

  clearData()
}

const changeFlavors=async ()=>{

  flavorsData.value.goodsId=dishData.value.id;

  let data=JSON.parse(JSON.stringify(flavorsData.value))
  //TODO
  //这里是修改  而且还要做出判断是 第一次 第一次直接修改 第二次需要发送请求修改
  if(modalButtonText.value==="添加")
  {
    //找到对应的name和options
    for (let i = 0; i < flavorsList.value.length; i++) {
      if(flavorsList.value[i].name===flavorsData.value.name)
      {
        //找到了  凭借下标找
        flavorsList.value[i].flavors[flavorsData.value.id]=data;
        flavorsList.value[i].flavors[flavorsData.value.id].id=null;
      }
    }

    secondModalButtonText.value="新增"
    secondModlTitle.value="新增口味"
    ElMessage.success("修改成功")
    return
  }


  const res=await changeFlavorsAPI(flavorsData.value);

  if(res.data.code===1)
  {
    flavorsList.value=res.data.data
    ElMessage.success("修改成功")
  }
  else
  {
    ElMessage.error(res.data.msg)
  }

  secondModalButtonText.value="新增"
  secondModlTitle.value="新增口味"
}

const deleteFlavors=async (data)=>{

  data=toRaw(data)
  console.log(data)
  //TODO
  if(modalButtonText.value==="添加")
  {
    //这里是删除
    for (let i = 0; i < flavorsList.value.length; i++) {
      if(flavorsList.value[i].name===data.name)
      {
        //找到了
        flavorsList.value[i].flavors = flavorsList.value[i].flavors.filter((item) => {
          return item.options !== data.options;
        });
      }
    }

    ElMessage.success("删除成功")
    return
  }

  // flavorsData.value.goodsId=dishData.value.id;

  const res=await deleteFlavorsAPI(data);

  if(res.data.code===1)
  {
    flavorsList.value=res.data.data
    ElMessage.success("删除成功")
  }
  else ElMessage.error(res.data.msg)
}

const deleteAllFlavorsByName=async (data)=>{

  //TODO
  if(modalButtonText.value==="添加")
  {
    flavorsList.value = flavorsList.value.filter((item) => {
      return item.name !== data.name;
    });

    return
  }

  data=toRaw(data)

  const res=await deleteAllFlavorsByNameAPI(data);

  if(res.data.code===1)
  {
    flavorsList.value=res.data.data
    ElMessage.success("删除成功")
  }
  else
  {
    ElMessage.error("删除失败")
  }

  flavorsData.value.name=''

}

const addOptions=(name)=>{
  flavorsData.value.name=name
}

const modifyFlavors=(flavor,index)=>{

  flavorsData.value.name=flavor.name;
  flavorsData.value.options=flavor.options
  flavorsData.value.price=flavor.price

  if(modalButtonText.value==="添加") flavorsData.value.id=index;
  else flavorsData.value.id=flavor.id;

  secondModlTitle.value="修改口味"
  secondModalButtonText.value="修改"

  //设置完数据之后
}

const delFlavorsName=(name)=>{

  let r=confirm("您确认要删除当前这条数据吗")
  if(r===false) return

  flavorsData.value.goodsId=dishData.value.id;
  flavorsData.value.name=name;

  deleteAllFlavorsByName(flavorsData.value)
}

const clearData=()=>{
  flavorsData.value.options='';
  flavorsData.value.name='';
}

const showChangeFlavorsName=()=>{

}

onMounted(()=>{
  getCategoryList()
  getAllDish()
})
</script>

<style scoped>
.dishHead
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
.dishLeft,
.dishRight
{
  /*display: flex;*/
}
ul
{
  padding-left: 0;
}
li
{
  list-style: none;
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
  outline: #01b6fd;
  border: 2px solid #eeeeee;
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
.myselect
{
  width: 150px;
  height: 40px;
  outline: #01b6fd;
  border:2px solid #eeeeee;
}
table
{
  margin-top: 30px;
  margin-left: 30px;

}
td
{
  flex:1;
  text-align: center;
  height: 120px;
  line-height: 120px;
}
thead tr td
{
  font-weight: bold;
  font-size: 18px;
}
tr
{
  display: flex;
  width: 1400px;
  text-align: center;
  justify-content: space-evenly;
  border-bottom: 2px solid #f1f1f1;
}
thead tr
{
  height: 60px;
  line-height: 60px;
}
thead tr td
{
  height: 60px;
  line-height: 60px;
}
.dishRight button:last-child
{
  margin-right: 40px;
  background: #eeeeee;
  color: #01b6fd;
}
.dishImage
{
  /*width: 261px;*/
  /*height: 261px;*/
  width: 150px;
}
.mb-5
{
  margin-bottom: 20px;
}
.dishImg img
{
  width: 100px;
  height: 100px;
}
.checkBox
{
  flex:1;
  display: flex;
  justify-content: center;
}
.form-check
{
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.total
{
  display: flex;
  flex-wrap: wrap;
  border: 2px dotted #01b6fd;
  border-radius: 20px;
}
.addTotal
{
  display: flex;
  flex-wrap: wrap;
}
.flavorItem
{
  position: relative;
  background: #eeeeee;
  padding: 0 10px;
  color:#01b6fd;
  border: 1px solid #01b6fd;
  margin-left: 13px;
  margin-top: 15px;
  line-height: 30px;
  border-radius: 10px;
  height: 30px;
}
.flavorItem .deleteFlavors
{
  display: none;
}
.flavorItem:hover .deleteFlavors
{
  display: block;
}
.flavorItemADD
{
  color:#01b6fd;
  /*border: 1px solid #01b6fd;*/
  font-size: 30px;
  margin-left: 13px;
  margin-top: 9px;
}
.flavorItemADD:hover
{
  color: black;
}
.flavorTitle
{
  margin-left: 0;
  font-weight: bold;
  font-size: 18px;
}
.addNewFlavors
{
  background: #01b6fd;
  color:white;
  padding:5px 10px;
  border-radius: 10px;
  font-size: 15px;
  font-weight: bold;
  margin-left: 0;
  /*border:1px dotted #01b6fd;*/
}
.editFlavorsName
{
  display: flex;
  margin-bottom: 20px;
}
.editFlavorsName input
{
  box-sizing: border-box;
  height: 40px;
  width: 190px;
  padding-left: 15px;
}
.editFlavorsName button
{
  font-size: 15px;
  padding: 5px 7px;
  margin-left: 10px;

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
.price
{
  font-size: 20px;
  font-weight: bold;
  color: red;
}
.closeFlavors
{
  margin-left: 5px
}
.closeFlavors:hover
{
  color: black;
}


.editFlavors
{
  margin-left: 5px;
}
.editFlavors:hover
{
  color: black;
}
.deleteFlavors
{
  position: absolute;
  top:-13px;
  right: -5px;
  color: #6c757d;
}
.deleteFlavors:hover
{
  color: black;
}
</style>