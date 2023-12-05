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
  endIndex: number = 0;
  offset: number = 0;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private breakpointObserver: BreakpointObserver,
  ) { 
    this.breakpointObserver.observe([
      "(min-width: 768px)"
    ]).subscribe((result: BreakpointState) => {
      if (result.matches) {
        this.offset = 3;
        this.setBoundsOnScreenWidthChange();
        this.updateRecipesToShow();
      } else {
        this.offset = 1;
        this.setBoundsOnScreenWidthChange();
        this.updateRecipesToShow();
      }
    });
  }

  ngOnInit(): void {
    this.recipesFound = this.data.recipesFound;
    this.updateRecipesToShow();
  }

  lowerIndex(): void {
    if(this.beginningIndex > 0) {
      this.beginningIndex = this.beginningIndex - 1;
      this.endIndex = this.endIndex - 1;
    };
    this.updateRecipesToShow();
  }

  increaseIndex(): void {
    let updatedEndIndex = this.endIndex + 1;
    
    if(updatedEndIndex <= this.recipesFound.length || updatedEndIndex <= this.recipesFound.length - this.offset) {
      this.beginningIndex += 1;
      this.endIndex += 1;
    }
    
    this.updateRecipesToShow();
  }

  setBoundsOnScreenWidthChange(): void {
    this.setEndIndex();
    this.setBeginningIndex();
  }

  setEndIndex(): void {
    this.endIndex = this.beginningIndex + this.offset;

    if(this.recipesFound != null && this.endIndex > this.recipesFound.length) {
      this.endIndex = this.recipesFound.length;
    }
  }

  setBeginningIndex(): void {
    this.beginningIndex = this.endIndex - this.offset;

    if(this.recipesFound != null && this.beginningIndex < 0) {
      this.beginningIndex = 0;
    }
  }

  updateRecipesToShow(): void {
    if(this.recipesFound != null) {
      this.recipesToShow = this.recipesFound.slice(this.beginningIndex, this.endIndex);
    };
  }
}
