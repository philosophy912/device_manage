import request from '@/utils/request'

export function fetchDepartmentList(query) {
  return request({
    url: '/department/list',
    method: 'get',
    params: query
  })
}

export function fetchAllDepartment() {
  return request({
    url: '/department/findAll',
    method: 'get'
  })
}

export function fetchDepartmentName(data) {
  return request({
    url: '/department/findName',
    method: 'post',
    data
  })
}

export function createDepartment(data) {
  return request({
    url: '/department/create',
    method: 'post',
    data
  })
}

export function updateDepartment(data) {
  return request({
    url: '/department/update',
    method: 'post',
    data
  })
}

export function deleteDepartment(data) {
  return request({
    url: '/department/delete',
    method: 'post',
    data
  })
}
