<template>
  <div>
    <nav-bar></nav-bar>
    <router-view />
  </div>
</template>

<script>
import NavBar from "@/components/NavBar.vue";
export default {
  name: "ProjectView",
  components: {
    NavBar,
  },
};
</script>

<style></style>
