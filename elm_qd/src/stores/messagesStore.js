import {defineStore} from "pinia";
import {ref} from 'vue'
import {useUserStore} from "@/stores/userStore";
import {getMessagesAPI} from "@/apis/message";
import {ElMessage} from "element-plus";

export const useMessagesStore=defineStore('messages',()=>{
    const messagesList=new Map()
    const userStore=useUserStore()

    const getMessagesListById=async (id)=>{

        console.log(messagesList.get(id))

        if(messagesList.get(id)!=null)
        {
            return messagesList.get(id);
        }
        else
        {
            //发送请求得到消息
            const res=await getMessagesAPI(userStore.user.id,id,20);
            console.log(res)
            if(res.data.code===1)
            {
                await messagesList.set(id,{messagesList:res.data.data,isEnd:res.data.map.isEnd})
            }
            else
            {
                ElMessage.error(res.data.msg)
            }

            return messagesList.get(id);
        }
    }

    const getMoreMessageListById=async (id,size)=>{

        const res=await getMessagesAPI(userStore.user.id,id,size);

        if(res.data.code===1)
        {
            messagesList.set(id,{messagesList:res.data.data,isEnd:res.data.map.isEnd})
        }
        else
        {
            ElMessage.error(res.data.msg)
        }
    }

    const addNewOneMessageById=async (message)=>{
        let id=null
        console.log("新消息"+message)
        if(message.senderId===userStore.user.id)
        {
            id=message.receiverId;
        }
        else
        {
            id=message.senderId
        }

        let oldData=messagesList.get(id);

        await messagesList.set(id,{messagesList:[...oldData.messagesList,JSON.parse(JSON.stringify(message))],isEnd:oldData.isEnd})
    }

    return{
        messagesList,
        getMessagesListById,
        getMoreMessageListById,
        addNewOneMessageById
    }

},{
 persist:true
})