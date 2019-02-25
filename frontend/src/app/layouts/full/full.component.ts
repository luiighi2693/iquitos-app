import * as $ from 'jquery';
import { MediaMatcher } from '@angular/cdk/layout';
import { Router } from '@angular/router';
import {
  ChangeDetectorRef,
  Component,
  NgZone,
  OnDestroy,
  ViewChild,
  HostListener,
  Directive,
  AfterViewInit
} from '@angular/core';
import { MenuItems } from '../../shared/menu-items/menu-items';
import { AppHeaderComponent } from './header/header.component';
import { AppSidebarComponent } from './sidebar/sidebar.component';

import { PerfectScrollbarConfigInterface } from 'ngx-perfect-scrollbar';
import {FullService} from './full.service';


export interface ListContent {
  value: string;
  uri: string;
}

/** @title Responsive sidenav */
@Component({
  selector: 'app-full-layout',
  templateUrl: 'full.component.html',
  styleUrls:  ['./full.component.scss']
})
export class FullComponent implements OnDestroy, AfterViewInit {
  mobileQuery: MediaQueryList;
  dir = 'ltr';
  green: boolean;
  blue: boolean;
  dark: boolean;
  minisidebar: boolean;
  boxed: boolean;
  danger: boolean;
  showHide: boolean;
  sidebarOpened;
  actualPath = null;

  public config: PerfectScrollbarConfigInterface = {};
  private _mobileQueryListener: () => void;

  contentList: ListContent[];
  contentSelected = '';

  constructor(
    private router: Router,
    changeDetectorRef: ChangeDetectorRef,
    media: MediaMatcher,
    public menuItems: MenuItems,
    public fullService: FullService
  ) {
    this.mobileQuery = media.matchMedia('(min-width: 768px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);

    this.fullService.changeContent.subscribe(menu => {
      this.contentList = menu;
    });

    this.fullService.change.subscribe(menuSelected => {
      this.contentSelected = menuSelected;
      // this.actualPath = this.router.url;
      // if(this.actualPath === '/ventas/sell') {
      //   (<any>$('#toggleMenu')).click();
      // }
    });

  }

  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }
  ngAfterViewInit() {
    // This is for the topbar search
    (<any>$('.srh-btn, .cl-srh-btn')).on('click', function() {
      (<any>$('.app-search')).toggle(200);
    });
    // This is for the megamenu
  }

  // Mini sidebar
  goTo(content: ListContent) {
    this.contentSelected = content.value;
    this.router.navigate([content.uri], { replaceUrl: true });
  }

  findMenu($event: any) {
    return this.contentList[this.contentList.map(x => x.value).indexOf($event)];
  }

  onResizeCustom(event: any) {
    console.log('here')
  }
}
