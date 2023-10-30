import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import ElementPlus  from 'element-plus'
import 'element-plus/dist/index.css'
// import piniaPluginPersistedstate from ''

import * as ElementPlusIconsVue from '@element-plus/icons-vue'

import App from './App.vue'
import router from './router/index.js'


const app = createApp(App)

app.use(createPinia().use(piniaPluginPersistedstate))
app.use(router)
app.use(ElementPlus)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.mount('#app')
