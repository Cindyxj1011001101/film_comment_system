import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: () => import('@/views/Manager.vue'),
      redirect: '/home',
      children: [
        { path: 'person', component: () => import('@/views/manager/Person.vue')},
        { path: 'password', component: () => import('@/views/manager/Password.vue')},
        { path: 'home', component: () => import('@/views/manager/Home.vue')},
        { path: 'admin', component: () => import('@/views/manager/Admin.vue')},
        { path: 'notice', component: () => import('@/views/manager/Notice.vue')},
        { path: 'category', component: () => import('@/views/manager/Category.vue')},
        { path: 'film', component: () => import('@/views/manager/Film.vue')},
        { path: 'comment', component: () => import('@/views/manager/Comment.vue')},
        { path: 'user', component: () => import('@/views/manager/User.vue')},
        { path: 'filmView', component: () => import('@/views/manager/FilmView.vue')},
        { path: 'filmDetail', component: () => import('@/views/manager/FilmDetail.vue')},        { path: 'bookCategory', component: () => import('@/views/manager/BookCategory.vue')},
        { path: 'book', component: () => import('@/views/manager/Book.vue')},
        { path: 'bookDetail', component: () => import('@/views/manager/BookDetail.vue')},
        { path: 'bookView', component: () => import('@/views/manager/BookView.vue')},
        { path: 'bookComment', component: () => import('@/views/manager/BookComment.vue')},
        { path: 'bookCategory', component: () => import('@/views/manager/BookCategory.vue')},
        { path: 'home_2', component: () => import('@/views/manager/home_2.vue')},

      ]
    },
    { path: '/test', component: () => import('@/views/test.vue')},
    { path: '/login', component: () => import('@/views/Login.vue')},
    { path: '/register', component: () => import('@/views/Register.vue')},
    { path: '/chattest', component: () => import('@/views/Chattest.vue')},

  ]
})

export default router
