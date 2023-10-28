import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { RecipeItemApi } from 'src/app/objects/recipe/recipe-item-api/recipe-item-api';

@Component({
  selector: 'app-recipe-item-on-click',
  templateUrl: './recipe-item-on-click.component.html',
  styleUrls: ['./recipe-item-on-click.component.scss']
})
export class RecipeItemOnClickComponent implements OnInit {
  recipe!: RecipeItemApi;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any 
  ) { }

  ngOnInit(): void {
    this.recipe = this.data.recipe;
  }

}
