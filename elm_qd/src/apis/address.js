import request from '@/utils/http.js'

export const getUserAddressAPI=async (id)=>{
    return request(
        {
            url:'/api/address/user',
            method:'POST',
            data:{
                id
            }
        }
    )
}

export const getDistrictAPI=async (data)=>{
    return request({
        url:"/api/district",
        method:"POST",
        data:data
    })
}
