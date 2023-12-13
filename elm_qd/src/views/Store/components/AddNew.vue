<template>
<div class="addBox">
  <slot></slot>
</div>
</template>

<script setup>

</script>

<style scoped>
.addBox
{
  flex: 1;
  box-sizing: border-box;
  height: 130px;
  background: #01b6fd;
  color: white;
  border-radius: 20px;
}
.addBox
{
  display: block;
  line-height: 130px;
  text-align: center;
}
.addBox:hover
{
  background: #7a8289;
}
</style>