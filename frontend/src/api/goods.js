import request from '@/utils/request'

export function fetchGoodsList(query) {
  return request({
    url: '/goods/list',
    method: 'get',
    params: query
  })
}

export function fetchGoodsName(data) {
  return request({
    url: '/goods/findName',
    method: 'post',
    data
  })
}

export function createGoods(data) {
  return request({
    url: '/goods/create',
    method: 'post',
    data
  })
}

export function updateGoods(data) {
  return request({
    url: '/goods/update',
    method: 'post',
    data
  })
}

export function deleteGoods(data) {
  return request({
    url: '/goods/delete',
    method: 'post',
    data
  })
}
