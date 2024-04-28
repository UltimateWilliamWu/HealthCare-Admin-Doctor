import request from '@/utils/request'

// 查询病床管理列表
export function listWard(query) {
  return request({
    url: '/system/ward/list',
    method: 'get',
    params: query
  })
}

// 查询病床管理详细
export function getWard(id) {
  return request({
    url: '/system/ward/' + id,
    method: 'get'
  })
}

// 新增病床管理
export function addWard(data) {
  return request({
    url: '/system/ward',
    method: 'post',
    data: data
  })
}

// 修改病床管理
export function updateWard(data) {
  return request({
    url: '/system/ward',
    method: 'put',
    data: data
  })
}

// 删除病床管理
export function delWard(id) {
  return request({
    url: '/system/ward/' + id,
    method: 'delete'
  })
}
