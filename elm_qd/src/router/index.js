import { createRouter, createWebHistory } from 'vue-router'
import Layout from '@/views/Layout/index.vue'
import Login from '@/views/Login/index.vue'
import Store from '@/views/Store/index.vue'
import WorkSpace from  '@/views/Store/components/WorkSpace.vue'
import Menu from '@/views/Menu/index.vue'
import MenuOrder from '@/views/Menu/components/MenuOrder.vue'
import MenuRemark from '@/views/Menu/components/MenuRemark.vue'
import MenuDetails from '@/views/Menu/components/MenuDetails.vue'
import DataStatistics from '@/views/Store/components/DataStatistics.vue'
import ManageCategory from '@/views/Store/components/ManageCategory.vue'
import ManageCombo from '@/views/Store/components/ManageCombo.vue'
import ManageDish from '@/views/Store/components/ManageDish.vue'
import ManageOrders from '@/views/Store/components/ManageOrders.vue'
import OrderDetails from '@/views/Store/components/OrderDetails.vue'
import UserCenter from '@/views/UserCenter/index.vue'
import Search from '@/views/Search/index.vue'
import UserInfo from '@/views/UserCenter/components/UserInfo.vue'
import UserOrder from '@/views/UserCenter/components/UserOrder.vue'
import UserMessage from "@/views/UserCenter/components/UserMessage.vue";
import UserRemark from "@/views/UserCenter/components/UserRemark.vue"
import FourZeroFour from '@/views/404/index.vue'
import BecomeStore from '@/views/UserCenter/components/BecomeStore.vue'
import StoreInfo from "@/views/Store/components/StoreInfo.vue";
import UserAddress from '@/views/UserCenter/components/UserAddress.vue'
import Pay from "@/views/Pay/index.vue";
import StoreChat from "@/views/Menu/components/StoreChat.vue";
import UserOrdersDetails from "@/views/UserCenter/components/UserOrdersDetails.vue";
import RemarkDetails from "@/views/UserCenter/components/RemarkDetails.vue";
import StoreMessage from "@/views/Store/components/StoreMessage.vue"
import WorkSpaceOrders from "@/views/Store/components/WorkSpaceOrders.vue"
import ShopCart from '@/views/UserCenter/components/ShopCart.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component:Layout
    },
    {
      path:'/login',
      component:Login
    },
    {
      path:'/user',component:UserCenter,
      redirect:'/user/info',
      children:[
        {path: 'info',component: UserInfo},
        {
          path:'order',component: UserOrder,
          redirect: to =>{
            return `/user/order/-1`
          },
          children: [
            {path:':status',component:UserOrdersDetails}
          ]
        },
        // {path: 'order/:status',component: UserOrdersDetails},
        {path:'message',component: UserMessage
        },
        {path:'remark',component: UserRemark,
          redirect: to =>{
            return `/user/remark/0`
          },
          children: [
            {path:':status',component:RemarkDetails}
          ]},
        {path: 'store',component: BecomeStore},
        {path:'address',component: UserAddress},
        {path:'shopcart',component: ShopCart}
      ]
    },
    {
      path:'/store',
      component:Store,
      redirect:'/store/workplace',
      children:[
        // {path:'',component:StoreHome},
        {path:'workplace',component:WorkSpace,
          redirect: to => {
            return `/store/workplace/1`; // 使用to.params.id获取实际的id值
          },
          children: [
            {path: ':status',component: WorkSpaceOrders}
          ]
        },
        {path: 'data',component: DataStatistics},
        {path: 'category',component: ManageCategory},
        // {path: 'combo',component: ManageCombo},
        {path: 'message',component: StoreMessage},
        {path: 'dish',component: ManageDish},
        {path:'info',component: StoreInfo},
        {path: 'order',component: ManageOrders,
          children: [
            {path:'details',component:OrderDetails,
              redirect: to =>{
              return `/order/details/-1`
            }},
            {path:'details/:choose',component:OrderDetails}
          ]
        }
      ]
    }
    ,
    {
      path:'/search',component:Search
    }
    ,
    {
      path:'/search/:search',component:Search
    }
    ,
    {
      path:"/menu/:id",
      component:Menu,
      redirect: to => {
        return `/menu/${to.params.id}/order`; // 使用to.params.id获取实际的id值
      },
      children: [
        {
          path: 'order', // 注意这里没有斜杠，它会被视为相对路径
          component: MenuOrder,
          children: [
            {
              path: 'details/:categoryId',
              component: MenuDetails
            }
          ]
        },
        {
          path: 'remark',
          component: MenuRemark
        },
        {
          path: 'chat',
          component: StoreChat
        }
        ]
    },
    {
      path:'/pay',
      component:Pay
    }
  ]
})

export default router
