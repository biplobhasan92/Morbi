import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegSuccessPopupComponent } from './reg-success-popup.component';

describe('RegSuccessPopupComponent', () => {
  let component: RegSuccessPopupComponent;
  let fixture: ComponentFixture<RegSuccessPopupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegSuccessPopupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegSuccessPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
