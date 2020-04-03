import { Component, OnInit, Input } from '@angular/core';

import { PizzaService } from '../../../services/pizza.service';
import { Pizza } from '../../../models/Pizza';

@Component({
  selector: 'app-user-pizza-view',
  templateUrl: './user-pizza-view.component.html',
  styleUrls: ['./user-pizza-view.component.css']
})
export class UserPizzaViewComponent implements OnInit {
  pizzas: Pizza[] = [];
  @Input() discountedOnly: boolean = false;
  loaded: boolean = false;
  constructor(private pizzaService: PizzaService) { }

  ngOnInit() {
    if(this.discountedOnly) {
      this.pizzaService.getDiscountedPizzas().subscribe(pizzas => {
        this.pizzas = pizzas;
        for (let index = 0; index < 2; index++) {
          pizzas.unshift(pizzas[0]); 
        }
        this.loaded = true;
      });
    }
    else {
      this.pizzaService.getAllPizzas().subscribe(pizzas => {
        this.pizzas = pizzas;
        for (let index = 0; index < 50; index++) {
          pizzas.unshift(pizzas[0]); 
        }
        this.loaded = true;
      });
    }
  }

  showDescounted(pizza: Pizza) {
    if(this.discountedOnly) {
      return pizza.discount_price > 0;
    }
    return true;
  }

}
