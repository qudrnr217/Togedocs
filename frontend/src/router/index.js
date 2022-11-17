import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import ApiDocs from "@/views/ApiDocs.vue";
import ApiTest from "@/views/ApiTest.vue";
import ProjectSelect from "@/views/ProjectSelect.vue";
import ProjectView from "@/views/ProjectView.vue";

const routes = [
  {
    path: "/",
    name: "home1",
    component: HomeView,
  },
  {
    path: "/home",
    name: "home",
    component: HomeView,
  }, {
    path: "/select",
    name: "select",
    component: ProjectSelect,
  },
  {
    path: "/project",
    name: "projectview",
    component: ProjectView,
    children: [

      {
        path: "docs/:projectId",
        name: "docs",
        component: ApiDocs,
      },
      {
        path: "test/:projectId",
        name: "test",
        component: ApiTest,
      },
    ],
  },
  //   {
  //     path: "/404",
  //     name: "notfound",
  //     component: () => import("@/views/NotFound.vue"),
  //   },
  //   {
  //     path: "/:pathMatch(.*)*",
  //     redirect: "/404",
  //   },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
