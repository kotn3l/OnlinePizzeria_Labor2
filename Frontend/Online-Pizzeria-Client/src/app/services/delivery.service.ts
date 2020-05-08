import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { DeliveryGuy } from '../models/DeliveryGuy';
import { AuthService } from '../services/auth.service';
import { OrderForDelivery } from '../models/OrderForDelivery';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-type': 'application/json' })
}

@Injectable({
  providedIn: 'root'
})

export class DeliveryService {

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) { }

  getDeliveryGuys() {
    return this.http.get<DeliveryGuy[]>(`${environment.apiBaseUrl}/api/delivery-guy/?session_string=${this.authService.userData.session_string}`);
  }

  postDelivery(deliveryGuy: number, orders: number[]) {
    return this.http.post(`${environment.apiBaseUrl}/api/delivery/?session_string=${this.authService.userData.session_string}&delivery_guy=${deliveryGuy}`, orders, httpOptions);
  }

  getDeliveryByDeliveryGuy() {
    return this.http.get<{delivery_id: number, orders: OrderForDelivery[]}[]>(`${environment.apiBaseUrl}/api/delivery/?session_string=${this.authService.userData.session_string}`);
  }

  postDeliveryDone(id: number) {
    return this.http.post(`${environment.apiBaseUrl}/api/delivery-done/?session_string=${this.authService.userData.session_string}&delivery_id=${id}`, null);
  }
}
