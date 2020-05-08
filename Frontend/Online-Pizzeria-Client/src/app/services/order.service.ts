import { Injectable } from '@angular/core';
import { Order } from '../models/Order';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from './auth.service';
import { OrderForDelivery } from '../models/OrderForDelivery';
import { OrderedPizza } from '../models/OrderedPizza';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-type': 'application/json' })
}

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) { }

  postOrder(order: Order) {
    return this.http.post(`${environment.apiBaseUrl}/api/order`, order, httpOptions);
  }

  getReadyOrders() {
    return this.http.get<OrderForDelivery[]>(`${environment.apiBaseUrl}/api/order-delivery/?session_string=${this.authService.userData.session_string}`);
  }

  getPrepareOrder() {
    return this.http.get<OrderedPizza[]>(`${environment.apiBaseUrl}/api/order-prep/?session_string=${this.authService.userData.session_string}`);
  }

  postPizzaPrepared(id: number) {
    return this.http.post(`${environment.apiBaseUrl}/api/order-prep/?session_string=${this.authService.userData.session_string}&orderd_pizza_id=${id}`, null);
  }
}
