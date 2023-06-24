import { Component, OnInit } from '@angular/core';
import { Recipe } from 'src/app/model/recipe/recipe';
import { RecipesViewFullService } from './recipes-view-full.service';

@Component({
  selector: 'app-recipes-view-full',
  templateUrl: './recipes-view-full.component.html',
  styleUrls: ['./recipes-view-full.component.scss']
})
export class RecipesViewFullComponent implements OnInit {
  recipesList: Recipe[] = [];

  constructor(
    private recipesViewFullService: RecipesViewFullService,
  ) { }

  ngOnInit(): void {
    this.recipesList = this.recipesViewFullService.createDummyRecipes();
  }
}
