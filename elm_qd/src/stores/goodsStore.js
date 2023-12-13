import {defineStore} from "pinia";
import {getGoodsDataAPI} from "@/apis/dish";
import {ElMessage} from "element-plus";


export const useGoodsStore=defineStore("goods",()=>{

    const goods=ref({
        goods:{
            name:'',
            url:'http://localhost:8081/img/4711798934cae6bbb1-195b-4878-b201-0a92f66cfa56.jpeg'
        },
        flavorsListData:[
            {name:'',flavors:[{id:1}]},
        ]
    });

    const getGoods=()=>{
        console.log(goods.value)
        return goods.value
    }

    const setGoods=async (id)=>{
        const res=await getGoodsDataAPI(id);

        if(res.data.code===1)
        {
            goods.value=res.data.data
            console.log(goods.value)
            return goods.value
        }
        else
        {
            ElMessage.error(res.data.msg)
            return null
        }
    }

    const getGoodsTotalPrice=()=>{
        for (let i = 0; i < goods.value.flavors.length; i++) {
            for (let j = 0; j < goods.value.flavorsListData[i].flavors.length; j++) {
                if(goods.value.flavorsListData[i].flavors[j].id===goods.value.flavors[i].id)
                {
                    goods.value.goods.price+=goods.value.flavorsListData[i].flavors[j].price
                }
            }
        }
    }

    return {
        goods,
        getGoods,
        setGoods,
        getGoodsTotalPrice
    }

},{
    persist:true
})