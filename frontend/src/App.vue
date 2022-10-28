<template>
  <div>
    <router-view />
    <router-link :to="{ name: 'home' }"> <div>홈</div></router-link>
    <router-link :to="{ name: 'select' }">
      <div>프로젝트 선택</div></router-link
    >
    <router-link :to="{ name: 'docs' }"> <div>공유 api 문서</div></router-link>
    <router-link :to="{ name: 'test' }"> <div>api test</div></router-link>
  </div>
</template>

<script>
export default {
  name: "App",
  components: {},
};
</script>

<style></style>
