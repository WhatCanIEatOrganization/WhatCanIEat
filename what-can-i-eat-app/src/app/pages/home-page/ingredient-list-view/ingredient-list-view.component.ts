import { Component, Input, OnInit } from '@angular/core';
import { Ingredient } from 'src/app/model/ingredient/ingredient';

@Component({
  selector: 'app-ingredient-list-view',
  templateUrl: './ingredient-list-view.component.html',
  styleUrls: ['./ingredient-list-view.component.scss']
})
export class IngredientListViewComponent implements OnInit {
  @Input() ingredientList: Ingredient[] = [];

  constructor() { }

  ngOnInit(): void {
  }

}
