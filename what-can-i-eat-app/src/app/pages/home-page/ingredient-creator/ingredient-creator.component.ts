import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';

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

  constructor(
    private dialogRef: MatDialogRef<IngredientCreatorComponent>,
  ) { }

  ngOnInit(): void {
  }

  onFormSubmit() {
    console.log(this.ingredientCreatorForm);
  }

}
