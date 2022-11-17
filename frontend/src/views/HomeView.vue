<template>
  <div class="center">
    <div class="clip">
      <img src="@/assets/placeholder.png" alt="" class="rotate2" />
      <img src="@/assets/축제해달.png" alt="" id="rotate" class="rotate3" />
      <img src="@/assets/login.png" alt="" class="login" @click="login()" />
    </div>

    <router-link :to="{ name: 'select' }">To test jh</router-link>
  </div>
</template>

<script>
import { shell } from "electron";
import { mapMutations } from "vuex";

export default {
  methods: {
    ...mapMutations("userStore", ["SET_TOKEN"]),
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
    let _this = this;
    window.addEventListener("resize", this.getDimensions);
    window.addEventListener("login-successful", event => {
      event;
      _this.$router.push({ name: "select" });
    });
  },
  // unmounted() {
  //   this.SET_TOKEN(localStorage.getItem("accessToken"));
  // },

  // ...mapMutations("commonStore", ["SET_USERNAME", "SET_USERID", "SET_IMGNO"]),
};
</script>

<style scoped>
.clip {
  width: 90%;
  height: 90%;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;

  background-color: var(--charcoal);
}

.center {
  max-height: 100vh;
  height: 100vh;
  width: 100vw;
  max-width: 100vw;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
.background {
}
.rotate3 {
  position: fixed;
  width: 40vw;
  animation: rotate_image3 180s linear infinite;
  transform-origin: 20% 50%;
}

.rotate2 {
  animation: rotate_image2 30s linear infinite;
  transform-origin: 50% 50%;
}
.login {
  position: fixed;
  width: 60vw;
  bottom: 15%;
}
@keyframes rotate_image4 {
  100% {
    transform: rotate(3200deg);
  }
}
@keyframes rotate_image3 {
  100% {
    transform: rotate(3200deg);
  }
}
@keyframes rotate_image2 {
  100% {
    transform: rotate(360deg);
  }
}
</style>
