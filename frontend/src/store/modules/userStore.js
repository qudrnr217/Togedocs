const userStore = {
  namespaced: true,
  state: {
    accessToken: "",
  },
  getters: {},
  mutations: {
    SET_TOKEN: (state, accessToken) => {
      state.token = accessToken;
    },
  },
  actions: {},
};

export default userStore;
