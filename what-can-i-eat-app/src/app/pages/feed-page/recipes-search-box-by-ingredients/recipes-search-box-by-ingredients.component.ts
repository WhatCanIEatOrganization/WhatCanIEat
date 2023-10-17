import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Observable, debounceTime, map, startWith } from 'rxjs';

export interface Ingredient {
  name: string;
}


@Component({
  selector: 'app-recipes-search-box-by-ingredients',
  templateUrl: './recipes-search-box-by-ingredients.component.html',
  styleUrls: ['./recipes-search-box-by-ingredients.component.scss']
})

export class RecipesSearchBoxByIngredientsComponent implements OnInit {
  filteredOptions!: Observable<Ingredient[]>;
  myControl = new FormControl<string | Ingredient>('');
  options: Ingredient[] = [{name: 'Mary'}, {name: 'Shelley'}, {name: 'Igor'}];
  formGroup!: FormGroup;






  
  // chosenIngredients: string[] = ["tomatos"];
  // apiIngredients: string[] = ["tomatos", "pasta", "potatos"];
  // apiIngredientsFiltered!: Observable<string[]>;
  // searchControl = new FormControl();
  // isFoundInDatabase = false;
  // isNoMoreSearchIngredients = false;
  // isDisabled = true;

  constructor() { }

  ngOnInit(): void {
    this.formGroup = new FormGroup({
      myControl: new FormControl<string | Ingredient>('')
    });

    this.filteredOptions = this.myControl.valueChanges.pipe(
      startWith(''),
      map(value => {
        const name = typeof value === 'string' ? value : value?.name;
        return name ? this._filter(name as string) : this.options.slice();
      }),
    );

    
    
    // this.apiIngredientsFiltered = this.searchControl.valueChanges.pipe(
    //   debounceTime(350),
    //   startWith(''), 
    //   map(value => this._filter(value)),
    // );
  }

  private _filter(name: string): Ingredient[] {
    const filterValue = name.toLowerCase();

    return this.options.filter(option => option.name.toLowerCase().includes(filterValue));
  }


  addIngredient(): void {
    // if(this.chosenIngredients.length > 5) {
    //   this.isNoMoreSearchIngredients = true;
    //   this.searchControl = new FormControl();
    // } else {
    //   this.apiIngredientsFiltered.subscribe(val => console.log(val));
    //   this.chosenIngredients.push(this.searchControl.value);
    //   this.searchControl = new FormControl();
    //   this.ngOnInit();
    // };
  }

  removeIng(ingToRemove: string): void {
    // this.chosenIngredients.splice(this.chosenIngredients.indexOf(ingToRemove), 1);
  }

  // private _filter(value: string): string[] {
  //   const filterValue = value.toLowerCase();
  //   if(value.length < 2) {
  //     this.isDisabled = true;
  //     return []
  //   } else {
  //     this.isDisabled = false;
  //     return this.apiIngredients.filter(ing => ing.toLowerCase().includes(filterValue));
  //   }
  // }

  check(): void {
    console.log(typeof this.myControl.value === "object");
  }

  displayFn(ingredient: Ingredient): string {
    return ingredient && ingredient.name ? ingredient.name : '';
  }
}
