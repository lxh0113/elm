import {defineStore} from "pinia";
import {ref} from 'vue'

export const useShopListStore=defineStore('shopList',()=>{
    //这里是存储 当前店铺的 一些数据
    const shopList=ref([])

    const addToList=(newGoods)=>{
        shopList.value.push(newGoods)
    }

    const getSum=()=>{
        return shopList.value.reduce((sum,item)=>sum+item.count,0);
    }

    const deleteFromList=(id)=>{
        shopList.value=shopList.value.map((item)=>{
            if(item.id!==id) return item
        })
    }

    const changeGoods=(id,good)=>{
        shopList.value.map((item,index)=>{
            if(item.id===id)
            {
                shopList.value[index]=good
            }
        })
    }

    const getTotalPrice=()=>{
        return shopList.value.reduce((acc, item) => acc + item.goods.price * item.count, 0)
    }

    return {
        shopList,
        getSum,
        addToList,
        deleteFromList,
        changeGoods,
        getTotalPrice
    }
},{
    persist:true
})