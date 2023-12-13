import {ref} from 'vue'
import {defineStore} from "pinia";

export const useUserStoreStore=defineStore("userStore",()=>{
    const userStoreId=ref("")

    const storePackageFee=ref(0)

    const setUserStoreId=(id)=>{
        userStoreId.value=id;
    }

    const getUserStoreId=()=>{
        return userStoreId.value
    }

    const setStorePackageFee=(packageFee)=>{
        storePackageFee.value=packageFee
    }

    const getStorePackageFee=()=>{
        return storePackageFee.value
    }

    return {
        userStoreId,
        setUserStoreId,
        getUserStoreId,
        storePackageFee,
        setStorePackageFee,
        getStorePackageFee
    }
},{
    persist:true
})