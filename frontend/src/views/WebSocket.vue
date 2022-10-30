<template>
  <div>
    <input type="text" v-model="content" />
    <button @click="sendfunc()">SEND</button>
    {{ content }}
  </div>
</template>

<script>
import { ref } from "vue";

import SockJS from "sockjs-client";
import Stomp from "stompjs";

export default {
  setup() {
    return {
      content: ref(""),
      socket: null,
      stompClient: null,
    };
  },
  created() {
    this.socket = new SockJS("http://localhost:8081/api/v1/ws");
    this.stompClient = Stomp.over(this.socket);
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe("/sub/1", (msg) => {
        const res = JSON.parse(msg.body);
        console.log(res);
      });
    });
  },
  methods: {
    sendfunc() {
      console.log("hi");
      const req = this.content;
      this.stompClient.send("/pub/1", {}, JSON.stringify(req));
    },
  },
};
</script>

<style></style>
