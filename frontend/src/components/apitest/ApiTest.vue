<template>
  <div id="All">
    <div style="height: 8%"></div>
    <div id="Top">
      <div class="q-px-md text-bold text-h6" style="text-align: center">
        {{ projectTitle }}의 ApiTest
      </div>
    </div>

    <div id="Maincontainer">
      <vue-resizable
        width="20%"
        maxWidth="400"
        active="['r', 0, 0, 0, 0, 0, 0, 0]"
      >
        <div id="Left">
          <hr style="border-bottom: 0px; margin-top: 0px" />
          <q-item style="font-weight: bold" dense>
            <q-item-section avatar> Api목록 </q-item-section>
          </q-item>
          <hr style="border-bottom: 0px" />
          <q-scroll-area
            id="apiListAll"
            :thumb-style="thumbstyle"
            style="height: 80.5vh"
          >
            <div v-for="(item, index) in apiList" v-bind:key="index">
              <div v-if="index == 0"></div>
              <div v-else>
                <q-item
                  clickable
                  @click="apiListDetail(index)"
                  :active="index == nowIndex"
                  v-ripple
                  active-class="bg-grey-4"
                >
                  <q-item-section
                    avatar
                    :style="{
                      color: 'var(--' + item.type + ')',
                      width: '6vw ',
                    }"
                  >
                    {{ item.type }}
                  </q-item-section>
                  <q-item-section>
                    {{ item.name }}
                  </q-item-section>
                </q-item>
              </div>
            </div>
          </q-scroll-area>
        </div>
      </vue-resizable>
      <div id="Main">
        <hr style="border-bottom: 0; border-left: 0; margin-top: 0" />
        <div id="MainTop">
          <div id="apiName">
            <div
              style="text-align: left; font-size: 1.2rem"
              class="q-pa-xs"
              v-if="apiName == ''"
            >
              Api이름
            </div>
            <div style="text-align: left; font-size: 1.2rem" class="q-pa-xs">
              {{ apiName }}
            </div>
          </div>
          <q-btn color="primary" id="savebtn" @click="saveLocalStorage"
            >Save</q-btn
          >
        </div>
        <hr style="border-bottom: 0; border-left: 0; margin-top: 0.3vh" />
        <div id="TypeURL">
          <select id="RequestType" v-model="methodType">
            <option value="GET">GET</option>
            <option value="POST">POST</option>
            <option value="PUT">PUT</option>
            <option value="PATCH">PATCH</option>
            <option value="DELETE">DELETE</option>
          </select>
          <div id="empty"></div>
          <input id="apiURL" v-model="apiURL" readonly />
          <div id="empty"></div>
          <q-btn color="primary" id="testbtn" @click="Test">Send</q-btn>
        </div>

        <div id="RequestBox">
          <q-tabs
            id="RequestOptions"
            :breakpoint="0"
            dense
            no-caps
            right-icon=" "
            left-icon=" "
            align="left"
          >
            <q-tab
              id="ReqeustOptionsDetail"
              @click="OptionSelect('RequestHeader')"
              >Header</q-tab
            >
            <q-tab
              id="ReqeustOptionsDetail"
              @click="OptionSelect('PathVariable')"
              >PathVariable</q-tab
            >
            <q-tab id="ReqeustOptionsDetail" @click="OptionSelect('Params')"
              >QueryParams</q-tab
            >
            <q-tab
              id="ReqeustOptionsDetail"
              @click="OptionSelect('RequestBody')"
              >RequestBody</q-tab
            >
          </q-tabs>

          <div id="RequestParent">
            <textarea
              id="HeaderArea"
              v-show="typeSelect == 'RequestHeader'"
              v-model="Header"
            />
            <div id="KeyValueInput" v-show="typeSelect == 'PathVariable'">
              <q-markup-table dense id="KeyValueTable">
                <thead>
                  <tr>
                    <th class="text-left">key</th>
                    <th class="text-left">PathVariable</th>
                    <th></th>
                  </tr>
                </thead>
                <tbody
                  v-for="(item, index) in PathVariables"
                  v-bind:key="index"
                >
                  <td>
                    <q-input
                      dense
                      outlined
                      id="PathVariableTableInput"
                      v-model="item.key"
                    ></q-input>
                  </td>
                  <td>
                    <q-input
                      dense
                      outlined
                      id="PathVariableTableInput"
                      v-model="item.value"
                    ></q-input>
                  </td>
                  <td>
                    <q-icon
                      class="q-pa-sm q-ma-xs cursor-pointer"
                      @click="deleteRow('PathVariable', index)"
                      :name="biDashCircle"
                    />
                  </td>
                </tbody>
                <tfoot>
                  <td>
                    <q-icon
                      class="q-pa-sm q-ma-xs cursor-pointer"
                      @click="addRow('PathVariable')"
                      :name="biPlusCircle"
                    />
                  </td>
                </tfoot>
              </q-markup-table>
            </div>

            <div id="KeyValueInput" v-show="typeSelect == 'Params'">
              <q-markup-table dense id="KeyValueTable">
                <thead>
                  <tr>
                    <th class="text-left">key</th>
                    <th class="text-left">QueryParams</th>
                    <th></th>
                  </tr>
                </thead>
                <tbody v-for="(item, index) in QueryParams" v-bind:key="index">
                  <td>
                    <q-input
                      dense
                      outlined
                      id="QueryParamsTableInput"
                      v-model="item.key"
                    ></q-input>
                  </td>
                  <td>
                    <q-input
                      dense
                      outlined
                      id="QueryParamsTableInput"
                      v-model="item.value"
                    ></q-input>
                  </td>
                  <td>
                    <q-icon
                      class="q-pa-sm q-ma-xs cursor-pointer"
                      @click="deleteRow('QueryParams', index)"
                      :name="biDashCircle"
                    />
                  </td>
                </tbody>
                <tfoot>
                  <td>
                    <q-icon
                      class="q-pa-sm q-ma-xs cursor-pointer"
                      @click="addRow('QueryParams')"
                      :name="biPlusCircle"
                    />
                  </td>
                </tfoot>
              </q-markup-table>
            </div>

            <textarea
              id="RequestBodyInput"
              v-show="typeSelect == 'RequestBody'"
              v-model="RequestBody"
            />
          </div>
        </div>
        <hr
          style="
            border-left: 0;
            border-bottom: 0px;
            margin-top: 0px;
            margin-bottom: 0px;
          "
        />
        <div id="ResponseBox">
          <div id="ResponseBoxTop" class="q-pa-xs">
            <div style="flex: 1">Response</div>
            <div style="flex: 10"></div>
            <div style="flex: 1">Status:</div>
            <div
              :style="{
                color: 'var(--Log' + parseInt(statusCode / 100) + ')',
              }"
              style="flex: 1"
            >
              {{ statusCode }}
            </div>
          </div>
          <q-tabs
            id="ResponseOptions"
            :breakpoint="0"
            dense
            no-caps
            right-icon=" "
            left-icon=" "
            align="left"
          >
            <q-tab
              id="ResponseOptionsDetail"
              @click="ResponseOptionSelect('ResponseBody')"
              >ResponseBody</q-tab
            >
            <q-tab
              id="ResponseOptionsDetail"
              @click="ResponseOptionSelect('Cookie')"
              >Cookie</q-tab
            >
            <q-tab
              id="ResponseOptionsDetail"
              @click="ResponseOptionSelect('Header')"
              >Header</q-tab
            >
          </q-tabs>
          <div id="ResponseParent">
            <textarea
              readonly
              v-show="ResponseTypeSelect == 'ResponseBody'"
              id="ResponseContent"
              v-model="res"
            ></textarea>
            <textarea
              readonly
              v-show="ResponseTypeSelect == 'Cookie'"
              id="ResponseContent"
              v-model="responseCookie"
            ></textarea>
            <textarea
              readonly
              v-show="ResponseTypeSelect == 'Header'"
              id="ResponseContent"
              v-model="responseHeader"
            ></textarea>
          </div>
        </div>
      </div>

      <details id="Right">
        <summary
          style="font-weight: bold; cursor: pointer"
          @click="logOpenClose"
        >
          <div v-if="logListOpened == false" style="width: 2.3vw">
            <hr style="border-bottom: 0px; border-left: 0; margin-top: 0" />
            <hr style="border-bottom: 0px; border-left: 0; margin-top: 40vh" />
            <div class="q-pa-xs">
              <q-icon
                :name="biArrowLeftCircle"
                style="text-align: left; margin-bottom: 0.5vh"
              />
              호출 로그
            </div>
            <hr style="margin-top: 15%; border-bottom: 0px; border-left: 0" />
          </div>
          <div v-if="logListOpened == true" style="width: 8vw">
            <hr
              style="
                border-bottom: 0px;
                border-left: 0;
                margin-top: 0px;
                width: 30vw;
              "
            />
            <div class="q-pa-xs">
              <q-icon
                :name="biArrowRightCircle"
                style="text-align: left; margin-bottom: 0.5vh"
              />
              호출 로그
            </div>
            <hr
              style="
                margin-top: 1.1vh;
                border-bottom: 0px;
                border-left: 0;
                width: 30vw;
              "
            />
          </div>
        </summary>
        <div style="width: 30vw">
          <q-scroll-area
            id="apiListAll"
            :thumb-style="thumbstyle"
            style="height: 80.5vh"
          >
            <div
              v-for="(log, index) in logList.slice().reverse()"
              v-bind:key="index"
            >
              <q-item clickable @click="logListDetail(index)" v-ripple dense>
                <q-item-section
                  :style="{
                    color: 'var(--Log' + parseInt(log.statusCode / 100) + ')',
                  }"
                  avatar
                >
                  {{ log.statusCode }}
                </q-item-section>
                <q-item-section>
                  {{ dateTimeFilter(log.logTime) }}
                </q-item-section>
                <q-item-section>
                  {{ log.userName }}
                </q-item-section>
              </q-item>
            </div>
          </q-scroll-area>
        </div>
      </details>
    </div>
    <q-dialog v-model="dialog">
      <q-card>
        <q-card-section><div class="text-h6">Log Detail</div></q-card-section>
        <q-card-section class="q-py-none">
          <q-markup-table wrap-cells="false">
            <tbody>
              <tr>
                <td>User</td>
                <td>{{ dialogContent.userName }}</td>
              </tr>
              <tr>
                <td>Time</td>
                <td>{{ dialogContent.logTime }}</td>
              </tr>
              <tr>
                <td>URL</td>
                <td>{{ dialogContent.url }}</td>
              </tr>
              <tr></tr>
              <tr>
                <td>StatusCode</td>
                <td>{{ dialogContent.statusCode }}</td>
              </tr>
              <tr>
                <td>RequestBody</td>
                <td>{{ dialogContent.requestBody }}</td>
              </tr>
              <tr>
                <td>ResponseBody</td>
                <td>{{ dialogContent.responseBody }}</td>
              </tr>
            </tbody>
          </q-markup-table>
        </q-card-section>
        <q-card-section align="right">
          <q-btn flat label="돌아가기" color="primary" v-close-popup />
        </q-card-section>
      </q-card>
    </q-dialog>
  </div>
</template>

<script>
import axios from "axios";
import VueResizable from "vue-resizable";
import { getDocs } from "@/api/apidocs.js";
import { getLogs, addLog } from "@/api/apitest.js";
import {
  biArrowRightCircle,
  biArrowLeftCircle,
  biDashCircle,
  biPlusCircle,
} from "@quasar/extras/bootstrap-icons";

import { mapMutations, mapState } from "vuex";
export default {
  components: { VueResizable },
  computed: {
    ...mapState("commonStore", ["projectId", "userName"]),
  },
  methods: {
    ...mapMutations("commonStore", [
      "SET_USERID",
      "SET_USERNAME",
      "SET_PROJECTID",
    ]),

    loadLogList() {
      getLogs(
        { pathVariable: { projectId: this.projectId, rowId: this.rowId } },
        (response) => {
          this.logList = response.data.logs;
        },
        (e) => {
          console.warn(e);
        }
      );
    },
    dateTimeFilter(time) {
      return time.substring(0, 16).replaceAll("T", " ").replaceAll("-", "/");
    },
    Test() {
      this.isBtnClicked = true;
      // Request의 값에 따라 다른 함수 실행(axios)
      // 다른 부분을 활성화한 채 테스트 버튼을 클릭했을 경우 PathVariable, Params, RequestBody의 값을 보고 실행
      // --> Method Type으로 변경
      this.apiSend(this.methodType);
    },
    apiSend(methodType) {
      //Header 설정. Json 오류날 경우 {}로 초기화
      var HeaderJson;
      try {
        HeaderJson = JSON.parse(this.Header);
      } catch {
        HeaderJson = "{}";
      }

      // PathVariable이 들어갈 부분에 대한 URL 수정
      var URL = this.apiURL;

      var pvCount = 0;
      var newURL = "";
      var start = false;
      for (var i = 0; i < URL.length; i++) {
        if (URL[i] == "{") {
          start = true;
        }
        if (!start) newURL += URL[i];
        if (URL[i] == "}") {
          start = false;

          newURL += this.PathVariables[pvCount].value;
          pvCount++;
        }
        if (URL[i] == "{") {
          start = true;
        }
      }
      URL = newURL;

      //QueryParams

      //Params JSON 에러 시 에러메시지 출력
      var paramJson = {};

      for (let QueryParam of this.QueryParams) {
        paramJson[QueryParam.key] = QueryParam.value;
      }

      try {
        paramJson = JSON.parse(paramJson);
      } catch {
        this.res = "Params JSON error";
      }

      //RequestBody
      //RequestBody JSON 세팅
      var PostJson;
      try {
        PostJson = JSON.parse(this.RequestBody);
      } catch {
        this.res = "RequestBody JSON error";
      }

      switch (methodType) {
        case "GET":
          axios
            .get(URL, { params: paramJson, headers: HeaderJson })
            .then((data) => {
              this.responsedata = data;
            })
            .catch((error) => {
              this.responsedata = error;
            });
          break;

        case "POST":
          axios
            .post(URL, PostJson, { params: paramJson, headers: HeaderJson })
            .then((data) => {
              this.responsedata = data;
            })
            .catch((error) => {
              this.responsedata = error;
            });
          break;

        case "PUT":
          axios
            .put(URL, PostJson, { params: paramJson, headers: HeaderJson })
            .then((data) => {
              this.responsedata = data;
            })
            .catch((error) => {
              this.responsedata = error;
            });
          break;

        case "PATCH":
          axios
            .patch(URL, PostJson, { params: paramJson, headers: HeaderJson })
            .then((data) => {
              this.responsedata = data;
            })
            .catch((error) => {
              this.responsedata = error;
            });
          break;

        case "DELETE":
          axios
            .delete(URL, { params: paramJson, headers: HeaderJson })
            .then((data) => {
              this.responsedata = data;
            })
            .catch((error) => {
              this.responsedata = error;
            });
          break;
      }
    },
    OptionSelect(data) {
      //Request의 타입을 설정
      this.typeSelect = data;
    },
    ResponseOptionSelect(data) {
      //Response의 타입을 설정
      this.ResponseTypeSelect = data;
    },
    apiListDetail(index) {
      //api 클릭시 중앙에 나타남
      this.nowIndex = index;
      this.res = "";
      this.index = index;
    },
    logListDetail(index) {
      this.isBtnClicked = false;
      //로그 클릭 시 보여줄 변수들 설정
      //여기서 모달 동작하게 해야 함. 아래 변수들을 사용하여 모달 표현
      //모달 완성 시 아래 코드 삭제
      index = this.logList.length - index - 1;

      this.dialog = true;
      this.dialogContent = {
        statusCode: this.logList[index].statusCode,
        logTime: this.logList[index].logTime,
        userName: this.logList[index].userName,
        url: this.logList[index].url,
        requestBody: this.logList[index].requestBody,
        responseBody: this.logList[index].responseBody,
      };
    },
    saveLocalStorage() {
      //프로젝트 번호와 api 번호에 맞춤
      //method, URL은 변경 여부 확인을 위해 저장
      //저장해야 할 것 : Params, RequestBody, Header, PathVariables
      var storageKey = {
        projectId: this.projectId,
        rowId: this.rowId,
      };
      var storageValue = {
        Header: this.Header,
        PathVariables: this.PathVariables,
        RequestBody: this.RequestBody,
        Params: this.Params,
        method: this.methodType,
        apiURL: this.apiURL,
        QueryParams: this.QueryParams,
      };
      storageKey = JSON.stringify(storageKey);
      storageValue = JSON.stringify(storageValue);

      localStorage.removeItem(storageKey);
      localStorage.setItem(storageKey, storageValue);
    },

    deleteRow(type, rowIndex) {
      if (type == "PathVariable") {
        this.PathVariables.splice(rowIndex, 1);
      } else {
        this.QueryParams.splice(rowIndex, 1);
      }
    },
    addRow(type) {
      if (type == "PathVariable") {
        this.PathVariables.push({ key: "", value: "" });
      } else {
        this.QueryParams.push({ key: "", value: "" });
      }
    },
    logOpenClose() {
      this.logListOpened = !this.logListOpened;
    },
  },

  data() {
    return {
      projectTitle: "",

      typeSelect: "",
      methodType: "",
      index: "",
      apiList: [],
      apiName: "",
      beforetype: "",
      nowIndex: 0,
      rowId: "",

      //Request
      BaseURL: "",
      apiURL: "",
      PathVariable: "",
      Params: "",
      RequestBody: "",
      PathVariables: [],
      QueryParams: [],
      Header: "",

      //Response
      statusCode: "",
      responsedata: "",
      res: "",
      ResponseTypeSelect: "",
      responseHeader: "",
      responseCookie: "",

      // icon
      biDashCircle,
      biPlusCircle,
      biArrowRightCircle,
      biArrowLeftCircle,

      // SEND 버튼 클릭 여부
      isBtnClicked: false,

      //log
      logList: [],
      dialog: false,
      dialogContent: {
        statusCode: "",
        logTime: "",
        userName: "",
        url: "",
        requestBody: "",
        responseBody: "",
      },
      logListOpened: false,

      // scroll
      thumbstyle: {
        right: "3px",
        bottom: "3px",
        borderRadius: "5px",
        background: "gray",
        width: "6px",
        height: "6px",
        opacity: 0.5,
      },
    };
  },
  watch: {
    responsedata(newdata) {
      //responsedata가 변경됐을 경우 작동
      //성공했을 경우의 response 처리
      if (newdata && newdata.data) {
        this.res = JSON.stringify(newdata.data, null, 4);
        this.statusCode = newdata.status;
        this.responseHeader = JSON.stringify(newdata.headers, null, 4);
        this.responseCookie = JSON.stringify(newdata.cookies, null, 4);
      } else {
        //실패했을 경우(ex : 404)의 response 처리
        this.res = newdata;
        this.res = JSON.stringify(this.res, null, 4);

        try {
          this.statusCode = newdata.response.status;
          this.responseHeader = JSON.stringify(
            newdata.response.headers,
            null,
            4
          );
          this.responseCookie = JSON.stringify(
            newdata.response.cookies,
            null,
            4
          );
        } catch {
          if (this.isBtnClicked) this.statusCode = "NetworkError";
          this.res = newdata;
        }
      }

      if (this.isBtnClicked) {
        //로컬스토리지에 한거 저장함
        //Save 버튼으로 변경

        //이 부분에 DB에 로그 저장하는 로직이 들어가야 함.
        let method = newdata.config.method,
          url = newdata.request.responseURL,
          requestBody = newdata.config.data,
          statusCode = newdata.request.status,
          responseBody = newdata.request.response;
        let logRequestBody = {
          userName: this.userName,
          method: method,
          url: url,
          requestBody: requestBody ? requestBody : "",
          statusCode: statusCode,
          responseBody: responseBody,
        };
        addLog(
          {
            pathVariable: { projectId: this.projectId, rowId: this.rowId },
            requestBody: logRequestBody,
          },
          (response) => {
            // success !
            response;
            this.loadLogList();
          },
          (e) => {
            console.warn(e);
          }
        );
      }
    },
    index(newindex) {
      //다른 api 선택했을 경우 그거에 맞춰서 전부 초기화
      this.RequestBody = this.apiList[newindex].RequestBody;
      this.Params = this.apiList[newindex].Params;
      this.Header = this.apiList[newindex].Header;
      this.methodType = this.apiList[newindex].type;
      this.PathVariable = this.apiList[newindex].PathVariable;
      this.apiURL = this.BaseURL + this.apiList[newindex].RequestURL;
      this.apiName = this.apiList[newindex].name;
      this.statusCode = "";
      this.rowId = this.apiList[newindex].rowId;
      this.responseHeader = "";
      this.responseCookie = "";

      var URLtemp = this.apiURL;
      var newURL = "";
      var temp = "";
      var start = false;
      this.PathVariables = [];
      this.QueryParams = [];

      //PathVariable 세팅을 위한 문자열 처리 '{}'를 인식함
      for (var i = 0; i < URLtemp.length; i++) {
        if (URLtemp[i] == "{") {
          start = true;
          newURL = newURL.slice(0, -1);
        }
        if (!start) newURL += URLtemp[i];
        if (URLtemp[i] == "}") {
          start = false;
          var keytemp = new Object();
          keytemp.key = temp;
          keytemp.value = "";
          this.PathVariables.push(keytemp);

          temp = "";
        }
        if (start && URLtemp[i] != "{") {
          temp += URLtemp[i];
        }
        if (URLtemp[i] == "{") {
          start = true;
        }
      }

      //localStorage로 확인. rowId로 불러오기
      let storageKey = {
        projectId: this.projectId,
        rowId: this.rowId,
      };
      let storageValueLoad = localStorage.getItem(JSON.stringify(storageKey));

      storageValueLoad = JSON.parse(storageValueLoad);

      if (
        storageValueLoad != null &&
        storageKey.rowId == this.rowId &&
        storageValueLoad.method == this.methodType
      ) {
        this.RequestBody = storageValueLoad.RequestBody;
        this.Params = storageValueLoad.Params;
        this.Header = storageValueLoad.Header;
        //PathVariable 로직
        let localStoragePathVariables = storageValueLoad.PathVariables;
        for (let URLpathVariable of this.PathVariables) {
          var isExist = false;
          for (let localStoragePathVariable of storageValueLoad.PathVariables) {
            if (localStoragePathVariable.key == URLpathVariable.key) {
              isExist = true;
            }
          }
          if (!isExist) {
            localStoragePathVariables.push(URLpathVariable);
          }
        }
        this.PathVariables = localStoragePathVariables;

        //QueryParam은 URL 문자열 처리가 없기 때문에 그냥 불러옴.
        this.QueryParams = storageValueLoad.QueryParams;
      } else {
        //localStorage.removeItem(storageKey);
      }

      /**Log 불러와야 하는 부분 */

      if (this.rowId) {
        this.loadLogList();
      }

      /******* 강제설정 데이터 있던 부분(삭제됨)*/

      /******* 강제설정 데이터 끝*/
    },
  },
  mounted() {
    //DB 및 BE 구현되면 받아와야 함
    var ApiListTop = {
      type: "",
      name: "",
      Header: "",
      PathVariable: "",
      Params: "",
      RequestBody: "",
      RequestURL: "",
    };

    //vuex에 데이터 저장하는 방식 (이후 이 페이지에서는 사라져야 함)

    //var apiListMount = [];
    getDocs({ pathVariable: { projectId: this.projectId } }, (data) => {
      let document = data.data;
      this.projectTitle = document.title;
      this.BaseURL = document.baseUrl;
      if (this.BaseURL == null || this.BaseURL == "null"){
        this.BaseURL = ""
      }
      for (let rowId of document.rows) {
        // one -> 이름
        // two -> method
        // three -> URL
        var obj = {
          rowId: rowId,
          type: document.data[rowId].two,
          name: document.data[rowId].one,
          Header: `{
}`,
          PathVariable: "",
          Params: "",
          RequestBody: "",
          RequestURL: document.data[rowId].three,
        };
        if (obj.type == null) {
          obj.type = "";
        }
        obj.type = obj.type.toUpperCase();

        this.apiList.push(obj);
      }
    });
    this.apiList.push(ApiListTop);

    this.index = 0;

    //처음 페이지 로드 시 (index값 변화했을때와 같음)
    this.RequestBody = this.apiList[this.index].RequestBody;
    this.Params = this.apiList[this.index].Params;
    this.Header = this.apiList[this.index].Header;
    this.methodType = this.apiList[this.index].type;
    this.PathVariable = this.apiList[this.index].PathVariable;
    this.typeSelect = "PathVariable";
    this.apiURL = this.BaseURL + this.apiList[this.index].RequestURL;
    this.apiName = this.apiList[this.index].name;
    this.beforetype = "PathVariable";
    this.rowId = this.apiList[this.index].rowId;

    //PathVariable 문자열 처리
    var URLtemp = this.apiURL;
    var newURL = "";
    var temp = "";
    var start = false;
    this.PathVariables = [];
    for (var i = 0; i < URLtemp.length; i++) {
      if (URLtemp[i] == "{") {
        start = true;
        newURL = newURL.slice(0, -1);
      }
      if (!start) newURL += URLtemp[i];
      if (URLtemp[i] == "}") {
        start = false;
        var keytemp = new Object();
        keytemp.key = temp;
        keytemp.value = "";
        this.PathVariables.push(keytemp);

        temp = "";
      }
      if (start && URLtemp[i] != "{") {
        temp += URLtemp[i];
      }
      if (URLtemp[i] == "{") {
        start = true;
      }
    }

    this.ResponseTypeSelect = "ResponseBody";
  },
};
</script>

<style scoped>
#KeyValueInput {
  width: 98%;
  height: 100%;
  resize: none;
  background-color: #ffffff;
  overflow-y: auto;
  border: 1px solid #000000;
  margin: 0px auto;
}
#KeyValueTable {
  height: 100%;
}
#ParamsInput {
  width: 98%;
  height: 100%;
  resize: none;
}
#RequestBodyInput {
  width: 98%;
  height: 100%;
  resize: none;
}
#HeaderArea {
  width: 98%;
  height: 100%;
  resize: none;
}
#All {
  width: 100vw;
  height: 88vh;
}
#Maincontainer {
  width: 100vw;
  height: 88vh;
  display: flex;
}
#Top {
  height: 5%;
  background-color: #ffffff;
}
#apiName {
  flex: 74;
}
#savebtn {
  flex: 8;
  font-size: 10px;
}
#MainTop {
  height: 4.7%;
  display: flex;
  width: 99%;
  margin: 0 auto;
}
#TypeURL {
  height: 6%;
  display: flex;
  width: 99%;
  margin: 0 auto;
}
#RequestType {
  text-align: center;
  flex: 8;
  background-color: #f3f3f3;
}
#apiURL {
  flex: 64;
  background-color: #f3f3f3;
}
#empty {
  flex: 1;
}
#testbtn {
  flex: 8;
  height: 100%;
  background-color: #f3f3f3;
}
#RequestBox {
  height: 40%;
  width: 99%;
  margin: 0 auto;
  background-color: #ffffff;
}
#ResponseBox {
  height: 40%;
  width: 99%;
  margin: 0 auto;
  background-color: #ffffff;
}
#ResponseBoxTop {
  height: 11%;
  width: 100%;
  display: flex;
}
#ResponseParent {
  width: 100%;
  height: 88%;
  display: flex;
  justify-content: center;
}
#ResponseContent {
  width: 98%;
  height: 95%;
  background-color: #ffffff;
  resize: none;
}
#RequestOptions {
  height: 15%;
  background-color: #ffffff;
}
#ReqeustOptionsDetail {
  margin-right: 3%;
  cursor: pointer;
  text-align: left;
  line-height: 240%;
}
#ResponseOptions {
  height: 15%;
  display: flex;
}
#ResponseOptionsDetail {
  margin-right: 3%;
  cursor: pointer;
  text-align: left;
  line-height: 240%;
}
#RequestParent {
  width: 100%;
  height: 80%;
  display: flex;
  justify-content: center;
}
/* .underbar{
  border-bottom: solid 3px red;
} */
#Left {
  width: 100%;
  height: 88vh;
  background-color: var(--cultured);
  overflow: hidden;
  overflow-y: hidden;
  border-right: #000000 solid 3px;
}

#Main {
  background-color: #ffffff;
  flex: 5;
  height: 100%;
  border-right: solid 1px;
}
#Right {
  height: 88vh;
  background-color: var(--cultured);
}
#apiListAll {
  white-space: nowrap;
  height: 100%;
}
#apiListDetail {
  cursor: pointer;
}
</style>
