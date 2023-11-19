import http from "@/utils/http.js";

export const getUserAddressAPI=(id)=>{
    return http(
        {
            url:'/api/address'+'/'+id,
            method:'GET'
        }
    )
}

export const getUserDefaultAddressAPI=async (id)=>{
    return http({
        url:'/api/address'+'/'+id,
        method:'POST'
    })
}

export const addUserAddressAPI=(data)=>{
    return http({
        url:'/api/address',
        method:'POST',
        data
    })
}

export const changeAddressAPI=async (data)=>{
    return http({
        url:'/api/address',
        method:'PUT',
        data
    })
}

export const deleteAddressAPI=async (data)=>{
    return http({
        url:'/api/address',
        method:'DELETE',
        data
    })
}

export const changeToDefaultAPI=async (data)=>{
    return http({
        url:'/api/address/toDefault',
        method:'POST',
        data
    })
}

export const getDistrictAPI=async (data)=>{
    return http({
        url:"/api/district",
        method:"POST",
        data:data
    })
}

export const getSingleDistrictAPI=(id)=>{
    return http({
        url:'/api/district'+'/'+id,
        method:'GET',
    })
}