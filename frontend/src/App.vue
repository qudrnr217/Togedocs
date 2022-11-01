<template>
  <div>
    <nav-bar></nav-bar>
    <router-view />
  </div>
</template>

<script>
import NavBar from "@/components/NavBar.vue";
export default {
  name: "App",
  components: {
    NavBar,
  },
};
</script>

<style>
@import "./const/main.css";
a {
  text-decoration: none;
  color: inherit;
}
</style>
