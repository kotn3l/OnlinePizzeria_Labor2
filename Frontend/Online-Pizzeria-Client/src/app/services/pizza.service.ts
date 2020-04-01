import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';

import { Pizza } from '../models/Pizza';

@Injectable({
  providedIn: 'root'
})
export class PizzaService {

  constructor(private http: HttpClient) { }
  getDiscountedPizzas(): Observable<Pizza[]> {
    return this.http.get<Pizza[]>(`${environment.apiBaseUrl}/api/pizzas/discount`);
  }
}
