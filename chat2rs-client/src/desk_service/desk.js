const { spawn } = require('child_process');
const axios = require('axios');

function exitDeskService() {
    axios.get('http://127.0.0.1:8000/exit')
        .then(response => {
            // 处理响应数据
            console.log('Response Data:', response.data);
        })
        .catch(error => {
            // 处理错误
            console.error('API request error:', error.message);
        });
}

function createDeskService(){

  const javaProcess = spawn('app/jre/bin/java', ['-jar', 'app/jar/rs-start-0.0.1-SNAPSHOT.jar']);

  //处理 Java 服务的输出
  javaProcess.stdout.on('data', (data) => {
    console.log(`Java Process Output: ${data}`);
    // 在此添加检测 Java 服务是否启动的逻辑
    // 例如，通过HTTP请求检测服务是否响应
  });

  //处理 Java 服务的错误
  javaProcess.stderr.on('data', (data) => {
    console.error(`Java Process Error: ${data}`);
    // 在此添加错误处理逻辑
  });
}

module.exports = {createDeskService,exitDeskService}