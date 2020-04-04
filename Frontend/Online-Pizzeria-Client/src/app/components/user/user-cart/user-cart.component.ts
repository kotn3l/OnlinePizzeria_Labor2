import { Component, OnInit } from '@angular/core';
import { CartService } from '../../../services/cart.service';
import { CartItem } from '../../../models/CartItem';

@Component({
  selector: 'app-user-cart',
  templateUrl: './user-cart.component.html',
  styleUrls: ['./user-cart.component.css']
})
export class UserCartComponent implements OnInit {

  cartItems: CartItem[];
  cartCount: number;

  constructor(private cartService: CartService) { }

  ngOnInit() {
    this.cartService.cartCount.subscribe(count => this.cartCount = count);
    this.cartService.getCartItems().subscribe(cartItems => this.cartItems = cartItems);
  }

  getTotalCost() {
    var sum = 0;
    this.cartItems.forEach(item => sum += item.count * item.price);
    return sum;
  }

  changeItemCount(pizzaId: number, number: number) {
    this.cartService.changeCartItemCount(pizzaId, number);
  }

  deleteItem(pizzaId: number) {
    this.cartService.deleteCartItem(pizzaId);
  }
}
