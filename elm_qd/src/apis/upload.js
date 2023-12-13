
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

export const uploadAvatarAPI=(userId,data)=>{
    return request({
        url:'/api/upload/avatar',
        method:'POST',
        data,
        params:{userId:userId},
        headers:{
            "Content-Type": "multipart/form-data"
        }
    })
}