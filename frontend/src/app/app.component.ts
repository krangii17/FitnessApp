import {Component} from '@angular/core';
import {MenuComponent} from './components';
import {from} from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'library-management';
  message = 'hii';
}
