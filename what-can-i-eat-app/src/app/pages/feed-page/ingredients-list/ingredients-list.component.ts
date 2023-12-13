import { Component, Input, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { IngredientCreatorComponent } from 'src/app/objects/ingredient/ingredient-creator/ingredient-creator.component';
import { concatMap } from 'rxjs/operators'
import { FormGroup } from '@angular/forms';
import { IngredientService } from 'src/app/objects/ingredient/ingredient.service';
import { SnackbarSuccessComponent } from 'src/app/common/dialog/snackbar-success/snackbar-success.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Ingredient } from 'src/app/objects/ingredient/ingredient';
import { RecipeDetailsDialogService } from 'src/app/objects/recipe/recipe-details-dialog.service';
import { RecipeService } from 'src/app/objects/recipe/recipe.service';
import { FeedPageService } from '../feed-page.service';


@Component({
  selector: 'app-ingredients-list',
  templateUrl: './ingredients-list.component.html',
  styleUrls: ['./ingredients-list.component.scss']
})
export class IngredientsListComponent implements OnInit {
  @Input() ingredientList: Ingredient[] = [];
  ingredientsListEmpty: boolean = true;
  isLoading: boolean = false;

  constructor(
    public dialog: MatDialog,
    private ingredientService: IngredientService,
    private _snackBar: MatSnackBar,
    private recipeDetailsDialogService: RecipeDetailsDialogService,
    private recipeService: RecipeService,
    private feedPageService: FeedPageService,
  ) { }

  ngOnInit(): void {
    this.getIngredientList();
  }

  setIngredientsListEmpty(): void {
    this.ingredientList.length === 0 ? this.ingredientsListEmpty = true : this.ingredientsListEmpty = false;
  }

  openDialog() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.panelClass = "dialog-no-padding";
    dialogConfig.width = "750px";
    dialogConfig.data = {
      operationType: "Create"
    }

    const dialogRef = this.dialog.open(IngredientCreatorComponent, dialogConfig);

    dialogRef.afterClosed()
      .pipe(concatMap((data: FormGroup) => {
        let ingredient: Ingredient = {
          id: '',
          name: data.get("ingredientName")!.value,
          type: data.get("amount")!.value
        }
        return this.ingredientService.createIngredient(ingredient);
      }))
      .subscribe({
        next: (val: Ingredient) => {
          this.openSnackbarSuccess(val.name, "created");
          this.getIngredientList();
        },
        error: () => {

        }
      });
  }

  modifyIngredient(ing: Ingredient): void {
    this.ingredientService.modifyIngredient(ing).subscribe({
      next: (val) => {
        this.getIngredientList();
        this.openSnackbarSuccess(val.name, "changed")
      },
      error: () => {

      }
    })
  }

  getIngredientList(): void {
    this.isLoading = true;
    this.ingredientService.getIngredientList().subscribe({
      next: (val) => {
        this.ingredientList = val;
        this.setIngredientsListEmpty();
        this.isLoading = false;
      },
      error: () => {

      }
    })
  }

  private openSnackbarSuccess(objectName: String, operationType: String): void {
    this._snackBar.openFromComponent(SnackbarSuccessComponent, {
      data: {
        message: `Ingredient ${objectName} has been ${operationType}!`
      }
    });
  }

  generateRecipe(): void {
    this.feedPageService.contentLoadingObservable.next(true);
    this.recipeService.generateRecipeByIngredients(this.ingredientList).subscribe({
      next: (val) => {
        this.recipeDetailsDialogService.showRecipeDetailsDialog(val);
        this.feedPageService.contentLoadingObservable.next(false);
      },
      error: () => {

      }
    })
  }
}
