import { BreakpointObserver, BreakpointState } from '@angular/cdk/layout';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { RecipeItemApi } from 'src/app/objects/recipe/recipe-item-api/recipe-item-api';

@Component({
  selector: 'app-recipes-search-dialog',
  templateUrl: './recipes-search-dialog.component.html',
  styleUrls: ['./recipes-search-dialog.component.scss']
})
export class RecipesSearchDialogComponent implements OnInit {
  recipesFound!: RecipeItemApi[];
  recipesToShow!: RecipeItemApi[];
  beginningIndex: number = 0;
  endIndex: number = 1;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private breakpointObserver: BreakpointObserver,
  ) { 
    this.breakpointObserver.observe([
      "(min-width: 768px)"
    ]).subscribe((result: BreakpointState) => {
      if (result.matches) {
        this.setEndIndex(3);
      } else {
        this.setEndIndex(1);
      }
    });
  }

  ngOnInit(): void {
    this.recipesFound = this.data.recipesFound;
    this.updateRecipesToShow();
  }

  setEndIndex(offset: number): void {
    this.endIndex = this.beginningIndex + offset;

    if(this.recipesToShow != null) {
      this.updateRecipesToShow();
    };
  }

  lowerIndex(): void {
    if(this.beginningIndex > 0) {
      this.beginningIndex = this.beginningIndex - 1;
      this.endIndex = this.endIndex - 1;
    };
    this.updateRecipesToShow();
  }

  increaseIndex(): void {
    if(this.beginningIndex != this.recipesFound.length - 5) {
      this.beginningIndex += 1;
      this.endIndex += 1;
    }
    this.updateRecipesToShow();
  }

  updateRecipesToShow(): void {
    this.recipesToShow = this.recipesFound.slice(this.beginningIndex, this.endIndex);
  }
}
