import { Injectable } from '@angular/core';
import { UserData } from '../models/UserData';
import { UserRole } from '../models/UserRole';
import { environment } from '../../environments/environment'
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { Router } from '@angular/router';
import { FlashMessagesService } from 'angular2-flash-messages';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-type': 'application/json' }),
  observe: 'response' as 'response'
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  userData: UserData = {
    session_string: '',
    role: null
  }
  constructor(
    private http: HttpClient,
    private router: Router
  ) {
    if (sessionStorage.getItem('userData') != null) {
      var data = JSON.parse(sessionStorage.getItem('userData'));
      this.userData.session_string = data.session_string;
      this.userData.role = data.role as UserRole;
    }
  }

  verifyAuth(): Observable<boolean> {
    if (this.userData.session_string != '') {
      return this.http.post(`${environment.apiBaseUrl}/api/auth/?session_string=${this.userData.session_string}`, this.userData, httpOptions).pipe(
        map(res => {
          return true;
        }),
        catchError(err => {
          this.clearSession();
          return of(false)
        })
      );
    }
    else {
      return of(false);
    }
  }

  login(email: string, password: string): Observable<boolean> {
    return this.http.post(`${environment.apiBaseUrl}/api/login`, { email: email, password: password }, httpOptions).pipe(
      map(res => {
        var response = res.body as { session_string: string, roles: string[] };
        var userData: UserData = {
          session_string: '',
          role: null
        };

        userData.session_string = response.session_string;
        for (let i = 0; i < response.roles.length; i++) {
          if (response.roles[i] == "ROLE_ADMIN") {
            userData.role = UserRole.administrator;
            break;
          }
          else if (response.roles[i] == "ROLE_MANAGER") {
            userData.role = UserRole.manager;
            break;
          }
          else if (response.roles[i] == "ROLE_KITCHEN") {
            userData.role = UserRole.kitchenStaff;
            break;
          }
          else if (response.roles[i] == "ROLE_DELIVERY") {
            userData.role = UserRole.deliveryGuy;
            break;
          }
        }
        if (userData.session_string != '') {
          this.setSession(userData);
          return true;
        }
        return false;
      }));
  }

  logout() {
    this.http.get(`${environment.apiBaseUrl}/api/logout?session_string=${this.userData.session_string}`);
    this.clearSession();
    this.router.navigate(['/admin/login']);
  }

  private setSession(userData: UserData) {
    sessionStorage.setItem('userData', JSON.stringify(userData));
  }
  private clearSession() {
    sessionStorage.removeItem('userData');
    this.userData = {
      session_string: '',
      role: null
    }
  }

  navigateMain() {
    switch (this.userData.role) {
      case UserRole.administrator:
        this.router.navigate(['/admin/make-delivery']);
        break;
      case UserRole.deliveryGuy:
        this.router.navigate(['/admin/delivery-list']);
        break;
      case UserRole.kitchenStaff:
        this.router.navigate(['/admin/prepare-order']);
        break;
      case UserRole.manager:
        this.router.navigate(['/admin/scheduler']);
        break;
    }
  }

  avaibleRoute(route: string): boolean {
    switch (route) {
      case "pizzas":
        return this.userData.role == UserRole.administrator ? true : false;
      case "users":
        return this.userData.role == UserRole.administrator ? true : false;
      case "make-delivery":
        return this.userData.role == UserRole.administrator ? true : false;
      case "scheduler":
        return this.userData.role == UserRole.manager ? true : false;
      case "prepare-order":
        return this.userData.role == UserRole.kitchenStaff ? true : false;
      case "delivery-list":
        return this.userData.role == UserRole.deliveryGuy ? true : false;
    }
  }
}
