import { createStore } from "vuex";
import createPersistedState from "vuex-persistedstate";

import projectStore from "@/store/modules/projectStore.js";
import commonStore from "@/store/modules/commonStore.js";

export default createStore({
  modules: {
    projectStore,
    commonStore,
  },
  plugins: [
    createPersistedState({
      path: ["userStore"],
    }),
  ],
});
