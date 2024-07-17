const { defineConfig } = require('@vue/cli-service');
module.exports = defineConfig({
    transpileDependencies: true,
    lintOnSave: false, // 关闭 ESLint 校验
    assetsDir: 'static', // 静态资源目录
    parallel: false, // 是否使用多进程构建
    publicPath: './', // 公共路径，通常设置为根目录或相对路径
});

