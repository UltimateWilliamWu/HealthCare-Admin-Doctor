import request from '@/utils/request'

// 查询排班总览列表
export function listScheduleAll(query) {
  return request({
    url: '/system/scheduleAll/list',
    method: 'get',
    params: query
  })
}

// 查询排班总览详细
export function getScheduleAll(id) {
  return request({
    url: '/system/scheduleAll/' + id,
    method: 'get'
  })
}

// 新增排班总览
export function addScheduleAll(data) {
  return request({
    url: '/system/scheduleAll',
    method: 'post',
    data: data
  })
}

// 修改排班总览
export function updateScheduleAll(data) {
  return request({
    url: '/system/scheduleAll',
    method: 'put',
    data: data
  })
}

// 删除排班总览
export function delScheduleAll(id) {
  return request({
    url: '/system/scheduleAll/' + id,
    method: 'delete'
  })
}
