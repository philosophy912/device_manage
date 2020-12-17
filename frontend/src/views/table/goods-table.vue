<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.name" clearable :placeholder="$t('table.title')" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" @clear="clearName" />
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        {{ $t('table.search') }}
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        {{ $t('table.add') }}
      </el-button>
    </div>

    <el-table :key="tableKey" v-loading="listLoading" :data="list" border fit highlight-current-row style="width: 100%;" @sort-change="sortChange">
      <el-table-column :label="$t('goods.id')" prop="id" sortable="custom" align="center" width="80" :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('goods.name')" min-width="100px" align="center">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('goods.employee')" min-width="70px" align="center">
        <template slot-scope="{row}">
          <span v-if="row.employeeName" class="link-type" @click="handleUpdate(row)">{{ row.employeeName }}</span>
          <span v-if="!row.employeeName" class="link-type" @click="handleUpdate(row)">无</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('goods.project')" min-width="100px" align="center">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.projectName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('goods.image')" min-width="120px" align="center">
        <template slot-scope="{row}">
          <img v-if="row.image" :src="row.image" alt="" class="link-type" width="100%" height="100%" @click="handleShowImage(row)"><img>
          <span v-if="!row.image" class="link-type" @click="handleUpdate(row)">无</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('goods.recipients_status')" min-width="70px" align="center">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ handleRecipientsStatus(row.recipientsStatus) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('goods.goods_status')" min-width="50px" align="center">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ handleGoodsStatus(row.goodsStatus) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('goods.description')" min-width="100px" align="center">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.description }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('goods.in_time')" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.inTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.actions')" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            {{ $t('table.edit') }}
          </el-button>
          <el-button v-if="row.status!='deleted'" size="mini" type="danger" @click="handleDelete(row,$index)">
            {{ $t('table.delete') }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="80px" style="width: 400px; margin-left:50px;">
        <el-form-item :label="$t('goods.name')" prop="name">
          <el-input v-model="temp.name" />
        </el-form-item>
        <el-form-item :label="$t('goods.image')" prop="image">
          <el-upload
            ref="upload"
            class="avatar-uploader"
            drag
            action="/device/upload"
            list-type="picture"
            accept="image/png, image/jpeg, image/gif, image/bmp"
            :show-file-list="false"
            :file-list="temp.images"
            :limit="1"
            :on-success="handleOnSuccess"
            :before-upload="beforeUpload"
          >
            <img v-if="temp.image" width="100%" height="100%" :src="temp.image" class="avatar" @click="clearFiles">
            <div v-else>
              <i class="el-icon-upload" />
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            </div>
          </el-upload>
        </el-form-item>
        <el-form-item :label="$t('goods.goods_status')" prop="goodsStatus">
          <el-switch v-model="temp.goodsStatus" active-text="好" inactive-text="坏" />
        </el-form-item>
        <el-form-item :label="$t('goods.project')" prop="projectName">
          <el-select v-model="temp.projectId" placeholder="请选择">
            <!-- label是文字，value是值 -->
            <el-option v-for="item in projects" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item v-if="dialogStatus==='create'" :label="$t('goods.count')" prop="count">
          <el-input v-model="temp.count" />
        </el-form-item>
        <el-form-item :label="$t('goods.description')" prop="description">
          <el-input v-model="temp.description" :rows="5" type="textarea" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          {{ $t('table.cancel') }}
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          {{ $t('table.confirm') }}
        </el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="dialogPvVisible" title="Reading statistics">
      <el-table :data="pvData" border fit highlight-current-row style="width: 100%">
        <el-table-column prop="key" label="Channel" />
        <el-table-column prop="pv" label="Pv" />
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogPvVisible = false">{{ $t('table.confirm') }}</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { fetchGoodsList, createGoods, updateGoods, deleteGoods } from '@/api/goods'
import { fetchAllProject } from '@/api/project'
import { notEmpty, isGoodsNameValid } from '@/utils/validates'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import Logger from 'chivy'

const log = new Logger('views/table/good-table')

const calendarTypeOptions = [
  { key: 'CN', display_name: 'China' },
  { key: 'US', display_name: 'USA' },
  { key: 'JP', display_name: 'Japan' },
  { key: 'EU', display_name: 'Eurozone' }
]

// arr to obj, such as { CN : "China", US : "USA" }
const calendarTypeKeyValue = calendarTypeOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

export default {
  name: 'GoodsTable',
  components: { Pagination },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.getList()
    })
  },
  directives: { waves },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'info',
        deleted: 'danger'
      }
      return statusMap[status]
    },
    typeFilter(type) {
      return calendarTypeKeyValue[type]
    }
  },
  data() {
    return {
      projects: [],
      // employees: [],
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        name: undefined,
        sort: '+id'
      },
      importanceOptions: [1, 2, 3],
      calendarTypeOptions,
      sortOptions: [{ label: 'ID Ascending', key: '+id' }, { label: 'ID Descending', key: '-id' }],
      statusOptions: ['published', 'draft', 'deleted'],
      showReviewer: false,
      temp: {
        id: undefined,
        name: '',
        code: '',
        image: '',
        images: [],
        employeeId: undefined,
        projectId: undefined,
        count: '',
        recipients_status: false,
        goodsStatus: true,
        inTime: new Date(),
        description: ''
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '添加'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        goodsStatus: [{ required: true }],
        projectName: [{ required: true, validator: notEmpty, trigger: 'change' }],
        name: [{ required: true, validator: isGoodsNameValid, trigger: 'blur' }]
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchGoodsList(this.listQuery).then(response => {
        this.list = response.data
        this.total = response.totalRows
        this.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    handleModifyStatus(row, status) {
      this.$message({
        message: '操作成功',
        type: 'success'
      })
      row.status = status
    },
    sortChange(data) {
      const { prop, order } = data
      if (prop === 'id') {
        this.sortByID(order)
      }
    },
    sortByID(order) {
      if (order === 'ascending') {
        this.listQuery.sort = '+id'
      } else {
        this.listQuery.sort = '-id'
      }
      this.handleFilter()
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        name: '',
        code: '',
        image: '',
        images: [],
        employeeId: undefined,
        projectId: undefined,
        count: '',
        recipients_status: false,
        goodsStatus: true,
        inTime: new Date(),
        description: ''
      }
    },
    handleCreate() {
      this.resetTemp()
      this.getProjectAndEmployee()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    getProjectAndEmployee() {
      fetchAllProject().then(response => {
        this.projects = response.data
        // fetchAllEmployee().then(response => {
        //   this.employees = response.data
        // })
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.temp.inTime = +new Date(this.temp.inTime) // change Thu Nov 30 2017 16:41:05 GMT+0800 (CST) to 1512031311464
          createGoods(this.temp).then(() => {
            this.dialogFormVisible = false
            this.$notify({
              title: '成功',
              message: '创建成功',
              type: 'success',
              duration: 2000
            })
            this.getList()
          })
        }
      })
    },
    handleUpdate(row) {
      this.getProjectAndEmployee()
      this.temp = Object.assign({}, row) // copy obj
      this.temp.inTime = +new Date(this.temp.inTime)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        log.debug('validate is successed')
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          log.debug('tempData = ' + JSON.stringify(tempData))
          updateGoods(tempData).then(() => {
            this.dialogFormVisible = false
            this.$notify({
              title: '成功',
              message: '更新成功',
              type: 'success',
              duration: 2000
            })
            this.getList()
          })
        }
      })
    },
    handleDelete(row, index) {
      this.$confirm('此操作将永久删除部门【' + row.name + '】, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const data = {
          'id': row.id,
          'name': row.name
        }
        deleteGoods(data).then(() => {
          this.$notify({
            title: '成功',
            message: '删除成功',
            type: 'success',
            duration: 2000
          })
          this.getList()
        }).catch(() => {
          this.$notify({
            title: '失败',
            message: '删除失败',
            type: 'error',
            duration: 2000
          })
        })
      }).catch(() => {
      })
    },
    handleFetchPv(pv) {
      // fetchPv(pv).then(response => {
      //   this.pvData = response.data.pvData
      //   this.dialogPvVisible = true
      // })
    },
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['timestamp', 'title', 'type', 'importance', 'status']
        const filterVal = ['timestamp', 'title', 'type', 'importance', 'status']
        const data = this.formatJson(filterVal)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: 'table-list'
        })
        this.downloadLoading = false
      })
    },
    formatJson(filterVal) {
      return this.list.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    },
    getSortClass: function(key) {
      const sort = this.listQuery.sort
      return sort === `+${key}` ? 'ascending' : 'descending'
    },
    handleShowImage(row) {
      log.debug('row.image = ' + JSON.stringify(row.image))
      this.$alert('<img src=' + row.image + ' width="100%" height="100%">', '', {
        dangerouslyUseHTMLString: true,
        showConfirmButton: false
      })
    },
    handleOnSuccess(response, file, fileList) {
      this.temp.images = fileList
      log.debug('file = ' + JSON.stringify(file))
      log.debug('fileList = ' + JSON.stringify(fileList))
      log.debug('response = ' + JSON.stringify(response))
      log.debug('images = ' + JSON.stringify(this.temp.images))
      this.temp.image = response.data
    },
    beforeUpload(file) {
      // 文件大于10M
      if (file.size > 10 * 1024 * 1024) {
        this.$notify({
          title: '文件过大',
          message: '文件过大，只允许10M以下的文件',
          type: 'error',
          duration: 2000
        })
        return false
      }
    },
    clearFiles() {
      this.$refs.upload.clearFiles()
    },
    handleGoodsStatus(status) {
      return status ? '好' : '坏'
    },
    handleRecipientsStatus(status) {
      return status ? '已领用' : '未领用'
    },
    clearName() {
      this.listQuery.name = undefined
      this.getList()
    }
  }
}
</script>
