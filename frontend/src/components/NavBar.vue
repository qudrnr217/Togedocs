<template>
  <q-header>
    <div class="navbar">
      <div class="navbar-pages">
        <router-link :to="{ name: 'select' }" @click="resetProjectId()">
          <div>
            <q-icon :name="biHouseFill" size="xs" />
            <span> Home</span>
          </div></router-link
        >
        <router-link :to="{ name: 'docs' }">
          <q-icon :name="biPencilFill" size="xs" />
          <span> API 명세서</span></router-link
        >
        <router-link :to="{ name: 'test' }">
          <q-icon :name="biSendFill" size="xs" />
          <span> API 테스트</span></router-link
        >
      </div>
      <div class="navbar-users">
        <q-avatar size="30px" class="q-mr-sm">
          <img :src="getUserImg(imgNo)" />
        </q-avatar>
        <div>{{ userName }}</div>
      </div>
    </div>
  </q-header>
</template>

<script>
import { mapMutations, mapState } from "vuex";
import {
  biHouseFill,
  biPencilFill,
  biSendFill,
} from "@quasar/extras/bootstrap-icons";
export default {
  name: "NavBar",
  setup() {
    return {
      // icon
      biHouseFill,
      biPencilFill,
      biSendFill,
    };
  },
  computed: {
    ...mapState("commonStore", ["userId", "userName", "imgNo"]),
  },
  methods: {
    ...mapMutations("commonStore", ["SET_PROJECTID"]),
    resetProjectId() {
      this.SET_PROJECTID(null);
    },
    getUserImg(imgNo) {
      return require(`@/assets/user/${imgNo}.png`);
    },
  },
};
</script>

<style scoped>
.navbar {
  display: flex;
  height: 3rem;
  width: 100vw;

  align-items: center;
  background: var(--cultured);
  color: var(--charcoal);
  box-shadow: 4px 4px var(--cultured);
  font-weight: bold;
  justify-content: space-between;
}
.navbar-pages {
  display: flex;
  width: 50%;

  justify-content: space-evenly;
  flex-direction: row;
  align-items: center;
}

.navbar-users {
  display: flex;
  margin-right: 40px;
  align-items: center;

  justify-content: space-evenly;
}
</style>
