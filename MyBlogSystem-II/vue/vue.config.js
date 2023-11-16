const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave : false, // 关闭 ES6 校验

  /* vue项目打包配置文件 */
  assetsDir: 'static',
  parallel: false,
  publicPath: './'
})
