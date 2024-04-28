import request from '@/utils/request'

// 查询检查费设置列表
export function listExamination(query) {
  return request({
    url: '/system/examination/list',
    method: 'get',
    params: query
  })
}

// 查询检查费设置详细
export function getExamination(id) {
  return request({
    url: '/system/examination/' + id,
    method: 'get'
  })
}

// 新增检查费设置
export function addExamination(data) {
  return request({
    url: '/system/examination',
    method: 'post',
    data: data
  })
}

// 修改检查费设置
export function updateExamination(data) {
  return request({
    url: '/system/examination',
    method: 'put',
    data: data
  })
}

// 删除检查费设置
export function delExamination(id) {
  return request({
    url: '/system/examination/' + id,
    method: 'delete'
  })
}
