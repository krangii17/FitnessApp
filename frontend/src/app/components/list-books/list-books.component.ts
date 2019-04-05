import {Component, OnInit} from '@angular/core';

export class Book {

    constructor(
        public id: number,
        public name: string
    ) {
    }
}

@Component({
    selector: 'app-list-books',
    templateUrl: './list-books.component.html',
    styleUrls: ['./list-books.component.css']
})
export class ListBooksComponent implements OnInit {
    books = [
        new Book(1, 'javainuse'),
        new Book(3, 'fountainhead')
    ];

    constructor() {
    }

    ngOnInit() {
    }
}
