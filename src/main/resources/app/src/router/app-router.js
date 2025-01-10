import {createRouter, createWebHashHistory} from 'vue-router'
import Students from '../Students.vue';
import Programs from '../Programs.vue';
import Courses from '../Courses.vue';

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    { path: '/students', component: Students },
    { path: '/programs', component: Programs },
    { path: '/courses', component: Courses },
  ],
});

export default router;