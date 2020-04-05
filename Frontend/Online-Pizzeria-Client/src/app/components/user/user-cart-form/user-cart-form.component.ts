import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-cart-form',
  templateUrl: './user-cart-form.component.html',
  styleUrls: ['./user-cart-form.component.css']
})
export class UserCartFormComponent implements OnInit {

  payMethods: [
    {
      "id": number,
      "method": string
    },
    {
      "id": number,
      "method": string
    }
  ]

  constructor() { }

  ngOnInit() {
    this.payMethods = [
      {
        "id": 1,
        "method": "cash"
      },
      {
        "id": 2,
        "method": "bank card"
      }
    ]
  }

}
