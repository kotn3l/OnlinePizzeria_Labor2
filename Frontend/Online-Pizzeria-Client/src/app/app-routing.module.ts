import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { UserMainComponent } from './components/user/user-main/user-main.component';
import { UserHomeComponent } from './components/user/user-home/user-home.component';
import { UserPizzaViewComponent } from './components/user/user-pizza-view/user-pizza-view.component';
import { UserCartComponent } from './components/user/user-cart/user-cart.component';


import { AdminMainComponent } from './components/admin/admin-main/admin-main.component';




const routes: Routes = [
  {
    path: '', component: UserMainComponent,
    children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { path: 'home', component: UserHomeComponent },
      { path: 'pizzas', component: UserPizzaViewComponent },
      { path: 'cart', component: UserCartComponent }
    ]
  },
  {
    path: 'admin', component: AdminMainComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
