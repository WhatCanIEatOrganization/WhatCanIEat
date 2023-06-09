import { Injectable } from '@angular/core';
import { Recipe } from 'src/app/model/recipe/recipe';

@Injectable({
  providedIn: 'root'
})
export class RecipesHistoryViewService {

  constructor() { }

  public createDummyRecipes(): Recipe[] {
    let recipesList: Recipe[] = [];

    let firstRecipe: Recipe = {
      name: 'Spaghetti Bolognese',
      description: 'Delicious pastaniti recipino'
    }

    let secondRecipe: Recipe = {
      name: 'Lasagna',
      description: 'Great dish for great hunger'
    }

    let thirdRecipe: Recipe = {
      name: 'Pizza',
      description: 'No skills no worries'
    }

    let fourthRecipe: Recipe = {
      name: 'Chicken with rice',
      description: 'Gotta get those gym kilograms!'
    }

    recipesList.push(firstRecipe);
    recipesList.push(secondRecipe);
    recipesList.push(thirdRecipe);
    recipesList.push(fourthRecipe);

    return recipesList;
  }
}
