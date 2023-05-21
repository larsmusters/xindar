import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Home',
      component: () => import('../views/Home.vue'),
    },
    {
      path: '/battle',
      name: 'Battle',
      component: () => import('../views/Battle.vue'),
    },
    {
      path: '/setup',
      name: 'Setup',
      component: () => import('@/views/BattleSetup/index.vue'),
    },
    {
      path: '/map',
      name: 'Maps',
      component: () => import('@/views/Maps.vue'),
    },
  ],
})

export default router
