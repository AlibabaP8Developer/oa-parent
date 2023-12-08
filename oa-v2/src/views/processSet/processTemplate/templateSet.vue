<template>
  <div class="app-container">
    <div id="app1">
      <fc-designer class="form-build" ref="designer"/>
      <el-button @click="save">获取数据</el-button>
    </div>
  </div>
</template>

<script>

export default {
  data() {
    return {
    }
  },

  created() {

  },

  methods: {
    save() {
      console.log(this.$refs.designer.getRule())
      console.log(this.$refs.designer.getOption())
    }
  }
}
</script>

<style scoped lang="scss">

</style>
