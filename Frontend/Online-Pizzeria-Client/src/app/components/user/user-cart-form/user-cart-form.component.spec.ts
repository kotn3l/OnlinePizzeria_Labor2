import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserCartFormComponent } from './user-cart-form.component';

describe('UserCartFormComponent', () => {
  let component: UserCartFormComponent;
  let fixture: ComponentFixture<UserCartFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserCartFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserCartFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
