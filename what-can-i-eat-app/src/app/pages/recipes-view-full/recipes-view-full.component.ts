import { HttpParams } from '@angular/common/http';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { RecipeItemApi } from 'src/app/objects/recipe/recipe-item-api/recipe-item-api';
import { RecipeService } from 'src/app/objects/recipe/recipe.service';

@Component({
  selector: 'app-recipes-view-full',
  templateUrl: './recipes-view-full.component.html',
  styleUrls: ['./recipes-view-full.component.scss']
})
export class RecipesViewFullComponent implements OnInit {
  @ViewChild('mat-paginator', { static: true, read: ElementRef }) paginator!: ElementRef;

  recipesList: RecipeItemApi[] = [];
  isLoading = true;
  pageNumber = 0;
  pageSize = 50;
  sortBy = "id";
  pagesAmount = 30;

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
}
