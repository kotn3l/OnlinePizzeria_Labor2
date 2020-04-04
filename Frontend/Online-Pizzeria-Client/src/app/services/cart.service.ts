import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { CartItem } from '../models/CartItem';

@Injectable({
  providedIn: 'root'
})

export class CartService {
  cart: CartItem[] = this.loadCartItems();

  private cartCountSource = new BehaviorSubject<number>(this.getCartCount());
  cartCount = this.cartCountSource.asObservable();

  maxOrder: number = 15;

  constructor() { }

  private loadCartItems() {
    if (sessionStorage.getItem('cart') == null) {
      return [];
    }
    else {
      return JSON.parse(sessionStorage.getItem('cart'));
    }
  }

  private getCartCount(): number {
    var sum = 0;
    this.cart.forEach(item => {
      sum += item.count;
    });
    return sum;
  }

  getCartItems(): Observable<CartItem[]> {
    return of(this.cart);
  }

  addCartItem(cartItem: CartItem) {
    if(this.cartCountSource.value + 1 > 15) {
      return;
    }

    var added = false;
    this.cart.forEach((item, index, cart) => {
      if (item.pizzaId == cartItem.pizzaId) {
        cart[index].count += cartItem.count;
        added = true;
      }
    });

    if (!added) {
      this.cart.push(cartItem);
    }

    this.cartCountSource.next(this.getCartCount());
    sessionStorage.setItem('cart', JSON.stringify(this.cart));
  }

  changeCartItemCount(pizzaId: number, count: number) {
    if(this.cartCountSource.value + count > 15) {
      return;
    }

    this.cart.forEach((item, index, cart) => {
      if (item.pizzaId == pizzaId) {
        cart[index].count += count;

        if(cart[index].count < 1) {
          this.deleteCartItem(pizzaId);
        }
      }
    });

    this.cartCountSource.next(this.getCartCount());
    sessionStorage.setItem('cart', JSON.stringify(this.cart));
  }

  deleteCartItem(pizzaId: number) {
    this.cart.forEach((item, index, cart) => {
      if (item.pizzaId == pizzaId) {
        cart.splice(index, 1);
      }
    });

    this.cartCountSource.next(this.getCartCount());
    sessionStorage.setItem('cart', JSON.stringify(this.cart));
  }

  clearCart() {
    this.cart = [];
    this.cartCountSource.next(this.getCartCount());

    sessionStorage.removeItem('cart');
  }
}
