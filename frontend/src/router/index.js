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
  },
  {
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
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
