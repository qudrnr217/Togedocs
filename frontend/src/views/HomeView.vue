<template>
  <div class="center">
    <div class="main">
      <div style="width: 100% height: 100%" class="rotate2">
        <img id="rotate" src="@/assets/togedog.jpg" alt="" class="poster" />
        <img @click="login()" src="@/assets/login.png" alt="" class="login" />
      </div>
    </div>
    {{ width }}
    <router-link :to="{ path: 'select' }">To test jh</router-link>
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
    getDimensions() {
      this.width = document.documentElement.clientWidth;
      let image = document.getElementById("rotate");
      image.style.transform = "rotate(" + this.width / 3 + "deg)";
    },
    login() {
      shell.openExternal(
        "http://k7a404.p.ssafy.io:8081/oauth2/authorization/google"
      );
      // shell.openExternal(
      //   "http://k7a404.p.ssafy.io:8081/oauth2/authorization/google"
      // );
    },
  },
  data: () => ({
    width: document.documentElement.clientWidth,
  }),
  mounted() {
    this.getDimensions();
    console.log("hi");
    let _this = this;
    console.log(_this);
    window.addEventListener("resize", this.getDimensions);
    window.addEventListener("login-successful", event => {
      console.log(event);
      _this.$router.push({ name: "select" });
    });
  },
};
</script>

<style scoped>
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

  background-size: cover;
}

.login {
  position: absolute;
  left: 10vw;
  bottom: 20vh;
  max-height: 20vh;
  max-width: 80vw;
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
.poster {
  max-height: 80vh;
  max-width: 80vw;
  background-size: cover;
}
.rotate2 {
  animation: rotate_image 30s linear infinite;
  transform-origin: 70% 50%;
}

@keyframes rotate_image {
  100% {
    transform: rotate(360deg);
  }
}
</style>
