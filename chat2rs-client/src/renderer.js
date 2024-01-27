/**
 * This file is loaded via the <script> tag in the index.html file and will
 * be executed in the renderer process for that window. No Node.js APIs are
 * available in this process because `nodeIntegration` is turned off and
 * `contextIsolation` is turned on. Use the contextBridge API in `preload.js`
 * to expose Node.js functionality from the main process.
 */

// 在渲染进程中的某个页面的 JavaScript 文件中

// 添加一个事件监听器，监听文件拖入事件
document.addEventListener('drop', (event) => {
    event.preventDefault(); // 阻止默认行为，避免浏览器打开文件

    // 获取拖入的文件列表
    const files = event.dataTransfer.files;

    // 处理拖入的文件
    for (const file of files) {
        console.log('拖入的文件路径：', file.path);
        // 在这里可以处理拖入的文件，例如读取文件内容、显示文件信息等
    }
});

// 阻止拖入文件时打开文件的默认行为
document.addEventListener('dragover', (event) => {
    event.preventDefault();
});