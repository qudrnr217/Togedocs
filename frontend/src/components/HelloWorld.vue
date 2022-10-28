<template>
  <div class="hello">

    <h2>기본 api URL : {{apiURL}}</h2>
    <input id="getInput" v-model="apiGET" />
    <button @click="URLGET">get</button>

    <br><br>
    <input id="PostInput" v-model="apiPostURL" />
    <textarea id="postInput" v-model="PostValue"/>
    <button @click="URLPOST" >post</button>
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
      URLGET: function(){

        // var URL = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev?serviceKey=hMlb8qO%2FdgkfNg5%2BiWHftA4WzS0Jj0dSbzyx5o4A3%2BM59BudkGG0DnEPK9aCbbay%2BrHppszUbsesl6hRLZ8N6Q%3D%3D&pageNo=1&numOfRows=10&LAWD_CD=11110&DEAL_YMD=201512";
        // axios.get(
        //   URL
        // ).then((data)=>{console.log(data)});

        var URL = this.apiURL + this.apiGET;
        axios.get(URL).then((data)=>{console.log(data)});

      },
      URLPOST: function(){

        var URL = this.apiURL + this.apiPostURL;
        var PostJson = JSON.parse(this.PostValue);
        axios.post(URL, PostJson)
        .then(data => {
          console.log(data)
        })
        // console.log(URL);
        // console.log(this.PostValue);
      }
  },

  data(){
    return {
      apiURL : 'http://localhost:8080',
      apiGET : '/api/book/{userSeq}',
      apiPostURL : '/api/user/signup',
      PostValue : `{
  "userEmail": "string",
  "userId": "string",
  "userName": "string",
  "userNickname": "string",
  "userPassword": "string",
  "userPhone": "string"
}`
    }
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
  width : 200px;
  height: 200px;
}
</style>
