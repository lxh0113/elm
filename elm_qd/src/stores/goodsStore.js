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
    return {
        goods,
        getGoods,
        setGoods
    }

},{
    persist:true
})