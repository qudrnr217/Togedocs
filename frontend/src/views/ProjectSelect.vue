<template>
  <div>
    <q-page-container>
      <div class="projectheader">
        <div class="buttons q-gutter-sm">
          <q-btn
            outline
            rounded
            :icon="fasUser"
            label="프로필 수정"
            @click="showModifyUserInfoModal()"
          >
          </q-btn>
          <q-btn
            outline
            rounded
            :icon="fasArrowRightFromBracket"
            label="로그아웃"
            @click="logout()"
          >
          </q-btn>
        </div>
        <div class="header">
          <div class="profileimg">
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
            <div class="row justify-center" style="width: 100%">
              <div class="col-1 text-center">
                <q-btn
                  round
                  color="secondary"
                  class="project-btn"
                  @click="showCreateProjectModal()"
                >
                  <q-tooltip class="tooltip">
                    새로운 프로젝트 생성하기
                  </q-tooltip>
                  <q-icon :name="fasPlus" size="xs" />
                </q-btn>
              </div>
              <div class="col-1 text-center">
                <q-btn
                  round
                  color="secondary"
                  class="project-btn"
                  @click="showJoinProjectModal()"
                >
                  <q-tooltip class="tooltip">
                    초대코드로 프로젝트 입장하기
                  </q-tooltip>
                  <q-icon :name="fasRightToBracket" size="xs" />
                </q-btn>
              </div>
            </div>
            <div class="cards q-gutter-sm">
              <div v-for="(project, idx) in projects" :key="idx" class="card">
                <project-card
                  :projectItem="project"
                  @update-projects="callGetProject()"
                />
              </div>
            </div>
          </div>
        </q-page-container>
        <q-dialog v-model="createProjectModal" persistent>
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
                    <q-btn
                      dense
                      round
                      @click="makeImgNo(1)"
                      :icon="fasRotate"
                    />
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
                @click="resetCreateProjectModal"
              />
              <q-btn
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
                    <q-btn
                      dense
                      round
                      @click="makeImgNo(2)"
                      :icon="fasRotate"
                    />
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
                label="적용"
                color="primary"
                v-close-popup
                @click="doModifyUserInfo()"
              />
            </q-card-actions>
          </q-card>
        </q-dialog>
        <q-dialog v-model="joinProjectModal" persistent>
          <q-card class="modal">
            <q-card-section>
              <div class="text-h6">프로젝트 입장</div>
            </q-card-section>
            <q-separator />
            <q-card-section>
              <div class="q-gutter-md">
                <q-input
                  label="초대코드"
                  filled
                  type="text"
                  v-model="joinProjectCode"
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
                @click="resetJoinProjectModal"
              />
              <q-btn
                label="확인"
                color="primary"
                @click="callGetProjectByCode"
              />
            </q-card-actions>
          </q-card>
        </q-dialog>
        <q-dialog v-model="joinProjectConfirmModal" persistent>
          <q-card class="modal">
            <q-card-section>
              <div class="container">
                <div class="imgcontainer">
                  <img :src="getProjectImg(joinProjectItem.imgNo)" />
                </div>

                <div class="project-info">
                  <div class="title">{{ joinProjectItem.title }}</div>
                  <div class="title-detail">
                    {{ joinProjectItem.desc }}
                  </div>
                  <div class="title-name">
                    <span>멤버 : </span>
                    <template v-for="name in joinProjectItem.names" :key="name">
                      <span class="q-ma-xs">
                        {{ name }}
                      </span>
                    </template>
                  </div>
                </div>
              </div>
            </q-card-section>
            <q-separator />
            <q-card-actions align="right">
              <q-btn flat label="뒤로 가기" color="primary" v-close-popup />
              <q-btn
                label="입장하기"
                color="primary"
                v-close-popup
                @click="callJoinProject"
              />
            </q-card-actions>
          </q-card>
        </q-dialog>
        <q-dialog v-model="warningDialog" position="top">
          <q-card style="width: 350px">
            <q-card-section class="row items-center no-wrap">
              <div>유효한 초대 코드가 아닙니다!</div>
              <q-space />
            </q-card-section>
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
import {
  getProjects,
  createProject,
  getProjectByCode,
  joinProject,
} from "@/api/project";
import jwt_decode from "jwt-decode";
import { getUserNameAndImgNo, modifyUserInfo, logoutUser } from "@/api/user";
import {
  fasPlus,
  fasRightToBracket,
  fasRotate,
  fasUser,
  fasArrowRightFromBracket,
} from "@quasar/extras/fontawesome-v6";

export default {
  computed: {
    ...mapState("commonStore", ["userId", "userName", "imgNo"]),
  },
  data() {
    return {
      createProjectModal: ref(false),
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

      joinProjectModal: ref(false),
      joinProjectConfirmModal: ref(false),
      joinProjectCode: ref(""),
      joinProjectItem: ref({}),
      warningDialog: ref(false),

      projects: ref([]),

      // icon
      fasPlus,
      fasRightToBracket,
      fasRotate,
      fasUser,
      fasArrowRightFromBracket,
    };
  },
  components: {
    ProjectCard,
  },
  mounted() {
    let token = localStorage.getItem("accessToken");
    if (token) {
      let userInfo = jwt_decode(token);
      this.SET_USERID(userInfo.userId);
      this.callGetUserNameAndImgNo(this.userId);
    }
    this.callGetProject();
  },
  methods: {
    ...mapActions("projectStore", ["FETCH_PROJECTS"]),
    ...mapMutations("commonStore", ["SET_USERNAME", "SET_USERID", "SET_IMGNO"]),
    ...mapMutations("userStore", ["SET_TOKEN"]),

    //logout api
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

    //프로젝트 생성 api
    createNewProject() {
      let requestBody = {
        title: this.newProject.title,
        desc: this.newProject.desc,
        imgNo: this.newProject.imgNo,
      };
      createProject(
        { requestBody: requestBody },
        (response) => {
          response;
          this.callGetProject();
        },
        (e) => {
          console.warn(e);
        }
      );
    },
    makeImgNo(type) {
      let imgNo = Math.floor(Math.random() * 10); // 0 ~ 9 까지의 난수 생성
      if (type == 1) {
        this.newProject.imgNo = imgNo;
      } else {
        this.modifyUserInfo.imgNo = imgNo;
      }
    },
    callGetUserNameAndImgNo(userId) {
      getUserNameAndImgNo(
        { pathVariable: { userId: userId } },
        (response) => {
          this.SET_USERNAME(response.data.userName);
          this.SET_IMGNO(response.data.imgNo);
        },
        (e) => {
          console.warn(e);
        }
      );
    },
    getUserImg(imgNo) {
      return require(`@/assets/user/${imgNo}.png`);
    },
    getProjectImg(imgNo) {
      return require(`@/assets/project/${imgNo}.png`);
    },
    showCreateProjectModal() {
      this.createProjectModal = true;
      this.makeImgNo(1);
    },
    showModifyUserInfoModal() {
      this.modifyUserInfoModal = true;
      this.modifyUserInfo.imgNo = this.imgNo;
      this.modifyUserInfo.name = this.userName;
    },
    resetCreateProjectModal() {
      this.newProject = {
        imgNo: 0,
        title: null,
        desc: null,
      };
    },
    doModifyUserInfo() {
      let params = {
        name: this.modifyUserInfo.name,
        imgNo: this.modifyUserInfo.imgNo,
      };
      modifyUserInfo({ requestBody: params }).then((data) => {
        data;
        this.callGetUserNameAndImgNo(this.userId);
        this.callGetProject();
      });
    },
    callGetProject() {
      getProjects()
        .then((data) => {
          this.projects = data.data;
        })
        .catch((e) => {
          console.warn(e);
        });
    },
    resetJoinProjectModal() {
      this.joinProjectCode = "";
    },
    showJoinProjectModal() {
      this.joinProjectModal = true;
    },
    showJoinProjectConfirmModal() {
      this.joinProjectConfirmModal = true;
    },
    callJoinProject() {
      this.joinProjectModal = false;
      this.joinProjectConfirmModal = false;
      joinProject(
        {
          requestBody: { code: this.joinProjectCode },
        },
        (response) => {
          response;
          this.joinProjectCode = "";
          this.callGetProject();
        },
        (error) => {
          console.warn(error);
        }
      );
    },
    callGetProjectByCode() {
      getProjectByCode(
        { pathVariable: { code: this.joinProjectCode } },
        (response) => {
          this.joinProjectItem = response.data;
          this.joinProjectConfirmModal = true;
        },
        (error) => {
          error;
          this.warningDialog = true;
          // console.warn(error);
        }
      );
    },
  },
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  overflow: hidden;
}
img {
  height: 20vh;
  width: 30vw;
  object-fit: cover;
  border-radius: 20px;
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
.project-info {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background: var(--white);
  width: 100%;
  height: 100%;
  border-radius: 20px;
}
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
.project-btn {
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
  margin: 5px 5px 0px 0px;
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
.tooltip {
  background-color: var(--charcoal);
}
</style>
