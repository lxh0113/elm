import http from "@/utils/http";

export const becomeStoreAPI=(data)=>{
    return http({
        url:'/api/store/become',
        method:'POST',
        data
    })
}

export const changeStoreAPI=(data)=>{
    return http({
        url:'/api/store/change',
        method:'POST',
        data
    })
}

export const getStoreInfoAPI=(storeId)=>{
    return http({
        url:'/api/store/getInfo'+'/'+storeId,
        method:'GET'
    })
}

export const recommendStoreAPI=(userId)=>{
    return http({
        url:'/api/store/getRecommend'+'/'+userId,
        method:'GET'
    })
}

export const getStoreOrigin=(storeId)=>{
    return http({
        url:'/api/store/getStore'+'/'+storeId,
        method:'GET'
    })
}

export const changeStoreStatusAPI=(data)=>{
    return http({
        url:'/api/store/changeStatus',
        method:'PUT',
        data
    })
}