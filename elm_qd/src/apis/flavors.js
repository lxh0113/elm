import http from "@/utils/http";
import {h} from "vue";

export const getAllFlavorsAPI=(data)=>{
    return http({
        url:'/api/flavors'+`/${data}`,
        method:'GET',
    })
}

export const addNewFlavorsAPI=(data)=>{
    return http(({
        url:'/api/flavors',
        method:'POST',
        data
    }))
}

export const changeFlavorsAPI=(data)=>{
    return http({
        url:'api/flavors',
        method:'PUT',
        data
    })
}

export const deleteFlavorsAPI=(data)=>{
    return http({
        url:'api/flavors',
        method:'DELETE',
        data
    })
}

export const deleteAllFlavorsByNameAPI=(data)=>{
    return http({
        url:'/api/flavors/delName',
        method:'POST',
        data
    })
}