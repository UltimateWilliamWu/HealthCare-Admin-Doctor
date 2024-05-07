import request from '@/utils/request'

export function uploadPhoto(data){
  return request({
    url: '/system/photo/upload',
    method: 'post',
    data:data,
  })
}
