<template>
  <UserHeader></UserHeader>
  <UserBody></UserBody>
</template>

<script setup>
import {onMounted, watch} from "vue";
import UserHeader from '@/views/UserCenter/components/UserHeader.vue'
import UserBody from '@/views/UserCenter/components/UserBody.vue'
import {useWsStore} from "@/stores/wsStore";
import {Constant} from "@/utils";

</script>

<style scoped>

</style>