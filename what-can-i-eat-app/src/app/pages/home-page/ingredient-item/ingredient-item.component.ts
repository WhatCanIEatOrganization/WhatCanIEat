import { Component, Input, OnInit } from '@angular/core';
import { Ingredient } from 'src/app/model/ingredient/ingredient';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { DialogConfirmationComponent } from 'src/app/common/dialog/dialog-confirmation/dialog-confirmation.component';
import { IngredientCreatorComponent } from '../ingredient-creator/ingredient-creator.component';

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

  constructor(
    public dialog: MatDialog,
    
    ) { }

  ngOnInit(): void {
    
  }

  openDialog() {
    const dialogRef = this.dialog.open(DialogConfirmationComponent, { 
      data: { ingredient: this.ingredient }
    });
  }

  openIngrdientModifier() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.panelClass = "dialog-no-padding";
    dialogConfig.width = "600px";
    dialogConfig.data = {
      operationType: "Modify",
      ingredientName: this.ingredient.name,
      amount: this.ingredient.amount,
      type: this.ingredient.amountType,
    }
    
    const dialogRef = this.dialog.open(IngredientCreatorComponent, dialogConfig);

    dialogRef.afterClosed().subscribe((data) => {
      console.log(data);
    })
  }
}
