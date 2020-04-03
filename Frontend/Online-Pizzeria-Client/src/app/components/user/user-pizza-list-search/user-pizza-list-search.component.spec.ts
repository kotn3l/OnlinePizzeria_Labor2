import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserPizzaListSearchComponent } from './user-pizza-list-search.component';

describe('UserPizzaListSearchComponent', () => {
  let component: UserPizzaListSearchComponent;
  let fixture: ComponentFixture<UserPizzaListSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserPizzaListSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserPizzaListSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
