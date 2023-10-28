import { Component, Input, OnInit } from '@angular/core';
import { Recipe } from 'src/app/model/recipe/recipe';
import { RecipeService } from 'src/app/objects/recipe/recipe.service';
import { MatDialog } from '@angular/material/dialog';
import { RecipeItemOnClickComponent } from '../../../objects/recipe/recipe-item-on-click/recipe-item-on-click.component';
import { RecipeDetailsDialogService } from 'src/app/objects/recipe/recipe-details-dialog.service';
import { RecipeItemApi } from 'src/app/objects/recipe/recipe-item-api/recipe-item-api';

@Component({
  selector: 'app-featured-meal',
  templateUrl: './featured-meal.component.html',
  styleUrls: ['./featured-meal.component.scss']
})
export class FeaturedMealComponent implements OnInit {
  recipe!: RecipeItemApi;
  isLoading: boolean = true;
  ingredientsAmount!: number;

  constructor(
    private dialog: MatDialog,
    private recipeDetailsDialogService: RecipeDetailsDialogService,
    private recipeService: RecipeService) { }

  ngOnInit(): void {
    this.getRecipe();
  }

  getRecipe(): void {
    this.recipeService.getRandomRecipe().subscribe({
      next: (val) => {
        this.recipe = val;
        this.ingredientsAmount = 0;
        // this.setIngredientsAmount(val);
        this.isLoading = false;
      },
      error: () => {

      }
    })
  }

  setIngredientsAmount(recipe: Recipe): void {
    this.ingredientsAmount = recipe.ingredientList.length;
  }

  onRecipeCardClick(): void {
    this.recipeDetailsDialogService.showRecipeDetailsDialog(this.recipe);
  }
}
