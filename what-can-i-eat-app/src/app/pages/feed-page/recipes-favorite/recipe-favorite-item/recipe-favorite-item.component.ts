import { Component, Input, OnInit } from '@angular/core';
import { Recipe } from 'src/app/model/recipe/recipe';

@Component({
  selector: 'app-recipe-favorite-item',
  templateUrl: './recipe-favorite-item.component.html',
  styleUrls: ['./recipe-favorite-item.component.scss']
})
export class RecipeFavoriteItemComponent implements OnInit {
  @Input() recipe!: Recipe;

  constructor() { }

  ngOnInit(): void {

  }

  public toggleFavorite(): void {
    
  }

}
