import { Component, OnInit, Input } from '@angular/core';

import { PizzaService } from '../../../services/pizza.service';
import { Pizza } from '../../../models/Pizza';
import { SearchService } from '../../../services/search.service';

@Component({
  selector: 'app-user-pizza-view',
  templateUrl: './user-pizza-view.component.html',
  styleUrls: ['./user-pizza-view.component.css']
})
export class UserPizzaViewComponent implements OnInit {
  pizzas: Pizza[] = [];
  displayedPizzas: Pizza[];
  @Input() discountedOnly: boolean = false;
  nameSearch: string;
  ingredientSearch: string;
  loaded: boolean = false;

  constructor(
    private pizzaService: PizzaService,
    private searchService: SearchService
  ) { }

  ngOnInit() {
    this.searchService.changeNameSearch('');
    this.searchService.changeIngredientSearch('');

    if (this.discountedOnly) {
      this.pizzaService.getDiscountedPizzas().subscribe(pizzas => {
        this.pizzas = pizzas;
        for (let index = 0; index < 2; index++) {
          pizzas.unshift(pizzas[0]);
        }
        this.loaded = true;
        this.updateDisplayed();
      });
    }
    else {
      this.pizzaService.getAllPizzas().subscribe(pizzas => {
        this.pizzas = pizzas;
        for (let index = 0; index < 10; index++) {
          pizzas.push(pizzas[0]);
        }
        this.loaded = true;
        this.updateDisplayed();
      });
    }

    this.searchService.currentNameSearch.subscribe(name => {
      this.nameSearch = name
      this.updateDisplayed();
    });
    this.searchService.currentIngredientSearch.subscribe(ingredient => {
      this.ingredientSearch = ingredient
      this.updateDisplayed();
    });
  }

  updateDisplayed() {
    this.displayedPizzas = [];
    this.pizzas.forEach(pizza => {
      if(this.showDescounted(pizza) && this.showNameSearch(pizza.name) && this.showIngredientSearch(pizza.ingredients)) {
        this.displayedPizzas.push(pizza);
      }
    });
  }

  showDescounted(pizza: Pizza) {
    if (this.discountedOnly) {
      return pizza.discount_price > 0;
    }
    return true;
  }

  showNameSearch(name: string) {
    if (this.nameSearch == '' || name.toLowerCase().includes(this.nameSearch.toLowerCase())) {
      return true;
    }
    return false;
  }

  showIngredientSearch(ingredients: string[]) {
    var containsIngredient = false;
    ingredients.forEach(ingredient => {
      if (ingredient.toLowerCase().includes(this.ingredientSearch.toLowerCase())) {
        containsIngredient = true;
      }
    });

    if (this.ingredientSearch == '' || containsIngredient) {
      return true;
    }
    return false;
  }
}
