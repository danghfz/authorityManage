import request from '@/utils/request'

// export function login(data) {
//   return request({
//     url: '/vue-admin-template/user/login',
//     method: 'post',
//     data
//   })
// }
export function login(username, password) {
  return request({
    url: '/user/login',
    method: 'post',
    params: username, password
  })
}

export function getInfo(token) {
  return request({
    url: '/sys/user/info',
    method: 'get'
    // params: { token }
  })
}

export function logout() {
  return request({
    url: '/vue-admin-template/user/logout',
    method: 'post'
  })
}
