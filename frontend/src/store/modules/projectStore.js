import { getProjects } from "@/api/project.js";

const projectStore = {
  namespaced: true,
  state: {
    projects: [],
  },
  getters: {},
  mutations: {
    SET_PROJECTS: (state, projects) => {
      state.projects = projects;
    },
  },
  actions: {
    FETCH_PROJECTS: ({ commit }, params) => {
      return getProjects(params, ({ data }) => {
        commit("SET_PROJECTS", data);
      });
    },
  },
};

export default projectStore;
