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
  options: Ingredient[] = [{name: 'Tomato'}, {name: 'Pasta'}, {name: 'Onion'}];
  formGroup!: FormGroup;

  searchArr: Ingredient[] = [];
  isDisabled = true;

  constructor() { }

  ngOnInit(): void {
    this.formGroup = new FormGroup({
      myControl: new FormControl<string | Ingredient>('')
    });

    this.filteredOptions = this.myControl.valueChanges.pipe(
      debounceTime(350),
      startWith(''),
      map(value => {
        this.isDisabled = typeof value === 'string';
        const name = typeof value === 'string' ? value : value?.name;
        return name ? this._filter(name as string) : this.options.slice();
      }),
    );
  }

  private _filter(name: string): Ingredient[] {
    const filterValue = name.toLowerCase();

    return this.options.filter(option => option.name.toLowerCase().includes(filterValue));
  }


  addIngredient(): void {
    let controlAsIngredient = this.myControl.value as Ingredient;

    this.searchArr.indexOf(controlAsIngredient) < 0 ? this.searchArr.push(controlAsIngredient) : console.log("show error");
    
    this.myControl = new FormControl<string | Ingredient>('');
    this.ngOnInit();
  }

  removeIng(ingToRemove: Ingredient): void {
    this.searchArr.splice(this.searchArr.indexOf(ingToRemove), 1);
  }

  check(): void {
    console.log(typeof this.myControl.value === "object");
    this.addIngredient();
  }

  displayFn(ingredient: Ingredient): string {
    return ingredient && ingredient.name ? ingredient.name : '';
  }
}
