import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Recipe } from 'src/app/model/recipe/recipe';
import { RecipeItemOnClickComponent } from '../recipe-item-on-click/recipe-item-on-click.component';
import { RecipeService } from 'src/app/objects/recipe/recipe.service';

@Component({
  selector: 'app-recipe-item-card',
  templateUrl: './recipe-item-card.component.html',
  styleUrls: ['./recipe-item-card.component.scss']
})
export class RecipeItemCardComponent implements OnInit {
  @Input() recipe!: Recipe;

  constructor(
    private dialog: MatDialog,
    private recipeService: RecipeService,
  ) { }

  ngOnInit(): void {
  }

  onRecipeCardClick(): void {
    const dialogRef = this.dialog.open(RecipeItemOnClickComponent)
  }

  deleteRecipe(): void {
    this.recipeService.deleteRecipe(this.recipe).subscribe({
      next: (recipe) => {
        console.log("Deleted");
      },
      error: () => {
        console.log("Not deleted");
      }
    })
  }

  public toggleFavorite(): void {
    // console.log(this.recipe);
    this.recipe.favorite ? this.recipe.favorite = false : this.recipe.favorite = true;
    // console.log(this.recipe);
    this.recipeService.modifyRecipe(this.recipe).subscribe({
      next: (recipe) => {
        console.log(this.recipe);
        console.log(recipe);
      },
      error: () => {
        console.log("Something went wrong");
      }
    })
  }
}
