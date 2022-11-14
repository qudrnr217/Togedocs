<template>
  <div class="center">
    <div class="main">
      <div class="titletext">TogeDocs</div>

      <button @click="login()" class="button">
        <div class="login" style="">
          <img src="@/assets/gitlab.png" alt="" class="gitlablogo" />
          <div class="login">로그인</div>
        </div>
      </button>
    </div>
    <router-link :to="{ path: 'select' }">To test jh</router-link>
  </div>
</template>

<script>
import { shell } from "electron";
// import { access } from "fs";
// import router from "vue";
export default {
  methods: {
    login() {
      shell.openExternal("http://localhost:8081/oauth2/authorization/google");
    },
  },
  mounted() {
    console.log("hi");
    let _this = this;
    console.log(_this);
    window.addEventListener("login-successful", event => {
      console.log(event);
      _this.$router.push({ name: "select" });
    });
  },
};
</script>

<style scoped>
.gitlablogo {
  height: 10vh;
}
.login {
  font-size: 5vw;
}
.spacing {
  display: flex;
  flex-direction: column;
  gap: 60px;
  width: 75vw;
  overflow: hidden;
}
.main {
  display: flex;
  justify-content: space-between;
  flex-direction: column;
  align-items: center;
  box-sizing: border-box;

  width: 80vw;
  height: 80vh;
  margin: auto;

  border-radius: 5px;
  background-image: url("@/assets/togedog.jpg");
  background-size: cover;
}
.titletext {
  font-size: 8vw;
  color: var(--cultured);
}
.login {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  padding: 8px 12px;
  gap: 64px;

  background: none;
  border-radius: 10px;
}
.button {
  background: var(--cultured);
  color: var(--charcoal);
  height: 15%;
  width: 60%;
  margin-bottom: 20px;

  border-radius: 10px;
  border-color: var(--cultured);
}
.center {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  flex-direction: column;
}
</style>
