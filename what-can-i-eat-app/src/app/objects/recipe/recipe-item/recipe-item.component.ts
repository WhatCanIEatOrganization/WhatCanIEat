import { Component, Input, OnInit } from '@angular/core';
import { Recipe } from 'src/app/model/recipe/recipe';
import { RecipeItemApi } from '../recipe-item-api/recipe-item-api';

@Component({
  selector: 'app-recipe-item',
  templateUrl: './recipe-item.component.html',
  styleUrls: ['./recipe-item.component.scss']
})
export class RecipeItemComponent implements OnInit {
  @Input() recipe!: RecipeItemApi;

  constructor() { }

  ngOnInit(): void {
  }

}
