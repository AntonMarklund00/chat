import { Component, ViewChild, ElementRef } from '@angular/core';
import {WebSocketService} from "./web-socket.service";

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

  usernameSet(username: string){
    this.username = username;
    this.openChat = true;
  }

}
