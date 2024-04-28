import request from '@/utils/request'

export function outboundMedicineFile(data){
  return request({
    url: '/system/outbounds',
    method: 'post',
    data: data
  })
}
