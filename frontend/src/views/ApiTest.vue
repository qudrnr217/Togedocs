<template>
  <div class="All">

    <div class="Top">

      <h2>프로젝트 이름1</h2>
      
    </div>
    <div class="Maincontainer">
      <div class="Left">
        <h2>left</h2>
      </div>
      <div class="Main">
        <div class="apiName" style="height:5%">
          <h2 style="text-align:left"> 회원가입</h2>
        </div>

        <div class="TypeURL">
          <textarea class="RequestType" v-model="methodType"/>
          <input class="apiURL" v-model="apiURL" />
          <button class="testbtn" @click="Test">Test</button>
        </div>

      <div class="Request">
          <br>
          <select v-model="typeSelect">
            <option value="Path">PathVariable</option>
            <option value="Param">Params</option>
            <option value="Body">RequestBody</option>
          </select>



          <p>Header</p>
          <textarea id="Header" v-model="Header"/>
        

          <p>PathVariable</p>
          <input id="PathVariableInput" v-model="PathVariable"/>



          <p>Params</p>
          <textarea id="ParamsInput" v-model="Params"/>



          <div>Body</div>
          <textarea id="BodyInput" v-model="Body"/>
      </div>

        <div>Response</div>
        <div>{{res}}</div>
      </div>

      <div class="Right">
        <h2>right</h2>
      </div>
    </div>
    
  </div>
</template>

<script>
import axios from "axios";
export default {
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
      
        if (this.typeSelect == "Path") this.PathVariablebtn();
        else if (this.typeSelect == "Param") this.Paramsbtn();
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
      res:''
    }
  },
  watch: {
    responsedata(newdata){
     // console.log(newdata.data);
      this.res = newdata.data;
      if (newdata.data == null) this.res = newdata;
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
    this.apiURL = 'https://k7a404.p.ssafy.io/api/book';

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
#postInput{
  width : 20%;
  height: 150px;
}
#ParamsInput{
  width: 95%;
  height: 15%;
}
#BodyInput{
  width: 95%;
  height: 20%;
}
#Header{
  width: 95%;
  height: 10%;
}
.All{
  width:95vw;
  height:100vh;
}
.Maincontainer{
  width:95vw;
  height:100vh;
  display: flex;
}
.Top{
  height: 5%;
  background-color: #ffffff;
}
.TypeURL{
  height: 6%;
  display: flex;
}
.RequestType{
  text-align: center;
  flex:1;
}
.apiURL{
  flex: 8;
}
.testbtn{
  flex: 1;
}
.Request{
  height: 50%;
  background-color: #F3F3F3;
}
.Left{
  width: 13%;
  height: 100vh;
  background-color:#e7e7e7;
  flex: 1;
}
.Main{
  width: 70%;
  background-color: #ffffff;
  flex: 4;
  text-align: center;
}
.Right{
  width: 15%;
  height:100vh;
  background-color: #e7e7e7;
  flex: 1;
}

</style>
