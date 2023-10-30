<template>
  <div>
    <div class="dishHead">
      <div class="dishLeft">
        <label>菜品名称：&nbsp;<input type="text" placeholder="请输入分类名称"></label>
        <label>菜品分类：&nbsp;<input type="text" placeholder="请输入分类类型"></label>
        <label>售卖状态：&nbsp;<input type="text" placeholder="请选择"></label>
        <button>查询</button>
      </div>
      <div class="dishRight">
        <button>+批量删除</button>
        <button>+新建菜品</button>
      </div>
    </div>
    <div class="dishFooter">
      <table>
        <thead>
        <tr>
          <td></td>
          <td>菜品名称</td>
          <td>图片</td>
          <td>菜品分类</td>
          <td>售价</td>
          <td>售卖状态</td>
          <td>最后操作时间</td>
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
          <td></td>
          <td></td>
          <td>修改 删除 待售</td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>

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
  margin-top: 30px;
  margin-left: 30px;

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
.dishRight button:last-child
{
  margin-right: 40px;
  background: #eeeeee;
  color: #01b6fd;
}

</style>