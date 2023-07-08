import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Recipe } from 'src/app/model/recipe/recipe';
import { RecipeItemOnClickComponent } from '../recipe-item-on-click/recipe-item-on-click.component';

@Component({
  selector: 'app-recipe-item-card',
  templateUrl: './recipe-item-card.component.html',
  styleUrls: ['./recipe-item-card.component.scss']
})
export class RecipeItemCardComponent implements OnInit {
  @Input() recipe!: Recipe;
  favorite = false;

  constructor(
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
  }

  onRecipeCardClick(): void {
    const dialogRef = this.dialog.open(RecipeItemOnClickComponent)
  }
}
