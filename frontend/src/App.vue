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
</style>
