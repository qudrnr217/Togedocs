<template>
  <div>
    <div
      style="
        display: flex;
        justify-content: center;
        align-items: center;
        box-sizing: border-box;

        width: 80vw;
        height: 80vh;
        margin: auto;

        background: #ffffff;
        border: 0.5px solid #37485d;
        box-shadow: -5px 5px 4px 5px #37485d;
        border-radius: 5px;
      ">
      <div class="spacing">
        <div
          class="service title"
          style="
            display: flex;
            flex-direction: row;
            justify-content: center;
            align-items: flex-start;
            padding: 8px;
            gap: 8px;
          ">
          <img src="@/assets/Cat.png" alt="" />
          <div
            style="
              font-size: 5.5rem;

              color: #000000;
            ">
            TogeDOGs
          </div>
        </div>
        <router-link :to="{ name: 'select' }">
          <div
            style="
              display: flex;
              flex-direction: row;
              align-items: center;
              justify-content: center;
              padding: 8px 12px;
              gap: 64px;

              background: #f5f5f5;
              border-radius: 10px;
            ">
            <img src="@/assets/gitlab.png" alt="" class="gitlablogo" />
            <div class="login">로그인</div>
          </div>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.gitlablogo {
  height: 10vh;
}
.login {
  font-size: 3rem;
}
.spacing {
  display: flex;
  flex-direction: column;
  gap: 60px;
  width: 75vw;
  overflow: hidden;
}
</style>
