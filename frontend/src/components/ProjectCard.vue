<template>
  <div class="container">
    <div class="imgcontainer">
      <img :src="getProjectImg(projectItem.imgNo)" />

      <q-btn
        class="router-btn"
        style="bottom: 12vh"
        size="1.5vh"
        @click="goToDocs(projectItem.projectId)"
      >
        <q-icon left :name="biPencilFill" size="xs" />
        <div>API 명세서</div>
      </q-btn>

      <q-btn
        class="router-btn"
        style="bottom: 6vh"
        size="1.5vh"
        @click="goToTest(projectItem.projectId)"
      >
        <q-icon left :name="biSendFill" size="xs" />
        <div>API 테스트</div>
      </q-btn>
    </div>

    <div class="projectinfo">
      <div class="title">{{ projectItem.title }}</div>
      <div class="title-detail">
        {{ projectItem.desc }}
      </div>
      <div class="title-name q-pb-md text-right">
        <template v-for="name in projectItem.names" :key="name">
          <span class="q-ma-xs">
            {{ name }}
          </span>
        </template>
      </div>
    </div>
    <q-popup-proxy context-menu>
      <q-card>
        <q-card-actions vertical>
          <q-btn flat>
            <q-icon left :name="fasDoorOpen" size="xs" />
            <div>프로젝트 탈퇴</div>
          </q-btn>
          <q-btn v-if="projectItem.role == 'ADMIN'" flat>
            <q-icon left :name="fasTrash" size="xs" />
            <div>프로젝트 삭제</div>
          </q-btn>
        </q-card-actions>
      </q-card>
    </q-popup-proxy>
  </div>
</template>
<script>
import { mapMutations } from "vuex";
import {
  biPencilFill,
  biSendFill,
  biTrashFill,
  biEscape,
} from "@quasar/extras/bootstrap-icons";

import { fasDoorOpen, fasTrash } from "@quasar/extras/fontawesome-v6";
export default {
  data() {
    return {};
  },
  setup() {
    return {
      // icon
      biPencilFill,
      biSendFill,
      biTrashFill,
      biEscape,
      fasDoorOpen,
      fasTrash,
    };
  },

  props: {
    projectItem: Object,
  },
  methods: {
    ...mapMutations("commonStore", ["SET_PROJECTID"]),
    goToDocs(id) {
      this.SET_PROJECTID(id);
      this.$router.push({ name: "docs" });
    },
    goToTest(id) {
      this.SET_PROJECTID(id);
      this.$router.push({ name: "test" });
    },
    getProjectImg(imgNo) {
      return require(`@/assets/project/${imgNo}.png`);
    },
  },
};
</script>

<style scoped>
ul {
  list-style: none;
  margin: 0;
  padding: 0;
}

li {
  margin: 0 0 0 0;
  padding: 0 0 0 0;
  border: 0;
  float: left;
}
.container {
  background: var(--white);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 2rem;
  border-radius: 20px;

  border-style: solid;
  overflow: hidden;

  border-width: thin;
  border-color: lightgray;
}
img {
  height: 20vh;
  width: 30vw;

  object-fit: cover;

  background-size: contain;
  background-repeat: no-repeat;
}
.title {
  font-size: 2.5vh;
  width: 28vw;
  font-weight: bolder;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.title-detail {
  height: 4.5vh;
  width: 28vw;
  font-size: 1.5vh;
  text-overflow: hidden;

  word-break: break-word;

  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  color: black;
}
.title-name {
  width: 28vw;
  font-size: 1.5vh;
  font-size: 500;
  font-weight: 300px;
  color: rgb(120, 120, 120);
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.imgcontainer {
  position: relative;
}
.router-btn {
  position: absolute;
  left: 50%;
  background-color: rgba(0, 0, 0, 0.5);
  color: var(--cultured);
  font-weight: bold;
  transform: translate(-50%, 0%);
  visibility: hidden;
  opacity: 0;
  transition: opacity 0.2s linear, visibility 1s linear;
}
.imgcontainer:hover .router-btn {
  opacity: 1;
  visibility: visible;
}

.projectinfo {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background: var(--white);
  width: 100%;
  height: 100%;
  border-radius: 20px;
}
.cards {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-evenly;
}
</style>
