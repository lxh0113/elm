import http from "@/utils/http.js";

export const getAllCategoryAPI=(data)=>{
    return http({
        url:'/api/category/all',
        method:'POST',
        data
    })
}

export const addNewCategoryAPI=(data)=>{
    return http({
        url:'/api/category',
        method:'PUT',
        data
    })
}

export const deleteCategoryAPI=(data)=>{
    return http({
        url:'/api/category',
        method:'DELETE',
        data
    })
}

export const changeCategoryAPI=(data)=>{
    return http({
        url:'/api/category',
        method:'POST',
        data
    })
}


export const exchangeSortAPI=(data)=>{
    return http({
        url:'/api/category/change',
        method:'POST',
        data
    })
}

export const searchCategoryAPI=(data)=>{
    return http({
        url:'/api/category/search',
        method:'POST',
        data
    })
}


