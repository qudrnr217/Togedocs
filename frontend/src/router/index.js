import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import ApiDocs from "@/views/ApiDocs.vue";
import ApiTest from "@/views/ApiTest.vue";
import ProjectSelect from "@/views/ProjectSelect.vue";
import WebSocket from "@/views/WebSocket.vue";
import TestJh from "@/components/ProjectCard.vue";
import ProjectView from "@/views/ProjectView.vue";

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/projectview",
    name: "projectview",
    component: ProjectView,
    children: [
      {
        path: "docs",
        name: "docs",
        component: ApiDocs,
      },
      {
        path: "test",
        name: "test",
        component: ApiTest,
      },
    ],
  },

  {
    path: "/select",
    name: "select",
    component: ProjectSelect,
  },
  {
    path: "/ws",
    name: "ws",
    component: WebSocket,
  },
  {
    path: "/testjh",
    name: "testjh",
    component: TestJh,
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
