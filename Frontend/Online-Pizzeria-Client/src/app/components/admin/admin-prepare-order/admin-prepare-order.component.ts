import { Component, OnInit } from '@angular/core';
import { OrderedPizza } from 'src/app/models/OrderedPizza';
import { OrderService } from 'src/app/services/order.service';
import { FlashMessagesService } from 'angular2-flash-messages';

@Component({
  selector: 'app-admin-prepare-order',
  templateUrl: './admin-prepare-order.component.html',
  styleUrls: ['./admin-prepare-order.component.css']
})
export class AdminPrepareOrderComponent implements OnInit {
  orderedPizzas: OrderedPizza[];
  constructor(
    private orderService: OrderService,
    private flashMessage: FlashMessagesService
  ) { }

  ngOnInit() {
    this.orderService.getPrepareOrder().subscribe(pizzas => {
      this.orderedPizzas = pizzas;
      this.orderedPizzas.sort((a, b) => { return a.prepNum - b.prepNum })
    });
  }

  ingredients(ingredients: {name: string}[]) {
    var string = ingredients[0].name;
    for (let i = 1; i < ingredients.length; i++) {
      string = string.concat(', ' + ingredients[i].name);
    }
    return string;
  }

  onDone(id: number) {
    this.orderService.postPizzaPrepared(id).subscribe(() => {
      this.orderService.getPrepareOrder().subscribe(pizzas => {
        this.orderedPizzas = pizzas;
        this.orderedPizzas.sort((a, b) => { return a.prepNum - b.prepNum })
      });
    },
      err => this.flashMessage.show(err.error.error, { cssClass: 'alert-danger', timeout: 4000 }));
  }
}
