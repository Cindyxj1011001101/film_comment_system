<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-input v-model="data.name" style="width: 250px;margin-right: 10px" placeholder="请输入名称查询"/>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>
    <div class="card" style="margin-bottom: 5px">
      <el-button type="primary" style="margin-bottom: 10px" @click="handleAdd">新增</el-button>

      <el-table :data="data.tableData" stripe>
        <el-table-column prop="name" label="名称"  min-width="50%"/>
        <el-table-column align="right" min-width="30%">
          <template #default="scope">
            <el-button @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="danger" @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="card">
      <el-pagination background layout="total, prev, pager, next"
                     v-model:current-page="data.pageNum"
                     v-model:page-size="data.pageSize"
                     :total="data.total"
                     @current-change="load"/>
    </div>
    <el-dialog v-model="data.formVisible" title="电影分类" width="40%">
      <el-form :model="data.form" label-width="80px" style="padding-right: 80px">
        <el-form-item label="名称">
          <el-input v-model="data.form.name" autocomplete="off" placeholder="请输入名称"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.formVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">保 存</el-button>
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
  tableData: [],
  total: 0,
  pageNum: 1,
  pageSize: 10,
  name: null,
  formVisible: false,
  form: {}
})
const load = () => {
  request.get('/category/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name
    }
  }).then(res => {
    data.tableData = res.data.list
    data.total = res.data.total

  })
}
load()
const reset = () => {
  data.name = null
  load()
}
const handleAdd = () => {
  data.form = {}
  data.formVisible = true
}
const add = () => {
  request.post('/category/add', data.form).then(res => {
    if (res.code === '200') {
      load()
      data.formVisible = false
      ElMessage.success('操作成功')
    } else {
      ElMessage.error(res.msg)
    }

  })
}
const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}
const update = () => {
  request.put('/category/update', data.form).then(res => {
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
  request.delete('/category/delete/'+id).then(res=>{
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