<template>
  <div>
    <router-link :to="{ path: 'projectview/docs' }">
      <div class="container">
        <div class="imgcontainer">
          <img src="@/assets/dogs.jpg" alt="" />
        </div>

        <div class="projectinfo">
          <div class="title">{{ projectItem.title }}</div>
          <div class="title-name">
            {{ projectItem.members.join(", ") }}
          </div>
          <div class="title-detail">
            {{ projectItem.desc }}
          </div>
        </div>
      </div>
    </router-link>
  </div>
</template>
<script>
export default {
  data() {
    return {};
  },

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
  font-size: 2vh;
  width: 28vw;
  font-weight: bolder;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  text-align: center;
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

.title-detail {
  margin-top: 5px;
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
</style>
