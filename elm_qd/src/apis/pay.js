import http from '@/utils/http.js'

export const toPayAPI=(subject,traceNo,totalAmount)=>{
    return http({
        url:'/api/alipay/pay',
        method:'GET',
        params:{subject,traceNo,totalAmount}
    })
}

export const makeOrdersAPI=(data)=>{
    return http({
        url:'/api/alipay/preDeal',
        method:'POST',
        data
    })
}

