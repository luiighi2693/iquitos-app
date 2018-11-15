import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProviderUpdateComponent } from './provider-update.component';

describe('ProviderUpdateComponent', () => {
  let component: ProviderUpdateComponent;
  let fixture: ComponentFixture<ProviderUpdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProviderUpdateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProviderUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
