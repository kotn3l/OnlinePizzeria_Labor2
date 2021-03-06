import { Component, OnInit, ViewChild } from '@angular/core';
import { Pizza } from 'src/app/models/Pizza';
import { PizzaService } from 'src/app/services/pizza.service';
import { FlashMessagesService } from 'angular2-flash-messages';

@Component({
  selector: 'app-admin-pizza-form',
  templateUrl: './admin-pizza-form.component.html',
  styleUrls: ['./admin-pizza-form.component.css']
})
export class AdminPizzaFormComponent implements OnInit {
  pizza: Pizza = {
    id: null,
    name: '',
    price: null,
    ingredients: [],
    discount_percent: 0
  }
  ingredientList: string[] = [];
  ingredientCount: number = 1;
  picture: File = null;
  isNew: boolean = true;
  @ViewChild('pizzaForm', { static: false }) form: any;
  constructor(
    private pizzaService: PizzaService,
    private flashMessage: FlashMessagesService
  ) { }

  ngOnInit() {
    this.pizzaService.selectedPizza.subscribe(pizza => {
      if (pizza.id != null) {
        this.pizza = pizza;
        this.pizza.ingredients.forEach(ingredient => {
          this.ingredientList.push(ingredient.name);
        })
        this.isNew = false;
        this.ingredientCount = this.pizza.ingredients.length;
      }
    });
  }

  onFileChanged(event) {
    this.picture = event.target.files[0];
  }

  onSubmit() {
    this.cleanIngredient();
    if (this.isNew) {
      this.addPizza();
    }
    else {
      this.updatePizza();
    }
    this.clear();
    this.form.reset();
    this.ingredientCount = 1;
  }

  addPizza() {
    this.pizzaService.addPizza(this.pizza, this.picture).subscribe(() => {
      this.flashMessage.show('Pizza succesfully added', { cssClass: 'alert-success', timeout: 4000 });
    },
      err => {
        var text: string = '';
        for (const key in err.error) {
          if (err.error.hasOwnProperty(key)) {
            text += err.error[key] + "<br />";
          }
        }
        this.flashMessage.show(text, { cssClass: 'alert-danger', timeout: 4000 });
      }
    );
  }

  updatePizza() {
    this.pizzaService.updatePizza(this.pizza, this.picture).subscribe(() => {
      this.flashMessage.show('Pizza succesfully updated', { cssClass: 'alert-success', timeout: 4000 });
    },
      err => {
        var text: string = '';
        for (const key in err.error) {
          if (err.error.hasOwnProperty(key)) {
            text += err.error[key] + "<br />";
          }
        }
        this.flashMessage.show(text, { cssClass: 'alert-danger', timeout: 4000 });
      }
    );
  }

  nextIngredient(count: number) {
    if (count == this.ingredientCount) {
      this.ingredientCount++;
    }
  }

  cleanIngredient() {
    this.pizza.ingredientList = [];
    this.ingredientList.forEach((ingredient, index) => {
      if (ingredient != null && ingredient != '' && ingredient != undefined) {
        this.pizza.ingredientList.push(ingredient);
      }
    });
  }

  clear() {
    this.pizza = {
      id: null,
      name: '',
      price: null,
      ingredients: [],
      ingredientList: [],
      discount_percent: 0
    }
    this.ingredientCount = 1;
    this.picture = null;
    this.isNew = true;
    this.pizzaService.clearFormPizza();
  }
}
