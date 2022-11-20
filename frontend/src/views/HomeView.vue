<template>
  <div class="center">
    <div class="clip">
      <img src="@/assets/background.png" alt="" class="rotate2" />
      <img src="@/assets/togedog.png" alt="" id="rotate" class="rotate3" />
      <img src="@/assets/login.png" alt="" class="login" @click="login()" />
    </div>
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
    goToSelect() {
      this.$router.push({ name: "select" });
    },

    login() {
      shell.openExternal(
        "http://k7a404.p.ssafy.io:8081/oauth2/authorization/google"
      );
      // shell.openExternal("http://localhost:8081/oauth2/authorization/google");
    },
  },
  data: () => ({
    width: document.documentElement.clientWidth,
  }),
  mounted() {
    this.getDimensions();
    window.addEventListener("resize", this.getDimensions);
    window.addEventListener("login-successful", this.goToSelect);
  },
  beforeUnmount() {
    window.removeEventListener("resize", this.getDimensions);
    window.removeEventListener("login-successful", this.goToSelect);
  },
};
</script>

<style scoped>
.clip {
  width: 100%;
  height: 100%;
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

.rotate3 {
  position: fixed;
  width: 40vw;
  animation: rotate_image3 240s linear infinite;
  transform-origin: 20% 50%;
}

.rotate2 {
  animation: rotate_image2 1200s linear infinite;
  transform-origin: 50% 50%;
  width: 200vw;
  height: 200vh;
}
.login {
  position: fixed;
  width: 60vw;
  bottom: 15%;
  animation: blink-effect 4s ease-in-out infinite;
}
.login:hover {
  transform: scale(1.02);
  animation: none;
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
@keyframes blink-effect {
  50% {
    opacity: 0%;
  }
}
</style>
