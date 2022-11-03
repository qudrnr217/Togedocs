<template>
  <q-layout view="hHh lpR fFf">
    <q-page-container>
      <div class="project-list column items-center">
        <div class="column">
          <q-btn
            stack
            icon="add"
            color="primary"
            label="새 프로젝트"
            class="create-project-btn"
          />
        </div>
        <div
          class="col-4 project-item"
          v-for="projectItem in projectItems"
          :key="projectItem"
        >
          <project-card></project-card>
          <!-- <q-card class="project-item items-center justify-between row">
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
          </q-card> -->
        </div>
      </div>
    </q-page-container>
  </q-layout>
</template>

<script>
import ProjectCard from "@/components/ProjectCard.vue";
export default {
  components: {
    ProjectCard,
  },
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
  max-height: 1vh;
  /* padding: 2rem; */
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
