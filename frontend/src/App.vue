<template>
  <q-layout view="hHh lpR fFf">
    <router-view />
  </q-layout>
</template>

<script>
export default {
  name: "App",
  components: {},
  mounted() {
    this.$router.push("/home");
  },
};
</script>

<style>
@import "./const/main.css";
a {
  text-decoration: none;
  color: inherit;
}
@font-face {
  font-family: "NanumSquare";
  font-weight: 400;
  src: url("@/assets/fonts/NanumSquareR.ttf");
  src: url("@/assets/fonts/NanumSquareR.ttf") format("truetype");
}
@font-face {
  font-family: "NanumSquare";
  font-weight: 700;
  src: url("@/assets/fonts/NanumSquareB.ttf");
  src: url("@/assets/fonts/NanumSquareB.ttf") format("truetype");
}

@font-face {
  font-family: "NanumSquare";
  font-weight: 300;
  src: url("@/assets/fonts/NanumSquareL.ttf");
  src: url("@/assets/fonts/NanumSquareL.ttf") format("truetype");
}
</style>
