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
}
