import { createApp } from 'vue'
// import Vue from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import ElementPlus  from 'element-plus'
import 'element-plus/dist/index.css'
import Vant from 'vant';
import 'vant/lib/index.css';
// import { useSocketStoreWithOut } from 'vue-native-websocket';

// const store = useSocketStoreWithOut();

import * as ElementPlusIconsVue from '@element-plus/icons-vue'

import App from './App.vue'
import router from './router/index.js'


const app = createApp(App)

app.use(createPinia().use(piniaPluginPersistedstate))
app.use(router)
app.use(ElementPlus)
app.use(Vant)


// app.use(websocket, 'ws://localhost:8081/api/websocket', {
//     connectManually: true, // 手动连接
//     format: 'json', // json格式
//     reconnection: true, // 是否自动重连
//     reconnectionAttempts: 5, // 自动重连次数
//     reconnectionDelay: 2000, // 重连间隔时间
// });


// Vue.config.productionTip = false

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.mount('#app')
