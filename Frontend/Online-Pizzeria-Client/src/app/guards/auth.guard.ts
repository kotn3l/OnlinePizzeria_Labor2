import { CanActivate, Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable()
export class AuthGuard implements CanActivate {
    constructor(
        private router: Router,
        private authService: AuthService
    ) { }

    canActivate(): Observable<boolean> {
        return this.authService.verifyAuth().pipe(
            map(res => {
                if(res) {
                    return true;
                }
                else {
                    this.router.navigate(['/admin/login']);
                    return false;
                }
            })
        );
    }
}