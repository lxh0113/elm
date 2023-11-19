import request from '@/utils/http.js'

export const loginAPI=(id,password)=>{
    console.log(password)
    return request({
        url:'/api/pre/login',
        method:'POST',
        data:{
            id,
            password
        }
    })
}

export const getUserInfoAPI=(id)=>{
    return request({
        url:'/api/pre/user',
        method:'POST',
        data:{
            id:id
        }
    })
}

export const registerAPI=(data)=>{
    return request({
        url:'/api/pre/register',
        method:'POST',
        data:data
    })
}

export const forgetPasswordAPI=(data)=>{
    return request({
        url:'/api/pre/forget',
        method:'POST',
        data:data
    })
}

export const getCaptchaAPI=(email)=>{
    return request(
        {
            url:'/api/pre/getCaptcha',
            method:'POST',
            data:{
                email:email
            }

        }
    )
}

