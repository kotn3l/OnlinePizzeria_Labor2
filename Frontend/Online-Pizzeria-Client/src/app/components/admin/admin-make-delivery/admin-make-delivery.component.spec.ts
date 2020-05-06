import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminMakeDeliveryComponent } from './admin-make-delivery.component';

describe('AdminMakeDeliveryComponent', () => {
  let component: AdminMakeDeliveryComponent;
  let fixture: ComponentFixture<AdminMakeDeliveryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminMakeDeliveryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminMakeDeliveryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
