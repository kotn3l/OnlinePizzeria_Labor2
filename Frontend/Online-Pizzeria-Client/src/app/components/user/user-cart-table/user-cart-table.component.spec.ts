import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserCartTableComponent } from './user-cart-table.component';

describe('UserCartTableComponent', () => {
  let component: UserCartTableComponent;
  let fixture: ComponentFixture<UserCartTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserCartTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserCartTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
