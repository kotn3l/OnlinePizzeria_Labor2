import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminPrepareOrderComponent } from './admin-prepare-order.component';

describe('AdminPrepareOrderComponent', () => {
  let component: AdminPrepareOrderComponent;
  let fixture: ComponentFixture<AdminPrepareOrderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminPrepareOrderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminPrepareOrderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
