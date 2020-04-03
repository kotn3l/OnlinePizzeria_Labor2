import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserMainComponent } from './components/user/user-main/user-main.component';
import { UserNavComponent } from './components/user/user-nav/user-nav.component';
import { UserHomeComponent } from './components/user/user-home/user-home.component';
import { UserPizzaViewComponent } from './components/user/user-pizza-view/user-pizza-view.component';
import { UserCartComponent } from './components/user/user-cart/user-cart.component';
import { AdminMainComponent } from './components/admin/admin-main/admin-main.component';
import { PizzaItemComponent } from './components/user/pizza-item/pizza-item.component';
import { UserFooterComponent } from './components/user/user-footer/user-footer.component';

@NgModule({
  declarations: [
    AppComponent,
    UserMainComponent,
    UserNavComponent,
    UserHomeComponent,
    UserPizzaViewComponent,
    UserCartComponent,
    AdminMainComponent,
    PizzaItemComponent,
    UserFooterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
