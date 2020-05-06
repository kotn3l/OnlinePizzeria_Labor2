import { Component, OnInit, ViewChild } from '@angular/core';
import { User } from 'src/app/models/User';
import { UserRole } from 'src/app/models/UserRole';
import { UserService } from 'src/app/services/user.service';
import { FlashMessagesService } from 'angular2-flash-messages';
import { typeWithParameters } from '@angular/compiler/src/render3/util';
import { FormDropdown } from 'src/app/models/FormDropdown';

@Component({
  selector: 'app-admin-user-list',
  templateUrl: './admin-user-list.component.html',
  styleUrls: ['./admin-user-list.component.css']
})
export class AdminUserListComponent implements OnInit {

  user: User = {
    email: '',
    name: '',
    role: UserRole.administrator
  };
  users: User[] = [];
  roles: FormDropdown[];
  showExtended: boolean = true;
  loaded: boolean = false;
  enableAdd: boolean = false;
  showUserForm: boolean = false;
  @ViewChild('userForm', { static: false }) form: any;
  data: any;

  constructor(
    private userService: UserService,
    private flashMessage: FlashMessagesService
  ) { }

  ngOnInit() {
    this.userService.getUsers().subscribe(users => {
      this.users = users;
      this.users.forEach(user => user.hide = true);
      this.loaded = true;
    });
    this.userService.getUserRoles().subscribe(roles => this.roles = roles);
  }

  toggleHide(user: User) {
    user.hide = !user.hide;
  }

  deleteUser(id: number) {
    if (confirm('Are you sure you want to remove this staff member?')) {
      this.userService.deleteUser(id).subscribe(() => {
        this.users.forEach((cur, index) => {
          if (id == cur.id) {
            this.users.splice(index, 1);
          }
        });
      },
        err => this.flashMessage.show(err.error, { cssClass: 'alert-danger', timeout: 4000 })
      )
    }
  }

  onSubmit({ value, valid }: { value: User, valid: boolean }) {
    if (!valid) {
      this.flashMessage.show('Form is not valid. Please check it again!', { cssClass: 'alert-warning', timeout: 8000 });
    } else {
      var userPost = {
        email: value.email,
        name: value.name,
        role_id: Number(value.role)
      }

      this.userService.addUser(userPost).subscribe(() => {
        this.flashMessage.show('User succesfully registered', { cssClass: 'alert-success', timeout: 4000 });
        this.user.hide = true;

        this.roles.forEach(role => {
          if (role.id == Number(this.user.role)) {
            this.user.role = role.text as UserRole
          }
        });

        this.users.unshift(JSON.parse(JSON.stringify(this.user)));
        this.form.reset();
      },
        err => {
          var text: string = '';
          for (const key in err.error.error) {
            if (err.error.error.hasOwnProperty(key)) {
              text += err.error.error[key] + "<br />";
            }
          }
          this.flashMessage.show(text, { cssClass: 'alert-danger', timeout: 4000 });
        });
    }
  }
}
