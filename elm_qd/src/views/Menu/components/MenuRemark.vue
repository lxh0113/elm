<template>
<div class="remarkBigBox">
  <div class="condition">

  </div>
  <div v-if="true" class="remarkItemBox">
    <ul>
      <li v-for="item in 4" :key="item">
        <div class="remark">
          <div class="remarkHeader">
            <div class="remarkAvatar">
              <img src="http://localhost:8081/img/4711798934ac83faf6-c7c2-49dc-8cd6-cc7e7305f9ee.jpeg" alt="">
              <div class="remarkName">
                <div>lxh</div>
                <div>2023/4/1</div>
              </div>
            </div>
            <div style="display: flex">
              <div class="remarkStarsText">星级</div>
              <ul class="remarkStars">
                <li v-for="item in 5">
                  <i class="bi bi-star-fill"></i>
                </li>
              </ul>
            </div>
          </div>
          <div class="remarkBody">
            <div class="remarkSku">
              <span>商品：&nbsp;</span><span>草莓麻薯×1 五分糖</span>
            </div>
            <p class="remarkText">
              网购了这么多年，这是我第一次这么认真的对待。我怀着忐忑的心情拿着手机盯着屏幕，迟迟不敢下手。我怕我一旦说出来，老板会觉得我在拍马屁，是不是想为了那几块钱的红包，又怕我的评价会误导后来的网友。为了写这次评价，我鼓起勇气喝了3瓶啤酒才有信心。为了不辜负老板对我的期望，我一定会客观，公正，如实的写下自己的评价:这是我这开心的一次网购，谢谢!
            </p>
            <div class="remarkPic">
              <img src="http://localhost:8081/img/4711798934e647f381-b447-49c8-b126-37b555e88d16.jpeg" alt="">
              <img src="http://localhost:8081/img/4711798934e647f381-b447-49c8-b126-37b555e88d16.jpeg" alt="">
              <img src="http://localhost:8081/img/4711798934e647f381-b447-49c8-b126-37b555e88d16.jpeg" alt="">
              <img src="http://localhost:8081/img/4711798934e647f381-b447-49c8-b126-37b555e88d16.jpeg" alt="">
            </div>
            <div class="storeResponse">
              <p class="responseText">
                感谢亲的光临和满意，就是我们最大的欣慰，最大的回报。xx店会更加努力做好！小店还有新品升级版的防雨膜，全面升级360°无死角防护哦~店里还有很多产品适合亲亲的呢，质量和服务也一样是杠杠滴哦，xx店为您保驾护航，为您的生活增添一份安全和快乐，缘定今生不负您的期待！欢迎您的再次光临！“关注店铺”更多的惊喜等到您的到来哈~
              </p>
            </div>
          </div>
        </div>
      </li>
    </ul>
  </div>
  <template v-else>
    <el-empty description="无数据" />
  </template>

</div>
</template>

<script setup>
import {ref,onMounted} from 'vue'
import {useUserStore} from "@/stores/userStore";

const userStore=useUserStore();


onMounted(()=>{

})
</script>

<style scoped>
ul
{
  padding-left:0;
}
.remark
{
  width: 100%;
  /*height: 300px;*/
  /*background: #01b6fd;*/
}
.remarkBigBox
{
  /*background: #f6f6f6;*/
}
.remarkItemBox ul li .remark
{
  box-sizing: border-box;
  background: white;
  /*padding:10px;*/
  margin-bottom: 30px;
  border: 1px dotted #7a8289;
  border-radius: 20px;
}
.remarkHeader
{
  display: flex;
  margin-left: 30px;
  /*background: #fed6e3;*/
  height: 100px;
  line-height: 100px;
  justify-content: space-between;

}
.remarkHeader img
{
  width: 70px;
  height: 70px;
  border-radius: 50%;
  margin-top: 15px;
}
.remarkHeader .remarkName
{
  margin-left: 30px;
  height: 100px;
  margin-top: 15px;
}
.remarkHeader .remarkName div
{
  height: 35px;
  line-height: 35px;
}
.remarkAvatar
{
  display: flex;
  margin-left: 30px;
}
.remarkHeader .remarkName div:nth-child(1)
{
  font-weight: bold;
  font-size: 18px;
}
.remarkHeader .remarkName div:nth-child(2)
{
  color: #7a8289;
  font-weight: bold;
}
.remarkHeader .remarkStars
{
  display: flex;
  margin-left: 30px;
  margin-right: 70px;
}
.remarkStars li
{
  margin-left: 5px;
  color:#01b6fd;
}
.remarkStarsText
{
  font-weight: bold;
  font-size: 17px;
}
.remarkText
{
  margin-left: 50px;
  font-size: 16px;
  margin-right: 50px;
  text-indent: 2em;
}
.remarkPic
{
  margin: 10px 50px;
  display: flex;
}
.remarkPic img
{
  width: 200px;
  height: 200px;
  margin-right: 10px;
}
.storeResponse
{
  box-sizing: border-box;
  background: #e3e3e3;
  margin-left: 50px;
  margin-right: 50px;
  border-radius: 10px;
  padding:10px;
  font-size: 15px;
  margin-bottom: 30px;
  text-indent: 2em;
}
.remarkSku
{
  display: flex;
  flex-wrap: wrap;
  margin: 0 50px;
  margin-bottom: 10px;
  font-size: 15px;
  color:#7a8289;
}
.remarkSku span
{
  margin-right: 10px;
}
</style>