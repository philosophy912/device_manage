import { fetchDepartmentName } from '@/api/department'
import { fetchProjectName } from '@/api/project'
import { fetchEmployeeName } from '@/api/employee'

import Logger from 'chivy'
const log = new Logger('utils/validates')

const regexMatch = (str, regex) => new RegExp(regex).test(str)
// 不能有特殊字符
const noSpecialCharacter = '^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$'

export const isDepartmentNameValid = (rule, value, callback) => {
  log.debug('value = ' + JSON.stringify(value))
  if (value.content === '') {
    callback(new Error('请输入正确的部门名称'))
  } else {
    if (!regexMatch(value.content, noSpecialCharacter)) {
      callback(new Error('部门名不能有特殊字符'))
    } else {
      const data = {
        'name': value
      }
      fetchDepartmentName(data).then(response => {
        if (response.data.length > 0) {
          callback(new Error('[' + value + ']已存在'))
        } else {
          callback()
        }
      })
    }
  }
}

export const isEmployeeNameValid = (rule, value, callback) => {
  log.debug('value = ' + JSON.stringify(value))
  if (value.content === '') {
    callback(new Error('请输入正确的员工名称'))
  } else {
    if (!regexMatch(value.content, noSpecialCharacter)) {
      callback(new Error('员工名不能有特殊字符'))
    } else {
      const data = {
        'name': value
      }
      fetchEmployeeName(data).then(response => {
        if (response.data.length > 0) {
          callback(new Error('[' + value + ']已存在'))
        } else {
          callback()
        }
      })
    }
  }
}

export const isProjectNameValid = (rule, value, callback) => {
  log.info('value = ' + JSON.stringify(value))
  if (value.content === '') {
    callback(new Error('请输入正确的项目名称'))
  } else {
    if (!regexMatch(value.content, noSpecialCharacter)) {
      callback(new Error('项目名不能有特殊字符'))
    } else {
      const data = {
        'name': value
      }
      fetchProjectName(data).then(response => {
        if (response.data.length > 0) {
          callback(new Error('[' + value + ']已存在'))
        } else {
          callback()
        }
      })
    }
  }
}
