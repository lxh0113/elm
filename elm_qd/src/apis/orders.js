import http from "@/utils/http";

export const getOrdersAPI=(data)=>{
    return http({
        url:'/api/orders/store',
        method:'POST',
        data
    })
}

export const changeOrdersStatusAPI=(data)=>{
    return http({
        url:'/api/orders',
        method:'PUT',
        data
    })
}

export const getUserOrdersAPI=(data)=>{
    return http({
        url:'/api/orders/user',
        method:'POST',
        data
    })
}

export const getUpdatedOrdersAPI=(orderId)=>{
    return http({
        url:'/api/orders/update'+'/'+orderId,
        method:'GET'
    })
}

export const getRemarksOrdersAPI=(buyerId,status,current)=>{
    return http({
        url:'/api/orders/remark',
        method:"POST",
        data:{buyerId,status,current}
    })
}