<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
  export default{
    name: 'App'
  }
</script>

<style>
  html, body, #app {
    /*全局配置布局*/
    padding: 0;
    margin: 0;
    background-color: #EFEFEF;
    height: 100%;
  }
</style>