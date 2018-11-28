import {EventEmitter, Injectable, Output} from '@angular/core';

export interface ListContent {
  value: string;
  uri: string;
}

@Injectable({
  providedIn: 'root'
})
export class FullService {

  @Output() change: EventEmitter<string> = new EventEmitter();
  @Output() changeContent: EventEmitter<ListContent[]> = new EventEmitter();

  constructor() { }

  changeMenuSelected(menuSelected) {
    this.change.emit(menuSelected);
  }

  changeMenu(menu) {
    this.changeContent.emit(menu);
  }
}
