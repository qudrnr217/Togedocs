const commonStore = {
  namespaced: true,
  state: {
    userId: 999,
    userName: "테스트",
    imgNo: 9,
    projectId: 1,
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
};

export default commonStore;
