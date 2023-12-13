import {defineStore} from "pinia";
import {ref} from 'vue'
import {getStoreInfoAPI, getStoreOrigin} from "@/apis/store";
import {useUserStore} from "@/stores/userStore.js";
import {useUserStoreStore} from "@/stores/userStoreStore";

export const useStoreStore = defineStore("store",()=>{
    let storeInfo=null

    const store=ref({})

    const userStore=useUserStore();
    const userStoreStore=useUserStoreStore()

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

    const getUserInfoByUserStore=async ()=>{
        if(storeInfo!==null) return storeInfo
        else
        {
            const res=await getStoreInfoAPI(userStoreStore.userStoreId)
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

    const getStore=async () => {
        if (JSON.stringify(store.value) === JSON.stringify({})) {
            const res = await getStoreOrigin(userStore.user.id)
            store.value=res.data.data
        } else {
            return store.value
        }
    }

    const setStore = (data)=>{
        console.log(data)
        store.value=data
    }

    const clearStore=()=>{
        store.value={}
        storeInfo=null
    }

    return{
        storeInfo,
        store,
        getUserInfo,
        setUserStore,
        getStore,
        setStore,
        clearStore,
        getUserInfoByUserStore
    }
},{
    persist:true
})