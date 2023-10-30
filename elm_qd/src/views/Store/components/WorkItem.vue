<template>
  <div class="bac">
    <div class="bacHead">
      <h4>{{title}}</h4>
      <slot></slot>
    </div>
  </div>
</template>

<script setup>
defineProps({title:{type:String}})
</script>

<style scoped>
.bac
{
  background: #eeeeee;
  /*width: 100px;*/
  margin-right: 15px;
  border-radius: 20px;
}
.bac .bacHead
{
  margin-left: 15px;
}
.bac h4
{
  line-height: 60px;
}
</style>