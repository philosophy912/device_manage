<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.name" clearable :placeholder="$t('table.title')" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" @clear="clearName" />
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
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('goods.code')" min-width="200px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.code }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('goods.employee')" min-width="70px" align="center">
        <template slot-scope="{row}">
          <span v-if="row.employeeName">{{ row.employeeName }}</span>
          <span v-if="!row.employeeName">无</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('goods.project')" min-width="70px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.projectName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('goods.image')" min-width="120px" align="center">
        <template slot-scope="{row}">
          <img v-if="row.image" :src="row.image" alt="" width="100%" height="100%" @click="handleShowImage(row)"><img>
          <span v-if="!row.image">无</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('goods.goods_status')" min-width="50px" align="center">
        <template slot-scope="{row}">
          <span>{{ handleGoodsStatus(row.goodsStatus) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('goods.recipients_time')" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.recipientsTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.actions')" align="center" width="100" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="handleReturn(row, $index)">
            {{ $t('goods.return') }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

  </div>
</template>

<script>
import { fetchGoodsRecipientsList, returnGoods } from '@/api/goods'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import Logger from 'chivy'

const log = new Logger('views/table/recipients-table')

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
  name: 'RecipientsTable',
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
        inTime: new Date()
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '添加'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {},
      downloadLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchGoodsRecipientsList(this.listQuery).then(response => {
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
    handleReturn(row, index) {
      log.debug(JSON.stringify(row))
      this.temp = Object.assign({}, row)
      this.$confirm('此操作将归还【' + row.name + '】, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        returnGoods(this.temp).then(response => {
          this.$notify({
            title: '成功',
            message: '设备[' + this.temp.name + ']Code[' + this.temp.code + ']归还成功',
            type: 'success',
            duration: 2000
          })
          this.getList()
        })
      }).catch(() => {})
    },
    clearName() {
      this.listQuery.name = undefined
      this.getList()
    }
  }
}
</script>
