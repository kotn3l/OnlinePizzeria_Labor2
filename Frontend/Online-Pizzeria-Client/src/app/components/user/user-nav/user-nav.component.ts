import { Component, OnInit } from '@angular/core';
import { CartService } from '../../../services/cart.service';

@Component({
  selector: 'app-user-nav',
  templateUrl: './user-nav.component.html',
  styleUrls: ['./user-nav.component.css']
})
export class UserNavComponent implements OnInit {

  cartCount: number;
  constructor(private cartService: CartService) { }

  ngOnInit() {
    this.cartService.cartCount.subscribe(count => this.cartCount = count);
  }

}
