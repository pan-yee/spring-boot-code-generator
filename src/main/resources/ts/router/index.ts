import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Layout from '@/layout/index.vue'

const routes: Array<RouteRecordRaw> = [
    {
        path: '/login',
        name: 'login',
        meta: {
            title: '登录',
            hideInMenu: true
        },
        component: () => import('../views/login/login.vue')
    },
    {
        path: '/',
        component: Layout,
        name: 'Layout',
        redirect: '/dashboard',  //自动跳转
        children: [
            {
                path: '/dashboard',
                component: () => import('../views/index/Dashboard.vue'),
                name: 'Dashboard',
                meta: {
                    title: '首页'
                }
            }
        ]
    },
    {
        path: '/system',
        component: Layout,
        name: 'system',
        meta: {
            title: '系统管理'
        },
        children: [
            {
                path: '/system/warehousePage',
                component: () => import('../views/system/WarehousePage.vue'),
                name: 'Warehouse',
                meta: {
                    title: 'Warehouse'
                },
            },
        ]
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router