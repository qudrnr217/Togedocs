<template>
  <div class="container row justify-around">
    <div>
      <q-avatar class="col-md-2" size="6vw">
        <img
          src="https://secure.gravatar.com/avatar/20bbf5d322e0760a476480837f6cc85e?s=180&d=identicon"
        />
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
    <div class="col-md-2 column"></div>
    <div class="col-md-2 column">
      <q-btn
        class="col q-ma-md bold padding"
        color="accent"
        rounded
        label="프로젝트 이동"
        @click="goToProjectUrl"
      />
      <q-btn
        class="col q-ma-md bold"
        rounded
        label="API 문서 이동"
        @click="goToApiDocs"
      />
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
  background: rgb(255, 255, 255);
  display: flex;
  align-items: center;
  margin: 1rem 0 1rem 0;
  border-radius: 20px;
  box-shadow: 5px 5px rgb(0, 4, 255);
}
img {
  height: 10vh;
  border-radius: 50%;
  background-size: contain;
  background-repeat: no-repeat;
}
.title {
  font-size: 3.5vh;
  font-weight: bolder;
}
.title-detail {
  padding-left: 5px;
  font-size: 2vh;
  font-size: 500;
  font-weight: 300px;
  color: rgb(120, 120, 120);
}
.timetable {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.bold {
  font-weight: 600;
}

.padding {
  margin-bottom: 0;
}
</style>
