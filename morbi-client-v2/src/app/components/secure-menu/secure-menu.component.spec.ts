import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SecureMenuComponent } from './secure-menu.component';

describe('SecureMenuComponent', () => {
  let component: SecureMenuComponent;
  let fixture: ComponentFixture<SecureMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SecureMenuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SecureMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
