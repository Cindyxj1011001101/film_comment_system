<template>
  <div class="login-container">
    <el-container class="container">
      <el-header>Header</el-header>
      <el-container>
        <el-aside class="aside_main" :class="{aside_main_show:!data.asideStatus}">Aside</el-aside>
        <el-container>
          <el-main class="main_cont">
            <!-- aside侧边栏按钮 -->
            <div class="aside_open_close" @click="data.asidechange">
              <i class="el-icon-arrow-left" v-if="data.aside_open_close"></i>
              <i class="el-icon-arrow-right" v-else></i>
            </div>
          </el-main>
          <el-footer>Footer</el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import {reactive} from "vue";

const data = reactive({
      asideStatus: false,
      aside_open_close: false,
  })

// 侧边栏收缩与展开
const asidechange=()=>{
  data.asideStatus = !data.asideStatus
      if (data.asideStatus) {
        setTimeout(() => {
          data.aside_open_close = true
        }, 500)
      } else {
        setTimeout(() => {
          data.aside_open_close = false
        }, 500)
      }
    }
</script>