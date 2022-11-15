<template>
  <div class="container">
    <div class="imgcontainer">
      <img src="@/assets/dogs.jpg" alt="" />
    </div>

    <div class="projectinfo">
      <div class="title">{{ projectItem.desc }}</div>
      <div class="title-detail">
        {{ projectItem.members.join(", ") }}
      </div>
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
  font-size: 2vh;
  width: 30vw;
  font-weight: bolder;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  text-align: center;
}
.title-detail {
  padding-left: 5px;
  width: 30vw;
  font-size: 1.5vh;
  font-size: 500;
  font-weight: 300px;
  color: rgb(120, 120, 120);
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  text-align: center;
}

.imgcontainer {
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
</style>
