
import request from '@/utils/http.js'

export const uploadImgAPI=(userId,data)=>{
    return request({
        url:'/api/upload/userAvatar',
        method:'POST',
        data,
        params:{userId},
        headers:{
            "Content-Type": "multipart/form-data"
        }
    })
}