import {defineStore} from "pinia";
import {h, ref} from 'vue'
import {useUserStore} from "@/stores/userStore";
import {ElMessage, ElNotification} from "element-plus";
import {messageTitle} from "@/utils/MessageTitle";

export const useWsStore=defineStore("ws",()=>{

    let ws=null
    const userStore=useUserStore()

    const message=ref({})

    const wsInit=()=>{
        if (ws && ws.readyState === WebSocket.OPEN) {
            console.log('WebSocket 连接已经存在');
            return false
        }

        if(typeof(WebSocket) === "undefined"){
            alert("您的浏览器不支持socket")
            return false
        }

        const open1 = (msg) => {
            ElNotification({
                title: '消息',
                message: h('i', { style: 'color: teal' }, msg),
            })
        }

        ws=new WebSocket("ws://localhost:8081/api/websocket"+'/'+userStore.user.id)
        console.log("ws连接已经建立")

        ws.onmessage=(event)=>{
            console.log("收到了消息"+event.data)

            const {messageType,receiverId,t}={...JSON.parse(event.data)}

            if(receiverId!==userStore.user.id)
            {
                return
            }

            message.value=JSON.parse(event.data)

            open1(messageTitle[messageType])
        }

        ws.onerror=()=>{
            ElMessage.error("网络连接出错")
        }

        ws.onclose=()=>{
            ElMessage.error("连接已经关闭")
        }
    }

    const sendMessage=(type,receiverId,data)=>{
        if(ws&&ws.readyState===WebSocket.OPEN)
        {
            ws.send(JSON.stringify({messageType:type,receiverId,t:data}))
        }
        else
        {
            ElMessage.error("当前连接已经断开，请重试")
        }
    }

    return {
        ws,
        message,
        wsInit,
        sendMessage
    }
})