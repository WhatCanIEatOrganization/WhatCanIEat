import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable, debounceTime, map, startWith } from 'rxjs';
import { BasicIngredient } from 'src/app/model/basic-ingredient/basicIngredient';
import { IngredientService } from 'src/app/objects/ingredient/ingredient.service';
import { RecipeService } from 'src/app/objects/recipe/recipe.service';
import { RecipesSearchDialogComponent } from './recipes-search-dialog/recipes-search-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { RecipeItemApi } from 'src/app/objects/recipe/recipe-item-api/recipe-item-api';
import { animate, style, transition, trigger } from '@angular/animations';

@Component({
  selector: 'app-recipes-search-box-by-ingredients',
  templateUrl: './recipes-search-box-by-ingredients.component.html',
  styleUrls: ['./recipes-search-box-by-ingredients.component.scss'],
  animations: [
    trigger(
      'inOutAnimation', 
      [
        transition(
          ':enter', 
          [
            style({ opacity: 0 }),
            animate('0.3s ease-out', 
                    style({ opacity: 1 }))
          ]
        ),
        transition(
          ':leave', 
          [
            style({ opacity: 1 }),
            animate('0.3s ease-in', 
                    style({  opacity: 0 }))
          ]
        )
      ]
    )
  ]
})

export class RecipesSearchBoxByIngredientsComponent implements OnInit {
  filteredOptions!: Observable<BasicIngredient[]>;
  myControl = new FormControl<string | BasicIngredient>('');
  options: BasicIngredient[] = [];
  formGroup!: FormGroup;
  isIngredientListLoading: boolean = true;
  searchArr: string[] = [];
  isDisabled: boolean = true;
  inputError: boolean = false;

  constructor(
    private ingredientService : IngredientService,
    private recipeService: RecipeService,
    private dialog: MatDialog,
  ) { }

  ngOnInit(): void {
    this.ingredientService.getIngredientTagsList().subscribe(value => {
      this.options = value;
    });
    
    this.formGroup = new FormGroup({
      myControl: new FormControl<string | BasicIngredient>(''),
    });

    this.filteredOptions = this.myControl.valueChanges.pipe(
      // debounceTime(150),
      startWith(''),
      map(value => {
        this.isDisabled = value === '';
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

    let controlAsIngredient: string;
    typeof this.myControl.value === 'string' ? controlAsIngredient = this.myControl.value : controlAsIngredient = this.myControl.value!.name;

    let isFound: boolean = false;

    this.searchArr.forEach(el => {
      if(el === controlAsIngredient) {
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

  removeIng(ingToRemove: string): void {
    this.searchArr.splice(this.searchArr.indexOf(ingToRemove), 1);
  }

  displayFn(ingredient: BasicIngredient): string {
    return ingredient && ingredient.name ? ingredient.name : '';
  }

  searchRecipes(): void {
    this.recipeService.getRecipesByIngredients(this.searchArr).subscribe((recipesFound) => {
      this.onRecipeCardClick(recipesFound);
    });
  };

  onRecipeCardClick(recipesFound: RecipeItemApi[]): void {
    const dialogRef = this.dialog.open(RecipesSearchDialogComponent, {
      panelClass: 'custom-modalbox',
      data: {
        recipesFound
      }
    });
  }
}
