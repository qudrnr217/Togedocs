<template>
  <div class="hello">
    <button @click="URLGO">asdf</button>
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
      URLGO: function(){

        const URL = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev?serviceKey=hMlb8qO%2FdgkfNg5%2BiWHftA4WzS0Jj0dSbzyx5o4A3%2BM59BudkGG0DnEPK9aCbbay%2BrHppszUbsesl6hRLZ8N6Q%3D%3D&pageNo=1&numOfRows=10&LAWD_CD=11110&DEAL_YMD=201512";
        axios.get(
          URL
        ).then((data)=>{console.log(data)});
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
</style>
