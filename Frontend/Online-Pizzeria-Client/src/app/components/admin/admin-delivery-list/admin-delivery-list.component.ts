import { Component, OnInit } from '@angular/core';
import { DeliveryService } from 'src/app/services/delivery.service';
import { OrderForDelivery } from 'src/app/models/OrderForDelivery';
import { FlashMessagesService } from 'angular2-flash-messages';

@Component({
  selector: 'app-admin-delivery-list',
  templateUrl: './admin-delivery-list.component.html',
  styleUrls: ['./admin-delivery-list.component.css']
})
export class AdminDeliveryListComponent implements OnInit {
  deliveries: { delivery_id: number, orders: OrderForDelivery[] }[];
  loaded: boolean = false;
  constructor(
    private deliveryService: DeliveryService,
    private flashMessage: FlashMessagesService
  ) { }

  ngOnInit() {
    this.deliveryService.getDeliveryByDeliveryGuy().subscribe(deliveries => {
      this.deliveries = deliveries;
      this.loaded = true;
    });
  }

  processPizzas(pizzas: string[]) {
    var procPizzas: { name: string, count: number }[] = [];
    for (let i = 0; i < pizzas.length; i++) {
      var found = false;
      for (let j = 0; j < procPizzas.length; j++) {
        if (procPizzas[j].name == pizzas[i]) {
          found = true;
          procPizzas[j].count += 1;
        }
      }
      if (!found) {
        procPizzas.push({ name: pizzas[i], count: 1 });
      }
    }
    return procPizzas;
  }

  deliveryDone(id: number) {
    this.deliveryService.postDeliveryDone(id).subscribe(() => {
      this.deliveryService.getDeliveryByDeliveryGuy().subscribe(deliveries => {
        this.deliveries = deliveries;
      });
    },
      err => this.flashMessage.show(err.error.error, { cssClass: 'alert-danger', timeout: 4000 })
    );
  }

}
