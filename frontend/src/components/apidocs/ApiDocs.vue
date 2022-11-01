<template>
  <div>
    <br />
    projectId: {{ document.projectId }}
    <table>
      <tr>
        <td></td>
        <td v-for="(col, colId) in document.cols" :key="colId">
          <strong>{{ col.name }}</strong>
        </td>
      </tr>
    </table>
    <table>
      <tr v-for="(row, rowId) in document.data" :key="rowId">
        <td>
          <strong>{{ rowId }}</strong>
        </td>
        <td v-for="(col, colId) in row" :key="colId">{{ col }}</td>
      </tr>
    </table>
    <br />
    <!-- 개발자용 보기 -->
    <div>rows: {{ document.rows }}</div>
    <div>cols: {{ document.cols }}</div>
    <div>data: {{ document.data }}</div>
  </div>
</template>

<script>
import { ref } from "vue";

import { getDocs } from "@/api/apidocs.js";

export default {
  setup() {
    return {
      // TODO: 나중에 자동으로 받아와서 채우는 걸로 변경
      projectId: ref(1),
      document: ref({
        projectId: null,
        rows: [],
        cols: [],
        data: {},
      }),
    };
  },
  mounted() {
    getDocs(
      { pathvariable: this.projectId },
      (response) => {
        console.log(response);
        this.document = response.data;
      },
      (error) => {
        console.warn(error);
      }
    );
  },
};
</script>

<style scoped>
td {
  width: 100px;
  border: 1px solid black;
}
</style>
