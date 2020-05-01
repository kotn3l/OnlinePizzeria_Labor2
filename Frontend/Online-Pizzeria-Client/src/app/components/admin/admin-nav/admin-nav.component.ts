import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-admin-nav',
  templateUrl: './admin-nav.component.html',
  styleUrls: ['./admin-nav.component.css']
})
export class AdminNavComponent implements OnInit {
  isLoggedIn: boolean;

  constructor(private authService: AuthService) { }

  ngOnInit() {
    this.authService.verifyAuth().subscribe(res => this.isLoggedIn = res);
  }

  avaibleRoute(route: string): boolean {
    return this.authService.avaibleRoute(route);
  }

  onLogoutClick() {
    this.authService.logout();
    this.isLoggedIn = false;
  }

}
