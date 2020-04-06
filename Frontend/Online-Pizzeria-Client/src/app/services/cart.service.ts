import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { CartItem } from '../models/CartItem';

@Injectable({
  providedIn: 'root'
})

export class CartService {
  
  private cartSource = new BehaviorSubject<CartItem[]>(this.loadCartItems());
  cart = this.cartSource.asObservable();

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
    this.cartSource.value.forEach(item => {
      sum += item.count;
    });
    return sum;
  }

  getCartItems(): Observable<CartItem[]> {
    return this.cart;
  }

  getItemIdsForOrder(): number[] {
    var itemList = [];
    this.cartSource.value.forEach(item => {
      for (let index = 0; index < item.count; index++) {
        itemList.push(item.pizzaId);
      }
    });

    return itemList;
  }

  addCartItem(cartItem: CartItem) {
    if(this.cartCountSource.value + 1 > 15) {
      return;
    }

    var added = false;
    this.cartSource.value.forEach((item, index, cart) => {
      if (item.pizzaId == cartItem.pizzaId) {
        cart[index].count += cartItem.count;
        added = true;
      }
    });

    if (!added) {
      this.cartSource.value.push(cartItem);
    }

    this.cartCountSource.next(this.getCartCount());
    sessionStorage.setItem('cart', JSON.stringify(this.cartSource.value));
  }

  changeCartItemCount(pizzaId: number, count: number) {
    if(this.cartCountSource.value + count > 15) {
      return;
    }

    this.cartSource.value.forEach((item, index, cart) => {
      if (item.pizzaId == pizzaId) {
        cart[index].count += count;

        if(cart[index].count < 1) {
          this.deleteCartItem(pizzaId);
        }
      }
    });

    this.cartCountSource.next(this.getCartCount());
    sessionStorage.setItem('cart', JSON.stringify(this.cartSource.value));
  }

  deleteCartItem(pizzaId: number) {
    this.cartSource.value.forEach((item, index, cart) => {
      if (item.pizzaId == pizzaId) {
        cart.splice(index, 1);
      }
    });

    this.cartCountSource.next(this.getCartCount());
    sessionStorage.setItem('cart', JSON.stringify(this.cartSource.value));
  }

  clearCart() {
    this.cartSource.next([]);
    this.cartCountSource.next(this.getCartCount());

    sessionStorage.removeItem('cart');
  }
}
