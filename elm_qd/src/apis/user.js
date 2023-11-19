import http from '@/utils/http.js'

export const changeUserInfoAPI=(data)=>{
    return http({
        url:'/api/user',
        method:'PUT',
        data
    })
}

