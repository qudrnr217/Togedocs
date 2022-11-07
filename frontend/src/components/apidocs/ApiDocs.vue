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

      <!-- Columns -->
      <q-card class="q-pa-xs row">
        <!-- blank -->
        <q-card class="q-pa-sm q-ma-xs cell cell-no" />
        <!-- cols -->
        <draggable
          class="row"
          v-model="document.cols"
          @start="dragCol = true"
          @end="dragCol = false"
          item-key="id_col"
          @change="onColChange"
          handle=".handle-col"
        >
          <template #item="{ element }">
            <div class="row q-pa-xs">
              <div
                class="q-pa-sm cell row handle-col"
                :style="{ width: element.width + 'px' }"
              >
                {{ element.name }}
                <q-popup-proxy
                  context-menu
                  @before-hide="callUpdateCol(element)"
                >
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
              <div style="position: relative">
                <div
                  class="col-width-handle"
                  v-touch-pan.preserveCursor.prevent.mouse.horizontal="
                    resizeCol
                  "
                  @mouseover="element.active = true"
                  @mouseleave="element.active = false"
                  @mousedown="setHandlingItem(element.uuid)"
                >
                  <div class="handling">
                    <q-icon v-show="element.active" name="drag_indicator" />
                  </div>
                </div>
              </div>
            </div>
          </template>
        </draggable>

        <!-- "+" btn -->
        <q-card class="q-pa-sm q-my-xs">
          <q-icon class="addBtn shadow-1 cursor-pointer" name="add" />
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
      <!-- Rows -->
      <draggable
        v-model="rowData"
        @start="dragRow = true"
        @end="dragRow = false"
        item-key="id_row"
        @change="onRowChange"
        handle=".handle-row"
      >
        <template #item="{ element, index }">
          <q-card class="q-pa-xs row">
            <q-card
              @mouseover="rowActive[index] = true"
              @mouseleave="rowActive[index] = false"
              class="q-pa-sm q-ma-xs cell text-right cell-no handle-row"
            >
              <template v-if="!rowActive[index]">
                {{ index + 1 }}
              </template>
              <template v-else>
                <q-icon name="drag_indicator" />
              </template>
              <!-- <q-icon name="drag_indicator" class="handle-row" size="20px" /> -->
            </q-card>
            <template v-for="(cell, index) in element" :key="index">
              <div
                class="q-px-sm q-ma-xs cell"
                :style="{ width: cell.width + 'px' }"
              >
                <q-input
                  dense
                  :style="{
                    width: cell.width - 15 + 'px',
                  }"
                  type="text"
                  v-model="document.data[cell.rowId][cell.colId]"
                  @focus="editFocus(cell)"
                  @keypress.enter="
                    callUpdateCell(
                      cell.rowId,
                      cell.colId,
                      document.data[cell.rowId][cell.colId]
                    )
                  "
                  @blur="
                    callUpdateCell(
                      cell.rowId,
                      cell.colId,
                      document.data[cell.rowId][cell.colId]
                    )
                  "
                />
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

      <!-- TEST... -->
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
  updateCell,
} from "@/api/apidocs.js";

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

      handling_item: ref({
        index: null,
        uuid: null,
        name: null,
        type: null,
        width: null,
      }),

      rowData: ref([]),
      rowActive: ref([]),
      focus: ref({ isFocusing: false, rowId: "", colId: "" }),

      drawer: ref(false),
      drawerWidth,
      drawerRowId,
      resizeDrawer(ev) {
        if (ev.isFirst) {
          initialDrawerWidth = drawerWidth.value;
        }
        drawerWidth.value = initialDrawerWidth - ev.offset.x;
      },
      dialog,
    };
  },
  mounted() {
    this.callGetDocs();

    this.userName = Math.round(Math.random() * 1000); // 나중에 유저를 token에서 가져오자.

    // WEBSOCKET CONNECTION
    this.socket = new SockJS(BASEURL + "/ws");
    this.stompClient = Stomp.over(this.socket);
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe(
        "/sub/" + this.projectId + "/refresh",
        (msg) => {
          msg;
          // 보낸 사람이 자신인지 확인하는 로직을 추가하려면:
          // if ( msg.body.username == this.userName ) { ... }

          console.log("다른 사용자가 REFRESH 요청을 보냈습니다.");
          let editing_content = "";
          if (this.focus.isFocusing)
            editing_content =
              this.document.data[this.focus.rowId][this.focus.colId];
          this.callGetDocs();
          if (this.focus.isFocusing)
            this.document.data[this.focus.rowId][this.focus.colId] =
              editing_content;
        }
      );
      this.stompClient.subscribe("/sub/" + this.projectId + "/focus", (msg) => {
        // 보낸 사람이 자신인지 확인하는 로직을 추가하려면:
        // if ( msg.body.username == this.userName ) { ... }

        var res = JSON.parse(msg.body);
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
    onColChange(evt) {
      this.callMoveCol(evt.moved.element.uuid, evt.moved.newIndex);
    },
    onRowChange(evt) {
      this.callMoveRow(
        this.document.rows[evt.moved.oldIndex],
        evt.moved.newIndex
      );
    },
    setHandlingItem(uuid) {
      this.handling_item.uuid = uuid;
      let t_cols = this.document.cols;
      for (let i = 0; i < t_cols.length; i++)
        if (t_cols[i].uuid == uuid) {
          this.handling_item = { ...t_cols[i] };
          this.handling_item.index = i;
        }
    },
    resizeCol(evt) {
      this.document.cols[this.handling_item.index].width =
        this.handling_item.width + evt.offset.x;
      if (evt.isFinal) {
        let element = {
          uuid: this.handling_item.uuid,
          name: this.handling_item.name,
          type: this.handling_item.type,
          width: this.document.cols[this.handling_item.index].width,
        };
        this.callUpdateCol(element);
      }
    },
    editFocus(cell) {
      // 여기서 focusrequest 보내야 함.
      this.focus.isFocusing = true;
      this.focus.rowId = cell.rowId;
      this.focus.colId = cell.colIs;
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
            let ith_row = [];
            res_doc.cols.forEach((col) => {
              let colId = col.uuid;
              let colWidth = col.width;
              ith_row.push({
                rowId: rowId,
                colId: colId,
                width: colWidth,
              });
            });
            this.rowData.push(ith_row);
            this.rowActive.push(false);
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
          response, this.refreshReq();
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
          response, this.refreshReq();
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
          response, this.refreshReq();
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
          response, this.refreshReq();
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
          response, this.refreshReq();
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
          response, this.refreshReq();
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
          response, this.refreshReq();
        },
        (error) => {
          console.warn(error);
        }
      );
    },
    callUpdateCell(rowId, colId, content) {
      console.log(rowId);
      console.log(colId);
      console.log(content);
      updateCell(
        {
          pathVariable: {
            projectId: this.projectId,
          },
          requestBody: {
            rowId: rowId,
            colId: colId,
            content: content,
          },
        },
        (response) => {
          response, this.refreshReq();
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
}
.addBtn {
  border-radius: 7px;
}

.col-width-handle {
  position: absolute;
  right: 0px;
  top: 0;
  bottom: 0;
  width: 15px;
  cursor: ew-resize;
}
.handling {
  position: absolute;
  top: 8px;
  bottom: 8px;
  right: 11px;
  width: 4px;
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

.test {
  border: 1px solid green;
}
</style>
