import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { RecipeItemApi } from 'src/app/objects/recipe/recipe-item-api/recipe-item-api';
import { RecipeService } from '../recipe.service';
import { IngredientService } from '../../ingredient/ingredient.service';
import { IngredientApi } from '../../ingredient/ingredient-api';

@Component({
  selector: 'app-recipe-item-on-click',
  templateUrl: './recipe-item-on-click.component.html',
  styleUrls: ['./recipe-item-on-click.component.scss']
})
export class RecipeItemOnClickComponent implements OnInit {
  recipe: RecipeItemApi = this.data.recipe;
  panelOpenState = false;
  ingredients!: IngredientApi[];

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private recipeService: RecipeService,
    private ingredientService: IngredientService,
  ) {

   }

  ngOnInit(): void {
    this.getRecipeIngredients();
  }

  getRecipeIngredients(): void {
    this.ingredientService.getIngredientsByIds(this.data.recipe.ingredients).subscribe({
      next: (val) => {
        this.ingredients = val;
      }
    })
  }

  public toggleFavorite(): void {
    this.recipe.favorite ? this.recipe.favorite = false : this.recipe.favorite = true;
    this.recipeService.modifyRecipe(this.recipe).subscribe({
      next: (recipe) => {
        this.recipe = recipe;
      },
      error: (error) => {

      }
    })
  }

}
