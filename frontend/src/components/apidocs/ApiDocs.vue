<template>
  <div>
    <q-page-container>
      <q-page class="row">
        <div class="col q-ma-xs">
          <div class="column full-height">
            <q-scroll-area
              class="col q-pa-sm"
              visible
              :thumb-style="thumbStyle"
            >
              <div style="overflow: auto">
                <div>My Name : {{ userName }}</div>
                <div>My Focus : {{ focus }}</div>
                <div>Cooperators : {{ users }}</div>
                projectId: {{ document.projectId }}

                <!-- Columns -->
                <div class="q-pa-xs row no-wrap">
                  <!-- blank -->

                  <div class="q-pa-sm q-ma-xs cell-no" />
                  <div class="q-pa-sm q-ma-xs cell-no" />
                  <!-- cols -->
                  <draggable
                    class="row no-wrap"
                    v-bind="dragOptions"
                    v-model="document.cols"
                    @start="dragCol = true"
                    @end="dragCol = false"
                    @choose="onChoose"
                    @unchoose="onUnchoose"
                    item-key="id_col"
                    @change="onColChange"
                    handle=".handle-col"
                  >
                    <template #item="{ element }">
                      <div
                        class="row q-pa-xs"
                        v-if="
                          element.category === 'REQUIRED' ||
                          element.category === 'ADDED'
                        "
                      >
                        <div class="drag-item">
                          <div
                            v-if="element.category === 'REQUIRED'"
                            v-on:click.right.prevent
                            class="q-pa-sm cell row handle-col"
                            :style="{ width: element.width + 'px' }"
                          >
                            {{ element.name }}
                            <q-icon
                              :name="biAsterisk"
                              style="font-size: 0.5em"
                            />
                          </div>
                          <div
                            v-else
                            class="q-pa-sm cell row handle-col"
                            :style="{ width: element.width + 'px' }"
                          >
                            {{ element.name }}
                            <q-popup-proxy
                              @before-show="putColName(element)"
                              @show="moveCursor('putColName')"
                            >
                              <q-banner style="max-width: 250px">
                                <div class="row items-baseline justify-between">
                                  <q-input
                                    filled
                                    dense
                                    v-model="updateColName"
                                    :rules="[(val) => !!val]"
                                    @keydown.enter.prevent="
                                      callUpdateColName(element)
                                    "
                                    class="col-10"
                                    ref="putColNameCursor"
                                  />
                                  <q-icon
                                    class="cursor-pointer"
                                    v-close-popup
                                    size="xs"
                                    :name="mdiArrowLeftBottomBold"
                                    @click="callUpdateColName(element)"
                                  />
                                </div>
                                <div class="row">
                                  <q-btn
                                    class="col-12"
                                    flat
                                    v-close-popup
                                    label="열 삭제"
                                    :icon="biTrash3"
                                    size="sm"
                                    @click="callDeleteCol(element.uuid)"
                                  />
                                </div>
                              </q-banner>
                            </q-popup-proxy>
                          </div>
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
                              <q-icon
                                v-show="element.active"
                                :name="fasGripLinesVertical"
                              />
                            </div>
                          </div>
                        </div>
                      </div>
                    </template>
                  </draggable>

                  <!-- "+" btn -->
                  <div class="q-pa-sm q-my-xs cursor-pointer">
                    <q-icon :name="biPlusCircle" />
                    <q-tooltip anchor="bottom middle" self="bottom middle">
                      열 추가
                    </q-tooltip>
                    <q-popup-proxy
                      v-model="addColPopup"
                      @show="moveCursor('addCol')"
                      @before-hide="resetAddColName"
                    >
                      <q-banner>
                        <div class="row items-baseline justify-between">
                          <q-input
                            filled
                            dense
                            v-model="addColName"
                            :rules="[(val) => !!val]"
                            @keyup.enter="callAddCol(addColName, 'text')"
                            class="col-10"
                            ref="addColCursor"
                          />
                          <q-icon
                            class="cursor-pointer"
                            v-close-popup
                            size="xs"
                            :name="mdiArrowLeftBottomBold"
                            @click="callAddCol(addColName, 'text')"
                          />
                        </div>
                      </q-banner>
                    </q-popup-proxy>
                  </div>
                  <q-dialog v-model="warningDialog" position="top">
                    <warning-dialog :msg="msg" />
                  </q-dialog>
                </div>
                <!-- -->
                <!-- Rows -->
                <draggable
                  v-model="rowData"
                  v-bind="dragOptions"
                  @start="dragRow = true"
                  @end="dragRow = false"
                  item-key="id_row"
                  @change="onRowChange"
                  handle=".handle-row"
                >
                  <template #item="{ element, index }">
                    <div class="q-pa-xs row no-wrap">
                      <div
                        class="q-pa-sm q-ma-xs cursor-pointer"
                        @click="openSideDrawer(document.rows[index])"
                      >
                        <q-icon :name="biLayoutSidebarInsetReverse" />
                        <q-tooltip anchor="bottom middle" self="bottom middle">
                          열기
                        </q-tooltip>
                      </div>
                      <div
                        @mouseover="rowActive[index] = true"
                        @mouseleave="rowActive[index] = false"
                        class="q-pa-sm q-ma-xs text-right cell-no handle-row drag-item"
                      >
                        <template v-if="!rowActive[index]">
                          {{ index + 1 }}
                        </template>
                        <template v-else>
                          <q-icon :name="fasGripVertical" />
                        </template>
                        <!-- <q-icon name="drag_indicator" class="handle-row" size="20px" /> -->
                      </div>
                      <template
                        v-for="(cell, col_idx) in element"
                        :key="col_idx"
                      >
                        <div
                          class="q-px-sm q-ma-xs cell"
                          :style="{ width: cell.width + 'px' }"
                          :class="{ active: cell.focuses.length != 0 }"
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
                            @focus="setFocus(cell.rowId, cell.colId)"
                            @keypress.enter="
                              pressEnter($event, index, col_idx, cell)
                            "
                            @blur="
                              clearFocus(),
                                callUpdateCell(
                                  cell.rowId,
                                  cell.colId,
                                  document.data[cell.rowId][cell.colId]
                                )
                            "
                            class="hoverable"
                          />
                          <div class="hide">
                            <template v-if="cell.focuses.length == 1">
                              {{ cell.focuses[0] }}
                            </template>
                            <template v-else-if="cell.focuses.length > 1">
                              {{ cell.focuses[0] }}, {{ cell.focuses[1] }}...
                            </template>
                          </div>
                        </div>
                      </template>

                      <q-popup-proxy context-menu>
                        <q-banner>
                          <q-btn
                            flat
                            label="행 삭제"
                            :icon="biTrash3"
                            size="sm"
                            @click="callDeleteRow(document.rows[index])"
                          />
                        </q-banner>
                      </q-popup-proxy>
                    </div>
                  </template>
                </draggable>
                <!-- -->
                <div class="q-pa-xs row">
                  <div
                    class="q-pa-sm q-ma-xs cursor-pointer"
                    @click="callAddRow()"
                  >
                    <q-icon :name="biPlusCircle" />
                    <q-tooltip anchor="bottom middle" self="bottom middle">
                      행 추가
                    </q-tooltip>
                  </div>
                </div>
                <br /><br />
              </div>
            </q-scroll-area>
          </div></div
      ></q-page>
    </q-page-container>
    <template v-if="initDrawer">
      <q-drawer
        v-model="drawer"
        side="right"
        :width="drawerWidth"
        :breakpoint="0"
        bordered
        class="bg-grey-3"
      >
        <div class="column full-height">
          <q-scroll-area class="col q-pa-sm" visible :thumb-style="thumbStyle">
            <q-btn
              flat
              @click="drawer = !drawer"
              round
              dense
              :icon="fasAnglesRight"
              size="sm"
            >
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
                    @keypress.enter="
                      callUpdateCell(
                        drawerRowId,
                        col.uuid,
                        document.data[drawerRowId][col.uuid]
                      )
                    "
                    @blur="
                      callUpdateCell(
                        drawerRowId,
                        col.uuid,
                        document.data[drawerRowId][col.uuid]
                      )
                    "
                    v-model="document.data[drawerRowId][col.uuid]"
                  />
                </td>
              </tr>
            </q-markup-table>
          </q-scroll-area>
        </div>
        <div
          v-touch-pan.preserveCursor.prevent.mouse.horizontal="resizeDrawer"
          class="q-drawer__resizer"
        ></div> </q-drawer
    ></template>
  </div>
</template>

<script>
import { ref } from "vue";
import { BASEURL } from "@/api/index.js";

import SockJS from "sockjs-client";
import Stomp from "stompjs";
import draggable from "vuedraggable";
// import WarningDialog from "./WarningDialog.vue";

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

import {
  biLayoutSidebarInsetReverse,
  biTrash3,
  biChevronDoubleRight,
  biArrowReturnLeft,
  biGripVertical,
  biPlusCircle,
  biAsterisk,
} from "@quasar/extras/bootstrap-icons";
import {
  fasGripLinesVertical,
  fasAnglesRight,
  fasGripVertical,
} from "@quasar/extras/fontawesome-v6";
import {
  mdiDragVerticalVariant,
  mdiArrowLeftBottomBold,
} from "@quasar/extras/mdi-v6";

export default {
  components: {
    draggable,
    // WarningDialog,
  },
  setup() {
    const addColPopup = ref(false);
    let initialDrawerWidth;
    const drawerWidth = ref(300);
    const drawerRowId = ref(null);

    const dragOptions = ref({
      animation: 200,
      disabled: false,
      ghostClass: "ghost",
    });

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
        uuid: null,
        name: null,
        type: null,
        width: null,
      }),

      rowData: ref([]),
      rowActive: ref([]),
      focus: ref({
        isFocusing: false,
        rowId: "",
        colId: "",
      }),
      initDrawer: ref(false),
      drawer: ref(false),
      drawerWidth,
      drawerRowId,
      dragOptions,
      resizeDrawer(ev) {
        if (ev.isFirst) {
          initialDrawerWidth = drawerWidth.value;
        }
        drawerWidth.value = initialDrawerWidth - ev.offset.x;
      },

      warningDialog: ref(false),
      msg: ref(""),

      addColPopup,
      updateColName: ref(""),
      userName: ref(""),
      users: ref({}),

      editing_content: ref(""),
      thumbStyle: {
        right: "3px",
        bottom: "3px",
        borderRadius: "5px",
        background: "gray",
        width: "6px",
        height: "6px",
        opacity: 0.5,
      },
      // icon
      biLayoutSidebarInsetReverse,
      biTrash3,
      biChevronDoubleRight,
      biArrowReturnLeft,
      biGripVertical,
      biPlusCircle,
      biAsterisk,
      fasGripLinesVertical,
      fasAnglesRight,
      fasGripVertical,
      mdiDragVerticalVariant,
      mdiArrowLeftBottomBold,
    };
  },
  mounted() {
    this.callGetDocs();

    this.userName = "user_" + Math.round(Math.random() * 100); // 나중에 유저를 token에서 가져오자.

    // WEBSOCKET CONNECTION
    this.socket = new SockJS(BASEURL + "/ws");
    this.stompClient = Stomp.over(this.socket);
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe(
        "/sub/" + this.projectId + "/refresh",
        async (msg) => {
          msg;

          // refresh해도 작업중인 content를 유지하는 코드
          let isEditing = false;
          // 1. focus.isFocusing = true일 경우
          if (this.focus.isFocusing) {
            // 1-1. 그리고 focus가 가리키는 셀이 삭제되지 않았을 경우에만
            // editing_content에 내용이 저장되고, isEditing = true 가 되면서 2로 갈 수 있음.
            if (
              this.getRowIdxFromRowId(this.focus.rowId) > -1 &&
              this.getColIdxFromColId(this.focus.colId) > -1
            ) {
              // 1-1. 그리고 focus가 가리키는 셀이 삭제되지 않았을 경우에만
              // editing_content에 내용을 저장해둔다.
              this.editing_content =
                this.document.data[this.focus.rowId][this.focus.colId];
              isEditing = true;
            } else {
              // 1-2. 하지만 focus가 가리키는 셀이 삭제됐을 경우,
              // 작성 중인 셀이 삭제되었다는 warning dialog를 띄우고 끝낸다.
              this.callGetDocs();
              this.deleteWarning();
              return;
            }
          }
          await this.callGetDocs();
          let rowIdIdx = this.getRowIdxFromRowId(this.focus.rowId);
          let colIdIdx = this.getColIdxFromColId(this.focus.colId);
          if (isEditing) {
            // 2. isEditing이 true일 경우
            if (rowIdIdx > -1 && colIdIdx > -1) {
              // 2-1. refresh를 했는데도 focus가 가리키는 셀이 남아있다면,
              // 알맞은 위치를 찾아서 editing_content 덮어쓰기 수행.
              this.document.data[this.focus.rowId][this.focus.colId] =
                this.editing_content;
              document
                .getElementsByClassName(rowIdIdx + "_" + colIdIdx)[0]
                .focus();
            } else {
              // 2-2. refresh를 했더니 focus가 가리키던 셀이 사라졌다면,
              // 작성 중인 셀이 삭제되었다는 warning dialog를 띄우고 끝낸다.
              this.deleteWarning();
            }
          }
        }
      );
      this.stompClient.subscribe("/sub/" + this.projectId + "/focus", (msg) => {
        let res = JSON.parse(msg.body);
        let res_content = JSON.parse(res.content);

        let rowIdIdx = -2,
          colIdIdx = -2;
        if (this.users[res.userName]) {
          rowIdIdx = this.getRowIdxFromRowId(this.users[res.userName].rowId);
          colIdIdx = this.getColIdxFromColId(this.users[res.userName].colId);
        }
        // -2라면 애초에 res.userName이 없었던 것
        // -1라면 res.userName은 있지만 request를 받은 시점에 rows/cols에 focus하던 cell이 없어진 것

        if (res_content == 0) {
          // 0. focusReq(0)를 받았으니 내 focus 정보를 focusReq(1)로 돌려준다.
          this.focusReq(1);
        } else if (res_content == 2) {
          // 2. focusReq(2)를 받았으니 송신자의 정보를 지운다.
          // - users에 userName이 있다면 지운다.
          if (this.users[res.userName]) {
            // 2-1. userName이 isFocusing이었을 경우 rowData의 focuses에서 userName을 지운다.
            if (
              this.users[res.userName].isFocusing &&
              rowIdIdx > -1 &&
              colIdIdx > -1
            ) {
              let index = this.rowData[rowIdIdx][colIdIdx].focuses.indexOf(
                res.userName
              );
              this.rowData[rowIdIdx][colIdIdx].focuses.splice(index, 1);
            }
            // 2-2. users에서 userName을 지운다.
            delete this.users[res.userName];
          }
        } else {
          // 1. focusReq(1)를 받았으니 내 users 변수에 신규/변경내용을 저장해준다.

          // 1-0. 내가 보냈다면 변경하지 않아도 됨.
          if (res.userName == this.userName) return;

          // 1-1. 요청한 사람이...
          // 신규가 아닌, 있던 유저이면서, isFocusing = true 라면 : focus를 지워주고,
          if (
            this.users[res.userName] &&
            this.users[res.userName].isFocusing &&
            rowIdIdx > -1 &&
            colIdIdx > -1
          ) {
            let index = this.rowData[rowIdIdx][colIdIdx].focuses.indexOf(
              res.userName
            );
            this.rowData[rowIdIdx][colIdIdx].focuses.splice(index, 1);
          }

          // (common) 해당 user가 없으면 추가, 있으면 교체
          this.users[res.userName] = {
            isFocusing: res_content.isFocusing,
            rowId: res_content.rowId,
            colId: res_content.colId,
          };

          // 1-2. 요청한 사람이
          // isFocusing = true 라면 : 새 focus를 push 해줌.
          if (res_content.isFocusing) {
            rowIdIdx = this.getRowIdxFromRowId(res_content.rowId);
            colIdIdx = this.getColIdxFromColId(res_content.colId);
            if (rowIdIdx > -1 && colIdIdx > -1) {
              this.rowData[rowIdIdx][colIdIdx].focuses.push(res.userName);
            }
          }
        }
      });

      this.focusReq(0);
      window.addEventListener("beforeunload", this.unLoadEvent);

      this.callGetDocs();
    });
  },
  beforeUnmount() {
    window.removeEventListener("beforeunload", this.unLoadEvent);
  },
  methods: {
    // getColIdxFromColId와 getRowIdxFromRowId는 없을 시 -1을 반환함.
    // 호출할 때마다 -1에 대한 예외처리를 해줘야 함.
    getColIdxFromColId(colId) {
      let colIdIdx = -1;
      let doc_cols = this.document.cols;
      for (let i = 0; i < doc_cols.length; i++)
        if (doc_cols[i].uuid == colId) {
          colIdIdx = i;
          break;
        }
      return colIdIdx;
    },
    getRowIdxFromRowId(rowId) {
      return this.document.rows.indexOf(rowId);
    },
    unLoadEvent() {
      this.focusReq(2);
      this.stompClient.disconnect();
    },
    onChoose() {
      const html = document.getElementsByTagName("html").item(0);
      html.classList.toggle("dragging-item", true);
    },
    onUnchoose() {
      const html = document.getElementsByTagName("html").item(0);
      html.classList.toggle("dragging-item", false);
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
      let colIdIdx = this.getColIdxFromColId(uuid);
      if (colIdIdx > -1)
        this.handling_item = { ...this.document.cols[colIdIdx] };
    },
    resizeCol(evt) {
      let colIdIdx = this.getColIdxFromColId(this.handling_item.uuid);
      if (colIdIdx < 0) {
        // resize 중이던 열이 삭제됐다는 warning dialog 표시
        this.deleteWarning();
        return;
      }
      this.document.cols[colIdIdx].width =
        this.handling_item.width + evt.offset.x;
      if (evt.isFinal) {
        let element = {
          uuid: this.handling_item.uuid,
          name: this.handling_item.name,
          type: this.handling_item.type,
          width: this.document.cols[colIdIdx].width,
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
    setFocus(rowId, colId) {
      this.focus = {
        isFocusing: true,
        rowId: rowId,
        colId: colId,
      };
      this.focusReq(1);
    },
    clearFocus() {
      let active_tag = document.activeElement.tagName;
      if (active_tag == "INPUT") return;
      else {
        this.focus = {
          isFocusing: false,
          rowId: "",
          colId: "",
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
          rowId: this.focus.rowId,
          colId: this.focus.colId,
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
    async callGetDocs() {
      await getDocs(
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
              let rowIdIdx = this.getRowIdxFromRowId(info.rowId);
              let colIdIdx = this.getColIdxFromColId(info.colId);
              // -1일 경우 가리키던 row/col가 사라졌다는 뜻

              if (rowIdIdx == -1 || colIdIdx == -1) {
                this.users[userName].isFocusing = false;
              } else {
                this.rowData[rowIdIdx][colIdIdx].focuses.push(userName);
              }
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
        this.colWarning();
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
      this.initDrawer = true;
      this.drawer = true;
      this.drawerRowId = rowId;
    },

    colWarning() {
      this.msg = "속성 이름을 입력해주세요!";
      this.warningDialog = true;
    },
    deleteWarning() {
      this.msg = "셀이 삭제되었습니다!";
      this.warningDialog = true;
      this.focus.isFocusing = false;
    },
    resetAddColName() {
      this.addColName = "";
    },
    putColName(element) {
      this.updateColName = element.name;
    },
    moveCursor(name) {
      this.$refs[name + "Cursor"].focus();
    },
    callUpdateColName(element) {
      if (this.updateColName.length == 0) {
        this.colWarning();
        return;
      }
      element.name = this.updateColName;
      this.callUpdateCol(element);
    },
  },
};
</script>

<style scoped>
.cell {
  position: relative;
  background: whitesmoke;
}
.cell-no {
  min-width: 30px;
  max-width: 30px;
}
.addBtn {
  border-radius: 7px;
}
.active {
  outline: 2px solid skyblue;
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

.hide {
  display: none;
  position: absolute;
  top: -10px;
  right: 0px;
  background: skyblue;
  padding: 0px 2px;
  border-radius: 5px !important;
}

.hoverable:hover + .hide {
  display: block;
}
.colWidth {
  width: 190px;
}
.drag-item {
  cursor: grab;
}
.dragging-item {
  cursor: grabbing !important;
}
</style>
