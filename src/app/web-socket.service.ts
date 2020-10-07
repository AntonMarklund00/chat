import { Injectable } from '@angular/core';
import {ChatMessageDto} from "./chatMessageDto";

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  webSocket: WebSocket;
  chatMessage: ChatMessageDto[] = [];

  constructor() { }

  public openWebSocket(){
    const client = new WebSocket("ws://localhost:8080/ws")
    this.webSocket = client;
    this.webSocket.onopen = (event => {
      console.log("Open: ", event);
    });

    this.webSocket.onmessage = (event => {
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
  }


}
