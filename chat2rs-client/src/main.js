// Modules to control application life and create native browser window
const { app, BrowserWindow, Menu, dialog, ipcMain } = require('electron')
const path = require('node:path')
const fs = require('fs');
const { createDeskService, exitDeskService } = require('./desk_service/desk')

const createWindow = () => {
  // Create the browser window.
  const mainWindow = new BrowserWindow({
    width: 1000,
    height: 1000,
    webPreferences: {
      nodeIntegration: true,
      contextIsolation: true,
      enableRemoteModule: true,
      preload: path.join(__dirname, 'preload.js')
    }
  })

  // 打开文件
  const openFile = (mainWindow) => {
    dialog
      .showOpenDialog({ properties: ['openFile', 'multiSelections'] })
      .then((result) => {
        if (result?.canceled) return;
        const filePaths = result.filePaths ?? [];
        filePaths.forEach((filepath) => {
          const parseUrl = path.parse(filepath);
          const name = parseUrl.base;
          const file = {
            path: filepath,
            name,
            data: fs.readFileSync(filepath),
            type: 'arraybuffer'
          }
          mainWindow.webContents.send('change-file', file);
        })
      })
  }
  // 创建菜单
  const createMenu = () => {
    let menuTemplate = [
      {
        label: app.name,
        submenu: [
          { role: 'about' },
          { type: 'separator' },
          { role: 'services' },
          { type: 'separator' },
          { role: 'hide' },
          { role: 'hideOthers' },
          { role: 'unhide' },
          { type: 'separator' },
          { role: 'quit' }
        ]
      },
      {
        label: 'View',
        submenu: [
          { role: 'reload' },
          { role: 'forceReload' },
          { role: 'toggleDevTools' },
          { type: 'separator' },
          { role: 'resetZoom' },
          { role: 'zoomIn' },
          { role: 'zoomOut' },
          { type: 'separator' },
          { role: 'togglefullscreen' }
        ]
      },
      {
        label: "文件",
        submenu: [
          {
            label: "打开",
            click: async () => {
              openFile(mainWindow);
            }
          },
        ]
      },
    ];
    let menuBuilder = Menu.buildFromTemplate(menuTemplate);
    Menu.setApplicationMenu(menuBuilder);
  }
  createMenu();
  // 打开文件
  // and load the index.html of the app.
  mainWindow.loadFile('./build/index.html')
  // mainWindow.loadURL('http://localhost:3000/')
  // Open the DevTools.
  mainWindow.webContents.openDevTools()
}


app.whenReady().then(() => {
  createWindow();

  //启动桌面服务器
  createDeskService();

  app.on('window-all-closed', function () {
    if (process.platform !== 'darwin') app.quit();
  });

  app.on('quit', () => {
    exitDeskService();
  })

  app.on('activate', function () {
    // On macOS it's common to re-create a window in the app when the
    // dock icon is clicked and there are no other windows open.
    if (BrowserWindow.getAllWindows().length === 0) createWindow()
  })
})

// Quit when all windows are closed, except on macOS. There, it's common
// for applications and their menu bar to stay active until the user quits
// explicitly with Cmd + Q.
app.on('window-all-closed', function () {
  if (process.platform !== 'darwin') app.quit()
})

// In this file you can include the rest of your app's specific main process
// code. You can also put them in separate files and require them here.
