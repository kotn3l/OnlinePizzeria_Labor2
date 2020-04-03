import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  private nameSearchSource = new BehaviorSubject<string>('');
  currentNameSearch = this.nameSearchSource.asObservable();

  private ingredientSearchSource = new BehaviorSubject<string>('');
  currentIngredientSearch = this.ingredientSearchSource.asObservable();

  constructor() { }

  changeNameSearch(name: string) {
    this.nameSearchSource.next(name);
  }

  changeIngredientSearch(ingredient: string) {
    this.ingredientSearchSource.next(ingredient);
  }
}
