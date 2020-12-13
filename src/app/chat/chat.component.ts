import {Component, ElementRef, Input, OnInit, Output, ViewChild, EventEmitter} from '@angular/core';
import {ChatMessageDto} from "../chatMessageDto";
import {WebSocketService} from "../web-socket.service";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  list: any;
  @ViewChild("message") message: ElementRef;
  @Input() username: string;
  sesList:ChatMessageDto[] = this.messageService.msg;
  input;
  constructor(private http: HttpClient, public messageService: WebSocketService) {
    this.getAllchat();

  }
  sendMessage() {
    if (this.input) {
      this.messageService.sendMessage(this.username, this.input, sessionStorage.getItem("room"));
      this.input = '';
    }
  }
  ngOnInit(): void {
    this.messageService.initializeWebSocketConnection();

  }

  getAllchat(){
    this.http.get('get/start').subscribe(data => this.list = data);
  }


}
