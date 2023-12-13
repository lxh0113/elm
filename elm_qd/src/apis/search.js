import http from "@/utils/http";

export const searchAPI=(text,status,choose)=>{
    return http({
        url:'/api/goods/user/search',
        method:'POST',
        params:{text,status,choose}
    })
}