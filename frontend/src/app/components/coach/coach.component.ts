import { Component, Input  } from '@angular/core';

@Component({
  selector: 'app-coach',
  templateUrl: './coach.component.html',
  styleUrls: ['./coach.component.css']
})
export class CoachComponent {

  isChecked = false;

  @Input() coachComponent;

  onClick() {
    this.isChecked = !this.isChecked;
    console.log(this.isChecked);

  }

}
