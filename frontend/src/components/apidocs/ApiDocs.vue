<template>
  <div>
    <input type="text-area" />
    <br />
    projectId: {{ document.projectId }}

    <q-card class="q-pa-xs row">
      <q-card class="q-pa-sm q-ma-xs cell cell-no"> </q-card>
      <draggable
        class="row"
        v-model="document.cols"
        @start="dragCol = true"
        @end="dragCol = false"
        item-key="id_col"
        @change="onColChange"
      >
        <template #item="{ element }">
          <div
            class="q-pa-sm q-ma-xs cell"
            :style="{ width: element.width + 'px' }"
          >
            {{ element.name }}
          </div>
        </template>
      </draggable>
    </q-card>
    <!--  -->
    <draggable
      v-model="rowData"
      @start="dragRow = true"
      @end="dragRow = false"
      item-key="id_row"
      @change="onRowChange"
    >
      <template #item="{ element, index }">
        <q-card class="q-pa-xs row">
          <q-card class="q-pa-sm q-ma-xs cell cell-no">
            {{ index + 1 }}
          </q-card>
          <template v-for="(cell, index) in element" :key="index">
            <div
              class="q-pa-sm q-ma-xs cell"
              :style="{ width: cell.width + 'px' }"
            >
              {{ cell.content }}
            </div>
          </template>
        </q-card>
      </template>
    </draggable>
    <!--  -->

    <br /><br />

    <q-markup-table>
      <tr>
        <td></td>
        <td></td>
        <td v-for="(col, colId) in document.cols" :key="colId">
          <strong>{{ col.name }}</strong>
          <q-btn
            color="primary"
            label="Del Col"
            @click="callDeleteCol(col.uuid)"
          />
        </td>
      </tr>
      <tr v-for="(row, rowId) in document.data" :key="rowId">
        <td>
          <q-icon name="drag_handle" />
        </td>
        <td>
          <strong>{{ rowId }}</strong>
          <q-btn
            color="primary"
            label="Del row"
            @click="callDeleteRow(rowId)"
          />
        </td>

        <td v-for="(col, colId) in row" :key="colId">{{ col }}</td>
      </tr>
    </q-markup-table>
    <q-btn color="primary" label="Add row" @click="callAddRow()" />
    <q-btn color="primary" label="Add col" @click="callAddCol()" />
    <!-- <table>
      <tr>
        <td></td>
        <td v-for="(col, colId) in document.cols" :key="colId">
          <strong>{{ col.name }}</strong>
        </td>
      </tr>
      <tr v-for="(row, rowId) in document.data" :key="rowId">
        <td>
          <strong>{{ rowId }}</strong>
        </td>
        <td v-for="(col, colId) in row" :key="colId">{{ col }}</td>
      </tr>
    </table> -->
    <br />
    <!-- 개발자용 보기 -->
    <div>rows: {{ document.rows }}</div>
    <div>cols: {{ document.cols }}</div>
    <div>data: {{ document.data }}</div>

    <br />
    <br />
  </div>
</template>

<script>
import { ref } from "vue";
import { BASEURL } from "@/api/index.js";

import SockJS from "sockjs-client";
import Stomp from "stompjs";
import draggable from "vuedraggable";

import {
  getDocs,
  addRow,
  addCol,
  moveRow,
  moveCol,
  deleteRow,
  deleteCol,
} from "@/api/apidocs.js";

moveRow;
moveCol;

export default {
  components: {
    draggable,
  },
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

      rowData: ref([]),
    };
  },
  mounted() {
    this.callGetDocs();

    this.userName = Math.round(Math.random() * 1000);

    this.socket = new SockJS(BASEURL + "ws");
    this.stompClient = Stomp.over(this.socket);
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe(
        "/sub/" + this.projectId + "/refresh",
        (msg) => {
          msg;
          // var res = JSON.parse(msg.body);
          // // 내가 보낸 메세지라면 return
          // if (res.userName == this.userName) return;
          // // 다른 사람이 보낸 메세지라면 계속

          // TODO: Refresh
          console.log("다른 사용자가 REFRESH 요청을 보냈습니다.");
          this.callGetDocs();
          // --
        }
      );
      this.stompClient.subscribe("/sub/" + this.projectId + "/focus", (msg) => {
        var res = JSON.parse(msg.body);
        // 내가 보낸 메세지라면 return
        if (res.userName == this.userName) return;
        // 다른 사람이 보낸 메세지라면 계속

        // TODO: Focus
        // 다른 사람의 포커스 위치를 옮겨줌. (내껀 내 프론트에서만 보여줌)
        console.log(
          res.userName,
          "사용자가 FOCUS를 ",
          res.content,
          "로 변경하였습니다."
        );
        // --
      });
    });
  },
  beforeUnmount() {
    this.stompClient.disconnect();
  },
  methods: {
    onColChange: function (evt) {
      this.callMoveCol(evt.moved.element.uuid, evt.moved.newIndex);
    },
    onRowChange: function (evt) {
      this.callMoveRow(evt.moved.element[0].rowId, evt.moved.newIndex);
    },
    refreshReq() {
      const req = { userName: this.userName, content: null };
      this.stompClient.send(
        "/pub/" + this.projectId + "/refresh",
        {},
        JSON.stringify(req)
      );
    },
    callGetDocs() {
      getDocs(
        {
          pathVariable: {
            projectId: this.projectId,
          },
        },
        (response) => {
          let res_doc = response.data;
          this.document = res_doc;
          this.rowData = [];
          res_doc.rows.forEach((rowId) => {
            let this_row = [];
            res_doc.cols.forEach((col) => {
              let colId = col.uuid;
              let colWidth = col.width;
              this_row.push({
                rowId: rowId,
                content: res_doc.data[rowId][colId],
                width: colWidth,
              });
            });
            this.rowData.push(this_row);
          });
        },
        (error) => {
          console.warn(error);
        }
      );
    },
    callAddRow() {
      addRow(
        {
          pathVariable: {
            projectId: this.projectId,
          },
        },
        (response) => {
          console.log(response);
          this.refreshReq();
        },
        (error) => {
          console.warn(error);
        }
      );
    },
    callAddCol() {
      addCol(
        {
          pathVariable: {
            projectId: this.projectId,
          },
          requestBody: {
            name: "temp",
            type: "text",
          },
        },
        (response) => {
          console.log(response);
          this.refreshReq();
        },
        (error) => {
          console.warn(error);
        }
      );
    },
    callMoveRow(fromId, toIndex) {
      console.log(fromId, toIndex);
      moveRow(
        {
          pathVariable: {
            projectId: this.projectId,
          },
          requestBody: {
            fromId: fromId,
            toIndex: toIndex,
          },
        },
        (response) => {
          console.log(response);
          this.refreshReq();
        },
        (error) => {
          console.warn(error);
        }
      );
    },
    callMoveCol(fromId, toIndex) {
      moveCol(
        {
          pathVariable: {
            projectId: this.projectId,
          },
          requestBody: {
            fromId: fromId,
            toIndex: toIndex,
          },
        },
        (response) => {
          console.log(response);
          this.refreshReq();
        },
        (error) => {
          console.warn(error);
        }
      );
    },
    callDeleteRow(rowId) {
      deleteRow(
        {
          pathVariable: {
            projectId: this.projectId,
            rowId: rowId,
          },
        },
        (response) => {
          console.log(response);
          this.refreshReq();
        },
        (error) => {
          console.warn(error);
        }
      );
    },
    callDeleteCol(colId) {
      deleteCol(
        {
          pathVariable: {
            projectId: this.projectId,
            colId: colId,
          },
        },
        (response) => {
          console.log(response);
          this.refreshReq();
        },
        (error) => {
          console.warn(error);
        }
      );
    },
  },
};
</script>

<style scoped>
.cell {
  background: whitesmoke;
}
.cell-no {
  width: 30px;
  text-align: right;
}
</style>
