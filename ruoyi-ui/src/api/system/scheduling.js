import request from '@/utils/request'

export function addSchedulingFile(data){
  return request({
    url: '/system/scheduleAll/addScheduling',
    method: 'post',
    data: data
  })
}
