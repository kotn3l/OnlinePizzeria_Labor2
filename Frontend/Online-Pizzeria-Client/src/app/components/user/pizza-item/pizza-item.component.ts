import { Component, OnInit, Input } from '@angular/core';
import { Pizza } from 'src/app/models/Pizza';
import { timer } from 'rxjs';
import { StringDecoder } from 'string_decoder';

@Component({
  selector: 'app-pizza-item',
  templateUrl: './pizza-item.component.html',
  styleUrls: ['./pizza-item.component.css']
})
export class PizzaItemComponent implements OnInit {
  @Input() pizza: Pizza;
  formatedIngredients: String = '';

  constructor() { }

  ngOnInit() {
    if (this.pizza.ingredients.length > 0) {
      this.formatedIngredients = this.formatedIngredients.concat(this.pizza.ingredients[0]);
      for (let index = 1; index < this.pizza.ingredients.length; index++) {
        this.formatedIngredients = this.formatedIngredients.concat(', ' + this.pizza.ingredients[index]);
      }
    }
  }
}
