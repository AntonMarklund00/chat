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

  constructor(private http: HttpClient, public webSocketService: WebSocketService) {
    this.getAllchat();
  }

  ngOnInit(): void {}

  getAllchat(){
    this.http.get('get/start').subscribe(data => this.list = data);
  }
  newMessage(){
    const chatMessageDto = new ChatMessageDto(this.username, this.message.nativeElement.value);
    this.webSocketService.sendMessage(chatMessageDto);
    let url = "/post/" + this.username + "/" + this.message.nativeElement.value;
    this.http.post(url, {}).subscribe();
    this.message.nativeElement.value = "";
  }

}
