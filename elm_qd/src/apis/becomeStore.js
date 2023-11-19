import http from '@/utils/http.js'

export const becomeStoreAPI=(data)=>{
    return http({
        url:'/api/store/become',
        method:'POST',
        data
    })
}