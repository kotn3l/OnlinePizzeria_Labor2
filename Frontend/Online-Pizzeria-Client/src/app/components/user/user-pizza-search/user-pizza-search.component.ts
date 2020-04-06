import { Component, OnInit } from '@angular/core';

import { SearchService } from '../../../services/search.service';

@Component({
  selector: 'app-user-pizza-search',
  templateUrl: './user-pizza-search.component.html',
  styleUrls: ['./user-pizza-search.component.css']
})
export class UserPizzaSearchComponent implements OnInit {

  openSearch: boolean = false;
  nameSearch: string;
  ingredientSearch: string;

  constructor(private searchService: SearchService) { }

  ngOnInit() {
    this.searchService.currentNameSearch.subscribe(name => this.nameSearch = name);
    this.searchService.currentIngredientSearch.subscribe(ingredient => this.ingredientSearch = ingredient);
  }

  toggleSearch() {
    this.openSearch = !this.openSearch;
  }

  onNameSearchChange(name: string) {
    this.searchService.changeNameSearch(name);
  }

  onIngredientSearchChange(ingredient: string) {
    this.searchService.changeIngredientSearch(ingredient);
  }

}
