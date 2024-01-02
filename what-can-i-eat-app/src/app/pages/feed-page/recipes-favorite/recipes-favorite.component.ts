import { Component, OnInit } from '@angular/core';
import { RecipeItemApi } from 'src/app/objects/recipe/recipe-item-api/recipe-item-api';
import { RecipeService } from 'src/app/objects/recipe/recipe.service';

@Component({
  selector: 'app-recipes-favorite',
  templateUrl: './recipes-favorite.component.html',
  styleUrls: ['./recipes-favorite.component.scss']
})
export class RecipesFavoriteComponent implements OnInit {
  recipesList: RecipeItemApi[] = [];
  isLoading: boolean = true;

  constructor(
    private recipesService: RecipeService,
  ) { }

  ngOnInit(): void {
    this.getFavoriteRecipes();
  }

  getFavoriteRecipes(): void {
    this.recipesService.getFavoriteRecipes().subscribe({
      next: (val) => {
        this.recipesList = val.slice(0, 3);
        this.isLoading = false;
      },
      error: () => {

      }
    })
  }

}
