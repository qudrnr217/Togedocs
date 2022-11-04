<template>
  <div class="container row justify-around">
    <div>
      <q-avatar class="col-md-2" size="7vw">
        <img
          src="https://secure.gravatar.com/avatar/20bbf5d322e0760a476480837f6cc85e?s=180&d=identicon"
        />
      </q-avatar>
    </div>
    <div class="col-md-5">
      <div class="column">
        <div class="title">{{ projectItem.title }}</div>
        <div class="title-detail">
          {{ projectItem.desc }} | {{ projectItem.members.join(", ") }}
        </div>
      </div>
    </div>
    <div class="col-md-2 column justify-around">
      <q-btn
        class="col q-ma-md"
        outline
        rounded
        color="secondary"
        label="프로젝트 이동"
        @click="goToProjectUrl"
      />
      <q-btn
        class="col q-ma-md"
        outline
        rounded
        color="primary"
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
  background-color: #c3cad4;
  display: flex;
  align-items: center;
  margin: 1rem 0 1rem 0;
  border-radius: 8px;
}
img {
  margin-left: 20px;
  margin-right: 20px;
  height: 10vh;
  border-radius: 50%;
}
.title {
  font-size: 4vh;
}
.title-detail {
  font-size: 2vh;
}
</style>
