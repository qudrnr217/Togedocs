<template>
  <q-card style="width: 350px">
    <q-card-section class="row items-center no-wrap">
      <div>{{ msg }}</div>
      <q-space />
    </q-card-section>
  </q-card>
</template>

<script>
export default {
  props: {
    msg: String,
  },
};
</script>

<style></style>
