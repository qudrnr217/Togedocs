<template>
  <q-layout view="hHh lpR fFf">
    <q-header elevated class="bg-primary text-white" height-hint="98">
      <q-toolbar>
        <q-toolbar-title>
          <q-avatar>
            <img src="https://cdn.quasar.dev/logo-v2/svg/logo-mono-white.svg" />
          </q-avatar>
          Title
        </q-toolbar-title>
      </q-toolbar>

      <q-tabs align="left">
        <q-route-tab to="/page1" label="Page One" />
        <q-route-tab to="/page2" label="Page Two" />
        <q-route-tab to="/page3" label="Page Three" />
      </q-tabs>
    </q-header>

    <q-page-container>
      <div class="project-list column justify-around items-center">
        <div class="column">
          <q-btn
            stack
            icon="add"
            color="primary"
            label="새 프로젝트"
            class="create-project-btn"
          />
        </div>
        <div class="col" v-for="projectItem in projectItems" :key="projectItem">
          <q-card class="project-item items-center justify-between row">
            <q-card-section horizontal>
              <q-item>
                <q-item-section avatar>
                  <q-avatar>
                    <img
                      src="https://secure.gravatar.com/avatar/20bbf5d322e0760a476480837f6cc85e?s=180&d=identicon"
                    />
                  </q-avatar>
                </q-item-section>
              </q-item>
              <q-item class="column">
                <div class="text-h5 q-mt-sm q-mb-xs col">
                  {{ projectItem.name }}
                </div>
                <div class="project-desc col">
                  {{ projectItem.desc }} | {{ projectItem.members.join(", ") }}
                </div>
              </q-item>
              <q-item class="row text-caption text-grey items-center"
                ><div class="col project-period">
                  2022.10.24 ~ 2022.11.23
                </div></q-item
              >
            </q-card-section>
            <q-btn
              class="go-to-project-btn"
              outline
              rounded
              color="primary"
              label="이동"
            />
          </q-card>
        </div>
      </div>
    </q-page-container>

    <q-footer elevated class="bg-grey-8 text-white">
      <q-toolbar>
        <q-toolbar-title>
          <q-avatar>
            <img src="https://cdn.quasar.dev/logo-v2/svg/logo-mono-white.svg" />
          </q-avatar>
          <div>Title</div>
        </q-toolbar-title>
      </q-toolbar>
    </q-footer>
  </q-layout>
</template>

<script>
export default {
  data() {
    return {
      projectItems: [
        {
          name: "1",
          members: ["황재완", "홍인호", "강병국"],
          desc: "1번 프로젝트",
        },
        {
          name: "1",
          members: ["황재완", "홍인호", "강병국"],
          desc: "2번 프로젝트",
        },
      ],
    };
  },
};
</script>

<style scoped>
.project-item {
  min-width: 80vw;
  min-height: 20vh;
  padding: 2rem;
}
.project-list {
  height: 80vh;
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
</style>
