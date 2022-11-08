<template>
  <q-layout
    view="hHh Lpr lff"
    container
    style="height: 500px"
    class="shadow-2 rounded-borders"
  >
    <div>My Name : {{ userName }}</div>
    <div>My Focus : {{ focus }}</div>
    <div>Cooperators : {{ users }}</div>
    <div>
      <input type="text-area" />
      <br />
      projectId: {{ document.projectId }}

      <!-- Columns -->
      <q-card class="q-pa-xs row">
        <!-- blank -->

        <q-card class="q-pa-sm q-ma-xs cell cell-no" />
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
          <q-popup-proxy v-model="addColPopup">
            <q-banner>
              <q-input
                filled
                dense
                v-model="addColName"
                @keyup.enter="callAddCol(addColName, 'text')"
              />
              <q-btn
                color="primary"
                label="Add Col"
                @click="callAddCol(addColName, 'text')"
              />
            </q-banner>
          </q-popup-proxy>
        </q-card>
        <q-dialog v-model="addColWarningDialog" position="top">
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
            <q-card class="q-pa-sm q-ma-xs cell">
              <q-icon
                class="addBtn shadow-1 cursor-pointer"
                name="add"
                @click="openSideDrawer(document.rows[index])"
              />
            </q-card>
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
            <template v-for="(cell, col_idx) in element" :key="col_idx">
              <div
                class="q-px-sm q-ma-xs cell"
                :style="{ width: cell.width + 'px' }"
              >
                <q-input
                  dense
                  borderless=""
                  :style="{
                    width: cell.width - 15 + 'px',
                  }"
                  type="text"
                  v-model="document.data[cell.rowId][cell.colId]"
                  :class="index + '_' + col_idx"
                  @keypress.enter="pressEnter($event, index, col_idx, cell)"
                  @focus="setFocus(index, col_idx)"
                  @blur="
                    clearFocus(),
                      callUpdateCell(
                        cell.rowId,
                        cell.colId,
                        document.data[cell.rowId][cell.colId]
                      )
                  "
                />
                {{ cell.focuses }}
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
      <!-- -->
      <q-card class="q-pa-xs row">
        <q-card class="q-pa-sm q-ma-xs">
          <q-icon
            class="addBtn shadow-1 cursor-pointer"
            name="add"
            @click="callAddRow()"
          />
        </q-card>
      </q-card>
      <br /><br />

      <template v-if="drawer">
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
            <tr v-for="(col, colId) in document.cols" :key="colId">
              <td>
                <strong>{{ col.name }}</strong>
              </td>
              <td>
                <q-input
                  dense
                  type="text"
                  v-model="document.data[drawerRowId][col.uuid]"
                />
              </td>
            </tr>
          </q-markup-table>

          <div
            v-touch-pan.preserveCursor.prevent.mouse.horizontal="resizeDrawer"
            class="q-drawer__resizer"
          ></div> </q-drawer
      ></template>
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
    const addColWarningDialog = ref(false);
    const addColPopup = ref(false);
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
      focus: ref({
        isFocusing: false,
        row_idx: "",
        col_idx: "",
      }),

      drawer: ref(false),
      drawerWidth,
      drawerRowId,
      resizeDrawer(ev) {
        if (ev.isFirst) {
          initialDrawerWidth = drawerWidth.value;
        }
        drawerWidth.value = initialDrawerWidth - ev.offset.x;
      },
      addColWarningDialog,
      addColPopup,

      updateColName: ref(""),

      userName: ref(""),
      users: ref({}),
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

          // TODO: refresh 해도 내가 작업중인 content는 유지될 수 있도록 하는 코드 (test 필요!!)
          let editing_content = "";
          let row_id = "",
            col_id = "";
          if (this.focus.isFocusing) {
            let cell_info =
              this.rowData[this.focus.row_idx][this.focus.col_idx];
            row_id = cell_info.rowId;
            col_id = cell_info.colId;
            editing_content = this.document.data[row_id][col_id];
          }
          this.callGetDocs();
          if (this.focus.isFocusing)
            this.document.data[row_id][col_id] = editing_content;
        }
      );
      this.stompClient.subscribe("/sub/" + this.projectId + "/focus", (msg) => {
        let res = JSON.parse(msg.body);
        let res_content = JSON.parse(res.content);

        if (res_content == 0) {
          // focusReq(0)를 받았으니 내 focus 정보를 focusReq(1)로 돌려준다.
          this.focusReq(1);
        } else if (res_content == 2) {
          // focusReq(2)를 받았으니 송신자의 정보를 지운다.
          // rowData에 있던 focus를 지워준다.
          if (this.users[res.userName] && this.users[res.userName].isFocusing) {
            let index = this.rowData[this.users[res.userName].row_idx][
              this.users[res.userName].col_idx
            ].focuses.indexOf(res.userName);
            this.rowData[this.users[res.userName].row_idx][
              this.users[res.userName].col_idx
            ].focuses.splice(index, 1);
            // this.users의 정보를 지운다.
            delete this.users[res.userName];
          }
        } else {
          // focusReq(1)를 받았으니 내 users 변수에 신규/변경내용을 저장해준다.

          // 내가 보냈다면 변경하지 않아도 됨.
          if (res.userName == this.userName) return;

          // 1. 요청한 사람이...
          // 신규가 아닌, 있던 유저이면서, isFocusing = true 라면 : focus를 지워주고,
          if (this.users[res.userName] && this.users[res.userName].isFocusing) {
            let index = this.rowData[this.users[res.userName].row_idx][
              this.users[res.userName].col_idx
            ].focuses.indexOf(res.userName);
            this.rowData[this.users[res.userName].row_idx][
              this.users[res.userName].col_idx
            ].focuses.splice(index, 1);
          }

          // 해당 user가 없으면 추가, 있으면 교체
          this.users[res.userName] = {
            isFocusing: res_content.isFocusing,
            row_idx: res_content.row_idx,
            col_idx: res_content.col_idx,
          };

          // 2. 요청한 사람이
          // isFocusing = true 라면 : 새 focus를 push 해줌.
          if (res_content.isFocusing) {
            this.rowData[this.users[res.userName].row_idx][
              this.users[res.userName].col_idx
            ].focuses.push(res.userName);
          }
        }
      });

      this.focusReq(0);
      window.addEventListener("beforeunload", this.unLoadEvent);
    });
  },
  beforeUnmount() {
    window.removeEventListener("beforeunload", this.unLoadEvent);
  },
  methods: {
    unLoadEvent() {
      this.focusReq(2);
      this.stompClient.disconnect();
    },
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
    focusHighlight(position) {
      position;
    },
    focusNextLine(row_idx, col_idx) {
      if (row_idx + 1 == this.document.rows.length) return;
      let nextLine = document.getElementsByClassName(
        row_idx + 1 + "_" + col_idx
      );
      nextLine[0].focus();
    },
    pressEnter(evt, row_idx, col_idx, cell) {
      if (evt.charCode === 13) {
        if (!evt.shiftKey) {
          // just enter
          if (row_idx + 1 != this.document.rows.length) {
            // can blur
            let nextLine = document.getElementsByClassName(
              row_idx + 1 + "_" + col_idx
            );
            nextLine[0].focus();
            return;
          }
        } else {
          // shift enter
          if (row_idx != 0) {
            // can blur
            let nextLine = document.getElementsByClassName(
              row_idx - 1 + "_" + col_idx
            );
            nextLine[0].focus();
            return;
          }
        }
        // cannot blur, force update
        this.callUpdateCell(
          cell.rowId,
          cell.colId,
          this.document.data[cell.rowId][cell.colId]
        );
      }
    },
    refreshReq() {
      const req = { userName: this.userName, content: null };
      this.stompClient.send(
        "/pub/" + this.projectId + "/refresh",
        {},
        JSON.stringify(req)
      );
    },
    setFocus(row_idx, col_idx) {
      this.focus = {
        isFocusing: true,
        row_idx: row_idx,
        col_idx: col_idx,
      };
      this.focusReq(1);
    },
    clearFocus() {
      let active_tag = document.activeElement.tagName;
      if (active_tag == "INPUT") return;
      else {
        this.focus = {
          isFocusing: false,
          row_idx: "",
          col_idx: "",
        };
        this.focusReq(1);
      }
    },
    focusReq(type) {
      let req = {
        userName: this.userName,
        content: null,
      };

      if (type == 0) {
        // 0. 수신한 모두가 송신자에게 focus 정보를 달라는 request
        req.content = 0;
        this.stompClient.send(
          "/pub/" + this.projectId + "/focus",
          {},
          JSON.stringify(req)
        );
      } else if (type == 1) {
        // 1. 내 focus를 전송함.
        req.content = JSON.stringify({
          isFocusing: this.focus.isFocusing,
          row_idx: this.focus.row_idx,
          col_idx: this.focus.col_idx,
        });
        this.stompClient.send(
          "/pub/" + this.projectId + "/focus",
          {},
          JSON.stringify(req)
        );
      } else {
        // 2. 수신하면 송신자를 지워달라는 request
        req.content = 2;
        this.stompClient.send(
          "/pub/" + this.projectId + "/focus",
          {},
          JSON.stringify(req)
        );
      }
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
              if (col.category !== "PAYLOAD") {
                let colId = col.uuid;
                let colWidth = col.width;
                ith_row.push({
                  rowId: rowId,
                  colId: colId,
                  width: colWidth,
                  focuses: [],
                });
              }
            });
            this.rowData.push(ith_row);
            this.rowActive.push(false);
          });

          // this.users를 순회하며 공동 작업중인 user들의 focus를 채워줌
          for (let userName in this.users) {
            let info = this.users[userName];
            if (info.isFocusing) {
              this.rowData[info.row_idx][info.col_idx].focuses.push(userName);
            }
          }
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
      this.addColPopup = false;
      this.addColName = "";
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
      if (this.drawerRowId == rowId) {
        this.drawer = false;
        this.drawerRowId = null;
      }
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
      if (content == undefined) content = "";
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

    colWarning() {
      this.colWarningDialog = true;
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
</style>
