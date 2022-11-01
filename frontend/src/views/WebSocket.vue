<template>
  <div>
    {{ userName }}
    <button @click="refreshReq()">SEND</button>
    <br />
    <input type="text" v-model="focus.rowId" /> <br />
    <input type="text" v-model="focus.colId" /> <br />
    <button @click="focusChange()">FOCUS</button>
  </div>
</template>

<script>
import { ref } from "vue";
import { BASEURL } from "@/api/index.js";

import SockJS from "sockjs-client";
import Stomp from "stompjs";

export default {
  setup() {
    return {
      socket: null,
      stompClient: null,

      project_id: ref("20221031001"), // 프로젝트 선택 구현 후 가져올 것.
      userName: ref(null), // 로그인 구현 후 가져올 것.
      focus: ref({ rowId: "r", colId: "c" }),
    };
  },
  mounted() {
    this.userName = Math.round(Math.random() * 1000);

    this.socket = new SockJS(BASEURL + "/ws");
    this.stompClient = Stomp.over(this.socket);
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe(
        "/sub/" + this.project_id + "/refresh",
        (msg) => {
          var res = JSON.parse(msg.body);
          // 내가 보낸 메세지라면 return
          if (res.userName == this.userName) return;
          // 다른 사람이 보낸 메세지라면 계속

          // TODO: Refresh
          console.log("다른 사용자가 REFRESH 요청을 보냈습니다.");
          // --
        }
      );
      this.stompClient.subscribe(
        "/sub/" + this.project_id + "/focus",
        (msg) => {
          var res = JSON.parse(msg.body);
          // 내가 보낸 메세지라면 return
          if (res.userName == this.userName) return;
          // 다른 사람이 보낸 메세지라면 계속

          // TODO: Focus
          // 다른 사람의 포커스 위치를 옮겨줌. (내껀 내 프론트에서만 보여줌)
          console.log(
            res.userName,
            "사용자가 FOCUS를 ",
            res.content,
            "로 변경하였습니다."
          );
          // --
        }
      );
    });
  },
  beforeUnmount() {
    this.stompClient.disconnect();
  },
  methods: {
    refreshReq() {
      const req = { userName: this.userName, content: null };
      this.stompClient.send(
        "/pub/" + this.project_id + "/refresh",
        {},
        JSON.stringify(req)
      );
    },
    focusChange() {
      const req = {
        userName: this.userName,
        content: JSON.stringify(this.focus),
      };
      this.stompClient.send(
        "/pub/" + this.project_id + "/focus",
        {},
        JSON.stringify(req)
      );
    },
  },
};
</script>

<style></style>
