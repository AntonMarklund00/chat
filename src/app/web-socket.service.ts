import { Injectable } from '@angular/core';
import {ChatMessageDto} from "./chatMessageDto";

declare var SockJS;
declare var Stomp;
@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  webSocket: WebSocket;
  chatMessage: ChatMessageDto[] = [];

  constructor() {
    this.initializeWebSocketConnection();


  }
  public stompClient;
  public msg = [];
  initializeWebSocketConnection() {
    const serverUrl = 'http://localhost:8080/socket';
    const ws = new SockJS(serverUrl);
    this.stompClient = Stomp.over(ws);
    const that = this;

    this.stompClient.connect({}, function(frame) {
      that.stompClient.subscribe('/message', (message) => {
        if (message.body) {
          that.msg.push(message.body);
        }
      });
    });
  }

  sendMessage(name:string, message:string) {
    const dto = new ChatMessageDto(name, message)
    console.log(JSON.stringify(dto))
    this.stompClient.send("/app/send/message/", {}, JSON.stringify(dto));
  }


}
