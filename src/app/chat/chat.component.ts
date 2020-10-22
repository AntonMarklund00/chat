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

  input;
  constructor(private http: HttpClient, public messageService: WebSocketService) {}
  sendMessage() {
    if (this.input) {
      this.messageService.sendMessage(this.username, this.input);
      this.input = '';
      //let url = "/post/" + this.username + "/" + this.message.nativeElement.value;
      //this.http.post(url, {}).subscribe();
    }
  }

  ngOnInit(): void {}

  getAllchat(){
    this.http.get('get/start').subscribe(data => this.list = data);
  }


}
