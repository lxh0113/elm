import {defineStore} from "pinia";
import {ref} from 'vue'
import {useUserStoreStore} from "@/stores/userStoreStore";

export const useShopListStore=defineStore('shopList',()=>{
    //这里是存储 当前店铺的 一些数据
    let shopList=ref([])
    const userStoreStore=useUserStoreStore();

    const getShopList=()=>{
        return shopList.value
    }

    const clearSelected=()=>{
        shopList.value=shopList.value.filter((item)=>{
            if(!item.isSelected) return item
        })
    }

    const clearShopList=()=>{
        shopList.value=[]
    }

    const addToList=(newGoods)=>{
        //先看是否已经有了

        //设置价格
        for (let i = 0; i < newGoods.flavors.length; i++) {
            newGoods.goods.price+=newGoods.flavors[i].price
            // for (let j = 0; j < newGoods.flavorsListData[i].flavors.length; j++) {
            //     if(newGoods.flavorsListData[i].flavors[j].id===newGoods.flavors[i].id)
            //     {
            //         newGoods.goods.price+=newGoods.flavorsListData[i].flavors[j].price
            //     }
            // }
        }

        for (let i = 0; i < shopList.value.length; i++) {
            if(JSON.stringify(shopList.value[i])===JSON.stringify(newGoods))
            {
                shopList.value[i].count++
                return
            }
        }

        shopList.value.push(newGoods)
    }

    const getSum=()=>{
        return shopList.value.reduce((sum,item)=>sum+item.count,0);
    }

    const getAllPackageFee=()=>{
        return getSum()*userStoreStore.storePackageFee
    }

    const deleteFromList = (id) => {
        shopList.value=shopList.value.filter((item)=>{
            if(item.goods.id!==id) return item
            else {
                console.log("找到了", item.goods.id,id)
            }
        })
        console.log(shopList.value)
    };

    const changeGoods = (id, good) => {
        console.log(id);
        const updatedList = shopList.value.map((item) => {
            if (item.goods.id === id) {
                // 如果找到匹配的商品，返回新的商品对象
                return good;
            }
            return item;
        });

        shopList.value = updatedList;

        console.log(shopList.value);

        // 在修改商品后检查是否需要删除
        if (good.count === 0) {
            console.log("当前数量为0");
            deleteFromList(good.goods.id);
        }
    };

    // const watchData(good)


    const getTotalPrice=()=>{
        return shopList.value.reduce((acc, item) => acc + item.goods.price * item.count, 0)+getAllPackageFee()
    }

    return {
        shopList,
        getShopList,
        clearShopList,
        clearSelected,
        getSum,
        addToList,
        deleteFromList,
        changeGoods,
        getTotalPrice,
        getAllPackageFee
    }
},{
    persist:true
})