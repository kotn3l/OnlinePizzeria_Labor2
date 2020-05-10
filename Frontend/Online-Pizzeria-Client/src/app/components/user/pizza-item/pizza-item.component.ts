import { Component, OnInit, Input } from '@angular/core';
import { Pizza } from 'src/app/models/Pizza';
import { CartItem } from '../../../models/CartItem';
import { CartService } from '../../../services/cart.service';

@Component({
  selector: 'app-pizza-item',
  templateUrl: './pizza-item.component.html',
  styleUrls: ['./pizza-item.component.css']
})
export class PizzaItemComponent implements OnInit {
  @Input() pizza: Pizza;
  cartItem: CartItem;
  formatedIngredients: String = '';

  constructor(private cartService: CartService) { }

  ngOnInit() {
    if (this.pizza.ingredients.length > 0) {
      this.formatedIngredients = this.formatedIngredients.concat(this.pizza.ingredients[0].name);
      for (let index = 1; index < this.pizza.ingredients.length; index++) {
        this.formatedIngredients = this.formatedIngredients.concat(', ' + this.pizza.ingredients[index].name);
      }
    }
  }

  addToCart(pizza: Pizza) {
    this.cartItem = {
      pizzaId: pizza.id,
      name: pizza.name,
      price: pizza.price,
      count: 1
    }

    this.cartService.addCartItem(this.cartItem);
  }
}
