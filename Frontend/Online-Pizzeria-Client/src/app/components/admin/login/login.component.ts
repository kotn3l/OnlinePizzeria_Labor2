import { Component, OnInit, ComponentFactoryResolver } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';
import { FlashMessagesService } from 'angular2-flash-messages';

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
    private flashMessage: FlashMessagesService,
    private router: Router
  ) { }

  ngOnInit() {
    this.authService.verifyAuth().subscribe(res => {
      if (res) {
        this.isLoggedIn = true;
        this.authService.navigateMain();
      }
    });
  }

  onSubmit() {
    this.authService.login(this.email, this.password).subscribe(res => {
      if (res) {
        this.flashMessage.show('Succesfull login!', { cssClass: 'alert-success', timeout: 4000 });
        this.router.routeReuseStrategy.shouldReuseRoute = () => false;
        this.router.onSameUrlNavigation = 'reload';
        this.router.navigate(['/admin/login']);
      }
    },
      err => this.flashMessage.show(err.error.error, { cssClass: 'alert-danger', timeout: 4000 })
    );
  }

}
