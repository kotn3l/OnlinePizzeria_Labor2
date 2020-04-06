import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { FormDropdown } from '../models/FormDropdown';

@Injectable({
  providedIn: 'root'
})
export class UtilityService {

  constructor(private http: HttpClient) { }

  getPayMethods(): Observable<FormDropdown[]> {
    return this.http.get<FormDropdown[]>(`${environment.apiBaseUrl}/api/pay-method`);
  }

  getCities(): Observable<FormDropdown[]> {
    return this.http.get<FormDropdown[]>(`${environment.apiBaseUrl}/api/delivery-city`);
  }
}
