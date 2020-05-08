import { Injectable } from '@angular/core';
import { UserData } from '../models/UserData';
import { UserRole } from '../models/UserRole';
import { environment } from '../../environments/environment'
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { Router } from '@angular/router';

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
    return this.http.post(`${environment.apiBaseUrl}/api/auth`, this.userData, httpOptions).pipe(
      map(res => {
        return true;
      }),
      catchError(err => {
        this.clearSession();
        return of(false)
      })
    );
    // if (this.userData.session_string != '') {
    //   return of(true);
    // } else {
    //   return of(false);
    // }
  }

  login(email: string, password: string) {
    this.http.post(`${environment.apiBaseUrl}/api/login`, { email: email, password: password }, httpOptions).pipe(
      map(res => {
        this.userData = res.body as UserData;
        sessionStorage.setItem('userData', JSON.stringify(this.userData));
        return true;
      }),
      catchError(err => of(false))
    );
    // this.userData = {
    //   session_string: 'abc123',
    //   role: UserRole.administrator
    // }
    sessionStorage.setItem('userData', JSON.stringify(this.userData));
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate(['/admin/login']);
  }

  logout() {
    this.http.get(`${environment.apiBaseUrl}/api/logout?session_string=${this.userData.session_string}`);
    this.clearSession();
    this.router.navigate(['/admin/login']);
  }

  clearSession() {
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
