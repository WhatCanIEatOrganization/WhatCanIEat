import { Component, Input, OnInit } from '@angular/core';
import { Ingredient } from 'src/app/model/ingredient/ingredient';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { IngredientCreatorComponent } from 'src/app/objects/ingredient/ingredient-creator/ingredient-creator.component';
import { concatMap } from 'rxjs/operators'
import { FormGroup } from '@angular/forms';
import { IngredientService } from 'src/app/objects/ingredient/ingredient.service';
import { SnackbarSuccessComponent } from 'src/app/common/dialog/snackbar-success/snackbar-success.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { IngredientPayLoad } from 'src/app/model/ingredient/ingredientPayload';


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
    private _snackBar: MatSnackBar
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
          id: 0,
          name: data.get("ingredientName")!.value,
          amount: data.get("amount")!.value,
          unitMeasure: data.get("type")!.value
        }
        return this.ingredientService.createIngredient(ingredient);
      }))
      .subscribe({
        next: (val: IngredientPayLoad) => {
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
}
