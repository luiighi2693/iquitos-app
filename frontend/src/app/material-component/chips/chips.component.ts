import {Component, OnInit} from '@angular/core';
import { MatChipInputEvent } from '@angular/material';
import { ENTER, COMMA } from '@angular/cdk/keycodes';
import {FormControl} from '@angular/forms';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
export interface DemoColor {
  name: string;
  color: string;
}

export interface User {
  name: string;
}

@Component({
  selector: 'app-chips',
  templateUrl: './chips.component.html',
  styleUrls: ['./chips.component.scss']
})
export class ChipsComponent implements OnInit {
  visible = true;
  selectable = true;
  removable = true;
  addOnBlur = true;

  // Enter, comma
  separatorKeysCodes = [ENTER, COMMA];

  fruits = [{ name: 'Lemon' }, { name: 'Lime' }, { name: 'Apple' }];

  availableColors: DemoColor[] = [
    { name: 'none', color: 'gray' },
    { name: 'Primary', color: 'primary' },
    { name: 'Accent', color: 'accent' },
    { name: 'Warn', color: 'warn' }
  ];

  myControl = new FormControl();
  options1: User[] = [{ name: 'Mary' }, { name: 'Shelley' }, { name: 'Igor' }];
  filteredOptions: Observable<User[]>;

  valueInput = '';

  displayFn(user?: User): string | undefined {
    return user ? user.name : undefined;
  }

  add(event: MatChipInputEvent): void {
    const input = event.input;
    // const value = event.value;

    // Add our fruit
    // if ((value || '').trim()) {
    //   this.fruits.push({ name: value.trim() });
    // }

    // Reset the input value
    if (input) {
      input.value = '';
    }
  }

  remove(fruit: any): void {
    const index = this.fruits.indexOf(fruit);

    if (index >= 0) {
      this.fruits.splice(index, 1);
    }
  }

  ngOnInit(): void {
    this.filteredOptions = this.myControl.valueChanges.pipe(
      startWith<string | User>(''),
      map(value => (typeof value === 'string' ? value : value.name)),
      map(name => (name ? this._filter(name) : this.options1.slice()))
    );
  }

  private _filter(name: string): User[] {
    const filterValue = name.toLowerCase();

    return this.options1.filter(
      option => option.name.toLowerCase().indexOf(filterValue) === 0
    );
  }

  addValue(user: User) {
    console.log(this.valueInput);
    if ((user.name || '').trim()) {
      this.fruits.push({ name: user.name.trim() });
    }
  }
}
