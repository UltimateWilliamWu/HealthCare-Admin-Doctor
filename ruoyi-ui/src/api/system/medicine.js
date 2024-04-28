import request from '@/utils/request'

// 查询库存管理列表
export function listMedicine(query) {
  return request({
    url: '/system/medicine/list',
    method: 'get',
    params: query
  })
}

// 查询库存管理详细
export function getMedicine(id) {
  return request({
    url: '/system/medicine/' + id,
    method: 'get'
  })
}

// 新增库存管理
export function addMedicine(data) {
  return request({
    url: '/system/medicine',
    method: 'post',
    data: data
  })
}

// 修改库存管理
export function updateMedicine(data) {
  return request({
    url: '/system/medicine',
    method: 'put',
    data: data
  })
}

// 删除库存管理
export function delMedicine(id) {
  return request({
    url: '/system/medicine/' + id,
    method: 'delete'
  })
}
