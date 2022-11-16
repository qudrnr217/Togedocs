<template>
  <div>
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
            :src="imgUrl(imgNo)"
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
          <div class="cards">
            <div v-for="(project, idx) in projects" :key="idx" class="card">
              <project-card :projectItem="project"></project-card>
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
                  :src="imgUrl(newProject.imgNo)"
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
                  :src="imgUrl(modifyUserInfo.imgNo)"
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
  </div>
</template>

<script>
import { ref } from "vue";
import ProjectCard from "@/components/ProjectCard.vue";
import { mapState, mapActions, mapMutations } from "vuex";
import { getProjects, postNewProject } from "@/api/project";
import jwt_decode from "jwt-decode";

export default {
  data() {
    return {
      imgNo: ref(""),
      userName: ref(""),

      projects: ref([]),

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
    };
  },
  components: {
    ProjectCard,
  },
  mounted() {
    let token = localStorage.getItem("accessToken");
    if (token) {
      let userInfo = jwt_decode(token);
      console.log("token: ", token);
      console.log("userinfo: ", userInfo);
      this.imgNo = userInfo.imgNo;
      this.userId = userInfo.userId;
      this.userName = userInfo.name;
      this.$store.commit("SET_USERNAME", userInfo.name);
      this.$store.commit("SET_USERID", userInfo.userId);
      this.$store.commit("SET_IMGNO", userInfo.imgNo);
    } else {
      // 테스트용. 로그인을 안하고 넘어오면 이 부분이 실행됨.
      // 최종 배포 후 else 아래는 모두 지울 것.
      this.userId = this.$store.getters.userId;
      this.userName = this.$store.getters.userName;
      this.$store.commit("SET_IMGNO", Math.random() * 10);
      this.imgNo = this.$store.getters.imgNo;
      // 여기까지
    }

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
    makeImgNo(type) {
      let imgNo = Math.floor(Math.random() * 10); // 0 ~ 9 까지의 난수 생성
      if (type == 1) {
        this.newProject.imgNo = imgNo;
      } else {
        this.modifyUserInfo.imgNo = imgNo;
      }
    },
    imgUrl(imgNo) {
      return "https://placeimg.com/100/100/nature?t=" + imgNo / 10;
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
      // axios 호출
      console.log("modify user info");
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
