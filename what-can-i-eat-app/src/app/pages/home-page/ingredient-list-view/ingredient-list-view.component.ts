import { Component, Input, OnInit } from '@angular/core';
import { Ingredient } from 'src/app/model/ingredient/ingredient';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { IngredientCreatorComponent } from 'src/app/objects/ingredient/ingredient-creator/ingredient-creator.component';
import { concatMap, map, mergeMap } from 'rxjs/operators'
import { FormGroup } from '@angular/forms';
import { IngredientService } from 'src/app/objects/ingredient/ingredient.service';
import { HttpResponse } from '@angular/common/http';


@Component({
  selector: 'app-ingredient-list-view',
  templateUrl: './ingredient-list-view.component.html',
  styleUrls: ['./ingredient-list-view.component.scss']
})
export class IngredientListViewComponent implements OnInit {
  @Input() ingredientList: Ingredient[] = [];
  ingredientsListEmpty: boolean = false;
  isLoading: boolean = false;
  
  constructor(
    public dialog: MatDialog,
    private ingredientService: IngredientService,
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
        next: (val: HttpResponse<Ingredient>) => {
          console.log("Ingredient created" + val);
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
      },
      error: () => console.log("Something went wrong!")
    })
  }

  getIngredientList(): void {
    // this.isLoading = true;
    this.ingredientService.getIngredientList().subscribe({
      next: (val) => {
        this.ingredientList = val;
        this.setIngredientsListEmpty();
        // this.isLoading = false;
      },
      error: () => {
        console.log("smh went wrong");
      }
    })
  }
}
