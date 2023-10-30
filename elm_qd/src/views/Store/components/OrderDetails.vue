<template>
<div>
  <table>
    <thead>
      <tr>
        <td>订单号</td>
        <td>订单菜品</td>
        <td>地址</td>
        <td>预计送达时间</td>
        <td>备注</td>
        <td>餐具数量</td>
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
        <td>派送 取消 查看</td>
      </tr>
    </tbody>
  </table>
</div>
</template>

<script setup>

</script>

<style scoped>
table
{
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
</style>