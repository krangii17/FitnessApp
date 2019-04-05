import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {first} from 'rxjs/operators';
import {User} from '../../_models';
import {AdminService} from '../../_services';

@Component({
    selector: 'app-admin-page',
    templateUrl: './admin-page.component.html',
    styleUrls: ['./admin-page.component.css']
})

export class AdminPageComponent implements OnInit {

    @ViewChild('readOnlyTemplate') readOnlyTemplate: TemplateRef<any>;
    @ViewChild('editTemplate') editTemplate: TemplateRef<any>;
    roles: Array<any>;
    status: Array<any>;
    editedUser: User;
    users: Array<User>;
    isNewRecord: boolean;
    selectedRow: number;
    searchText: string;

    constructor(private adminService: AdminService) {
        this.users = new Array<User>();
    }

    ngOnInit() {
        this.loadUsers();
        this.roles = ['admin', 'coach', 'user'];
        this.status = ['active', 'not active'];
    }

    editUser(user: User, id: number) {
        this.adminService.getUserById(id).pipe(first()).subscribe((gettedUser: User) => {
            this.editedUser = gettedUser;
        });
        this.editedUser.id = user.id;
        this.editedUser.firstName = user.firstName;
        this.editedUser.lastName = user.lastName;
        this.editedUser.status = user.status;
        this.editedUser.role = user.role;
        this.editedUser.email = user.email;
    }

    loadTemplate(user: User) {
        if (this.editedUser && this.editedUser.id === user.id) {
            return this.editTemplate;
        } else {
            return this.readOnlyTemplate;
        }
    }

    saveUser() {
        if (this.isNewRecord) {
            this.adminService.createUser(this.editedUser).pipe(first()).subscribe(data => {
                this.loadUsers();
            });
            this.isNewRecord = false;
            this.editedUser = null;
        } else {
            this.adminService.editUser(this.editedUser.id, this.editedUser).pipe(first()).subscribe(data => {
                this.loadUsers();
            });
            this.editedUser = null;
        }
    }

    cancel() {
        if (this.isNewRecord) {
            this.users.pop();
            this.isNewRecord = false;
        }
        this.editedUser = null;
    }

    deleteUser(user: User) {
        this.adminService.deleteUserById(user.id);
        this.loadUsers();
    }

    setClickedRow(index: number) {
        this.selectedRow = index;
    }

    private loadUsers() {
        this.adminService.getAllUsers().pipe(first()).subscribe((data: User[]) => {
            this.users = data;
        });
    }

}

