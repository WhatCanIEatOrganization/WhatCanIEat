import { HttpParams } from '@angular/common/http';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { RecipeItemApi } from 'src/app/objects/recipe/recipe-item-api/recipe-item-api';
import { RecipeService } from 'src/app/objects/recipe/recipe.service';

@Component({
  selector: 'app-recipes-view-full',
  templateUrl: './recipes-view-full.component.html',
  styleUrls: ['./recipes-view-full.component.scss']
})
export class RecipesViewFullComponent implements OnInit {

  recipesList: RecipeItemApi[] = [];
  isLoading = true;
  pageNumber = 0;
  pageSize = 25;
  sortBy = "id";
  pagesAmount = 400;

  constructor(
    private recipeService: RecipeService,
  ) { }

  ngOnInit(): void {
    this.getRecipeList();
  }  

  getRecipeList(): void {
    const reqParams = new HttpParams()
        .set('page', this.pageNumber)
        .set('size', this.pageSize)
        .set('sortBy', this.sortBy);

    this.recipeService.getRecipesPaginatedAndStorted(reqParams).subscribe({
      next: (recipes) => {
        this.recipesList = recipes;
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
      }
    });
  }

  handlePageEvent(e: PageEvent): void {
    this.pageNumber = e.pageIndex;
    this.getRecipeList();
    this.isLoading = true;

    window.scroll({ 
      top: 0, 
      left: 0, 
      behavior: 'smooth' 
    });
  }
}
