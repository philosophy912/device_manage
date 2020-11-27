import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/department/list',
    method: 'get',
    params: query
  })
}

export function fetchAll() {
  return request({
    url: '/department/findall',
    method: 'get'
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
