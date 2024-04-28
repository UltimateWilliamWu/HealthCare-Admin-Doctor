import request from '@/utils/request'

export function addMedicineFile(data){
  return request({
    url: '/system/inbounds',
    method: 'post',
    data: data
  })
}
