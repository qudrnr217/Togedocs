<template>
  <div>
    <div class="container">
      <img src="@/assets/Cat.png" alt="" />
      <div>
        <div class="title">{프로젝트타이틀}</div>
        <div class="title-detail">{참여자}</div>
      </div>
      <div>
        <div class="startdate">
          <div>시작일</div>
          <b>2022.11.02</b>
        </div>
        <div class="enddate">
          <div>종료일</div>
          <b>2022.11.28</b>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.container {
  width: 80vw;
  height: 15vh;
  background-color: #c3cad4;
  display: flex;
  align-items: center;
  justify-content: space-between;
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
