<template>
  <div>
    <input type="text-area" />
    <br />
    projectId: {{ document.projectId }}
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
    <q-layout
      view="hHh Lpr lff"
      container
      style="height: 300px"
      class="shadow-2 rounded-borders"
    >
      <q-btn flat @click="drawer = !drawer" round dense icon="menu"></q-btn>
      <q-drawer
        v-model="drawer"
        side="right"
        overlay
        show-if-above
        :width="drawerWidth"
        :breakpoint="0"
        bordered
        class="bg-grey-3"
      >
        <q-list>
          <q-item v-for="i in 5" :key="i" v-ripple>
            <q-item-section> Menu item {{ i }} </q-item-section>
          </q-item>
          <tr v-for="(row, rowId) in document.data" :key="rowId">
            <td>
              <strong>{{ rowId }}</strong>
            </td>

            <td v-for="(col, colId) in row" :key="colId">{{ col }}</td>
          </tr>
        </q-list>
        <div
          v-touch-pan.preserveCursor.prevent.mouse.horizontal="resizeDrawer"
          class="q-drawer__resizer"
        ></div>
      </q-drawer>
      <q-page-container>
        <q-page padding>
          <p v-for="n in 15" :key="n">
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Fugit nihil
            praesentium molestias a adipisci, dolore vitae odit, quidem
            consequatur optio voluptates asperiores pariatur eos numquam rerum
            delectus commodi perferendis voluptate?
          </p>
        </q-page>
      </q-page-container>
    </q-layout>
    <br />
    <!-- 개발자용 보기 -->
    <div>rows: {{ document.rows }}</div>
    <div>cols: {{ document.cols }}</div>
    <div>data: {{ document.data }}</div>
  </div>
</template>

<script>
import { ref } from "vue";
import { BASEURL } from "@/api/index.js";

import SockJS from "sockjs-client";
import Stomp from "stompjs";
// import draggable from "vuedraggable";

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
    // draggable,
  },
  setup() {
    let initialDrawerWidth;
    const drawerWidth = ref(300);

    return {
      // TODO: 나중에 자동으로 받아와서 채우는 걸로 변경
      projectId: ref(1),
      document: ref({
        projectId: null,
        rows: [],
        cols: [],
        data: {},
      }),
      drawer: ref(false),
      drawerWidth,
      resizeDrawer(ev) {
        if (ev.isFirst === true) {
          initialDrawerWidth = drawerWidth.value;
        }
        drawerWidth.value = initialDrawerWidth - ev.offset.x;
      },
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
          console.log(response);
          this.document = response.data;
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
    openSideDrawer(rowId) {
      rowId;
    },
  },
};
</script>

<style scoped>
td {
  width: 100px;
  border: 1px solid black;
}
.q-drawer__resizer {
  position: absolute;
  top: 0;
  bottom: 0;
  left: -2px;
  width: 4px;
  background-color: red;
  cursor: ew-resize;
}
</style>
