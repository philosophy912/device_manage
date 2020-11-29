import { fetchDepartmentName } from '@/api/department'
import { fetchProjectName } from '@/api/project'

import Logger from 'chivy'
const log = new Logger('utils/validates')

// const regexMatch = (str, regex) => new RegExp(regex).test(str)

export const isDepartmentNameValid = (rule, value, callback) => {
  log.debug('value = ' + JSON.stringify(value))
  if (value.content === '') {
    callback(new Error('请输入正确的部门名称'))
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

export const isProjectNameValid = (rule, value, callback) => {
  log.debug('value = ' + JSON.stringify(value))
  if (value.content === '') {
    callback(new Error('请输入正确的项目名称'))
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
