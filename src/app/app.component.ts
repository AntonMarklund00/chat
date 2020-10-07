import { Component, ViewChild, ElementRef } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {WebSocketService} from "./web-socket.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'chat-angular';
  list: any;
  @ViewChild("message") message: ElementRef;


  constructor(private http: HttpClient, public webSocketService: WebSocketService) {
    this.getAllchat();
  }

  getAllchat(){

    this.http.get('allChat').subscribe(data => this.list = data);
  }

  ngOnInit() {
    this.webSocketService.openWebSocket();
  }
  ngOnDestroy(){
    this.webSocketService.closeWebSocket();
  }
  newMessage(){
    let url = "/post/" + this.message.nativeElement.value;
    this.http.post(url, {}).subscribe();
    this.getAllchat();
  }

}
