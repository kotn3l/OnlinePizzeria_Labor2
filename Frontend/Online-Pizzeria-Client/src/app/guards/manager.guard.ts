import { CanActivate, Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Observable, of } from 'rxjs';
import { UserRole } from '../models/UserRole';

@Injectable()
export class ManagerGuard implements CanActivate {
    constructor(
        private router: Router,
        private authService: AuthService
    ) { }

    canActivate(): Observable<boolean> {
        if (this.authService.userData.role == UserRole.manager) {
            return of(true);
        } else {
            this.router.navigate(['/admin/unauthorized']);
            return of(false);
        }
    }
}