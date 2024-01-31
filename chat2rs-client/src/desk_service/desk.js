const { spawn } = require('child_process');
const axios = require('axios');

function exitDeskService() {
    axios.get('http://127.0.0.1:10086/exit')
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

    const jarPath = 'app/jar/chat2research.jar';

    const javaArgs = [
        '-jar', // 指定要执行的 JAR 文件
        '-Dchat2rs.version=1',
        jarPath // JAR 文件的路径
    ];


    const javaProcess = spawn('app/jre/bin/java', javaArgs);

    //处理 Java 服务的输出
  javaProcess.stdout.on('data', (data) => {
    console.log(`Java Process Output: ${data}`);
  });

  //处理 Java 服务的错误
  javaProcess.stderr.on('data', (data) => {
    console.error(`Java Process Error: ${data}`);
  });
}

module.exports = {createDeskService,exitDeskService}