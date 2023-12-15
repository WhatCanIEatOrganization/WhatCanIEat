import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { RecipeItemApi } from 'src/app/objects/recipe/recipe-item-api/recipe-item-api';
import { RecipeService } from '../recipe.service';
import { IngredientService } from '../../ingredient/ingredient.service';
import { Ingredient } from '../../ingredient/ingredient';
import { Recipe } from '../models/recipe/recipe';
import { UserRecipe } from '../user-recipe/user-recipe';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SnackbarSuccessComponent } from 'src/app/common/dialog/snackbar-success/snackbar-success.component';

@Component({
  selector: 'app-recipe-item-on-click',
  templateUrl: './recipe-item-on-click.component.html',
  styleUrls: ['./recipe-item-on-click.component.scss']
})
export class RecipeItemOnClickComponent implements OnInit {
  recipe: Recipe | RecipeItemApi = this.data.recipe;
  panelOpenState = false;
  ingredients!: Ingredient[];
  hasImage!: boolean;
  showSaveButton!: boolean;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private recipeService: RecipeService,
    private ingredientService: IngredientService,
    private _snackBar: MatSnackBar,
  ) {

   }

  ngOnInit(): void {
    this.getRecipeIngredients();
    this.hasImage = this.recipe.imageUrl != null && this.recipe.imageUrl != "";
    this.showSaveButton = this.data.isChatGptGenerated;
  }

  getRecipeIngredients(): void {
    if(typeof this.recipe.ingredients[0] === 'number') {
          this.ingredientService.getIngredientsByIds(this.data.recipe.ingredients).subscribe({
      next: (val) => {
        this.ingredients = val;
        }
      })
    } else {
      this.ingredients = this.data.recipe.ingredients;
    }
  }

  saveRecipe(): void {
    let recipe: Recipe = this.data.recipe;

    let userRecipe: UserRecipe = {
      ...recipe,
    }

    this.recipeService.createRecipe(userRecipe).subscribe({
      next: (value: UserRecipe) => {
        console.log(value);
        this.openSnackbar("Recipe " + value.name + " has been created succesfully!");
        this.showSaveButton = false;
      },
      error: () => {
        this.openSnackbar("While creating recipe " + recipe.name + " something went wrong, please try again!");
      }
    });
  }

  private openSnackbar(messageToShow: String): void {
    this._snackBar.openFromComponent(SnackbarSuccessComponent, {
      data: {
        message: messageToShow
      }
    });
  }

  public toggleFavorite(): void {
    this.recipe.favorite ? this.recipe.favorite = false : this.recipe.favorite = true;
    // this.recipeService.modifyRecipe(this.recipe).subscribe({
    //   next: (recipe) => {
    //     this.recipe = recipe;
    //   },
    //   error: (error) => {

    //   }
    // })
  }

}
