
<template>
<div id ="app">
  <MenuBar />
  <router-view/>
</div>
</template>

<script>

import MenuBar from "./MenuBar.vue"

export default {
  name: "App",
  components: {
    MenuBar
  },
};
</script>
