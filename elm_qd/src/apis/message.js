import http from '@/utils/http.js'
import {h} from "vue";

export const getMessagesAPI=(senderId,receiverId,size)=>{
    return http({
        url:'/api/message/lists',
        method:'POST',
        data:{senderId,receiverId,size}
    })

}

export const getLeftFriendsAPI=(senderId)=>{
    return http({
        url:'/api/message/people',
        method:'POST',
        data:{
            senderId
        }
    })
}