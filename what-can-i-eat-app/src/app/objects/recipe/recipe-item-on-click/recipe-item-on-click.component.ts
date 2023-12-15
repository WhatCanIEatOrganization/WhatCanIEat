import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { RecipeItemApi } from 'src/app/objects/recipe/recipe-item-api/recipe-item-api';
import { RecipeService } from '../recipe.service';
import { IngredientService } from '../../ingredient/ingredient.service';
import { Ingredient } from '../../ingredient/ingredient';
import { Recipe } from '../models/recipe/recipe';

@Component({
  selector: 'app-recipe-item-on-click',
  templateUrl: './recipe-item-on-click.component.html',
  styleUrls: ['./recipe-item-on-click.component.scss']
})
export class RecipeItemOnClickComponent implements OnInit {
  recipe: Recipe = this.data.recipe;
  panelOpenState = false;
  ingredients!: Ingredient[];
  hasImage!: boolean;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private recipeService: RecipeService,
    private ingredientService: IngredientService,
  ) {

   }

  ngOnInit(): void {
    // this.getRecipeIngredients();
    this.hasImage = this.recipe.imageUrl != null && this.recipe.imageUrl != "";
  }

  getRecipeIngredients(): void {
    console.log(this.data.recipe.ingredients);

    console.log(typeof this.data.recipe.ingredients)
    // this.ingredientService.getIngredientsByIds(this.data.recipe.ingredients).subscribe({
    //   next: (val) => {
    //     this.ingredients = val;
    //   }
    // })
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
