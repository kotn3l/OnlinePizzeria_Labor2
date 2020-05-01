import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminDeliveryListComponent } from './admin-delivery-list.component';

describe('AdminDeliveryListComponent', () => {
  let component: AdminDeliveryListComponent;
  let fixture: ComponentFixture<AdminDeliveryListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminDeliveryListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminDeliveryListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
