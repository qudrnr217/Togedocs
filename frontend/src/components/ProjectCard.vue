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
      <div class="row" style="width: 100%">
        <div class="col title q-px-sm">{{ projectItem.title }}</div>
        <div class="col text-right">
          <q-btn flat :icon="biGearFill" size="sm">
            <q-popup-proxy>
              <q-card>
                <q-card-actions vertical>
                  <q-btn flat @click="leaveProjectDialog = true">
                    <q-icon left :name="fasDoorOpen" size="xs" />
                    <div>프로젝트 탈퇴</div>
                  </q-btn>
                  <q-btn
                    flat
                    :disable="checkUserRole()"
                    @click="deleteProjectDialog = true"
                  >
                    <q-icon left :name="fasTrash" size="xs" />
                    <div>프로젝트 삭제</div>
                  </q-btn>
                </q-card-actions>
              </q-card>
            </q-popup-proxy></q-btn
          >
        </div>
      </div>
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

    <q-dialog v-model="leaveProjectDialog" persistent>
      <q-card>
        <q-card-section class="row items-center">
          <q-avatar
            :icon="biExclamationTriangleFill"
            color="primary"
            text-color="white"
          />
          <span class="q-ml-sm">
            {{ projectItem.title }}을 떠나시겠습니까?
          </span>
        </q-card-section>
        <q-card-actions align="right">
          <q-btn flat label="취소" color="primary" v-close-popup />
          <q-btn
            label="탈퇴"
            color="primary"
            @click="callLeaveProject()"
            v-close-popup
          />
        </q-card-actions>
      </q-card>
    </q-dialog>
    <q-dialog v-model="deleteProjectDialog" persistent>
      <q-card>
        <q-card-section class="row items-center">
          <q-avatar
            :icon="biExclamationTriangleFill"
            color="primary"
            text-color="white"
          />
          <span class="q-ml-sm">
            {{ projectItem.title }}을 삭제하시겠습니까?
          </span>
        </q-card-section>
        <q-card-actions align="right">
          <q-btn flat label="취소" color="primary" v-close-popup />
          <q-btn
            label="삭제"
            color="primary"
            @click="callDeleteProject()"
            v-close-popup
          />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>
<script>
import { ref } from "vue";
import { mapMutations } from "vuex";

import { deleteProject, leaveProject } from "@/api/project.js";
import {
  biPencilFill,
  biSendFill,
  biTrashFill,
  biGearFill,
  biEscape,
  biExclamationTriangleFill,
} from "@quasar/extras/bootstrap-icons";

import { fasDoorOpen, fasTrash } from "@quasar/extras/fontawesome-v6";
export default {
  data() {
    return {};
  },
  setup() {
    return {
      projectPopup: ref(false),
      deleteProjectDialog: ref(false),
      leaveProjectDialog: ref(false),

      // icon
      biPencilFill,
      biSendFill,
      biTrashFill,
      biGearFill,
      biEscape,
      biExclamationTriangleFill,
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
    checkUserRole() {
      return this.projectItem.role !== "ADMIN";
    },
    callDeleteProject() {
      deleteProject(
        {
          pathVariable: { projectId: this.projectItem.projectId },
        },
        (response) => {
          response;
          // this.callGetProject();
          this.$emit("update-projects");
        },
        (error) => {
          console.warn(error);
        }
      );
    },
    callLeaveProject() {
      leaveProject(
        {
          pathVariable: { projectId: this.projectItem.projectId },
        },
        (response) => {
          response;
          // this.callGetProject();
          this.$emit("update-projects");
        },
        (error) => {
          console.warn(error);
        }
      );
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
