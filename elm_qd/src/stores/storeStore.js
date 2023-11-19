import {defineStore} from "pinia";
// import {ref} from 'vue'
import {getStoreInfoAPI} from "@/apis/store";
import {useUserStore} from "@/stores/userStore.js";

export const useStoreStore = defineStore("store",()=>{
    let storeInfo=null

    const userStore=useUserStore();

    const getUserInfo=async ()=>{
        if(storeInfo!==null) return storeInfo
        else
        {
            const res=await getStoreInfoAPI(userStore.user.id)
            console.log(res)
            storeInfo=res.data.data

            return storeInfo
        }
    }

    const setUserStore=async ()=>{
        const res=await getStoreInfoAPI(userStore.user.id)
        console.log(res)
        storeInfo=res.data.data

        return storeInfo
    }

    return{
        storeInfo,
        getUserInfo,
        setUserStore
    }
},{
    persist:true
})