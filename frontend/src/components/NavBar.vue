<template>
  <div>
    <div class="navbar">
      <router-link :to="{ name: 'home' }"> <div>홈</div></router-link>
      <router-link :to="{ name: 'select' }">
        <div>프로젝트 선택</div></router-link
      >
      <router-link :to="{ name: 'docs' }">
        <div>공유 api 문서</div></router-link
      >
      <router-link :to="{ name: 'test' }"> <div>api test</div></router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "NavBar",
};
</script>

<style scoped>
.navbar {
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
  align-items: center;
  height: 3rem;
  width: 100vw;
  margin-bottom: 20px;
  background-color: aquamarine;
}
</style>
