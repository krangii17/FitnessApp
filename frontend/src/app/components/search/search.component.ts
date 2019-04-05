import { Component, OnInit } from '@angular/core';
import {CoachService} from "../../_services";
import {first} from "rxjs/operators";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  users = [];
  searchStr = '';

  constructor(private coachService: CoachService) {}

  ngOnInit() {
    this.coachService.getUsers()
        .pipe(first()).subscribe( data => {
      this.users = data;
    });
  }

}
