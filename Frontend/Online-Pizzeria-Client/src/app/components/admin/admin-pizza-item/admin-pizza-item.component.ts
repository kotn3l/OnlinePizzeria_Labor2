import { Component, OnInit, Input } from '@angular/core';
import { Pizza } from 'src/app/models/Pizza';
import { PizzaService } from 'src/app/services/pizza.service';
import { FlashMessagesService } from 'angular2-flash-messages';

@Component({
  selector: 'app-admin-pizza-item',
  templateUrl: './admin-pizza-item.component.html',
  styleUrls: ['./admin-pizza-item.component.css']
})
export class AdminPizzaItemComponent implements OnInit {
  hide: boolean = true;
  prevDiscountPrice: number;
  @Input() pizza: Pizza;
  formatedIngredients: string = '';
  constructor(
    private pizzaService: PizzaService,
    private flashMessage: FlashMessagesService
    ) { }

  ngOnInit() {
    if (this.pizza.ingredients.length > 0) {
      this.formatedIngredients = this.formatedIngredients.concat(this.pizza.ingredients[0].name);
      for (let index = 1; index < this.pizza.ingredients.length; index++) {
        this.formatedIngredients = this.formatedIngredients.concat(', ' + this.pizza.ingredients[index].name);
      }
    }
    this.prevDiscountPrice = this.pizza.discount_price;
  }

  changeDiscountPrice(number: number) {
    this.pizza.discount_price += (5 * number);
  }

  discountChanged() {
    return this.prevDiscountPrice != this.pizza.discount_price ? false : true;
  }

  updatePizza() {
    this.pizzaService.updatePizza(this.pizza, null).subscribe(() => {
      this.flashMessage.show('Pizza discount changed', { cssClass: 'alert-success', timeout: 4000 });
      this.prevDiscountPrice = this.pizza.discount_price;
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
    )
  }

  editPizza(pizza: Pizza) {
    this.pizzaService.setFormPizza(pizza);
  }

  deletePizza(id: number) {
    if(confirm('Are you sure you want to delete this pizza?')) {
      this.pizzaService.deletePizza(id).subscribe(() => {
        this.flashMessage.show('Pizza succesfully deleted', { cssClass: 'alert-success', timeout: 4000 });
      },
      err => this.flashMessage.show(err.error.error, { cssClass: 'alert-danger', timeout: 4000 })
      )
    }
  }
}
