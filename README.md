<h1 align="center">Chat2Research</h1>
<div align="center">
    这是一个智能的文献管理工具，支持多端管理，支持多端同步
</div>

[![License](https://img.shields.io/github/license/alibaba/fastjson2?color=4D7A97&logo=apache)](https://www.apache.org/licenses/LICENSE-2.0.html)

## 简介
&emsp; &emsp;Chat2Research 是一款开源免费的文案管理工具，支持个人部署/团队私有化部署 ，和传统的文献管理关键 endnote/zetero 等对比，页面更加清爽，同时集成了AI 读文献的能力，并且拥有
AIGC 交互能力，能够大大提高文献阅读能力 ，同时可以根据个人研究方向，构建自己的文献阅读库 。未来同时会支持 文献编写时的引用管理插件（
Microsoft Word，WPS 等）

## 特性
- 本地文献管理
- 智能解读，利用大模型能力
- 客户端使用 Electron + Spring 的方式进行管理

## 调试/开发环境
- java 17
- node 20
### 工程目录
- chat2research/chat2rs-client 前端工程
- chat2research/chat2rs-server 后端工程

- ### 前端本地调试
``` bash
$ git clone git@github.com:chat2research/chat2research.git
$ sh build.sh
$ cd chat2rs-client
$ npm run install
$ npm run build
$ npm run start:electron
```
- ### 后端本地调试
``` bash
cd chat2rs-server
mvn clean  package -B '-Dmaven.test.skip=true' -f  chat2rs-server/pom.xml
```

## 本项目借鉴了 AI-java ，chat2db 的部分 同大模型api交互的代码
## Star History
<a href="https://star-history.com/#chat2research/chat2research&Date">
  <picture>
    <source media="(prefers-color-scheme: dark)" srcset="https://api.star-history.com/svg?repos=chat2research/chat2research&type=Date&theme=dark" />
    <source media="(prefers-color-scheme: light)" srcset="https://api.star-history.com/svg?repos=chat2research/chat2research&type=Date" />
    <img alt="Star History Chart" src="https://api.star-history.com/svg?repos=chat2research/chat2research&type=Date" />
  </picture>
</a>