import { Component, OnInit } from '@angular/core';
import { Recipe } from 'src/app/model/recipe/recipe';
import { RecipeItemApi } from 'src/app/objects/recipe/recipe-item-api/recipe-item-api';
import { RecipeService } from 'src/app/objects/recipe/recipe.service';

@Component({
  selector: 'app-recipes-view-full',
  templateUrl: './recipes-view-full.component.html',
  styleUrls: ['./recipes-view-full.component.scss']
})
export class RecipesViewFullComponent implements OnInit {
  recipesList: RecipeItemApi[] = [];
  isLoading = true;

  constructor(
    private recipeService: RecipeService,
  ) { }

  ngOnInit(): void {
    this.getRecipeList();
  }  

  getRecipeList(): void {
    this.recipeService.getRecipeList().subscribe({
      next: (recipes) => {
        this.recipesList = recipes;
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
      }
    });
  }
}
