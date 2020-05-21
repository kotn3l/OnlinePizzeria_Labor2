import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminPizzaFormComponent } from './admin-pizza-form.component';

describe('AdminPizzaFormComponent', () => {
  let component: AdminPizzaFormComponent;
  let fixture: ComponentFixture<AdminPizzaFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminPizzaFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminPizzaFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
