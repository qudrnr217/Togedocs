<template>
  <div>
    <div
      style="
        display: flex;
        justify-content: center;
        align-items: center;
        box-sizing: border-box;

        width: 80vw;
        height: 80vh;
        margin: auto;

        background: var(--white);
        border: 0.5px solid var(--charcoal);
        box-shadow: -5px 5px 4px 5px var(--charcoal);
        border-radius: 5px;
      "
    >
      <div class="spacing">
        <div
          class="service title"
          style="
            display: flex;
            flex-direction: row;
            justify-content: center;
            align-items: flex-start;
            padding: 8px;
            gap: 8px;
          "
        >
          <img src="@/assets/dogs.jpg" alt="" style="width: 25vw" />
          <div style="font-size: 8vw">TogeDocs</div>
        </div>
        <button @click="login()">
          <div
            style="
              display: flex;
              flex-direction: row;
              align-items: center;
              justify-content: center;
              padding: 8px 12px;
              gap: 64px;

              background: var(--cultured);
              border-radius: 10px;
            "
          >
            <img src="@/assets/gitlab.png" alt="" class="gitlablogo" />
            <div class="login">로그인</div>
          </div>
        </button>
      </div>
    </div>
    <router-link :to="{ name: 'projectview' }">To test jh</router-link>
  </div>
</template>

<script>
import { shell } from "electron";
// import { BASEURL } from "@/api/index.js";
// import { access } from "fs";
// import router from "vue";
// import { getProjects } from "@/api/project";
export default {
  methods: {
    login() {
      shell.openExternal(
        "http://k7a404.p.ssafy.io:8081/oauth2/authorization/google"
      );
      // shell.openExternal(
      //   "http://k7a404.p.ssafy.io:8081/oauth2/authorization/google"
      // );
    },
  },
  mounted() {
    console.log("hi");
    let _this = this;
    console.log(_this);
    window.addEventListener("login-successful", (event) => {
      console.log(event);
      _this.$router.push({ name: "select" });
    });
  },
};
</script>

<style scoped>
npm .gitlablogo {
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
</style>
