import { Component, OnInit, ViewChild } from '@angular/core';

import { FormDropdown } from '../../../models/FormDropdown';
import { Order } from '../../../models/Order';
import { UtilityService } from '../../../services/utility.service';
import { CartService } from '../../../services/cart.service';
import { OrderService } from '../../../services/order.service';
import { FlashMessagesService } from 'angular2-flash-messages';

@Component({
  selector: 'app-user-cart-form',
  templateUrl: './user-cart-form.component.html',
  styleUrls: ['./user-cart-form.component.css']
})
export class UserCartFormComponent implements OnInit {

  payMethods: FormDropdown[];
  cities: FormDropdown[];

  currentOrder: Order = {
    name: '',
    email: '',
    telephone: '',
    city: '1',
    street: '',
    house_number: '',
    other: '',
    comment: '',
    pay_method: '',
    order: []
  }

  @ViewChild('orderForm', { static: false }) form: any;

  constructor(
    private utilityService: UtilityService,
    private cartService: CartService,
    private orderService: OrderService,
    private flashMessage: FlashMessagesService
  ) { }

  ngOnInit() {
    this.utilityService.getPayMethods().subscribe(methods => this.payMethods = methods);
    this.utilityService.getCities().subscribe(cities => this.cities = cities);
  }

  onSubmit({ value, valid }: { value: Order, valid: boolean }) {
    if (!valid) {
      this.flashMessage.show('Form is not valid. Please check it again!', { cssClass: 'alert-warning', timeout: 8000 })
    } else {
      value.order = this.cartService.getItemIdsForOrder();

      this.orderService.postOrder(value).subscribe(
        next => {
          this.cartService.clearCart();
          this.form.reset();

          this.currentOrder = {
            name: '',
            email: '',
            telephone: '',
            city: '1',
            street: '',
            house_number: '',
            other: '',
            comment: '',
            pay_method: '',
            order: []
          }

          this.flashMessage.show('Order posted succesfully', { cssClass: 'alert-success', timeout: 8000 });
        },
        err => {
          var responseMessage: string = '';
          for (var key in err.error) {
            responseMessage += err.error[key] + "<br />";
          }

          this.flashMessage.show(responseMessage, { cssClass: 'alert-warning', timeout: 8000 })
        }
      );
    }
  }
}
