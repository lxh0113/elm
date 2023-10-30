<template>
  <div class="bigBox">
    <div class="header">
      <ul>
        <li v-for="item in 3" :key="item">
          <el-icon ><Bowl /></el-icon>
          <span>食材新鲜</span>
        </li>
      </ul>
    </div>
    <hr>
    <div class="footer">
      <div class="h">
        <ul>
          <li v-for="item in 7" :key="item">关于我们</li>
        </ul>
      </div>
      <div class="f">
        <span>CopyRight &copy; 饿了么</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.bigBox
{
  width: 100%;
  min-width: 1240px;
  height: 600px;
  background: #333333;
}
.header ul
{
  display: flex;
  justify-content: space-evenly;
}
.header ul li
{
  list-style: none;
}
.header ul li
{
  font-size: 40px;
  line-height: 300px;
  color: white;
}
hr
{
  width: 80%;
  margin: 0 auto;
  color:#eeeeee;
}
.footer .h ul
{
  display: flex;
  height: 200px;
  justify-content: center;
}
.footer .h ul li
{
  list-style: none;
  color: white;
  padding: 0 20px;
  height: 20px;
  margin-top: 100px;
}
.footer .h ul li:nth-child(n+1)
{
  border-right: 1px solid white;
}
.footer .h ul li:last-child
{
  border: none;
}
.footer .f
{
  display: flex;
  justify-content: center;
  color: white;
}
</style>