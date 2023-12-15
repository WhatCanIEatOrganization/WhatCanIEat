import { Component, Input, OnInit } from '@angular/core';
import { RecipeDetailsDialogService } from 'src/app/objects/recipe/recipe-details-dialog.service';
import { RecipeItemApi } from 'src/app/objects/recipe/recipe-item-api/recipe-item-api';
import { RecipeService } from 'src/app/objects/recipe/recipe.service';

@Component({
  selector: 'app-recipe-favorite-item',
  templateUrl: './recipe-favorite-item.component.html',
  styleUrls: ['./recipe-favorite-item.component.scss']
})
export class RecipeFavoriteItemComponent implements OnInit {
  @Input() recipe!: RecipeItemApi;

  constructor(
    private recipeDialogService: RecipeDetailsDialogService,
    private recipeService: RecipeService,
  ) { }

  ngOnInit(): void {

  }

  public toggleFavorite(): void {
    
  }

  onRecipeCardClick(): void {
    this.recipeDialogService.showRecipeDetailsDialog(this.recipe);
  }
}
