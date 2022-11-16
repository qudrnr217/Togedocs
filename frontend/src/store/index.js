import { createStore } from "vuex";
import createPersistedState from "vuex-persistedstate";

import projectStore from "@/store/modules/projectStore.js";

export default createStore({
  state: {
    apiStoreList: [],
    userId: 999,
    userName: "테스트",
    imgNo: 9,
    projectId: 0,
  },
  getters: {
    userId: (state) => state.userId,
    userName: (state) => state.userName,
    imgNo: (state) => state.imgNo,
    projectId: (state) => state.projectId,
  },
  mutations: {
    SET_USERID: (state, userId) => {
      state.userId = userId;
    },
    SET_USERNAME: (state, userName) => {
      state.userName = userName;
    },
    SET_IMGNO: (state, imgNo) => {
      state.imgNo = imgNo;
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
