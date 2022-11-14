<template>
  <q-header>
    <div class="navbar">
      <div class="navbar-pages">
        <router-link :to="{ name: 'select' }" style="color: var(--coral)">
          <div>Home</div></router-link
        >
        <router-link :to="{ name: 'docs' }">
          <div>공유 api 문서</div></router-link
        >
        <router-link :to="{ name: 'test' }"> <div>api test</div></router-link>
      </div>
      <div class="navbar-users">
        <div
          style="
            width: 30px;
            height: 30px;
            border-radius: 50%;

            background: var(--cultured);
          "></div>
        <div style="color: var(--coral)">{사용자닉네임}</div>
        <div>...</div>
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

  align-items: center;
  background: none;
  box-shadow: 4px 4px var(--cultured);
  font-weight: bold;
  justify-content: space-between;
}
.navbar-pages {
  display: flex;
  width: 65%;

  justify-content: space-evenly;
  flex-direction: row;
  align-items: center;
}

.navbar-users {
  display: flex;
  width: 35%;
  margin-right: 20px;
  align-items: center;

  justify-content: space-evenly;
}
</style>
