import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserPizzaViewComponent } from './user-pizza-view.component';

describe('UserPizzaViewComponent', () => {
  let component: UserPizzaViewComponent;
  let fixture: ComponentFixture<UserPizzaViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserPizzaViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserPizzaViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
