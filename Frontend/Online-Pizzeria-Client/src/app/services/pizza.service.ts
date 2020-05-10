import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';

import { Pizza } from '../models/Pizza';
import { AuthService } from './auth.service';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-type': 'application/json' })
}

@Injectable({
  providedIn: 'root'
})
export class PizzaService {

  constructor(
    private http: HttpClient,
    private authService: AuthService
    ) { }

  getDiscountedPizzas(): Observable<Pizza[]> {
    return this.http.get<Pizza[]>(`${environment.apiBaseUrl}/api/pizza/discount`);
  }

  getAllPizzas() {
    return this.http.get<Pizza[]>(`${environment.apiBaseUrl}/api/pizza`);
  }

  addPizza(pizza: Pizza, pictureFile: File) {
    var postPizza = {
      name: pizza.name,
      price: pizza.price,
      ingredients: pizza.ingredients,
      picture: pictureFile
    }
    console.log(postPizza);

    return this.http.post(`${environment.apiBaseUrl}/api/pizza/?session_string=${this.authService.userData.session_string}`, postPizza, httpOptions);
  }

  updatePizza(pizza: Pizza, pictureFile: File) {
    var postPizza = {
      name: pizza.name,
      price: pizza.price,
      ingredients: pizza.ingredients,
      picture: pictureFile == null ? pizza.picture : pictureFile,
      discount_price: pizza.discount_price
    }

    return this.http.put(`${environment.apiBaseUrl}/api/pizza/?session_string=${this.authService.userData.session_string}&pizza_id=${pizza.id}`, postPizza, httpOptions);
  }

  deletePizza(id: number) {
    return this.http.delete(`${environment.apiBaseUrl}/api/pizza/?session_string=${this.authService.userData.session_string}&pizza_id=${id}`);
  }

  private pizzaSource = new BehaviorSubject<Pizza>({ id: null, name: null, price: null, ingredients: null, picture: null, discount_price: null });
  selectedPizza = this.pizzaSource.asObservable();

  private stateSource = new BehaviorSubject<boolean>(true);
  stateClear = this.stateSource.asObservable();

  setFormPizza(pizza: Pizza) {
    this.pizzaSource.next(pizza);
  }

  clearFormPizza() {
    this.pizzaSource.next({ id: null, name: null, price: null, ingredients: null, picture: null, discount_price: null });
  }
}
