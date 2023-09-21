import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Observable, Subscription, debounceTime, map, startWith } from 'rxjs';
import { MatAutocompleteModule } from '@angular/material/autocomplete';

@Component({
  selector: 'app-recipes-search-box-by-ingredients',
  templateUrl: './recipes-search-box-by-ingredients.component.html',
  styleUrls: ['./recipes-search-box-by-ingredients.component.scss']
})
export class RecipesSearchBoxByIngredientsComponent implements OnInit {
  chosenIngredients: string[] = ["tomatos"];
  apiIngredients: string[] = ["tomatos", "pasta", "potatos"];
  apiIngredientsFiltered!: Observable<string[]>;
  searchControl = new FormControl();

  constructor() { }

  ngOnInit(): void {
    this.apiIngredientsFiltered = this.searchControl.valueChanges.pipe(
      debounceTime(350),
      startWith(''),
      map(value => this._filter(value || '')),
    );
  }

  addIngredient(): void {
    this.chosenIngredients.push(this.searchControl.value);
  }

  removeIng(ingToRemove: string): void {
    this.chosenIngredients.reduce()this.chosenIngredients.find((el) => {
      return el === ingToRemove
    })
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.apiIngredients.filter(ing => ing.toLowerCase().includes(filterValue));
  }
}
