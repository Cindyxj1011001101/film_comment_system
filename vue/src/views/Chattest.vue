<template>
  <div>
    <div class="card" style="margin-bottom: 5px;">
      <el-input
          v-model="data.input"
          style="width: 300px; margin-right: 10px"
          placeholder="请输入问题查询"
          clearable
          @keyup.enter="load"
      ></el-input>
      <el-button type="primary" @click="load" :loading="loading">查询</el-button>
      <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
    </div>
    <div style="margin: 10px 0; padding: 10px; border: 1px solid #eee; border-radius: 4px;">
      <div v-if="loading">思考中...</div>
      <div v-else-if="data.chatans">{{ data.chatans }}</div>
      <div v-else style="color: #999">等待您提出问题...</div>
    </div>
  </div>
</template>

<script setup>
import request from "@/utils/request";
import {reactive, ref} from "vue";
import {ElMessage} from "element-plus";

const data = reactive({
  input: "",
  chatans: ""
})

const loading = ref(false);

const load = async () => {
  if (!data.input?.trim()) {
    ElMessage.warning("请输入问题内容");
    return;
  }

  try {
    loading.value = true;
    const response = await request.post('/api/bozh/chat', {
      input: data.input  // 修正参数传递方式
    });
    data.chatans = response.data?.chatans || "未获取到回答";
  } catch (error) {
    console.error("请求失败:", error);
    ElMessage.error("请求失败: " + (error.response?.data?.message || error.message));
    data.chatans = "请求AI服务失败";
  } finally {
    loading.value = false;
  }
}

const reset = () => {
  data.input = "";
  data.chatans = "";
}
</script>