<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.name" :placeholder="$t('table.title')" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        {{ $t('table.search') }}
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
          <span class="link-type">{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('goods.code')" min-width="200px" align="center">
        <template slot-scope="{row}">
          <span class="link-type">{{ row.code }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('goods.employee')" min-width="70px" align="center">
        <template slot-scope="{row}">
          <span v-if="row.employeeName" class="link-type">{{ row.employeeName }}</span>
          <span v-if="!row.employeeName" class="link-type">无</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('goods.project')" min-width="100px" align="center">
        <template slot-scope="{row}">
          <span class="link-type">{{ row.projectName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('goods.image')" min-width="120px" align="center">
        <template slot-scope="{row}">
          <img v-if="row.image" :src="row.image" alt="" class="link-type" width="100%" height="100%" @click="handleShowImage(row)"><img>
          <span v-if="!row.image" class="link-type">无</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('goods.goods_status')" min-width="50px" align="center">
        <template slot-scope="{row}">
          <span class="link-type">{{ handleGoodsStatus(row.goodsStatus) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('goods.return_time')" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.returnTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.actions')" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="handleRecipients(row, $index)">
            {{ $t('goods.recipients') }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="80px" style="width: 400px; margin-left:50px;">
        <el-form-item :label="$t('goods.name')" prop="name">
          <el-input v-model="temp.name" :disabled="true" />
        </el-form-item>
        <el-form-item :label="$t('goods.employee')" prop="employeeName">
          <el-select v-model="temp.employeeId" filterable placeholder="请选择">
            <el-option v-for="item in employees" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item v-if="dialogStatus==='create'" :label="$t('goods.count')" prop="count">
          <el-input v-model="temp.count" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          {{ $t('table.cancel') }}
        </el-button>
        <el-button type="primary" @click="updateRecipients()">
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
import { fetchGoodsNonRecipientsList, recipientsGoods } from '@/api/goods'
// import { fetchAllProject } from '@/api/project'
import { fetchAllEmployee } from '@/api/employee'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import Logger from 'chivy'

const log = new Logger('views/table/non-recipients-table')
// const testUrl = 'http://127.0.0.1:8080'

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
  name: 'NonRecipientsTable',
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
      employees: [],
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
        inTime: new Date()
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '添加',
        select: '选择'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        // image: [{ required: false, message: 'sex is required', trigger: 'change' }],
        // goodsStatus: [{ required: false, message: 'type is required', trigger: 'change' }],
        // employeeName: [{ type: 'date', required: false, message: 'timestamp is required', trigger: 'change' }],
        // projectName: [{ type: 'date', required: false, message: 'timestamp is required', trigger: 'change' }],
        // name: [{ required: false, message: 'title is required', trigger: 'blur' }],
        // count: [{ required: false, message: '数量不能为空', trigger: 'blur' }]
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
      fetchGoodsNonRecipientsList(this.listQuery).then(response => {
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
    getProjectAndEmployee() {
      fetchAllEmployee().then(response => {
        this.employees = response.data
        log.debug('this.employees = ' + JSON.stringify(this.employees))
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
    handleGoodsStatus(status) {
      return status ? '好' : '坏'
    },
    handleRecipients(row, index) {
      log.info('row = ' + JSON.stringify(row))
      this.getProjectAndEmployee()
      this.temp = Object.assign({}, row) // copy obj
      this.dialogStatus = 'select'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateRecipients() {
      log.info('temp = ' + JSON.stringify(this.temp))
      // 查询出来employeeName
      this.employees.forEach(employee => {
        if (this.temp.employeeId === employee.id) {
          this.temp.employeeName = employee.name
        }
      })
      recipientsGoods(this.temp).then(response => {
        this.dialogFormVisible = false
        this.$notify({
          title: '成功',
          message: '设备[' + this.temp.name + ']被[' + this.temp.employeeName + ']借出',
          type: 'success',
          duration: 2000
        })
        this.getList()
      })
    }
  }
}
</script>
