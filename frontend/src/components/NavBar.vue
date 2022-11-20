<template>
  <q-header>
    <div class="navbar">
      <div class="navbar-pages">
        <router-link :to="{ name: 'select' }">
          <div @click="resetProjectId()">
            <q-icon :name="fasReply" size="sm" color="primary" />
            <q-tooltip>프로젝트 다시 선택하기</q-tooltip>
          </div>
        </router-link>
        <router-link :to="{ name: 'docs' }">
          <div :class="{ selected: $route.fullPath == '/project/docs' }">
            <q-icon :name="biPencilFill" size="xs" />
            <span>API 명세서</span>
          </div>
        </router-link>
        <router-link :to="{ name: 'test' }">
          <div :class="{ selected: $route.fullPath == '/project/test' }">
            <q-icon :name="biSendFill" size="xs" />
            <span>API 테스트</span>
          </div>
        </router-link>
      </div>
      <div class="navbar-users">
        <!-- no-caps -->
        <q-btn-dropdown
          unelevated
          rounded
          color="white"
          text-color="primary"
          :dropdown-icon="fasCaretDown"
        >
          <template v-slot:label>
            <q-avatar size="30px" class="q-mr-sm">
              <img :src="getUserImg(imgNo)" />
            </q-avatar>
            <div>{{ userName }}</div>
          </template>
          <q-list>
            <q-item clickable v-close-popup @click="logout()">
              <q-item-section
                ><q-btn
                  flat
                  :icon="fasArrowRightFromBracket"
                  label="로그아웃"
                  @click="logout()"
                >
                </q-btn
              ></q-item-section>
            </q-item>
          </q-list>
        </q-btn-dropdown>
      </div>
    </div>
  </q-header>
</template>

<script>
import { mapMutations, mapState } from "vuex";
import { logoutUser } from "@/api/user";
import {
  biHouseFill,
  biPencilFill,
  biSendFill,
} from "@quasar/extras/bootstrap-icons";
import {
  fasReply,
  fasCaretDown,
  fasArrowRightFromBracket,
} from "@quasar/extras/fontawesome-v6";
export default {
  name: "NavBar",
  setup() {
    return {
      // icon
      biHouseFill,
      biPencilFill,
      biSendFill,
      fasReply,
      fasCaretDown,
      fasArrowRightFromBracket,
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
    logout() {
      logoutUser(
        () => {
          this.$router.push({ name: "home" });
        },
        (e) => {
          console.warn(e);
        }
      );
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

.selected {
  color: var(--coral);
}
</style>
