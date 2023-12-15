import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { RecipeItemOnClickComponent } from 'src/app/objects/recipe/recipe-item-on-click/recipe-item-on-click.component';
import { RecipeItemApi } from './recipe-item-api/recipe-item-api';
import { Recipe } from './models/recipe/recipe';

@Injectable({
  providedIn: 'root'
})
export class RecipeDetailsDialogService {

  constructor(
    private dialog: MatDialog,
  ) { }

  showRecipeDetailsDialog(recipe: Recipe | RecipeItemApi): void {
    const dialogRef = this.dialog.open(RecipeItemOnClickComponent, {
      data: {
        recipe: recipe
      }
    });
  }
}
