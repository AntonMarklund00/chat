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

  /*constructor() { }

  public openWebSocket(){
    const client = new WebSocket("ws://localhost:8080/ws")
    this.webSocket = client;
    this.webSocket.onopen = (event => {

    });

    this.webSocket.onmessage = (event => {
      if(!event.data.startsWith('{"')) {
        const chatMassageDtoAdmin = new ChatMessageDto("Admin", event.data);
        this.chatMessage.push(chatMassageDtoAdmin);
      }
      const chatMessageDto = JSON.parse(event.data);
      this.chatMessage.push(chatMessageDto);
    });

    this.webSocket.onclose = (event => {
      console.log("Close: ", event);
    });

  }

  public sendMessage(chatMessageDto:ChatMessageDto){
    this.webSocket.send(JSON.stringify(chatMessageDto));

  }

  public closeWebSocket(){
    this.webSocket.close();
  }*/


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
