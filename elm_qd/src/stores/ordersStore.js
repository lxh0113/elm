import {defineStore} from "pinia";
import {ref} from 'vue'

export const useOrdersStore=defineStore('orders',()=>{

    const orderData=ref({})

    const setOrderData=(data)=>{
        orderData.value=data
    }

    const getOrderData=()=>{
        return orderData.value
    }

    const clearOrdersData=()=>{
        orderData.value={}
    }


    return {
        orderData,
        setOrderData,
        getOrderData,
        clearOrdersData
    }

},{
    persist:true
})