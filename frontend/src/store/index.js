import { createStore } from "vuex";
import createPersistedState from "vuex-persistedstate";

import projectStore from "@/store/modules/projectStore.js";

export default createStore({
  state: {},
  getters: {},
  mutations: {},
  actions: {},
  modules: {
    projectStore,
  },
  plugins: [
    createPersistedState({
      path: ["userStore"],
    }),
  ],
});
