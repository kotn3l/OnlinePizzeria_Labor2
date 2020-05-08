import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Scheduler } from '../models/Scheduler';
import { HttpClient } from '@angular/common/http';
import { AuthService } from './auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SchedulingService {

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) { }

  getSchedulingAlgorithms(): Observable<Scheduler[]> {
    return this.http.get<Scheduler[]>(`${environment.apiBaseUrl}/api/scheduling/?sessin_string=${this.authService.userData.session_string}`);
  }

  postActiveAlgorithm(id: number) {
    return this.http.post(`${environment.apiBaseUrl}/api/scheduling/?session_string=${this.authService.userData.session_string}&scheduling_id=${id}`, null);
  }
}
