const { defineConfig } = require("@vue/cli-service");

module.exports = defineConfig({
  transpileDependencies: ["quasar"],
  pluginOptions: {
    electronBuilder: {
      builderOptions: {
        win: {
          icon: "./thumbnail.png",
        },
      },
      nodeIntegration: true,
    },
    quasar: {
      importStrategy: "kebab",
      rtlSupport: false,
    },
  },
});
