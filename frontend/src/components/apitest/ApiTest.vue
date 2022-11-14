<template>
  <div id="All">
    <div style="height: 12%"></div>
    <div id="Top">
      <div style="font-weight: bold">프로젝트 이름1</div>
    </div>

    <div id="Maincontainer">
      <drag-col
        @isDragging="isDragging"
        @dragging="draggingCol"
        width="100%"
        height="100%"
        leftPercent="20"
        sliderWidth="15"
      >
        <template #left>
          <div id="Left">
            <div id="apiListAll">
              <div style="font-weight: bold; font-size: 15px">api 목록</div>

              <div v-for="(item, index) in apiList" v-bind:key="index">
                <q-item
                  clickable
                  @click="apiListDetail(index)"
                  :active="index === nowIndex"
                  v-ripple
                >
                  <q-item-section avatar>
                    {{ item.type }}
                  </q-item-section>
                  <q-item-section>
                    {{ item.name }}
                  </q-item-section>
                </q-item>
              </div>
            </div>
          </div>
        </template>

        <template #right>
          <div id="Main">
            <div id="apiName">
              <div style="text-align: left; font-size: 15px">{{ apiName }}</div>
            </div>

            <div id="TypeURL">
              <select id="RequestType" v-model="methodType">
                <option value="GET">GET</option>
                <option value="POST">POST</option>
                <option value="PUT">PUT</option>
                <option value="PATCH">PATCH</option>
                <option value="DELETE">DELETE</option>
              </select>
              <div id="empty"></div>
              <input id="apiURL" v-model="apiURL" />
              <div id="empty"></div>
              <q-btn color="primary" id="testbtn" @click="Test">Send</q-btn>
            </div>

            <div id="RequestBox">
              <q-tabs id="RequestOptions" dense no-caps align="left">
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
                  >Params</q-tab
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

                <div
                  id="PathVariableInput"
                  v-show="typeSelect == 'PathVariable'"
                >
                  <q-markup-table dense id="PathVariableTable">
                    <thead>
                      <tr>
                        <th class="text-left">key</th>
                        <th class="text-left">PathVariable</th>
                      </tr>
                    </thead>
                    <tbody
                      v-for="(item, index) in PathVariables"
                      v-bind:key="index"
                    >
                      <td>{{ item.key }}</td>
                      <td>
                        <q-input
                          dense
                          outlined
                          id="PathVariableTableInput"
                          v-model="item.value"
                        ></q-input>
                      </td>
                    </tbody>
                  </q-markup-table>
                </div>

                <textarea
                  id="ParamsInput"
                  v-show="typeSelect == 'Params'"
                  v-model="Params"
                />

                <textarea
                  id="RequestBodyInput"
                  v-show="typeSelect == 'RequestBody'"
                  v-model="RequestBody"
                />
              </div>
            </div>
            <div style="height: 1%"></div>

            <div id="ResponseBox">
              <div id="ResponseBoxTop">
                <div style="flex: 1">Response</div>
                <div style="flex: 10"></div>
                <div style="flex: 2">Status : {{ statusCode }}</div>
              </div>
              <q-tabs id="ResponseOptions" dense no-caps align="left">
                <q-tab
                  id="ResponseOptionsDetail"
                  @click="ResponseOptionSelect('RequestBody')"
                  >RequestBody</q-tab
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
                  v-show="ResponseTypeSelect == 'RequestBody'"
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
        </template>
      </drag-col>

      <details id="Right">
        <summary style="width: 6vw; font-weight: bold; cursor: pointer">
          호출 로그
        </summary>

        <div
          style="width: 30vw"
          v-for="(log, index) in logList"
          v-bind:key="index"
        >
          <q-item clickable @click="logListDetail(index)" v-ripple>
            <q-item-section avatar>
              {{ log.status }}
            </q-item-section>
            <q-item-section>
              {{ log.time }}
            </q-item-section>
            <q-item-section>
              {{ log.user }}
            </q-item-section>
          </q-item>
        </div>
      </details>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { DragCol } from "vue-resizer";
import { getDocs } from "@/api/apidocs.js";
export default {
  components: { DragCol },
  props: {
    msg: String,
  },
  methods: {
    Test() {
      this.isBtnClicked = true;
      // Request의 값에 따라 다른 함수 실행(axios)
      // 다른 부분을 활성화한 채 테스트 버튼을 클릭했을 경우 PathVariable, Params, RequestBody의 값을 보고 실행
      if (this.PathVariables.length != 0) this.PathVariablebtn();
      else if (this.Params != "") this.Paramsbtn();
      else this.RequestBodybtn();
    },
    PathVariablebtn() {
      //PathVariable 버튼

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
      console.log("PV테스트" + URL);
      //Header 설정. Json 오류날 경우 {}로 초기화
      var HeaderJson;
      try {
        HeaderJson = JSON.parse(this.Header);
      } catch {
        HeaderJson = "{}";
      }

      //axios 동작
      if (this.methodType == "GET") {
        axios
          .get(URL, { headers: HeaderJson })
          .then((data) => {
            this.responsedata = data;
          })
          .catch((error) => {
            this.responsedata = error;
          });
      } else if (this.methodType == "DELETE") {
        axios
          .delete(URL, { headers: HeaderJson })
          .then((data) => {
            this.responsedata = data;
          })
          .catch((error) => {
            this.responsedata = error;
          });
      }
    },
    Paramsbtn() {
      //Params 버튼
      var URL = this.apiURL + this.apinextURL;

      //Params JSON 에러 시 에러메시지 출력
      var paramJson;
      try {
        paramJson = JSON.parse(this.Params);
      } catch {
        this.res = "Params JSON error";
      }

      //Header 설정. Json 오류날 경우 {}로 초기화
      var HeaderJson;
      try {
        HeaderJson = JSON.parse(this.Header);
      } catch {
        HeaderJson = "{}";
      }

      //axios 동작
      if (this.methodType == "GET") {
        axios
          .get(URL, { params: paramJson, headers: HeaderJson })
          .then((data) => {
            this.responsedata = data;
          })
          .catch((error) => {
            this.responsedata = error;
          });
      } else if (this.methodType == "DELETE") {
        axios
          .delete(URL, { params: paramJson, headers: HeaderJson })
          .then((data) => {
            this.responsedata = data;
          })
          .catch((error) => {
            this.responsedata = error;
          });
      }
    },
    RequestBodybtn() {
      //RequestBody 버튼
      var URL = this.apiURL + this.apinextURL;

      //RequestBody JSON 세팅
      var PostJson;
      try {
        PostJson = JSON.parse(this.RequestBody);
      } catch {
        this.res = "RequestBody JSON error";
      }

      //Header Json 세팅. 오류나면 {}로 초기화함
      var HeaderJson;
      try {
        HeaderJson = JSON.parse(this.Header);
      } catch {
        HeaderJson = "{}";
      }

      //axios 동작
      if (this.methodType == "POST") {
        axios
          .post(URL, PostJson, { headers: HeaderJson })
          .then((data) => {
            this.responsedata = data;
          })
          .catch((error) => {
            this.responsedata = error;
          });
      } else if (this.methodType == "PUT") {
        axios
          .put(URL, PostJson, { headers: HeaderJson })
          .then((data) => {
            this.responsedata = data;
          })
          .catch((error) => {
            this.responsedata = error;
          });
      } else if (this.methodType == "PATCH") {
        axios
          .patch(URL, PostJson, { headers: HeaderJson })
          .then((data) => {
            this.responsedata = data;
          })
          .catch((error) => {
            this.responsedata = error;
          });
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
      this.statusCode = this.logList[index].status;
      this.Params = this.logList[index].Params;
      this.RequestBody = this.logList[index].RequestBody;
      this.Header = this.logList[index].Header;
      this.responsedata = this.logList[index].responsedata;
      this.PathVariables = this.logList[index].PathVariables;
      this.responseHeader = this.logList[index].responseHeader;
      this.responseCookie = this.logList[index].responseCookie;
    },
  },

  data() {
    return {
      apiURL: "",
      typeSelect: "",
      methodType: "",
      apinextURL: "",
      PathVariable: "",
      Params: "",
      RequestBody: "",
      Header: "",
      responsedata: "",
      res: "",
      ResponseTypeSelect: "",
      index: "",
      apiList: [],
      apiName: "",
      beforetype: "",
      PathVariables: [],
      nowIndex: 0,
      statusCode: "",
      responseHeader: "",
      responseCookie: "",
      logList: [],
      isBtnClicked: false,
    };
  },
  watch: {
    responsedata(newdata) {
      //responsedata가 변경됐을 경우 작동
      //성공했을 경우의 response 처리
      this.res = newdata.data;
      if (newdata.data != null) {
        this.res = JSON.stringify(this.res);
        this.statusCode = newdata.status;
        this.responseHeader = JSON.stringify(newdata.headers);
        this.responseCookie = JSON.stringify(newdata.cookies);
      } else {
        //실패했을 경우(ex : 404)의 response 처리
        this.res = newdata;
        this.res = JSON.stringify(this.res);

        try {
          this.statusCode = newdata.response.status;
          this.responseHeader = JSON.stringify(newdata.response.headers);
          this.responseCookie = JSON.stringify(newdata.response.cookies);
        } catch {
          if (this.isBtnClicked) this.statusCode = "NetworkError";
          this.res = newdata;
        }
      }

      if (this.isBtnClicked) {
        console.log("버튼클릭한거");
        //이 부분에 DB에 로그 저장하는 로직이 들어가야 함.
      } else {
        console.log("로그누른거");
      }
    },
    index(newindex) {
      //다른 api 선택했을 경우 그거에 맞춰서 전부 초기화
      this.RequestBody = this.apiList[newindex].RequestBody;
      this.Params = this.apiList[newindex].Params;
      this.Header = this.apiList[newindex].Header;
      this.methodType = this.apiList[newindex].type;
      this.PathVariable = this.apiList[newindex].PathVariable;
      this.apiURL = this.apiList[newindex].RequestURL;
      this.apiName = this.apiList[newindex].name;
      this.statusCode = "";

      var URLtemp = this.apiURL;
      var newURL = "";
      var temp = "";
      var start = false;
      this.PathVariables = [];

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

      /**Log 불러와야 하는 부분 */
      /**this.logList 라는 배열에 Log들이 담기면 됨 */
      /** */

      /******* 강제설정 데이터 시작 (삭제 필요)*/
      if (newindex == 1) {
        //Logs 1, 2, 3은 강제설정 데이터
        var Log1 = new Object();
        Log1.time = "2022.11.18 10:18";
        Log1.status = "200";
        Log1.user = "강병국";

        var Log2 = new Object();
        Log2.time = "2022.11.15 12:18";
        Log2.status = "Error";
        Log2.user = "홍인호";
        Log2.Params = "";
        Log2.RequestBody = `{
  "userId": "asdf",
  "userPassword": "asdf"
}`;
        Log2.Header = `{

}`;
        Log2.responsedata = `AxiosError: Network Error`;
        Log2.PathVariables = [];
        Log2.statusCode = "Error";
        Log2.responseHeader = "";
        Log2.responseCookie = "";

        var Log3 = new Object();
        Log3.time = "2022.11.11 16:18";
        Log3.status = "404";
        Log3.user = "홍인호";

        this.logList = [
          Log1,
          Log2,
          Log3,
          Log3,
          Log3,
          Log3,
          Log3,
          Log3,
          Log3,
          Log3,
          Log3,
          Log3,
          Log3,
        ];
      } else if (newindex == 4) {
        Log3 = new Object();
        Log3.time = "2022.11.11 16:18";
        Log3.status = "200";
        Log3.user = "홍인호";
        (Log3.Params = ""),
          (Log3.RequestBody = `{
}`);
        Log3.Header = `{

}`;
        Log3.responsedata = ``;
        Log3.PathVariables = [];
        var tempPV = new Object();
        tempPV.key = "projectId";
        tempPV.value = 1;
        Log3.PathVariables.push(tempPV);
        console.log(Log3.PathVariables);
        Log3.statusCode = "200";
        Log3.responseHeader = `{"cache-control":"no-cache, no-store, max-age=0, must-revalidate","content-type":"application/json;charset=UTF-8","expires":"0","pragma":"no-cache"}`;

        this.logList = [Log3];
      } else {
        this.logList = [];
      }

      /******* 강제설정 데이터 끝*/
    },
  },
  mounted() {
    //DB 및 BE 구현되면 받아와야 함
    var ApiListTop = {
      type: "Method",
      name: "이름",
      Header: "",
      PathVariable: "",
      Params: "",
      RequestBody: "",
      RequestURL: "",
    };

    var testprojectID = 1;

    //var apiListMount = [];
    getDocs({ pathVariable: { projectId: testprojectID } }, (data) => {
      for (let key in data.data.data) {
        // one -> 이름
        // two -> method
        // three -> URL
        var obj = {
          type: data.data.data[key].two,
          name: data.data.data[key].one,
          Header: `{

}`,
          PathVariable: "",
          Params: "",
          RequestBody: "",
          RequestURL: data.data.data[key].three,
        };

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
    this.apiURL = this.apiList[this.index].RequestURL;
    this.apiName = this.apiList[this.index].name;
    this.beforetype = "PathVariable";

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

    this.ResponseTypeSelect = "RequestBody";
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
#PathVariableInput {
  width: 98%;
  height: 100%;
  resize: none;
  background-color: #ffffff;
  overflow-y: auto;
  border: 1px solid #000000;
  margin: 0px auto;
}
#PathVariableTable {
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
  width: 95vw;
  height: 83vh;
}
#Maincontainer {
  width: 95vw;
  height: 83vh;
  display: flex;
}
#Top {
  height: 5%;
  background-color: #ffffff;
}
#apiName {
  height: 4%;
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
  height: 80%;
  background-color: #f3f3f3;
}
#RequestBox {
  height: 40%;
  width: 99%;
  margin: 0 auto;
  background-color: #f3f3f3;
}
#ResponseBox {
  height: 50%;
  width: 99%;
  margin: 0 auto;
  background-color: #d9d9d9;
}
#ResponseBoxTop {
  height: 8%;
  width: 100%;
  display: flex;
}
#ResponseParent {
  width: 100%;
  height: 70%;
  display: flex;
  justify-content: center;
}
#ResponseContent {
  width: 98%;
  height: 100%;
  background-color: #ffffff;
  resize: none;
}
#RequestOptions {
  height: 15%;
  background-color: #f3f3f3;
}
#ReqeustOptionsDetail {
  margin-right: 3%;
  cursor: pointer;
  text-align: left;
  line-height: 240%;
}
#ResponseOptions {
  height: 12%;
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
  height: 70%;
  display: flex;
  justify-content: center;
}
/* .underbar{
  border-bottom: solid 3px red;
} */
#Left {
  width: 100%;
  height: 83vh;
  background-color: #e7e7e7;
  overflow: hidden;
  overflow-y: auto;
}

#Main {
  background-color: #ffffff;
  flex: 5;
  height: 100%;
}
#Right {
  height: 83vh;
  background-color: #e7e7e7;
  overflow: hidden;
  overflow-y: auto;
}
#apiListAll {
  white-space: nowrap;
}
#apiListDetail {
  cursor: pointer;
}
</style>
