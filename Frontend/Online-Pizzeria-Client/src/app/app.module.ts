import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms'
import { HttpClientModule } from '@angular/common/http';
import { FlashMessagesModule } from 'angular2-flash-messages'

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
import { UserPizzaSearchComponent } from './components/user/user-pizza-search/user-pizza-search.component';
import { UserPizzaListSearchComponent } from './components/user/user-pizza-list-search/user-pizza-list-search.component';
import { UserCartTableComponent } from './components/user/user-cart-table/user-cart-table.component';
import { UserCartFormComponent } from './components/user/user-cart-form/user-cart-form.component';
import { AdminNavComponent } from './components/admin/admin-nav/admin-nav.component';
import { LoginComponent } from './components/admin/login/login.component';
import { AdminPizzaListComponent } from './components/admin/admin-pizza-list/admin-pizza-list.component';
import { UnauthorizedComponent } from './components/admin/unauthorized/unauthorized.component';
import { AdminUserListComponent } from './components/admin/admin-user-list/admin-user-list.component';
import { AdminMakeDeliveryComponent } from './components/admin/admin-make-delivery/admin-make-delivery.component';
import { AdminSchedulerComponent } from './components/admin/admin-scheduler/admin-scheduler.component';
import { AdminPrepareOrderComponent } from './components/admin/admin-prepare-order/admin-prepare-order.component';
import { AdminDeliveryListComponent } from './components/admin/admin-delivery-list/admin-delivery-list.component';

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
    UserFooterComponent,
    UserPizzaSearchComponent,
    UserPizzaListSearchComponent,
    UserCartTableComponent,
    UserCartFormComponent,
    AdminNavComponent,
    LoginComponent,
    AdminPizzaListComponent,
    UnauthorizedComponent,
    AdminUserListComponent,
    AdminMakeDeliveryComponent,
    AdminSchedulerComponent,
    AdminPrepareOrderComponent,
    AdminDeliveryListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    FlashMessagesModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
