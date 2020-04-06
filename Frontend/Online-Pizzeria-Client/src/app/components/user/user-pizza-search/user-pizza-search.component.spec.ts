import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserPizzaSearchComponent } from './user-pizza-search.component';

describe('UserPizzaSearchComponent', () => {
  let component: UserPizzaSearchComponent;
  let fixture: ComponentFixture<UserPizzaSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserPizzaSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserPizzaSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
