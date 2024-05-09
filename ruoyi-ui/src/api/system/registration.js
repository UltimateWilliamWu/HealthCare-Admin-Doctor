import request from '@/utils/request'

// 查询问诊列表
export function listRegistration(query) {
  return request({
    url: '/system/registration/list',
    method: 'get',
    params: query
  })
}

// 查询问诊详细
export function getRegistration(id) {
  return request({
    url: '/system/registration/' + id,
    method: 'get'
  })
}

// 新增问诊
export function addRegistration(data) {
  return request({
    url: '/system/registration',
    method: 'post',
    data: data
  })
}

// 修改问诊
export function updateRegistration(data) {
  return request({
    url: '/system/registration',
    method: 'put',
    data: data
  })
}

// 删除问诊
export function delRegistration(id) {
  return request({
    url: '/system/registration/' + id,
    method: 'delete'
  })
}

export function chargeOptions(){
  return request({
    url:'/system/registration/chargeOptions',
    method:'get',
  })
}

export function getRegistrations(username){
  return request({
    url:'/system/registration/s/'+username,
    method:'get',
  })
}
