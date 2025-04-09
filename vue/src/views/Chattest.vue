<template>
  <div>
    <div class="card" style="margin-bottom: 5px;">
      <el-input v-model="data.input" style="width: 300px; margin-right: 10px" placeholder="请输入问题查询"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
    </div>
    <div style="margin: 10px">ai的回答：{{data.chatans}}</div>
  </div>
</template>

<script setup>

import request from "@/utils/request";
import {reactive} from "vue";

const data = reactive({
  input: null,
  chatans: null
})

const load = () => {
  request.get('/ai/chat', {
    params: {
      input: data.input
    }
  }).then(res => {
    data.chatans = res.data?.chatans
  })
}
load()
</script>