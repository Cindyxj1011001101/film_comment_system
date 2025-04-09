<template>
  <div>
    <!--查询行-->
    <div class="card" style="margin-bottom: 5px">
      <el-input v-model="data.bookName" style="width: 250px;margin-right: 10px" placeholder="请输入书籍名查询"/>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>
    <!--表格-->
    <div class="card" style="margin-bottom: 5px">
      <el-table :data="data.tableData" stripe>
        <el-table-column prop="bookName" label="书籍名称"  min-width="50%"/>
        <el-table-column prop="score" label="评分"  min-width="50%">
          <template  #default="scope">
            <el-rate disabled v-model="scope.row.score" allow-half />
          </template>
        </el-table-column>
        <el-table-column prop="comment" label="评论"  min-width="50%">
          <template #default="scope">
            <el-button @click="preview(scope.row.comment)">查看内容</el-button>
          </template>
        </el-table-column>
        <el-table-column prop="userName" label="用户名称"  min-width="50%"/>
        <el-table-column prop="time" label="评论时间"  min-width="50%"/>
        <el-table-column prop="type" label="类型"  min-width="50%">
          <template  #default="scope">
            <el-tag type="success" v-if="scope.row.type==='长评'">长评</el-tag>
            <el-tag type="primary" v-if="scope.row.type==='短评'">短评</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" >
          <template #default="scope">
            <el-button type="danger" @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!--换页栏-->
    <div class="card">
      <el-pagination background layout="total, prev, pager, next"
                     v-model:current-page="data.pageNum"
                     v-model:page-size="data.pageSize"
                     :total="data.total"
                     @current-change="load"/>
    </div>

    <!--查看内容弹窗-->
    <el-dialog v-model="data.formVisibleComment" title="评论内容" width="40%">
      <div v-html="data.commentContent">

      </div>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.formVisibleComment = false">关 闭</el-button>
        </div>
      </template>
    </el-dialog>

  </div>
</template>

<script setup>
import {reactive} from "vue";
import request from "@/utils/request";
import {ElMessage,ElMessageBox} from "element-plus";



const data = reactive({
  user: JSON.parse(localStorage.getItem('system-user') || '{}'),
  tableData: [],
  total: 0,
  pageNum: 1,
  pageSize: 10,
  bookName: null,
  formVisible: false,
  form: {},
  formVisibleComment:false,
  commentContent:null
})
const load = () => {
  request.get('/bookComment/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      bookName: data.bookName,
      userId:data.user.role==='ADMIN'?null:data.user.id
    }
  }).then(res => {
    data.tableData = res.data.list
    data.total = res.data.total

  })
}
load()
const reset = () => {
  data.bookName = null
  load()
}
const handleAdd = () => {
  data.form = {}
  data.formVisible = true
}
const add = () => {
  request.post('/bookComment/add', data.form).then(res => {
    if (res.code === '200') {
      load()
      data.formVisible = false
      ElMessage.success('操作成功')
    } else {
      ElMessage.error(res.msg)
    }

  })
}
const preview=(comment)=>{
  data.commentContent=comment
  data.formVisibleComment=true;
}
const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}
const update = () => {
  request.put('/bookComment/update', data.form).then(res => {
    if (res.code === '200') {
      load()
      data.formVisible = false
      ElMessage.success('操作成功')
    } else {
      ElMessage.error(res.msg)
    }

  })
}
const save = () => {
  data.form.id ? update() : add()
}
const del=(id)=>{
  ElMessageBox.confirm('是否确认删除数据','确认删除',{type:'warning'}).then(res=>{
    request.delete('/bookComment/delete/'+id).then(res=>{
      if (res.code === '200') {
        load()
        data.formVisible = false
        ElMessage.success('操作成功')
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(err => {})
}
</script>