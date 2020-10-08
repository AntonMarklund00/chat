import { Component, ViewChild, ElementRef } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {WebSocketService} from "./web-socket.service";
import {ChatMessageDto} from "./chatMessageDto";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'chat-angular';
  openChat: boolean = false;
  username: string;
  constructor(public webSocketService: WebSocketService) {}

  ngOnInit() {

    this.webSocketService.openWebSocket();
  }
  ngOnDestroy(){
    this.webSocketService.closeWebSocket();
    this.openChat = false;
  }

  usernameSet(username: string){
    this.username = username;
    this.openChat = true;
  }

}
