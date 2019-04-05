import {Pipe, PipeTransform} from '@angular/core';
import {User} from "../_models";

@Pipe({
    name: 'userFilter'
})
export class UserFilterPipe implements PipeTransform {
    transform(users: User[], searchText: string): User[] {
        if (!users || !searchText) {
            return users;
        }
        return users.filter(user =>
            user.firstName.toLowerCase().includes(searchText.toLowerCase()) ||
            user.lastName.toLowerCase().includes(searchText.toLowerCase()));
    }
}
