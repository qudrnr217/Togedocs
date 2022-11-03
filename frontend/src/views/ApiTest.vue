<template>
  <div id="All">

    <div id="Top">

      <div>프로젝트 이름1</div>
      
    </div>
    <div id="Maincontainer">
      <div id="Left">
          <div id="apiListAll">
            <div>api 목록</div>
            
            <div v-for="(item, index) in $store.state.apiStoreList" v-bind:key="index">
              <q-item clickable @click="apiListDetail(index)" :active="index === nowIndex" v-ripple>
                <q-item-section avatar>
                  {{item.type}}
                </q-item-section>
                <q-item-section>
                  {{ item.name }}
                </q-item-section>
              </q-item>
 

            </div>

        
        </div>
      </div>


      <div id="Main">
        <div id="apiName">
          <div style="text-align:left">{{apiName}}</div>
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
        <q-tabs id="RequestOptions"
        dense
        align="left">
          <q-tab id="ReqeustOptionsDetail" @click="OptionSelect('RequestHeader')">Header</q-tab>
          <q-tab id="ReqeustOptionsDetail" @click="OptionSelect('PathVariable')" >PathVariable</q-tab>
          <q-tab id="ReqeustOptionsDetail" @click="OptionSelect('Params')" >Params</q-tab>
          <q-tab id="ReqeustOptionsDetail" @click="OptionSelect('Body')" >RequestBody</q-tab>
        </q-tabs>

        <div id="RequestParent">
          <textarea id="HeaderArea" v-show="typeSelect == 'RequestHeader'" v-model="Header"/>
        
          <div id="PathVariableInput" v-show="typeSelect == 'PathVariable'">
            <q-markup-table dense id="PathVariableTable">
              <thead>
                <tr>
                  <th class="text-left">key</th>
                  <th class="text-left">PathVariable</th>
                </tr>
              </thead>
              <tbody v-for="(item, index) in PathVariables" v-bind:key="index">
                <td>{{item.key}}</td>
                <td><q-input outlined id= "PathVariableTableInput" v-model="item.value"></q-input></td>
              </tbody>
            </q-markup-table>
          </div>


          <textarea id="ParamsInput" v-show="typeSelect == 'Params'" v-model="Params"/>

          <textarea id="BodyInput" v-show="typeSelect == 'Body'" v-model="Body"/>
        </div>
   
      </div>
      <div style="height: 1%">
      </div>  

      <div id="ResponseBox">
        <div id="ResponseBoxTop">Response</div>
        <q-tabs id="ResponseOptions"
        dense
        align="left">
          <q-tab id="ResponseOptionsDetail" @click="ResponseOptionSelect('Body')">Body</q-tab>
          <q-tab id="ResponseOptionsDetail" @click="ResponseOptionSelect('Cookie')" >Cookie</q-tab>
          <q-tab id="ResponseOptionsDetail" @click="ResponseOptionSelect('Header')" >Header</q-tab>
        </q-tabs>
          <div id="ResponseParent">
            <textarea readonly v-show="ResponseTypeSelect == 'Body'" id="ResponseContent" v-model="res"></textarea>
          </div>
        </div>
      </div>

      <details id="Right">
        <api-logs></api-logs>
        
      </details>
    </div>
    
  </div>
</template>

<script>
import axios from "axios";
import apiLogs from '@/components/apiTest/apiLogs.vue';
export default {
  components: { apiLogs }, 
  props: {
    msg: String
  },
  methods:{
      Test(){
        
        //typeSelect값에 따라 다른 함수 실행(axios)
        if (this.typeSelect == "PathVariable") this.PathVariablebtn();
        else if (this.typeSelect == "Params") this.Paramsbtn();
        else if (this.typeSelect == "Body") this.Bodybtn();
        else {
          // Header를 활성화한 채 테스트 버튼을 클릭했을 경우 PathVariable, Params, Body의 값을 보고 실행
          if (this.PathVariables.length != 0) this.PathVariablebtn();
          else if (this.Params != '') this.Paramsbtn();
          else this.Bodybtn();
        }
      },
      PathVariablebtn(){

        var URL = this.apiURL + this.apinextURL;
        for (let pv of this.PathVariables){
          URL += "/";
          URL += pv.value;
        }
        var HeaderJson; 
        try{
          HeaderJson = JSON.parse(this.Header);
        } catch{
          HeaderJson = '{}';
        }

        if (this.methodType == "GET"){
          axios.get(URL, {headers : HeaderJson}).then((data)=>{this.responsedata = data}).catch((error) => {this.responsedata = error});
        } else if (this.methodType == "DELETE"){
          axios.delete(URL, {headers : HeaderJson}).then((data)=>{this.responsedata = data}).catch((error) => {this.responsedata = error});
        }

      },
      Paramsbtn(){
        var URL = this.apiURL + this.apinextURL;
        var paramJson;
        try{
          paramJson = JSON.parse(this.Params);
        }catch{
          this.res = 'Params JSON error';
        }

        var HeaderJson; 
        try{
          HeaderJson = JSON.parse(this.Header);
        } catch{
          HeaderJson = '{}';
        }

        if (this.methodType == "GET"){
          axios.get(URL, {params : paramJson,
          headers : HeaderJson
          }).then((data)=>{this.responsedata = data}).catch((error) => {this.responsedata = error});
        } else if (this.methodType == "DELETE"){
          axios.delete(URL, {params : paramJson,
          headers : HeaderJson
          }).then((data)=>{this.responsedata = data}).catch((error) => {this.responsedata = error});
        }

      },
      Bodybtn(){

        var URL = this.apiURL + this.apinextURL;
        var PostJson;
        try{
          PostJson = JSON.parse(this.Body);
        }catch{
          this.res = 'Body JSON error';
        }
        var HeaderJson;
        try{
          HeaderJson = JSON.parse(this.Header);
        } catch{
          HeaderJson = '{}';
        }

        if (this.methodType == "POST"){
          axios.post(URL, PostJson, {headers : HeaderJson})
          .then(data => {
            this.responsedata = data
          }).catch((error) => {this.responsedata = error})
        }
        else if (this.methodType == "PUT"){
          axios.put(URL, PostJson, {headers : HeaderJson})
          .then(data => {
            this.responsedata = data
          }).catch((error) => {this.responsedata = error})
        }
        else if (this.methodType == "PATCH"){
          axios.patch(URL, PostJson, {headers : HeaderJson})
          .then(data => {
            this.responsedata = data
          }).catch((error) => {this.responsedata = error})
        }
        // console.log(URL);
        // console.log(this.PostValue);
      },
      OptionSelect(data){
        this.typeSelect = data;
        console.log(data);
        if (data != "RequestHeader") this.beforetype = data;
        console.log(this.beforetype);
      },
      ResponseOptionSelect(data){
        this.ResponseTypeSelect=data;
      },
      apiListDetail(index){
        console.log(this.$store.state.apiStoreList[index]);
        this.nowIndex = index;
        this.res = '';
        this.index = index;
      },
      PathVariableDetect(URL){
        console.log(URL);
      },
  },

  data(){
    return {
      apiURL : '',
      typeSelect: '',
      methodType: '',
      apinextURL: '',
      PathVariable: '',
      Params: '',
      Body: '',
      Header: '',
      responsedata: '',
      res:'',
      ResponseTypeSelect: '',
      index: '',
      apiList:[],
      apiName: '',
      beforetype: '',
      PathVariables : [],
      nowIndex: 0,
    }
  },
  watch: {
    responsedata(newdata){
     // console.log(newdata.data);
      this.res = newdata.data;
      if (newdata.data == null) this.res = newdata;

      this.res = JSON.stringify(this.res);
      
      console.log(newdata);
    },
    index(newindex){
      this.Body=this.$store.state.apiStoreList[newindex].RequestBody;
      this.Params = this.$store.state.apiStoreList[newindex].Params;
      this.Header=this.$store.state.apiStoreList[newindex].Header;
      this.methodType = this.$store.state.apiStoreList[newindex].type;
      this.PathVariable = this.$store.state.apiStoreList[newindex].PathVariable;
      this.typeSelect = 'PathVariable';
      this.apiURL = this.$store.state.apiStoreList[newindex].RequestURL;
      this.apiName = this.$store.state.apiStoreList[newindex].name;

      var URLtemp = this.apiURL;
      var newURL = '';
      var temp = '';
      var start = false;
      this.PathVariables = [];
      for (var i=0; i<URLtemp.length; i++){
        if (URLtemp[i] == "{") {
            start = true;
            newURL = newURL.slice(0, -1);
        }
        if (!start) newURL += URLtemp[i];
        if (URLtemp[i] == "}") {
          start = false;
          var keytemp = new Object();
          keytemp.key = temp;
          keytemp.value = '';
          this.PathVariables.push(keytemp);

          temp = '';
        }
        if (start && URLtemp[i] != "{"){
          temp += URLtemp[i];
        }
        if (URLtemp[i] == "{") {
          start = true;
        }
        
      }
      this.apiURL = newURL;
      console.log(this.PathVariables);
      }

  },
  mounted(){
  var Item1 = new Object();
    Item1.type = "POST";
    Item1.name = "로그인";
    Item1.Header=`{

}`;
    Item1.PathVariable='';
    Item1.Params = '';
    Item1.RequestBody = `{
  "userId": "String",
  "userPassword": "String"
}`
    Item1.RequestURL = 'https://k7a404.p.ssafy.io/api/auth/login';


    var Item2 = new Object();
    Item2.type = "GET";
    Item2.name = "book 조회";
    Item2.Header=`{

}`;
    Item2.PathVariable='';
    Item2.Params = '';
    Item2.RequestBody = '';
    Item2.RequestURL = 'https://k7a404.p.ssafy.io/api/book/{bookseq}';
    
    var Item3 = new Object();
    Item3.type = "POST";
    Item3.name = "회원가입";
    Item3.Header=`{

}`;
    Item3.PathVariable='';
    Item3.Params = '';
    Item3.RequestBody = `{
  "userEmail": "string",
  "userId": "string",
  "userName": "string",
  "userNickname": "string",
  "userPassword": "string",
  "userPhone": "string"
}`;
    Item3.RequestURL = 'https://k7a404.p.ssafy.io/api/user/signup';


    var Item4 = new Object();
    Item4.type = "GET";
    Item4.name = "개별 랜드마크 조회";
    Item4.Header=`{

}`;
    Item4.PathVariable='';
    Item4.Params = '';
    Item4.RequestBody = ``;
    Item4.RequestURL = 'https://k7a404.p.ssafy.io/api/book/{userSeq}/{bookSeq}';
    

    this.apiList = [Item4, Item1, Item2, Item3];
    this.$store.state.apiStoreList = this.apiList;
    this.index = 0;


    this.Body=this.$store.state.apiStoreList[this.index].RequestBody;
    this.Params = this.$store.state.apiStoreList[this.index].Params;
    this.Header=this.$store.state.apiStoreList[this.index].Header;
    this.methodType = this.$store.state.apiStoreList[this.index].type;
    this.PathVariable = this.$store.state.apiStoreList[this.index].PathVariable;
    this.typeSelect = 'PathVariable';
    this.apiURL = this.$store.state.apiStoreList[this.index].RequestURL;
    this.apiName = this.$store.state.apiStoreList[this.index].name;
    this.beforetype = 'PathVariable';

    var URLtemp = this.apiURL;
      var newURL = '';
      var temp = '';
      var start = false;
      this.PathVariables = [];
      for (var i=0; i<URLtemp.length; i++){
        if (URLtemp[i] == "{") {
            start = true;
            newURL = newURL.slice(0, -1);
        }
        if (!start) newURL += URLtemp[i];
        if (URLtemp[i] == "}") {
          start = false;
          var keytemp = new Object();
          keytemp.key = temp;
          keytemp.value = '';
          this.PathVariables.push(keytemp);

          temp = '';
        }
        if (start && URLtemp[i] != "{"){
          temp += URLtemp[i];
        }
        if (URLtemp[i] == "{") {
          start = true;
        }
        
      }
      this.apiURL = newURL;
      console.log(this.PathVariables);


    this.ResponseTypeSelect = "Body"

  }

}

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
#PathVariableInput{
  width:98%;
  height:100%;
  resize: none;
  background-color: #ffffff;
  overflow-y: auto;
  border: 1px solid #000000;
  margin: 0px auto;
}
#PathVariableTable{
  height: 100%;
}
#ParamsInput{
  width:98%;
  height:100%;
  resize: none;
}
#BodyInput{
  width:98%;
  height:100%;
  resize: none;
}
#HeaderArea{
  width:98%;
  height:100%;
  resize: none;
}
#All{
  width:95vw;
  height:83vh;
}
#Maincontainer{
  width:95vw;
  height:83vh;
  display: flex;
}
#Top{
  height: 5%;
  background-color: #ffffff;
}
#apiName{
  height: 4%;
}
#TypeURL{
  height: 6%;
  display: flex;
  width:99%;
  margin:0 auto; 
}
#RequestType{
  text-align: center;
  flex:8;
  background-color: #F3F3F3;
}
#apiURL{
  flex: 64;
  background-color: #F3F3F3;
}
#empty{
  flex:1;
}
#testbtn{
  flex: 8;
  height: 80%;
  background-color: #F3F3F3;
}
#RequestBox{
  height: 40%;
  width:99%;
  margin:0 auto; 
  background-color: #F3F3F3;
}
#ResponseBox{
  height:50%;
  width:99%;
  margin:0 auto; 
  background-color: #D9D9D9;
}
#ResponseBoxTop{
  height: 8%;
}
#ResponseParent{
  width:100%;
  height: 70%;
  display: flex;
  justify-content: center;
}
#ResponseContent{
  width:98%;
  height:100%;
  background-color: #ffffff;
  resize: none;
}
#RequestOptions{
  height: 15%;
  background-color: #F3F3F3;
}
#ReqeustOptionsDetail{
  margin-right: 3%;
  cursor: pointer;
  text-align: left;
  line-height: 240%;
}
#ResponseOptions{
  height: 12%;
  display: flex;
}
#ResponseOptionsDetail{
  margin-right: 3%;
  cursor: pointer;
  text-align: left;
  line-height: 240%;
}
#RequestParent{
  width:100%;
  height: 70%;
  display: flex;
  justify-content: center;
} 
/* .underbar{
  border-bottom: solid 3px red;
} */
#Left{
  width: 10%;
  height: 83vh;
  background-color:#e7e7e7; 
  resize: horizontal;
  overflow: hidden;
}
#Left::-webkit-resizer {
  border-width: 8px;
  border-style: solid;
  border-color: transparent orangered orangered transparent;


}
#Main{
  background-color: #ffffff;
  flex: 5;
}
#Right{
  height:83vh;
  background-color: #e7e7e7;
}
#apiListAll{
    white-space:nowrap;
}
#apiListDetail{
    cursor: pointer;
}


</style>
