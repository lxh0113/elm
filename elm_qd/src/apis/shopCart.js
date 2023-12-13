import http from '@/utils/http.js'

export const getShopCartAPI=(data)=>{
    return http({
        url:'/api/shopcart',
        method:'POST',
        data
    })
}

export const addToShopListAPI=(data)=>{
    return http({
        url:'/api/shopcart',
        method:'PUT',
        data
    })
}

export const deleteCartOneAPI=(id)=>{
    return http({
        url:'/api/shopcart'+'/'+id,
        method:'DELETE'
    })
}

export const deleteListAPI=(data)=>{
    return http({
        url:'/api/shopcart',
        method:'DELETE',
        data
    })
}

export const updateShopCartAPI=(data)=>{
    return http({
        url:'/api/shopcart/update',
        method:'POST',
        data
    })
}