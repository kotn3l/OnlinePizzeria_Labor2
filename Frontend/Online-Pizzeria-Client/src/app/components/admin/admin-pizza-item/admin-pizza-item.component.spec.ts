import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminPizzaItemComponent } from './admin-pizza-item.component';

describe('AdminPizzaItemComponent', () => {
  let component: AdminPizzaItemComponent;
  let fixture: ComponentFixture<AdminPizzaItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminPizzaItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminPizzaItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
