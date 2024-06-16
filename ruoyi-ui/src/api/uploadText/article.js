import request from '@/utils/request'

export function getAll() {
  return request({
    url: '/article/selectAll',
    method: 'get',
  })
}

export function deleteById(id,url) {
  return request({
    url: '/article/delete',
    method: 'delete',
    params: {id:id,url:url},
    headers:{
      'Content-Type':'multipart/form-data'
    }
  })
}

export function upload(data) {
  return request({
    url: '/article/upload',
    method: 'post',
    data:data,
    headers: { // 设置正确的Content-Type
      'Content-Type': 'multipart/form-data'
    },
  })
}

export function update(data) {
  return request({
    url: '/article/update',
    method: 'put',
    data:data,
    headers: { // 设置正确的Content-Type
      'Content-Type': 'multipart/form-data'
    },
  })
}
