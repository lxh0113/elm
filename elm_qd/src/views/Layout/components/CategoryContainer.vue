<template>
  <div class="bigBox">
    <div class="smallBox">
      <div class="header">
        <div class="title">
          <h3>{{title}}<small> >> {{subTitle}}</small></h3>
        </div>
      </div>
      <div class="footer">
        <slot></slot>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({title:{type:String},subTitle:{type:String}})
</script>

<style scoped>
.bigBox
{
  width: 1240px;
  margin: 0 auto;
  background: white;
}
.smallBox
{
  width: 1240px;

  margin: 0 auto;
  background: white;
}
.smallBox .footer
{
  width: 1240px;
}
.header
{
  height: 100px;
}
.header h3
{
  margin-left: 30px;
  line-height: 100px;
  font-weight:400;
  font-size: 30px;
  color:#01b6fd;
}
.header h3 small
{
  margin-left: 50px;
  font-size: 20px;
  font-weight: lighter;
  color:#01b6fd;
}
</style>