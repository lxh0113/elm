<template>
<LayoutNav></LayoutNav>
  <LayoutHeader></LayoutHeader>
  <div class="backcolor">
    <div class="menuBox">
      <div class="backurl">
        <img src="@/img/slider/7.gif" alt="">
      </div>
      <div class="trans">
        <div class="details">
          <div class="header">
            <div class="left">
              <h1>奶茶店</h1>
              <span class="star">4.8分</span>
              <span>月售3000+</span>
            </div>
            <div class="right">
              <img src="@/img/slider/8.gif" alt="">
            </div>
          </div>
          <div class="footer">
            <p>很好很好吃</p>
          </div>
        </div>
        <div class="mainBox">
          <div class="choose">
            <ul>
              <li>
                <RouterLink :to="'/menu/' + $route.params.id + '/order'">点餐</RouterLink>
              </li>
              <li>
                <RouterLink :to="'/menu/' + $route.params.id + '/remark'">评价</RouterLink>
              </li>
            </ul>
          </div>
          <div class="orderArea">
            <RouterView></RouterView>
          </div>
        </div>
      </div>
    </div>
  </div>
  <ComputeFooter></ComputeFooter>
</template>

<script setup>
import LayoutNav from '@/views/Layout/components/LayoutNav.vue'
import LayoutHeader from '@/views/Layout/components/LayoutHeader.vue'
import ComputeFooter from '@/views/Menu/components/ComputeFooter.vue'
</script>

<style scoped>
.menuBox
{
  width: 1240px;
  /*height: 3000px;*/
  background: white;
  margin: 0 auto;

}
.backcolor
{
  width: 100%;
  height: 100%;
  background: #eeeeee;
}
.menuBox .backurl
{
  width: 100%;
  height: 500px;
}
.backurl img
{
  height: 500px;
  width: 1240px;
}
.trans
{
  width: 1180px;
  margin: 0 auto;
  transform: translateY(-100px);
}
.details
{
  width: 1180px;
  margin: 0 auto;
  height: 300px;
  border-radius: 30px;
  background: white;
  box-shadow: 0 3px 8px rgb(0,0,0,0.2);
}
.details .header {
  width: 1180px;
  margin: 0 auto;
  /*background: pink;*/
  display: flex;
  justify-content: space-between;
  height: 180px;
}
.details .footer
{
  height: 120px;
}
.details .header .left
{
  margin-top: 30px;
  margin-left: 30px;
}
.details .header .left h1
{
  line-height: 80px;
}
.details .header .left span
{
  line-height: 20px;
  font-weight: bold;
  font-size: 20px;
  color: #ff636b;
  margin-right: 30px;
}

.details .header .right
{
  margin-top: 30px;
  margin-right: 30px;
}

.details .header .right img
{
  width: 120px;
  height: 120px;
  border-radius: 20px;
}

.details .footer p
{
  margin: 0 30px;
}

.mainBox
{
  width: 1180px;
  margin: 0 auto;
  /*height: 2000px;*/
  background: white;
  padding-bottom: 30px;
  margin-top: 30px;
  border-radius: 30px;
  box-shadow: 0 3px 8px rgb(0,0,0,0.2);
}
.choose
{
  width: 1120px;
  height: 100px;
  /*background: palegoldenrod;*/
  margin:0 auto;
  margin-bottom: 30px;
  line-height: 100px;
}
a
{
  font-size: 20px;
  text-decoration: none;
  color: #01b6fd;
  padding:0 30px;
  font-weight: bold;
}
a.router-link-active
{
  color: #ff636b;
}
.choose ul
{
  display: flex;
  justify-content: space-evenly;
}
.choose ul li
{
  list-style: none;
}
.orderArea
{
  width: 1120px;
  margin: 0 auto;
  /*height: 1000px;*/
  /*margin-bottom: 30px;*/
  /*background: #01b6fd;*/
}
</style>