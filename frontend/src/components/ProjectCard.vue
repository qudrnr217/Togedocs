<template>
  <div>
    <!-- <router-link
      :to="{ name: 'docs', params: { projectId: projectItem.projectId } }"
    > -->
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
    </div>
    <!-- </router-link> -->
  </div>
</template>
<script>
import { biPencilFill, biSendFill } from "@quasar/extras/bootstrap-icons";
import { mapMutations } from "vuex";
export default {
  data() {
    return {};
  },
  setup() {
    return {
      // icon
      biPencilFill,
      biSendFill,
    };
  },

  props: {
    projectItem: Object,
  },
  methods: {
    ...mapMutations("commonStore", ["SET_PROJECTID"]),
    goToDocs(id) {
      console.log(id);
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
