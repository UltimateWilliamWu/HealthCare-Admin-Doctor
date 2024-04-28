import request from '@/utils/request'

export function addSchedule(data) {
  return request({
    url: 'system/scheduleAll/addSchedule',
    method: 'post',
    data: data
  })
}

export function addBatchSchedule(data) {
  return request({
    url: 'system/scheduleAll/addBatchSchedule',
    method: 'post',
    data: data
  })
}

export function getSchedule(query) {
  return request({
    url: 'system/scheduleAll/getSchedule',
    method: 'get',
    params: query
  })
}


