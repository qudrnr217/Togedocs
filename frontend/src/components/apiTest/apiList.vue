<template>
  <div>
    <div class="apiListAll">
        <div>api 목록</div>

        <div class="apiListDetail" @click="apiListDetail(index)" v-for="(item, index) in $store.state.apiStoreList" v-bind:key="index">{{item.type}}  {{item.name}}</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "apiList",
  data(){
    return {
        apiList:[],
    }
  },
  mounted(){
    //response.data.gc[i].count;
    var Item1 = new Object();
    Item1.type = "POST";
    Item1.name = "로그인";
    Item1.Header=`{
  "Authorization" : "token"
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
  "Authorization" : "token"
}`;
    Item2.PathVariable='/2';
    Item2.Params = '';
    Item2.RequestBody = '';
    Item2.RequestURL = 'https://k7a404.p.ssafy.io/api/book';
    
    var Item3 = new Object();
    Item3.type = "POST";
    Item3.name = "회원가입";
    Item3.Header=`{
  "Authorization" : "token"
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

    this.apiList = [Item1, Item2, Item3];
    this.$store.state.apiStoreList = this.apiList;


  },
  methods:{
    apiListDetail(index){
        this.$router.push({name: 'Query', query: {key : index}})
        console.log(this.$store.state.apiStoreList[index]);
    }
  }
};
</script>

<style scoped>
.apiListAll{
    white-space:nowrap;
}
.apiListDetail{
    cursor: pointer;
}

</style>
