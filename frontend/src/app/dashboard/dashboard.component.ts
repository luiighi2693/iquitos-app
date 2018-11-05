import { Component, OnInit } from '@angular/core';

declare function consoleLogMethod(): any;

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  sideNavDisplayFlag: boolean;

  constructor() {}

  ngOnInit() {
    this.sideNavDisplayFlag = false;
    consoleLogMethod();
  }

  changeSideNavDisplay() {
    this.sideNavDisplayFlag = !this.sideNavDisplayFlag;
  }
}
