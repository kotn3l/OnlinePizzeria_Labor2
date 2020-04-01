import { Component, OnInit } from '@angular/core';

import { PizzaService } from '../../../services/pizza.service';
import { Pizza } from '../../../models/Pizza';

@Component({
  selector: 'app-user-pizza-view',
  templateUrl: './user-pizza-view.component.html',
  styleUrls: ['./user-pizza-view.component.css']
})
export class UserPizzaViewComponent implements OnInit {
  pizzas: Pizza[] = [];
  constructor(private pizzaService: PizzaService) { }

  ngOnInit() {
    this.pizzaService.getDiscountedPizzas().subscribe(pizzas => {
      this.pizzas = pizzas;
      for (let index = 0; index < 100; index++) {
        pizzas.unshift(pizzas[0]); 
      }
    });
  }

}
