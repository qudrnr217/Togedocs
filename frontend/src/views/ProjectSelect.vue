<template>
  <div>
    <q-page-container>
      <div class="projectheader">
        <div class="buttons q-gutter-sm">
          <q-btn @click="showModifyUserInfoModal()">회원정보수정</q-btn>
          <!-- logout 함수는 미구현. this.$router.push로 home으로 돌아가게 해야할듯 -->
          <q-btn @click="logout()">로그아웃</q-btn>
        </div>
        <div class="header">
          <div class="profileimg">
            <!-- @/assets/togedog.jpg -->
            <q-img
              :src="getUserImg(imgNo)"
              style="width: 100%; border-radius: 20px"
            />
          </div>
          <div class="username">{{ userName }} 님 반갑습니다.</div>
        </div>

        <div class="shadow"></div>
      </div>

      <q-layout view="hHh lpR fFf" color="warning">
        <q-page-container>
          <div class="project-list">
            <div class="column">
              <q-btn
                stack
                color="secondary"
                class="create-project-btn"
                @click="showCreatePjtModal()"
              >
                <q-tooltip class="bg-positive">
                  새로운 프로젝트를 생성합니다
                </q-tooltip>
                <div style="font-size: 20px">+</div></q-btn
              >
            </div>
            <div class="cards q-gutter-sm">
              <div v-for="(project, idx) in projects" :key="idx" class="card">
                <project-card :projectItem="project" />
              </div>
            </div>
          </div>
        </q-page-container>
        <q-dialog v-model="createPjtModal" persistent>
          <q-card class="modal">
            <q-card-section>
              <div class="text-h6">새 프로젝트 생성</div>
            </q-card-section>
            <q-separator />
            <q-card-section>
              <div class="q-gutter-md">
                <div class="text-center q-gutter-xs">
                  <q-img
                    :src="getProjectImg(newProject.imgNo)"
                    spinner-color="white"
                    style="height: 100px; width: 100px; border-radius: 5px"
                  />
                  <div>
                    <q-btn dense @click="makeImgNo(1)">RESET</q-btn>
                  </div>
                </div>
                <q-input
                  label="프로젝트 이름"
                  filled
                  type="text"
                  v-model="newProject.title"
                />
                <q-input
                  label="프로젝트 설명"
                  filled
                  type="textarea"
                  v-model="newProject.desc"
                  autogrow
                />
              </div>
            </q-card-section>

            <q-separator />

            <q-card-actions align="right">
              <q-btn
                flat
                label="취소"
                color="primary"
                v-close-popup
                @click="resetCreatePjtModal"
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
        <q-dialog v-model="modifyUserInfoModal" persistent>
          <q-card class="modal">
            <q-card-section>
              <div class="text-h6">회원정보 수정</div>
            </q-card-section>
            <q-separator />
            <q-card-section>
              <div class="q-gutter-md">
                <div class="text-center q-gutter-xs">
                  <q-img
                    :src="getUserImg(modifyUserInfo.imgNo)"
                    spinner-color="white"
                    style="height: 100px; width: 100px; border-radius: 5px"
                  />
                  <div>
                    <q-btn dense @click="makeImgNo(2)">RESET</q-btn>
                  </div>
                </div>
                <q-input
                  label="이름"
                  filled
                  type="text"
                  v-model="modifyUserInfo.name"
                />
              </div>
            </q-card-section>

            <q-separator />

            <q-card-actions align="right">
              <q-btn flat label="취소" color="primary" v-close-popup />
              <q-btn
                flat
                label="생성"
                color="primary"
                v-close-popup
                @click="doModifyUserInfo()"
              />
            </q-card-actions>
          </q-card>
        </q-dialog>
      </q-layout>
    </q-page-container>
  </div>
</template>

<script>
import { ref } from "vue";
import ProjectCard from "@/components/ProjectCard.vue";
import { mapState, mapActions, mapMutations } from "vuex";
import { getProjects, postNewProject } from "@/api/project";
import jwt_decode from "jwt-decode";
import { modifyUserInfo } from "@/api/user";

export default {
  computed: {
    ...mapState("commonStore", ["userId", "userName", "imgNo"]),
  },
  data() {
    return {
      createPjtModal: ref(false),
      newProject: ref({
        imgNo: "",
        title: "",
        desc: "",
      }),

      modifyUserInfoModal: ref(false),
      modifyUserInfo: ref({
        imgNo: "",
        name: "",
      }),

      projects: ref([]),
    };
  },
  components: {
    ProjectCard,
  },
  mounted() {
    let token = localStorage.getItem("accessToken");
    if (token) {
      let userInfo = jwt_decode(token);
      this.imgNo = userInfo.imgNo;
      this.userId = userInfo.userId;
      this.userName = userInfo.name;
      this.SET_USERNAME(userInfo.name);
      this.SET_USERID(userInfo.userId);
      this.SET_IMGNO(userInfo.imgNo);
    } else {
      // 테스트용. 로그인을 안하고 넘어오면 이 부분이 실행됨.
      // 최종 배포 후 else 아래는 모두 지울 것.
      this.SET_IMGNO(Math.random() * 10);
      // 여기까지
    }

    this.callGetProject();
  },
  methods: {
    ...mapActions("projectStore", ["FETCH_PROJECTS"]),
    ...mapMutations("commonStore", ["SET_USERNAME", "SET_USERID", "SET_IMGNO"]),
    ...mapMutations("userStore", ["SET_TOKEN"]),
    //프로젝트 생성 api
    createNewProject() {
      let params = {
        title: this.newProject.title,
        desc: this.newProject.desc,
        imgNo: this.newProject.imgNo,
      };
      postNewProject(params).then((data) => {
        data;
        this.callGetProject();
      });
    },
    makeImgNo(type) {
      let imgNo = Math.floor(Math.random() * 10); // 0 ~ 9 까지의 난수 생성
      if (type == 1) {
        this.newProject.imgNo = imgNo;
      } else {
        this.modifyUserInfo.imgNo = imgNo;
      }
    },
    getUserImg(imgNo) {
      return require(`@/assets/user/${imgNo}.png`);
    },
    getProjectImg(imgNo) {
      return require(`@/assets/project/${imgNo}.png`);
    },
    showCreatePjtModal() {
      this.createPjtModal = true;
      this.makeImgNo(1);
    },
    showModifyUserInfoModal() {
      this.modifyUserInfoModal = true;
      this.modifyUserInfo.imgNo = this.imgNo;
      this.modifyUserInfo.name = this.userName;
    },
    resetCreatePjtModal() {
      this.newProject = {
        imgNo: null,
        title: null,
        desc: null,
      };
    },
    doModifyUserInfo() {
      let params = {
        name: modifyUserInfo.name,
        imgNo: modifyUserInfo.imgNo,
      };
      modifyUserInfo(params).then((data) => {
        data;
      });
      // axios 호출
      // TODO:
      // 토큰을 재발급 받아야되나? 토큰에 userId(얘는 괜찮) userName imgNo이 있어서...
      // 그리고 this.SET_USERNAME this.SET_IMGNO 해줘야됨.
    },
    callGetProject() {
      getProjects()
        .then((data) => {
          this.projects = data.data;
        })
        .catch(() => {
          // TEST용 코드. 나중에 catch를 통째로 삭제할 것.
          this.projects = [
            {
              myName: "정승욱",
              names: ["정승욱", "김하연", "강병국"],
              projectId: 1,
              role: "ADMIN",
              title: "asdf",
              desc: "asdfasdf",
              imgNo: 0,
            },
            {
              myName: "정승욱",
              names: ["정승욱", "김하연", "강병국"],
              projectId: 1,
              role: "ADMIN",
              title: "asdf",
              desc: "asdfasdf",
              imgNo: 0,
            },
            {
              myName: "정승욱",
              names: ["정승욱", "김하연", "강병국"],
              projectId: 1,
              role: "ADMIN",
              title: "asdf",
              desc: "asdfasdf",
              imgNo: 0,
            },
          ];
        });
    },
  },
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
.modal {
  min-width: 40vw;
  overflow: hidden;
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
