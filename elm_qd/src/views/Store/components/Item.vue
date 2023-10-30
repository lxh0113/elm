<template>
  <div class="bac">
    <div class="bacHead">
      <slot></slot>
    </div>
  </div>
</template>

<script setup>

</script>

<style scoped>
.bac
{
  background: #eeeeee;
  /*width: 100px;*/
  margin-right: 15px;
  height: 130px;
  line-height: 130px;
  border-radius: 20px;
  text-align: center;
}
slot
{
  width: 100%;
  /*margin: 20px auto;*/
}
</style>