import { Component, OnInit, ViewChild, ElementRef, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {}

  @ViewChild("username") username: ElementRef;
  @ViewChild("room") room: ElementRef;

  @Output() usernameSet = new EventEmitter<string>();

  setUsername(){
    if(this.username.nativeElement.value != "" || null || undefined){
      this.usernameSet.emit(this.username.nativeElement.value);
      sessionStorage.setItem("room", this.room.nativeElement.value);
    }
  }

}
