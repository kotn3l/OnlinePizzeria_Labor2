import { Component, OnInit } from '@angular/core';
import { PizzaService } from 'src/app/services/pizza.service';
import { Pizza } from 'src/app/models/Pizza';

@Component({
  selector: 'app-admin-pizza-list',
  templateUrl: './admin-pizza-list.component.html',
  styleUrls: ['./admin-pizza-list.component.css']
})
export class AdminPizzaListComponent implements OnInit {
  pizzas: Pizza[];
  loaded: boolean = false;
  constructor(private pizzaService: PizzaService) { }

  ngOnInit() {
    this.pizzaService.getAllPizzas().subscribe(pizzas => {
      this.pizzas = pizzas;
      this.loaded = true;
    });
  }

}
