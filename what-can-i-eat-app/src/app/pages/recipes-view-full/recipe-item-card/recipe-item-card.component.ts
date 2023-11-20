import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Recipe } from 'src/app/model/recipe/recipe';
import { RecipeItemOnClickComponent } from '../../../objects/recipe/recipe-item-on-click/recipe-item-on-click.component';
import { RecipeService } from 'src/app/objects/recipe/recipe.service';
import { filter, concatMap } from 'rxjs';
import { DialogConfirmationComponent } from 'src/app/common/dialog/dialog-confirmation/dialog-confirmation.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SnackbarSuccessComponent } from 'src/app/common/dialog/snackbar-success/snackbar-success.component';
import { RecipeItemApi } from 'src/app/objects/recipe/recipe-item-api/recipe-item-api';
import { RecipeDetailsDialogService } from 'src/app/objects/recipe/recipe-details-dialog.service';

@Component({
  selector: 'app-recipe-item-card',
  templateUrl: './recipe-item-card.component.html',
  styleUrls: ['./recipe-item-card.component.scss']
})
export class RecipeItemCardComponent implements OnInit {
  @Input() recipe!: RecipeItemApi;
  @Output() public delete = new EventEmitter<RecipeItemApi>();
  hasImage!: boolean;

  constructor(
    private dialog: MatDialog,
    private recipeService: RecipeService,
    private _snackBar: MatSnackBar,
    private recipeDetailsDialogService: RecipeDetailsDialogService
  ) { }

  ngOnInit(): void {
    this.hasImage = this.recipe.imageUrl != null
  }

  onRecipeCardClick(): void {
    this.recipeDetailsDialogService.showRecipeDetailsDialog(this.recipe);
  }

  
  deleteRecipe(): void {
    const dialogRef = this.dialog.open(DialogConfirmationComponent, { 
      data: { 
        objectType: "recipe",
        objectName: this.recipe.name
      }
    });
    
    dialogRef.afterClosed()
    .pipe(
        filter((val) => val === true),
        concatMap(() => { return this.recipeService.deleteRecipe(this.recipe)})
    ).subscribe({
      next: (val) => {
        this.delete.emit();
        this.openSnackbarSuccess(this.recipe);
      },
      error: () => console.log("error")
    })
  }

  private openSnackbarSuccess(recipe: RecipeItemApi): void {
    this._snackBar.openFromComponent(SnackbarSuccessComponent, {
      data: {
        message: `Recipe ${recipe.name} has been deleted!`
      }
    });
  }

  public toggleFavorite(): void {
    this.recipe.favorite ? this.recipe.favorite = false : this.recipe.favorite = true;
    this.recipeService.modifyRecipe(this.recipe).subscribe({
      next: (recipe) => {
        this.recipe = recipe;
      },
      error: () => {
        console.log("Something went wrong");
      }
    })
  }
}
