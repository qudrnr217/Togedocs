<template>
  <div>
    <div class="projectheader">
      <div class="buttons">
        <button>회원정보수정</button>
        <router-link :to="{ name: 'home' }">
          <button>로그아웃</button>
        </router-link>
      </div>
      <div class="header">
        <div class="profileimg">
          <img
            src="@/assets/togedog.jpg"
            alt=""
            style="width: 100%; border-radius: 20px"
          />
        </div>
        <div class="username">{{ myname }} 님 반갑습니다.</div>
      </div>

      <div class="shadow"></div>
    </div>

    <q-layout view="hHh lpR fFf">
      <q-page-container>
        <div class="project-list">
          <div class="column">
            <q-btn
              stack
              color="secondary"
              class="create-project-btn"
              @click="createNewProjectBtnClicked = true"
              ><q-tooltip class="bg-positive"
                >새로운 프로젝트를 생성합니다</q-tooltip
              >
              <div style="font-size: 20px">+</div></q-btn
            >
          </div>
          <div class="cards">
            <div v-for="(project, idx) in projects" :key="idx" class="card">
              <project-card :projectItem="project"></project-card>
            </div>
          </div>
        </div>
      </q-page-container>
      <q-dialog v-model="createNewProjectBtnClicked">
        <q-card class="createNewProjectDialog">
          <q-card-section>
            <div class="text-h6">새 프로젝트 생성</div>
          </q-card-section>
          <q-separator />
          <q-card-section class="scroll">
            <div class="q-gutter-y-md column">
              <q-input
                class="col"
                clearable
                filled
                type="url"
                v-model="newProject.title"
                label="프로젝트 이름"
              />
              <q-input
                class="col"
                clearable
                filled
                v-model="newProject.url"
                label="프로젝트 URL"
              />
              <q-input
                class="col"
                clearable
                filled
                autogrow
                v-model="newProject.desc"
                label="프로젝트 설명"
              />
              <div class="row justify-between">
                <q-input
                  class="col-5"
                  clearable
                  filled
                  v-model="newProject.date.from"
                  mask="date"
                  :rules="['date']"
                  label="프로젝트 시작일"
                >
                  <template v-slot:append>
                    <q-icon name="event" class="cursor-pointer">
                      <q-popup-proxy
                        cover
                        transition-show="scale"
                        transition-hide="scale"
                      >
                        <q-date minimal v-model="newProject.date.from">
                          <div class="row items-center justify-end">
                            <q-btn
                              v-close-popup
                              label="Close"
                              color="primary"
                              flat
                            />
                          </div>
                        </q-date>
                      </q-popup-proxy>
                    </q-icon>
                  </template>
                </q-input>
                <q-icon name="arrow_right_alt" size="3.5rem" color="grey-6" />
                <q-input
                  class="col-5"
                  clearable
                  filled
                  v-model="newProject.date.to"
                  mask="date"
                  :rules="['date']"
                  label="프로젝트 종료일"
                >
                  <template v-slot:append>
                    <q-icon name="event" class="cursor-pointer">
                      <q-popup-proxy
                        cover
                        transition-show="scale"
                        transition-hide="scale"
                      >
                        <q-date minimal v-model="newProject.date.to">
                          <div class="row items-center justify-end">
                            <q-btn
                              v-close-popup
                              label="Close"
                              color="primary"
                              flat
                            />
                          </div>
                        </q-date>
                      </q-popup-proxy>
                    </q-icon>
                  </template>
                </q-input>
              </div>
            </div>
          </q-card-section>

          <q-separator />

          <q-card-actions align="right">
            <q-btn
              flat
              label="취소"
              color="primary"
              v-close-popup
              @click="resetCreateNewProjectDialog"
            />
            <q-btn
              flat
              label="생성"
              color="primary"
              v-close-popup
              @click="createNewProject"
            />
          </q-card-actions>
        </q-card>
      </q-dialog>
    </q-layout>
  </div>
</template>

<script>
import ProjectCard from "@/components/ProjectCard.vue";
import { postNewProject, getProjects } from "@/api/project";
import { getMyName } from "@/api/user";
import { mapState, mapActions, mapMutations } from "vuex";

export default {
  data() {
    return {
      projects: [],
      myname: "",
    };
  },
  components: {
    ProjectCard,
  },
  mounted() {
    //사용자의 이름 가져오기
    getMyName().then((data) => {
      this.myname = data;
    });

    //사용자의 참여하고있는 프로젝트 정보가져오는 api
    getProjects().then((data) => {
      console.log(data);
      this.projects = data.data;
    });
  },
  computed: {
    ...mapState("projectStore", ["projects"]),
  },
  methods: {
    ...mapActions("projectStore", ["FETCH_PROJECTS"]),
    ...mapMutations("userStore", ["SET_TOKEN"]),
    //프로젝트 생성 api
    createNewProject() {
      let params = {
        title: this.projects.title,
        desc: this.projects.desc,
        imgNo: this.projects.imgNo,
      };
      postNewProject(params).then((data) => {
        console.log(data);
      });
    },
    resetCreateNewProjectDialog() {
      this.newProject = {
        title: null,
        url: null,
        desc: null,
        date: { from: null, to: null },
      };
    },
  },

  // data() {
  //   return {
  //     createNewProjectBtnClicked: false,
  //     newProject: {
  //       title: null,
  //       url: null,
  //       desc: null,
  //       date: { from: null, to: null },
  //     },
  //   };
  // },
};
</script>

<style scoped>
.project-item {
  min-width: 80vw;
  max-height: 1vh;
  /* padding: 2rem; */
}
.project-list {
  padding-top: 0;
  background-color: var(--cultured);
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100vw;
  padding-top: 30px;
}
.create-project-btn {
  margin-right: 1rem;
  margin-bottom: 1rem;
}
.go-to-project-btn {
  margin-right: 5rem;
}
.project-period {
  font-size: 1rem;
  margin-left: 10rem;
}
.createNewProjectDialog {
  min-width: 40vw;
}
.profileimg {
  display: flex;
  justify-content: center;
  width: 30vw;
  border-radius: 50%;
}
.projectheader {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  margin-bottom: 0px;
}
.shadow {
  width: 100vw;
  height: 1px;

  background: linear-gradient(
    180deg,
    var(--cultured) 0%,
    rgba(58, 53, 53, 0.458) 100%
  );
}
.header {
  display: flex;
  width: 100vw;
  flex-direction: column;
  align-items: center;
  margin-top: 2vh;
}
.username {
  font-size: 4vh;
  font-weight: bold;
  margin-bottom: 5vh;
  margin-top: 5vh;
}
.buttons {
  position: absolute;
  display: flex;
  align-items: center;
  top: 3vh;
  right: 0;
}
button {
  border: 0;
  outline: 0;
  background: none;
  color: var(--charcoal);
}
.cards {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-evenly;
}
</style>
