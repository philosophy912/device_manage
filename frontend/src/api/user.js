import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/user/login',
    // url: '/vue-element-admin/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/user/info',
    // url: '/vue-element-admin/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    // url: '/vue-element-admin/user/logout',
    method: 'post'
  })
}
