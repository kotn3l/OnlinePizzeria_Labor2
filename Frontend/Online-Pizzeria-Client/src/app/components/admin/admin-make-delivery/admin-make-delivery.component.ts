import { Component, OnInit } from '@angular/core';
import { DeliveryGuy } from 'src/app/models/DeliveryGuy';
import { DeliveryService } from 'src/app/services/delivery.service';
import { FlashMessagesService } from 'angular2-flash-messages';
import { OrderForDelivery } from 'src/app/models/OrderForDelivery';
import { OrderService } from 'src/app/services/order.service';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-make-delivery',
  templateUrl: './admin-make-delivery.component.html',
  styleUrls: ['./admin-make-delivery.component.css']
})
export class AdminMakeDeliveryComponent implements OnInit {
  deliveryGuys: DeliveryGuy[];
  orders: OrderForDelivery[];
  selectedGuy: number;
  sum: number = 0;
  selectedOrders: number[] = [];

  constructor(
    private deliveryService: DeliveryService,
    private orderService: OrderService,
    private authService: AuthService,
    private flashMessage: FlashMessagesService,
    private router: Router
  ) { }

  ngOnInit() {
    this.deliveryService.getDeliveryGuys().subscribe(res => this.deliveryGuys = res, err => {
      this.flashMessage.show(err, { cssClass: 'alert-danger', timeout: 4000 });
    });

    this.orderService.getReadyOrders().subscribe(orders => this.orders = orders, err => {
      this.flashMessage.show(err, { cssClass: 'alert-danger', timeout: 4000 });
    });
    this.selectedGuy = null;
  }

  onSelect(id: number) {
    this.selectedGuy = id;
  }

  onCheck(id: number, sum: number) {
    if (this.selectedOrders.includes(id)) {
      this.selectedOrders.splice(this.selectedOrders.indexOf(id), 1);
      this.sum -= sum;
    }
    else {
      this.selectedOrders.push(id);
      this.sum += sum;
    }
  }

  sumColor() {
    if (this.sum < 8) {
      return 'text-warning';
    }
    else if (this.sum > 10) {
      return 'text-danger';
    }
    else {
      return 'text-success';
    }
  }

  submitOrder() {
    this.deliveryService.postDelivery(this.selectedGuy, this.selectedOrders).subscribe(next => {
      this.flashMessage.show('Delivery succesfully posted', { cssClass: 'alert-success', timeout: 4000 });
      this.router.onSameUrlNavigation = 'reload';
      this.router.navigate(['/admin/login']);
    }, err => {
      var text: string = '';
      for (const key in err.error.error) {
        if (err.error.error.hasOwnProperty(key)) {
          text += err.error.error[key] + "<br />";
        }
      }
      this.flashMessage.show(text, { cssClass: 'alert-danger', timeout: 4000 });
    })
  }
}
