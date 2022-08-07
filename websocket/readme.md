### websocket
WebSocket 是 HTML5 开始提供的一种在单个 TCP 连接上进行全双工通讯的协议。\
WebSocket 使得客户端和服务器之间的数据交换变得更加简单，允许服务端主动向客户端推送数据。在 WebSocket API 中，浏览器和服务器只需要完成一次握手，两者之间就直接可以创建持久性的连接，并进行双向数据传输。

### stomp：Simple (or Streaming) Text Orientated Messaging Protocol
简单(流)文本定向消息协议，它提供了一个可互操作的连接格式，允许STOMP客户端与任意STOMP消息代理（Broker）进行交互。STOMP协议由于设计简单，易于开发客户端，因此在多种语言和多种平台上得到广泛地应用。

#### 为什么需要STOMP
直接使用 WebSocket（SockJS） 就很类似于 使用 TCP 套接字来编写 web 应用，因为没有高层协议，就需要我们定义应用间所发送消息的语义，还需要确保连接的两端都能遵循这些语义；\
因此，可以在 WebSocket 之上使用 STOMP协议，来为浏览器 和 server间的 通信增加适当的消息语义。

#### frame
STOMP在websocket上提供了一中基于帧线路格式（frame-based wire format）。


### 参考
http://t.zoukankan.com/ll409546297-p-10655392.html
