import axios from 'axios'
import {ElMessage} from "element-plus";
import 'element-plus/theme-chalk/el-message.css'

import {useRouter} from "vue-router";
import router from "@/router";

const http=axios.create(
    {
        baseURL:'http://localhost:8081',
        // timeout:5000,
        headers: {
            'Content-type': 'application/json',
            'contentType': 'application/x-www-form-urlencoded'
        }
    }
)
// axios.defaults.headers.set('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
axios.defaults.withCredentials = true;

//axios 拦截器
http.interceptors.request.use(
    (config)=>{
        //config.headers.token='12345'
        const token=localStorage.getItem("token");
        const long_token=localStorage.getItem("long_token");
        if(token) config.headers.token=token
        if(long_token) config.headers.long_token=long_token

        return config;
    },
    function (error){

        if(error?.response?.status===401)
        {
            alert("身份验证失败")
        }

        return Promise.reject(error)
    }
)


http.interceptors.response.use(
    (response) => {

        console.log(response.headers.token)

        if(response?.status===401)
        {
            ElMessage.error("登录失败，请重新登录")
            location.href='/login'
        }
        else if(response?.headers?.token)
        {
            localStorage.setItem('token',response.headers['token']);
            localStorage.setItem('long_token',response.headers['long_token']);
        }

        return response;
    },
    (e) => {
        const router = useRouter()
        ElMessage({
            type: 'warning',
            message: "错误"
        })
        if (e?.response?.status === 401) {
            router.push('/login')
            // location.href="http://localhost:5173/login"
        }
        return Promise.reject(e)
    }
)

export default http
