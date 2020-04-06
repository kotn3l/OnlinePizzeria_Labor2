import { Component, OnInit, ViewChild } from '@angular/core';

import { FormDropdown } from '../../../models/FormDropdown';
import { Order } from '../../../models/Order';
import { UtilityService } from '../../../services/utility.service';
import { CartService } from '../../../services/cart.service';
import { OrderService } from '../../../services/order.service';

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

  orderPosted: boolean = false;
  orderSucces: boolean = false;
  orderFail: boolean = false;
  responseMessage: string[] = [];

  @ViewChild('orderForm', { static: false }) form: any;

  constructor(
    private utilityService: UtilityService,
    private cartService: CartService,
    private orderService: OrderService
  ) { }

  ngOnInit() {
    this.utilityService.getPayMethods().subscribe(methods => this.payMethods = methods);
    this.utilityService.getCities().subscribe(cities => this.cities = cities);
  }

  onSubmit({ value, valid }: { value: Order, valid: boolean }) {
    if (!valid) {
      console.log('Form is not valid');
    } else {
      value.order = this.cartService.getItemIdsForOrder();

      this.orderService.postOrder(value)
        .subscribe(
          next => {
            this.orderSucces = true;
            this.orderFail = false;

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
          },
          error => {
            this.orderFail = true;
            this.orderSucces = false;

            this.responseMessage = [];
            for (var key in error.error) {
              this.responseMessage.push(error.error[key]);
            }
          }
        );
      this.orderPosted = true;
    }
  }

}
