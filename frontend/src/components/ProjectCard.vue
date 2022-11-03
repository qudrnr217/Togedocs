<template>
  <div class="container">
    <img src="@/assets/Cat.png" alt="" />
    <div>
      <div class="title">{프로젝트타이틀}</div>
      <div class="title-detail">{참여자}</div>
    </div>
    <router-link :to="{ path: 'projectview/docs' }"
      ><button>프로젝트로</button>
    </router-link>
  </div>
</template>

<script>
export default {};
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
