<template>
<div>
  <div class="CategoryHead">
    <div class="categoryLeft">
      <label>分类名称：&nbsp;<input type="text" placeholder="请输入分类名称"></label>
      <label>分类类型：&nbsp;<input type="text" placeholder="请输入分类类型"></label>
      <button>查询</button>
    </div>
    <div class="categoryRight">
      <button>+新增菜品分类</button>
      <button>+新增套餐分类</button>
    </div>
  </div>
  <div class="categoryTable">
    <table>
      <thead>
        <tr>
          <td>分类名称</td>
          <td>分类类型</td>
          <td>排序</td>
          <td>状态</td>
          <td>操作时间</td>
          <td>操作</td>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td>修改 删除 禁用</td>
        </tr>
      </tbody>
    </table>
  </div>
</div>
</template>

<script setup>

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
  margin-left: 30px;
  margin-top: 30px;
}
td
{
  text-align: center;
  height: 40px;
  line-height: 40px;
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
  justify-content: space-evenly;
}
.categoryRight button:last-child
{
  margin-right: 40px;
  background: #eeeeee;
  color: #01b6fd;
}

</style>