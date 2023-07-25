import { Component, OnInit } from '@angular/core';
import { Recipe } from 'src/app/model/recipe/recipe';
import { RecipesHistoryViewService } from './recipes-history-view.service';
import { RecipeService } from 'src/app/objects/recipe/recipe.service';

@Component({
  selector: 'app-recipes-history',
  templateUrl: './recipes-history.component.html',
  styleUrls: ['./recipes-history.component.scss']
})
export class RecipesHistoryComponent implements OnInit {
  recipe!: Recipe;
  isLoading: boolean = true;

  constructor(
    private recipeService: RecipeService,
  ) { }

  ngOnInit(): void {
    this.getRandomRecipe();
  }

  getRandomRecipe(): void {
    this.recipeService.getRandomRecipe().subscribe({
      next: (recipe) => {
        this.recipe = recipe;
        this.isLoading = false;

      },
      error: () => {
        console.log("error");
        this.isLoading = true;
      }
    })
  }
}
