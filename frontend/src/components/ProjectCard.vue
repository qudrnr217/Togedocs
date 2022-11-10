<template>
  <div class="container row justify-around black05">
    <div>
      <q-avatar class="col-md-2" size="7vw">
        <img
          src="https://secure.gravatar.com/avatar/20bbf5d322e0760a476480837f6cc85e?s=180&d=identicon" />
      </q-avatar>
    </div>
    <div class="col-md-6">
      <div class="column">
        <div class="title">{{ projectItem.desc }}</div>
        <div class="title-detail">
          {{ projectItem.members.join(", ") }}
        </div>
      </div>
    </div>
    <div class="col-md-2 column">
      <div class="timetable">
        <b>시작일</b>
        <div>{2022.11.07}</div>
        <b>종료일</b>
        <div>{2022.11.08}</div>
      </div>
    </div>
    <div class="col-md-2 column">
      <q-btn
        class="col q-ma-md"
        rounded
        label="프로젝트 이동"
        @click="goToProjectUrl" />
      <q-btn
        class="col q-ma-md"
        rounded
        label="API 문서 이동"
        @click="goToApiDocs" />
    </div>
  </div>
</template>
<script>
export default {
  props: {
    projectItem: Object,
  },
  methods: {
    goToProjectUrl() {
      window.location.href = this.projectItem.url;
    },
    goToApiDocs() {
      this.$router.push({ name: "docs" });
    },
  },
};
</script>

<style scoped>
.container {
  width: 80vw;
  height: 20vh;
  background: linear-gradient(25deg, #b8ceef, #c0f5fb, #f8f8ba);
  display: flex;
  align-items: center;
  margin: 1rem 0 1rem 0;
  border-radius: 8px;
  box-shadow: 5px 5px lightgrey;
}
img {
  margin-left: 20px;
  margin-right: 20px;
  height: 10vh;
  border-radius: 50%;
  background-size: contain;
  background-repeat: no-repeat;
}
.title {
  font-size: 4vh;
  font-weight: bolder;
}
.title-detail {
  font-size: 2vh;
  font-size: 500;
  font-weight: bold;
}
.timetable {
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>
