import { Injectable } from '@angular/core';
import { Order } from '../models/Order';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const httpOptions = {
  headers: new HttpHeaders({'Content-type': 'application/json'})
}

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient) { }

  postOrder(order: Order) {
    return this.http.post(`${environment.apiBaseUrl}/api/order`, order, httpOptions);
  }
}
