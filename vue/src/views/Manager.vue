<template>
  <div>
    <!--最上行信息栏-->
    <div style="height: 60px; background-color: #fff; display: flex; align-items: center; border-bottom: 1px solid #ddd">
      <!--左侧书影评论系统文字-->
      <div style="flex: 1">
        <div style="padding-left: 20px; display: flex; align-items: center">
          <img src="@/assets/imgs/logo.png" alt="" style="width: 40px">
          <div style="font-weight: bold; font-size: 24px; margin-left: 5px; color: #777fb8">书影评论系统</div>
        </div>
      </div>
      <!--右侧用户名称头像-->
      <div style="width: fit-content; padding-right: 10px; display: flex; align-items: center;">
        <img style="width: 40px; height: 40px; border-radius: 50%" :src="data.user.avatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'" alt="">
        <span style="margin: 5px">{{ data.user.name }}</span>
      </div>
    </div>

    <div style="display: flex">
      <!--左侧栏-->
      <div style="width: 200px; border-right: 1px solid #ddd; min-height: calc(100vh - 60px)">
        <el-menu
            router
            style="border: none"
            :default-active="router.currentRoute.value.path"
            :default-openeds="['1', '2','3', '4']">

          <el-sub-menu index="3">
            <template #title>
              <el-icon><Menu /></el-icon>
              <span>最新资讯</span>
            </template>
            <el-menu-item index="/home">
              <el-icon><Star /></el-icon>
              <span>hottt✿电影速递✿</span>
            </el-menu-item>
            <el-menu-item index="/home_2">
              <el-icon><Notebook /></el-icon>
              <span>hottt✿书籍速递✿</span>
            </el-menu-item>
          </el-sub-menu>


          <el-menu-item index="/filmView">
            <el-icon><Film /></el-icon>
            <span>电影列表</span>
          </el-menu-item>

          <el-menu-item index="/bookView">
            <el-icon><Film /></el-icon>
            <span>书籍列表</span>
          </el-menu-item>

          <el-sub-menu index="1" v-if="data.user.role==='ADMIN'">
            <template #title>
              <el-icon><Menu /></el-icon>
              <span>信息管理</span>
            </template>
            <el-menu-item index="/category"  v-if="data.user.role==='ADMIN'">
              <el-icon><Grid /></el-icon>
              <span>电影分类</span>
            </el-menu-item>
            <el-menu-item index="/bookCategory">
              <el-icon><Grid /></el-icon>
              <span>书籍分类</span>
            </el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="2" v-if="data.user.role==='ADMIN'">
            <template #title>
              <el-icon><Menu /></el-icon>
              <span>信息管理</span>
            </template>
            <el-menu-item index="/film"  v-if="data.user.role==='ADMIN'">
              <el-icon><Film /></el-icon>
              <span>电影信息</span>
            </el-menu-item>
            <el-menu-item index="/book">
              <el-icon><Reading /></el-icon>
              <span>书籍信息</span>
            </el-menu-item>
          </el-sub-menu>


          <el-sub-menu index="3" v-if="data.user.role==='ADMIN'">
            <template #title>
              <el-icon><Menu /></el-icon>
              <span>评论管理</span>
            </template>
            <el-menu-item index="/comment">
              <el-icon><Comment /></el-icon>
              <span>电影评论</span>
            </el-menu-item>
            <el-menu-item index="/bookComment">
              <el-icon><Comment /></el-icon>
              <span>书籍评论</span>
            </el-menu-item>
          </el-sub-menu>

          <el-menu-item index="/notice"  v-if="data.user.role==='ADMIN'">
            <el-icon><Bell /></el-icon>
            <span>公告信息</span>
          </el-menu-item>

          <el-sub-menu index="4"  v-if="data.user.role==='ADMIN'">
            <template #title>
              <el-icon><Memo /></el-icon>
              <span>用户管理</span>
            </template>
            <el-menu-item index="/admin">
              <el-icon><User /></el-icon>
              <span>管理员信息</span>
            </el-menu-item>
            <el-menu-item index="/user">
              <el-icon><User /></el-icon>
              <span>用户信息</span>
            </el-menu-item>
          </el-sub-menu>

          <el-menu-item index="/person">
            <el-icon><User /></el-icon>
            <span>个人资料</span>
          </el-menu-item>

          <el-menu-item index="/password">
            <el-icon><Lock /></el-icon>
            <span>修改密码</span>
          </el-menu-item>

          <el-menu-item index="login" @click="logout">
            <el-icon><SwitchButton /></el-icon>
            <span>退出系统</span>
          </el-menu-item>

        </el-menu>
      </div>
      <!--中间切换部分-->
      <div style="flex: 1; width: 0; background-color: #f8f8ff; padding: 10px">
        <router-view @updateUser="updateUser" />
      </div>
    </div>

    <!--对话部分-->
    <div class="ai-chat-container">
      <!-- 触发按钮 -->
      <div class="trigger-icon" @click="toggleChat">
        <div v-if="!showChat" style="color:#FFFFFF">打开</div>
        <div v-else style="color:#FFFFFF">关闭</div>
      </div>

      <!-- 侧边栏对话界面 -->
      <transition name="slide-fade">
        <div v-if="showChat" class="chat-sidebar">
          <!-- 消息容器 -->
          <div class="messages-container">
            <div
                v-for="(msg, index) in messages"
                :key="index"
                :class="['message-bubble', msg.sender]"
            >
              <div class="message-content">{{ msg.content }}</div>
            </div>
          </div>

          <!-- 输入区域 -->
          <div class="input-container">
            <input
                v-model="inputMessage"
                @keyup.enter="sendMessage"
                placeholder="请输入您的问题..."
            />
            <button @click="sendMessage">
              <div>发送</div>
            </button>
          </div>
        </div>
      </transition>
    </div>
  </div>
</template>

<script setup>
import { reactive } from "vue";
import router from "@/router";
import {ElMessage} from "element-plus";

const data = reactive({
  user: JSON.parse(localStorage.getItem('system-user') || '{}'),
  dialogueAwake: false,
  dialogueWidth: 0
})

if (!data.user?.id) {
  ElMessage.error('请登录！')
  router.push('/login')
}

const updateUser = () => {
  data.user = JSON.parse(localStorage.getItem('system-user') || '{}')
}

const logout = () => {
  ElMessage.success('退出成功')
  localStorage.removeItem('system-user')
  router.push('/login')
}





import { ref } from 'vue';

const showChat = ref(false);
const inputMessage = ref('');
const messages = ref([
  {
    content: '您好！我是AI助手，有什么可以帮您？',
    sender: 'ai'
  }
]);

const toggleChat = () => {
  showChat.value = !showChat.value;
};

const sendMessage = () => {
  if (!inputMessage.value.trim()) return;

  // 用户消息
  messages.value.push({
    content: inputMessage.value,
    sender: 'user'
  });

  // 模拟AI回复
  setTimeout(() => {
    messages.value.push({
      content: '已收到您的消息，正在处理中...',
      sender: 'ai'
    });
  }, 1000);

  inputMessage.value = '';
};






</script>

<style scoped>
.ai-chat-container {
  position: fixed;
  right: 0;
  top: 100px;
  transform: translateY(-50%);
  z-index: 1000;
}

.trigger-icon {
  width: 48px;
  height: 48px;
  background: #2c3e50;
  border-radius: 50% 0 0 50%;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
  transition: all 0.3s;
}

.trigger-icon:hover {
  background: #34495e;
  transform: translateX(-5px);
}

.chat-sidebar {
  width: 360px;
  height: 70vh;
  background: white;
  position: fixed;
  right: 0;
  top: 400px;
  transform: translateY(-50%);
  box-shadow: -2px 0 12px rgba(0,0,0,0.1);
  display: flex;
  flex-direction: column;
}

.messages-container {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
}

.message-bubble {
  margin-bottom: 15px;
  max-width: 80%;
  padding: 12px;
  border-radius: 12px;
}

.message-bubble.user {
  background: #afafaf;
  color: white;
  margin-left: auto;
}

.message-bubble.ai {
  background: #f1f2f6;
  margin-right: auto;
}

.input-container {
  display: flex;
  padding: 15px;
  border-top: 1px solid #eee;
}

input {
  flex: 1;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 8px;
  margin-right: 10px;
}

button {
  padding: 0 15px;
  background: #787878;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}

/* 动画效果 */
.slide-fade-enter-active,
.slide-fade-leave-active {
  transition: all 0.3s ease;
}

.slide-fade-enter-from,
.slide-fade-leave-to {
  transform: translateX(100%) translateY(-50%);
  opacity: 0;
}
</style>