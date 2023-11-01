import { Component, Input, OnInit } from '@angular/core';
import { Recipe } from 'src/app/model/recipe/recipe';
import { RecipeDetailsDialogService } from 'src/app/objects/recipe/recipe-details-dialog.service';
import { RecipeItemApi } from 'src/app/objects/recipe/recipe-item-api/recipe-item-api';

@Component({
  selector: 'app-recipe-favorite-item',
  templateUrl: './recipe-favorite-item.component.html',
  styleUrls: ['./recipe-favorite-item.component.scss']
})
export class RecipeFavoriteItemComponent implements OnInit {
  @Input() recipe!: RecipeItemApi;

  constructor(
    private recipeDialogService: RecipeDetailsDialogService,
  ) { }

  ngOnInit(): void {

  }

  public toggleFavorite(): void {
    
  }

  onRecipeCardClick(): void {
    this.recipeDialogService.showRecipeDetailsDialog(this.recipe);
  }
}
