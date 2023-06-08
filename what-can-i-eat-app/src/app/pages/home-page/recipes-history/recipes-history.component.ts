import { Component, OnInit } from '@angular/core';
import { Recipe } from 'src/app/model/recipe/recipe';
import { RecipesHistoryViewService } from './recipes-history-view.service';

@Component({
  selector: 'app-recipes-history',
  templateUrl: './recipes-history.component.html',
  styleUrls: ['./recipes-history.component.scss']
})
export class RecipesHistoryComponent implements OnInit {
  recipesList: Recipe[] = [];

  constructor(
    public recipesHistoryViewService: RecipesHistoryViewService,
  ) { }

  ngOnInit(): void {
    this.recipesList = this.recipesHistoryViewService.createDummyRecipes();
  }

}
