import { createStore } from "vuex";
import createPersistedState from "vuex-persistedstate";

import projectStore from "@/store/modules/projectStore.js";

export default createStore({
  state: {
    apiStoreList: [],
    userId: 0,
    projectId: 1,
  },
  getters: {
    userId: (state) => state.userId,
    projectId: (state) => state.projectId,
  },
  mutations: {
    SET_USERID: (state, userId) => {
      state.userId = userId;
    },
    SET_PROJECTID: (state, projectId) => {
      state.projectId = projectId;
    },
  },
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
