import http from '@/utils/http.js'
import {ref} from "vue";

export const getAllDishesAPI=(data)=>{
    return http({
        url:'/api/goods/all',
        method:'POST',
        data
    })
}

export const addNewDishAPI=(data)=>{
    return http({
        url:'/api/goods',
        method:'POST',
        data
    })
}

export const deleteDishAPI=(data)=>{
    return http({
        url:'/api/goods',
        method:'DELETE',
        data
    })
}

export const modifyDishAPI=(data)=>{
    return http({
        url:'/api/goods',
        method:'PUT',
        data
    })
}

export const searchDishAPI=(data)=>{
    return http({
        url:'/api/goods/search',
        method:'POST',
        data
    })
}

export const mulDeleteDishAPI=(data)=>{
    return http({
        url:'/api/goods/muldel',
        method:'DELETE',
        data
    })
}

export const getGoodsByCategoryAPI=(data)=>{
    return http({
        url:'/api/goods/category',
        method:'POST',
        data
    })
}

export const getGoodsDataAPI=(id)=>{
    return http({
        url:'/api/goods/data'+'/'+id,
        method:'GET',
    })
}



