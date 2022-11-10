<template>
  <q-header>
    <div class="navbar">
      <div class="navbar-pages">
        <router-link :to="{ name: 'select' }">
          <div>프로젝트 선택</div></router-link
        >
        <router-link :to="{ name: 'docs' }">
          <div>공유 api 문서</div></router-link
        >
        <router-link :to="{ name: 'test' }"> <div>api test</div></router-link>
      </div>
      <div class="navbar-users">
        <img src="@/assets/bell.png" alt="" />
        <img src="@/assets/settings.png" alt="" />
        <div
          style="
            width: 30px;
            height: 30px;
            border-radius: 50%;

            background: #d9d9d9;
          "
        ></div>
      </div>
    </div>
  </q-header>
</template>

<script>
export default {
  name: "NavBar",
};
</script>

<style scoped>
.navbar {
  display: flex;
  height: 3rem;
  width: 100vw;

  margin-bottom: 20px;
  align-items: center;
  background-color: #37485d;
  color: rgb(255, 255, 255);
  justify-content: space-between;
}
.navbar-pages {
  display: flex;
  width: 60vw;
  justify-content: space-evenly;
  flex-direction: row;
  align-items: center;
}

.navbar-users {
  display: flex;
  margin-right: 20px;

  width: 20vw;
  justify-content: space-evenly;
}
</style>
