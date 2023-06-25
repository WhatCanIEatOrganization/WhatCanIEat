import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

export interface IngredientCreatorData {
  operationType: string,
  ingredientName: string,
  amount: string,
  type: string,
}

@Component({
  selector: 'app-ingredient-creator',
  templateUrl: './ingredient-creator.component.html',
  styleUrls: ['./ingredient-creator.component.scss']
})
export class IngredientCreatorComponent implements OnInit {
  ingredientCreatorForm = new FormGroup({
    ingredientName: new FormControl(''),
    amount: new FormControl(''),
    type: new FormControl(''),
  });

  dialogTypeOfOperation: string | undefined;

  constructor(
    private dialogRef: MatDialogRef<IngredientCreatorComponent>,
    @Inject(MAT_DIALOG_DATA) public data: IngredientCreatorData
  ) { }

  ngOnInit(): void {
    this.setFormWithValues();
    this.dialogTypeOfOperation = this.data.operationType;
  }

  onFormSubmit() {
    console.log(this.ingredientCreatorForm);
  }

  setFormWithValues(): void {
    if(this.data != null) {
      this.ingredientCreatorForm.setValue({
        ingredientName: this.data.ingredientName,
        amount: this.data.amount,
        type: this.data.type
      })
    }
  }
}
