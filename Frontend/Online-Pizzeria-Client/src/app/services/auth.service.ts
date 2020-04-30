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
      this.userData.role = UserRole[data.role as string];
    }
  }

}
