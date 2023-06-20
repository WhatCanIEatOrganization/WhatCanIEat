import { Component, Inject, Input, OnInit } from '@angular/core';
import { Ingredient } from 'src/app/model/ingredient/ingredient';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { DialogConfirmationComponent } from 'src/app/common/dialog/dialog-confirmation/dialog-confirmation.component';

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
}
