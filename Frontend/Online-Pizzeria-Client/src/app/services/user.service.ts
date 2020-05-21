import { Injectable } from '@angular/core';
import { User } from '../models/User';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment'
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { FormDropdown } from '../models/FormDropdown';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-type': 'application/json' })
}

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) { }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${environment.apiBaseUrl}/api/user/?session_string=${this.authService.userData.session_string}`);
  }

  getUserRoles(): Observable<FormDropdown[]> {
    return this.http.get<FormDropdown[]>(`${environment.apiBaseUrl}/api/roles/?session_string=${this.authService.userData.session_string}`);
  }

  addUser(user) {
    return this.http.post(`${environment.apiBaseUrl}/api/register/?session_string=${this.authService.userData.session_string}`, user, httpOptions);
  }

  deleteUser(id: number) {
    return this.http.delete(`${environment.apiBaseUrl}/api/user/?session_string=${this.authService.userData.session_string}&user_id=${id}`);
  }
}
