import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable, debounceTime, map, startWith } from 'rxjs';
import { BasicIngredient } from 'src/app/model/basic-ingredient/basicIngredient';
import { IngredientService } from 'src/app/objects/ingredient/ingredient.service';

@Component({
  selector: 'app-recipes-search-box-by-ingredients',
  templateUrl: './recipes-search-box-by-ingredients.component.html',
  styleUrls: ['./recipes-search-box-by-ingredients.component.scss']
})

export class RecipesSearchBoxByIngredientsComponent implements OnInit {
  filteredOptions!: Observable<BasicIngredient[]>;
  myControl = new FormControl<string | BasicIngredient>('');
  options: BasicIngredient[] = [];
  formGroup!: FormGroup;
  isIngredientListLoading: boolean = true;
  searchArr: BasicIngredient[] = [];
  isDisabled: boolean = true;
  inputError: boolean = false;

  constructor(
    private ingredientService : IngredientService,
  ) { }

  ngOnInit(): void {
    this.ingredientService.getIngredientTagsList().subscribe(value => {
      this.options = value;
    });
    
    this.formGroup = new FormGroup({
      myControl: new FormControl<string | BasicIngredient>(''),
    });

    this.filteredOptions = this.myControl.valueChanges.pipe(
      debounceTime(150),
      startWith(''),
      map(value => {
        this.isDisabled = typeof value === 'string';
        const name = typeof value === 'string' ? value : value?.name;
        return name ? this._filter(name as string) : this.options.slice();
      }),
    );
  }

  private _filter(name: string): BasicIngredient[] {
    const filterValue = name.toLowerCase();

    return this.options.filter(option => option.name.toLowerCase().includes(filterValue));
  }


  addIngredient(): void {
    let controlAsIngredient = this.myControl.value as BasicIngredient;

    let isFound: boolean = false;

    this.searchArr.forEach(el => {
      if(el.name === controlAsIngredient.name) {
        isFound = true;
      }
    });

    isFound ? this.inputError = true : this.searchArr.push(controlAsIngredient);

    setTimeout(() => {
      this.inputError = false;
    }, 3500);
    
    this.myControl = new FormControl<string | BasicIngredient>('');
    this.ngOnInit();
  }

  removeIng(ingToRemove: BasicIngredient): void {
    this.searchArr.splice(this.searchArr.indexOf(ingToRemove), 1);
  }

  displayFn(ingredient: BasicIngredient): string {
    return ingredient && ingredient.name ? ingredient.name : '';
  }
}
