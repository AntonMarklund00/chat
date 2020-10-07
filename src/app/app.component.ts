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
  list: any;
  @ViewChild("message") message: ElementRef;
  @ViewChild("name") name: ElementRef;


  constructor(private http: HttpClient, public webSocketService: WebSocketService) {
    this.getAllchat();
  }

  getAllchat(){

    this.http.get('get/start').subscribe(data => this.list = data);
  }

  ngOnInit() {
    this.webSocketService.openWebSocket();
  }
  ngOnDestroy(){
    this.webSocketService.closeWebSocket();
  }
  newMessage(){

    const chatMessageDto = new ChatMessageDto(this.name.nativeElement.value, this.message.nativeElement.value);
    this.webSocketService.sendMessage(chatMessageDto);
    let url = "/post/" + this.name.nativeElement.value + "/" + this.message.nativeElement.value;
    this.http.post(url, {}).subscribe();
  }

}
