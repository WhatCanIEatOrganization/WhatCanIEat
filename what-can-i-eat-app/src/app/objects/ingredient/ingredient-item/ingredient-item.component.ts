import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Ingredient } from 'src/app/model/ingredient/ingredient';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { DialogConfirmationComponent } from 'src/app/common/dialog/dialog-confirmation/dialog-confirmation.component';
import { IngredientCreatorComponent } from '../ingredient-creator/ingredient-creator.component';
import { IngredientService } from '../ingredient.service';
import { concatMap, defaultIfEmpty, filter, map, switchMap, tap } from 'rxjs';

export interface IngredientCreatorData {
  operationType: string,
  ingredientName: string,
  amount: string,
  type: string,
}

@Component({
  selector: 'app-ingredient-item',
  templateUrl: './ingredient-item.component.html',
  styleUrls: ['./ingredient-item.component.scss']
})
export class IngredientItemComponent implements OnInit {
  @Input() ingredient!: Ingredient;
  @Output() public removeIngredient = new EventEmitter<Ingredient>();
  @Output() public modifyIngredient = new EventEmitter<Ingredient>();

  constructor(
    public dialog: MatDialog,
    private ingredientService: IngredientService,
    ) { }

  ngOnInit(): void {
    
  }

  openDialog() {
    const dialogRef = this.dialog.open(DialogConfirmationComponent, { 
      data: { ingredient: this.ingredient }
    });
    
    dialogRef.afterClosed()
    .pipe(
        filter((val) => val === true),
        concatMap(() => { return this.ingredientService.deleteIngredient(this.ingredient)})
    ).subscribe({
      next: (val) => {
        this.removeIngredient.emit(val);
      },
      error: () => console.log("error")
    })
  }

  openIngrdientModifier() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.panelClass = "dialog-no-padding";
    dialogConfig.width = "750px";
    dialogConfig.data = {
      operationType: "Modify",
      ingredientName: this.ingredient.name,
      amount: this.ingredient.amount,
      type: this.ingredient.unitMeasure,
    }
    
    const dialogRef = this.dialog.open(IngredientCreatorComponent, dialogConfig);

    dialogRef.afterClosed()
      .pipe(map(
        (val) => {
          let ing: Ingredient = {
            id: this.ingredient.id,
            name: val.get('ingredientName').value,
            amount: val.get('amount').value,
            unitMeasure: val.get('type').value
          }
          return ing;
        }
      ))
      .subscribe(val => {
        this.modifyIngredient.emit(val)
      });
  }
}
