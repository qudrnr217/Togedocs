<template>
  <q-layout
    view="hHh Lpr lff"
    container
    style="height: 500px"
    class="shadow-2 rounded-borders"
  >
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
              <q-popup-proxy context-menu @before-hide="callUpdateCol(element)">
                <q-banner>
                  <q-input
                    filled
                    v-model="element.name"
                    dense
                    @keydown.enter.prevent="callUpdateCol(element)"
                    @blur="callUpdateCol(element)"
                  />
                  <q-btn
                    color="primary"
                    label="Del Col"
                    @click="callDeleteCol(element.uuid)"
                  />
                </q-banner>
              </q-popup-proxy>
            </div>
          </template>
        </draggable>

        <q-card class="q-pa-sm q-ma-xs cell cell-no">
          <q-icon name="add" />
          <q-popup-proxy>
            <q-banner>
              <q-input filled dense v-model="addColName" />
              <q-btn
                color="primary"
                label="Add Col"
                @click="callAddCol(addColName, 'text')"
              />
            </q-banner>
          </q-popup-proxy>
        </q-card>
        <q-dialog v-model="dialog" position="top">
          <q-card style="width: 350px">
            <q-card-section class="row items-center no-wrap">
              <div>속성 이름을 입력해주세요!</div>

              <q-space />
            </q-card-section>
          </q-card>
        </q-dialog>
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

            <q-popup-proxy context-menu>
              <q-banner>
                <q-btn
                  color="primary"
                  label="Del Row"
                  @click="callDeleteRow(document.rows[index])"
                />
              </q-banner>
            </q-popup-proxy>
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
            <q-btn
              color="primary"
              label="Open"
              @click="openSideDrawer(rowId)"
            />
          </td>

          <td v-for="(col, colId) in row" :key="colId">{{ col }}</td>
        </tr>
      </q-markup-table>
      <q-btn color="primary" label="Add row" @click="callAddRow()" />
      <q-btn color="primary" label="Add col" @click="callAddCol()" />

      <q-drawer
        v-model="drawer"
        side="right"
        overlay
        :width="drawerWidth"
        :breakpoint="0"
        bordered
        class="bg-grey-3"
      >
        <q-btn flat @click="drawer = !drawer" round dense label="close">
        </q-btn>
        <br />
        <q-markup-table>
          <tr v-for="(value, key) in document.data[drawerRowId]" :key="key">
            <td>
              <strong>{{ key }}</strong>
            </td>
            <td>
              {{ value }}
            </td>
          </tr>
        </q-markup-table>
        <div
          v-touch-pan.preserveCursor.prevent.mouse.horizontal="resizeDrawer"
          class="q-drawer__resizer"
        ></div>
      </q-drawer>

      <br />
      <!-- 개발자용 보기 -->
      <div>rows: {{ document.rows }}</div>
      <div>cols: {{ document.cols }}</div>
      <div>data: {{ document.data }}</div>
    </div>
  </q-layout>
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
  updateCol,
} from "@/api/apidocs.js";

moveRow;
moveCol;

export default {
  components: {
    draggable,
  },
  setup() {
    const dialog = ref(false);

    let initialDrawerWidth;
    const drawerWidth = ref(300);
    const drawerRowId = ref(null);

    return {
      // TODO: 나중에 자동으로 받아와서 채우는 걸로 변경
      projectId: ref(1),
      document: ref({
        projectId: null,
        rows: [],
        cols: [],
        data: {},
      }),
      addColName: ref(""),
      rowData: ref([]),
      drawer: ref(false),
      drawerWidth,
      drawerRowId,
      resizeDrawer(ev) {
        if (ev.isFirst === true) {
          initialDrawerWidth = drawerWidth.value;
        }
        drawerWidth.value = initialDrawerWidth - ev.offset.x;
      },
      dialog,
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
    callAddCol(name, type) {
      if (name.length == 0) {
        this.addColWarning();
        return;
      }
      addCol(
        {
          pathVariable: {
            projectId: this.projectId,
          },
          requestBody: {
            name: name,
            type: type,
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
    callUpdateCol(element) {
      updateCol(
        {
          pathVariable: {
            projectId: this.projectId,
            colId: element.uuid,
          },
          requestBody: {
            name: element.name,
            type: element.type,
            width: element.width,
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
      this.drawer = true;
      this.drawerRowId = rowId;
    },

    addColWarning() {
      this.dialog = true;
      console.log(this.addColName);
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
