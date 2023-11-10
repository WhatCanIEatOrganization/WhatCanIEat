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
  ingredientsAmount!: string;
  hasImage: boolean = false;
  preparationTime!: string;

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
        this.hasImage = val.imageUrl != null || val.imageUrl != undefined;
        this.preparationTime = this.setPreparationTime(val);
        this.ingredientsAmount = this.setIngredientsAmount(val);
        this.isLoading = false;
      },
      error: () => {

      }
    })
  }

  setPreparationTime(recipe: RecipeItemApi): string {
    let preparationTime: string;
    recipe.preptime === 0 ? preparationTime = "not specified." : preparationTime = recipe.preptime + " min.";

    return preparationTime;
  }

  setIngredientsAmount(recipe: RecipeItemApi): string {
    let ingredientsAmount: string;
    recipe.ingredients.length === 0 ? ingredientsAmount = "not specified." : ingredientsAmount = recipe.ingredients.length + " products.";

    return ingredientsAmount;
  }

  onRecipeCardClick(): void {
    this.recipeDetailsDialogService.showRecipeDetailsDialog(this.recipe);
  }
}
