"use strict";
import jwt_decode from "jwt-decode";
import { app, protocol, BrowserWindow } from "electron";
import { createProtocol } from "vue-cli-plugin-electron-builder/lib";
import installExtension, { VUEJS3_DEVTOOLS } from "electron-devtools-installer";
// import { access } from "original-fs";
const isDevelopment = process.env.NODE_ENV !== "production";

let win;
async function createWindow() {
  // Create the browser window.
  win = new BrowserWindow({
    width: 1200,
    height: 900,
    webPreferences: {
      // Use pluginOptions.nodeIntegration, leave this alone
      // See nklayman.github.io/vue-cli-plugin-electron-builder/guide/security.html#node-integration for more info
      nodeIntegration: process.env.ELECTRON_NODE_INTEGRATION,
      contextIsolation: !process.env.ELECTRON_NODE_INTEGRATION,
      webSecurity: false,
    },
    autoHideMenuBar: true,
  });

  if (process.env.WEBPACK_DEV_SERVER_URL) {
    // Load the url of the dev server if in development mode
    await win.loadURL(process.env.WEBPACK_DEV_SERVER_URL);
    if (!process.env.IS_TEST) {
      //win.webContents.openDevTools()
    }
  } else {
    createProtocol("app");
    // Load the index.html when not in development
    win.loadURL("app://./index.html");
  }

  win.setIcon("./src/assets/thumbnail.png");

  // CORS 에러 해결
  win.webContents.session.webRequest.onBeforeSendHeaders(
    (details, callback) => {
      callback({ requestHeaders: { Origin: "*", ...details.requestHeaders } });
    }
  );

  win.webContents.session.webRequest.onHeadersReceived((details, callback) => {
    callback({
      responseHeaders: {
        "Access-Control-Allow-Origin": ["*"],
        ...details.responseHeaders,
      },
    });
  });
}

// Quit when all windows are closed.
app.on("window-all-closed", () => {
  // On macOS it is common for applications and their menu bar
  // to stay active until the user quits explicitly with Cmd + Q
  if (process.platform !== "darwin") {
    app.quit();
  }
});

app.on("activate", () => {
  // On macOS it's common to re-create a window in the app when the
  // dock icon is clicked and there are no other windows open.
  if (BrowserWindow.getAllWindows().length === 0) createWindow();
  // CORS 에러 해결
  win.webContents.session.webRequest.onBeforeSendHeaders(
    (details, callback) => {
      callback({ requestHeaders: { Origin: "*", ...details.requestHeaders } });
    }
  );

  win.webContents.session.webRequest.onHeadersReceived((details, callback) => {
    callback({
      responseHeaders: {
        "Access-Control-Allow-Origin": ["*"],
        ...details.responseHeaders,
      },
    });
  });
});

// This method will be called when Electron has finished
// initialization and is ready to create browser windows.
// Some APIs can only be used after this event occurs.
app.on("ready", async () => {
  if (isDevelopment && !process.env.IS_TEST) {
    // Install Vue Devtools
    try {
      await installExtension(VUEJS3_DEVTOOLS);
    } catch (e) {
      console.error("Vue Devtools failed to install:", e.toString());
    }
  }
  createWindow();
});

// Exit cleanly on request from parent process in development mode.
if (isDevelopment) {
  if (process.platform === "win32") {
    process.on("message", data => {
      if (data === "graceful-exit") {
        app.quit();
      }
    });
  } else {
    process.on("SIGTERM", () => {
      app.quit();
    });
  }
}

if (!app.isDefaultProtocolClient("myapp")) {
  // Define custom protocol handler. Deep linking works on packaged versions of the application!
  app.setAsDefaultProtocolClient("myapp");
}

app.on("will-finish-launching", function () {
  // Protocol handler for osx
  app.on("open-url", function (event, url) {
    event.preventDefault();
    deeplinkingUrl = url;

    // logEverywhere('open-url# ' + deeplinkingUrl)
    logEverywhere("open-url# " + url);
  });
});

// Deep linked url
let deeplinkingUrl;
let info;
let info2;
let accesstoken;
let accesstoken2;
let refreshtoken;
let userInfo;
let exp;
const gotTheLock = app.requestSingleInstanceLock();
if (gotTheLock) {
  app.on("second-instance", (e, argv) => {
    // Someone tried to run a second instance, we should focus our window.
    asyncTest(argv);
  });
} else {
  app.quit();
}

function asyncTest(argv) {
  if (process.platform == "win32") {
    // Keep only command line / deep linked arguments
    deeplinkingUrl = argv.slice(1);
    info = deeplinkingUrl[1].split(",");
    info2 = info[0].split("token=");
    accesstoken = info2[2];
    accesstoken2 = info[1].split("%20refreshToken=");
    refreshtoken = accesstoken2[1].substring(0, accesstoken2[1].length - 1);
    userInfo = jwt_decode(accesstoken);
    exp = userInfo.exp;
    win.webContents.executeJavaScript(`console.log(window.location.href)`);
    // logEverywhere("accesstoken: " + accesstoken);
    // logEverywhere("refreshtoken: " + refreshtoken);
    // logEverywhere("refreshtoken: " + exp);
    win.webContents.executeJavaScript(
      `window.localStorage.setItem('accessToken','${accesstoken}')`
    );
    win.webContents.executeJavaScript(
      `window.localStorage.setItem('refreshToken','${refreshtoken}')`
    );
    win.webContents.executeJavaScript(
      `window.localStorage.setItem('exp','${exp}')`
    );
    win.webContents.executeJavaScript(
      `window.dispatchEvent(new CustomEvent('login-successful'))`
    );
  }
  // logEverywhere('app.makeSingleInstance# '+accesstoken[2])

  if (win) {
    // window.localStorage.setItem("accessToken",store.get("accessToken"))
    if (win.isMinimized()) win.restore();
    win.focus();
  }
}

if (process.platform == "win32") {
  // Keep only command line / deep linked arguments
  deeplinkingUrl = process.argv.slice(1);
}
logEverywhere("createWindow# " + deeplinkingUrl);

// Scheme must be registered before the app is ready
protocol.registerSchemesAsPrivileged([
  { scheme: "app", privileges: { secure: true, standard: true } },
]);

// Log both at dev console and at running node console instance
function logEverywhere(s) {
  if (win && win.webContents) {
    win.webContents.executeJavaScript(`console.log("${s}")`);
  }
}
