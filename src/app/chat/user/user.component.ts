import { Component, OnInit, ViewChild, ElementRef, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  @ViewChild("username") username: ElementRef;
  @Output() usernameSet = new EventEmitter<string>();

  setUsername(){
    this.usernameSet.emit(this.username.nativeElement.value);

  }

}
