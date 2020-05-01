import { Component, OnInit, ComponentFactoryResolver } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  email: string;
  password: string;

  isLoggedIn: boolean = false;
  constructor(
    private authService: AuthService,
    private router: Router
    ) { }

  ngOnInit() {
    this.authService.verifyAuth().subscribe(res => {
      if(res) {
        this.isLoggedIn = true;
        this.authService.navigateMain();
      }
    });
  }

  onSubmit() {
    this.authService.login(this.email, this.password);
  }

}
