import { Component, Input, OnInit } from '@angular/core';
import { Ingredient } from 'src/app/model/ingredient/ingredient';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { IngredientCreatorComponent } from 'src/app/objects/ingredient/ingredient-creator/ingredient-creator.component';

@Component({
  selector: 'app-ingredient-list-view',
  templateUrl: './ingredient-list-view.component.html',
  styleUrls: ['./ingredient-list-view.component.scss']
})
export class IngredientListViewComponent implements OnInit {
  @Input() ingredientList: Ingredient[] = [];
  
  constructor(
    public dialog: MatDialog,
  ) { }

  ngOnInit(): void {
  }

  openDialog() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.panelClass = "dialog-no-padding";
    dialogConfig.width = "750px";
    dialogConfig.data = {
      operationType: "Create"
    }

    const dialogRef = this.dialog.open(IngredientCreatorComponent, dialogConfig);

    dialogRef.afterClosed().subscribe((data) => {
      console.log(data);
    })
  }
}
