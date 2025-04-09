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
        <el-table-column prop="cover" label="封面"  min-width="50%">
          <template #default="scope">
            <el-image :src="scope.row.cover" style="width: 40px; height: 40px; border-radius:5px" :preview-src-list="[scope.row.cover]" preview-teleported ></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="descr" label="简介"  min-width="50%" show-overflow-tooltip/>
        <el-table-column prop="year" label="上映年份"  min-width="50%"/>
        <el-table-column prop="director" label="导演"  min-width="50%"/>
        <el-table-column prop="actors" label="演员"  min-width="50%" show-overflow-tooltip/>
        <el-table-column prop="categoryName" label="类型"  min-width="50%"/>
        <el-table-column prop="country" label="国家"  min-width="50%"/>
        <el-table-column prop="language" label="语言"  min-width="50%"/>
        <el-table-column prop="date" label="上映日期"  min-width="50%"/>
        <el-table-column prop="duration" label="时长"  min-width="50%"/>
        <el-table-column prop="imdb" label="IMDB"  min-width="50%"/>
        <el-table-column align="right">
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
    <el-dialog v-model="data.formVisible" title="电影信息" width="40%">
      <el-form :model="data.form" label-width="80px" style="padding-right: 80px">
        <el-form-item label="名称">
          <el-input v-model="data.form.name" autocomplete="off" placeholder="请输入名称"/>
        </el-form-item>
        <el-form-item label="封面" prop="cover">
          <el-upload :action="uploadUrl" list-type="picture" :on-success="handleImgSuccess">
            <el-button type="primary">上传图片</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="简介">
          <el-input type="textarea" :rows="5" v-model="data.form.descr" autocomplete="off" placeholder="请输入简介" />
        </el-form-item>
        <el-form-item label="上映年份">
          <el-input v-model="data.form.year" autocomplete="off" placeholder="请输入上映年份"/>
        </el-form-item>
        <el-form-item label="导演">
          <el-input v-model="data.form.director" autocomplete="off" placeholder="请输入导演"/>
        </el-form-item>
        <el-form-item label="演员">
          <el-input type="textarea"  :rows="5" v-model="data.form.actors" autocomplete="off" placeholder="请输入演员" />
        </el-form-item>
<!--        <el-form-item label="类型">-->
<!--          <el-input v-model="data.form.categoryId" autocomplete="off" placeholder="请输入类型"/>-->
<!--        </el-form-item>-->
        <el-form-item label="国家">
          <el-input v-model="data.form.country" autocomplete="off" placeholder="请输入国家"/>
        </el-form-item>
        <el-form-item label="语言">
          <el-input v-model="data.form.language" autocomplete="off" placeholder="请输入语言"/>
        </el-form-item>
        <el-form-item label="上映日期">
          <el-date-picker
              style="width:100%"
              v-model="data.form.date"
              type="date"
              placeholder="请选择上映日期"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item label="时长">
          <el-input v-model="data.form.duration" autocomplete="off" placeholder="请输入时长"/>
        </el-form-item>
        <el-form-item label="IMDB">
          <el-input v-model="data.form.imdb" autocomplete="off" placeholder="请输入IMDB"/>
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

const uploadUrl = import.meta.env.VITE_BASE_URL + '/files/upload'

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
  request.get('/film/selectPage', {
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
  request.post('/film/add', data.form).then(res => {
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
  request.put('/film/update', data.form).then(res => {
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
  request.delete('/film/delete/'+id).then(res=>{
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

const handleImgSuccess = (res) => {
  data.form.cover = res.data
}

</script>