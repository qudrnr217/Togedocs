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

::-webkit-scrollbar {
  height: 8px;
  width: 8px;
  background: gray;
}

/* Track */
::-webkit-scrollbar-track {
  background: #f1f1f1;
}

/* Handle */
::-webkit-scrollbar-thumb {
  background: #888;
}

/* Handle on hover */
::-webkit-scrollbar-thumb:hover {
  background: #555;
}

::-webkit-scrollbar-thumb:horizontal {
  background: rgb(150, 150, 150);
  border-radius: 10px;
}
</style>
