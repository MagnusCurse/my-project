const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,

  /* vue项目打包配置文件 */
  assetsDir: 'static',
  parallel: false,
  publicPath: './',

  devServer: {
    // 跨域配置
    proxy: {
      '/api': {
        target: 'http://localhost:8081', // 接口域名
        changeOrigin: true, // 是否跨域
        pathRewrite: {
          '^/api': '', // 将 '/api' 替换为空
        },
      },
    }
  }
})
