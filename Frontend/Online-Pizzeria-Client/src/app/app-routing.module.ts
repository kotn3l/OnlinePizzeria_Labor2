import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { UserMainComponent } from './components/user/user-main/user-main.component';
import { UserHomeComponent } from './components/user/user-home/user-home.component';
import { UserPizzaListSearchComponent } from './components/user/user-pizza-list-search/user-pizza-list-search.component';
import { UserCartComponent } from './components/user/user-cart/user-cart.component';

import { AdminMainComponent } from './components/admin/admin-main/admin-main.component';
import { LoginComponent } from './components/admin/login/login.component';
import { AuthGuard } from './guards/auth.guard';
import { AdminGuard } from './guards/admin.guard';
import { ManagerGuard } from './guards/manager.guard';
import { UnauthorizedComponent } from './components/admin/unauthorized/unauthorized.component';
import { AdminPizzaListComponent } from './components/admin/admin-pizza-list/admin-pizza-list.component';
import { AdminUserListComponent } from './components/admin/admin-user-list/admin-user-list.component';
import { AdminMakeDeliveryComponent } from './components/admin/admin-make-delivery/admin-make-delivery.component';
import { AdminSchedulerComponent } from './components/admin/admin-scheduler/admin-scheduler.component';
import { AdminPrepareOrderComponent } from './components/admin/admin-prepare-order/admin-prepare-order.component';
import { KitchenStaffGuard } from './guards/kitchen-staff.guard';
import { AdminDeliveryListComponent } from './components/admin/admin-delivery-list/admin-delivery-list.component';
import { DeliveryGuyGuard } from './guards/delivery-guy.guard';


const routes: Routes = [
  {
    path: '', component: UserMainComponent,
    children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { path: 'home', component: UserHomeComponent },
      { path: 'pizzas', component: UserPizzaListSearchComponent },
      { path: 'cart', component: UserCartComponent }
    ]
  },
  {
    path: 'admin', component: AdminMainComponent,
    children: [
      { path: '', redirectTo: 'login', pathMatch: 'full' },
      { path: 'login', component: LoginComponent },
      { path: 'unauthorized', component: UnauthorizedComponent },
      { path: 'pizzas', component: AdminPizzaListComponent, canActivate: [AuthGuard, AdminGuard] },
      { path: 'users', component: AdminUserListComponent, canActivate: [AuthGuard, AdminGuard] },
      { path: 'make-delivery', component: AdminMakeDeliveryComponent, canActivate: [AuthGuard, AdminGuard] },
      { path: 'scheduler', component: AdminSchedulerComponent, canActivate: [AuthGuard, ManagerGuard] },
      { path: 'prepare-order', component: AdminPrepareOrderComponent, canActivate: [AuthGuard, KitchenStaffGuard] },
      { path: 'delivery-list', component: AdminDeliveryListComponent, canActivate: [AuthGuard, DeliveryGuyGuard] }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
  exports: [RouterModule],
  providers: [AuthGuard, AdminGuard, ManagerGuard, KitchenStaffGuard, DeliveryGuyGuard]
})
export class AppRoutingModule { }
