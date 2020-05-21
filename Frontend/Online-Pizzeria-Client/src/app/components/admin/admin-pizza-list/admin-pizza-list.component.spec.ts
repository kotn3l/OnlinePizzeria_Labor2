import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminPizzaListComponent } from './admin-pizza-list.component';

describe('AdminPizzaListComponent', () => {
  let component: AdminPizzaListComponent;
  let fixture: ComponentFixture<AdminPizzaListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminPizzaListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminPizzaListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
