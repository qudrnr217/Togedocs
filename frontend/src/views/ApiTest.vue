<template>
  <div class="All">

    <div class="Top">

      <div>프로젝트 이름1</div>
      
    </div>
    <div class="Maincontainer">
      <div class="Left">
        <api-list></api-list>
      </div>




      <div class="Main">
        <div class="apiName">
          <div style="text-align:left">회원가입</div>
        </div>

        <div class="TypeURL">
          <select class="RequestType" v-model="methodType">
            <option value="GET">GET</option>
            <option value="POST">POST</option>
            <option value="PUT">PUT</option>
            <option value="PATCH">PATCH</option>
            <option value="DELETE">DELETE</option>
          </select>
          <div class="empty"></div>
          <input class="apiURL" v-model="apiURL" />
          <div class="empty"></div>
          <button class="testbtn" @click="Test">Send</button>
        </div>

      <div class="RequestBox">
        <div class="RequestOptions">
          <div :class="{'underbar': typeSelect == 'RequestHeader' }" id="ReqeustOptionsDetail" @click="OptionSelect('RequestHeader')">Header</div>
          <div :class="{'underbar': typeSelect == 'PathVariable' }" id="ReqeustOptionsDetail" @click="OptionSelect('PathVariable')" >PathVariable</div>
          <div :class="{'underbar': typeSelect == 'Params' }" id="ReqeustOptionsDetail" @click="OptionSelect('Params')" >Params</div>
          <div :class="{'underbar': typeSelect == 'Body' }" id="ReqeustOptionsDetail" @click="OptionSelect('Body')" >RequestBody</div>
        </div>

        <div class="RequestParent">
          <textarea id="HeaderArea" v-show="typeSelect == 'RequestHeader'" v-model="Header"/>
        
          <textarea id="PathVariableInput" v-show="typeSelect == 'PathVariable'" v-model="PathVariable"/>


          <textarea id="ParamsInput" v-show="typeSelect == 'Params'" v-model="Params"/>

          <textarea id="BodyInput" v-show="typeSelect == 'Body'" v-model="Body"/>
        </div>
   
      </div>
      <div style="height: 1%">
      </div>  

      <div class="ResponseBox">
        <div class="ResponseBoxTop">Response</div>
        <div class="ResponseOptions">
          <div :class="{'underbar': ResponseTypeSelect == 'Body' }" id="ResponseOptionsDetail" @click="ResponseOptionSelect('Body')">Body</div>
          <div :class="{'underbar': ResponseTypeSelect == 'Cookie' }" id="ResponseOptionsDetail" @click="ResponseOptionSelect('Cookie')" >Cookie</div>
          <div :class="{'underbar': ResponseTypeSelect == 'Header' }" id="ResponseOptionsDetail" @click="ResponseOptionSelect('Header')" >Header</div>
        </div>
          <div class="ResponseParent">
            <textarea readonly v-show="ResponseTypeSelect == 'Body'" id="ResponseContent" v-model="res"></textarea>
          </div>
        </div>
      </div>

      <details class="Right">
        <summary>호출 로그</summary>
        <div class="tpt">details 과 summary 그리고 css까지 적용</div>
      </details>
    </div>
    
  </div>
</template>

<script>
import axios from "axios";
import apiList from '@/components/apiTest/apiList.vue';
export default {
  components: { apiList },
  name: 'HelloWorld', 
  props: {
    msg: String
  },
  methods:{
      Test(){

        // var URL = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev?serviceKey=hMlb8qO%2FdgkfNg5%2BiWHftA4WzS0Jj0dSbzyx5o4A3%2BM59BudkGG0DnEPK9aCbbay%2BrHppszUbsesl6hRLZ8N6Q%3D%3D&pageNo=1&numOfRows=10&LAWD_CD=11110&DEAL_YMD=201512";
        // axios.get(
        //   URL
        // ).then((data)=>{this.responsedata = data});
      
        if (this.typeSelect == "PathVariable") this.PathVariablebtn();
        else if (this.typeSelect == "Params") this.Paramsbtn();
        else if (this.typeSelect == "Body") this.Bodybtn();
      },
      PathVariablebtn(){

        var URL = this.apiURL + this.apinextURL + this.PathVariable;
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
      },
      ResponseOptionSelect(data){
        this.ResponseTypeSelect=data;
      }

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
    }
  },
  watch: {
    responsedata(newdata){
     // console.log(newdata.data);
      this.res = newdata.data;
      if (newdata.data == null) this.res = newdata;

      this.res = JSON.stringify(this.res);
      console.log(newdata);
    }
  },
  mounted(){
    this.Body=`{
  "userEmail": "string",
  "userId": "string",
  "userName": "string",
  "userNickname": "string",
  "userPassword": "string",
  "userPhone": "string"
}`;
    this.Params = `{
  "userSeq" : int,
  "bookSeq" : int
}`;
    this.Header=`{
  "Authorization" : "token"
}`;
    this.methodType = "GET";
    //this.apinextURL = '/api/book';
    this.PathVariable = '/{userSeq}';
    this.typeSelect = 'PathVariable';
    this.apiURL = 'https://k7a404.p.ssafy.io/api/book';
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
.All{
  width:95vw;
  height:83vh;
}
.Maincontainer{
  width:95vw;
  height:83vh;
  display: flex;
}
.Top{
  height: 5%;
  background-color: #ffffff;
}
.apiName{
  height: 4%;
}
.TypeURL{
  height: 6%;
  display: flex;
  width:99%;
  margin:0 auto; 
}
.RequestType{
  text-align: center;
  flex:8;
  background-color: #F3F3F3;
}
.apiURL{
  flex: 64;
  background-color: #F3F3F3;
}
.empty{
  flex:1;
}
.testbtn{
  flex: 8;
  background-color: #F3F3F3;
}
.RequestBox{
  height: 40%;
  width:99%;
  margin:0 auto; 
  background-color: #F3F3F3;
}
.ResponseBox{
  height:50%;
  width:99%;
  margin:0 auto; 
  background-color: #D9D9D9;
}
.ResponseBoxTop{
  height: 8%;
}
.ResponseParent{
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
.RequestOptions{
  height: 12%;
  display: flex;
  background-color: #F3F3F3;
}
#ReqeustOptionsDetail{
  margin-right: 3%;
  cursor: pointer;
  text-align: left;
  line-height: 240%;
}
.ResponseOptions{
  height: 12%;
  display: flex;
}
#ResponseOptionsDetail{
  margin-right: 3%;
  cursor: pointer;
  text-align: left;
  line-height: 240%;
}
.RequestParent{
  width:100%;
  height: 70%;
  display: flex;
  justify-content: center;
}
.underbar{
  border-bottom: solid 3px red;
}
.Left{
  width: 10%;
  height: 83vh;
  background-color:#e7e7e7; 
  resize: horizontal;
  overflow: auto;
}
.Left::-webkit-resizer {
  border-width: 8px;
  border-style: solid;
  border-color: transparent orangered orangered transparent;


}
.Main{
  background-color: #ffffff;
  flex: 5;
}
.Right{
  height:83vh;
  background-color: #e7e7e7;
}

</style>
