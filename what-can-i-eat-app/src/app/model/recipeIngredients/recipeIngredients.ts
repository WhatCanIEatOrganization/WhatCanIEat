import { Ingredient } from "../ingredient/ingredient";
import { Recipe } from "../recipe/recipe";

export interface RecipeIngredients {
    id?: number;
    recipe: Recipe;
    ingredientsList: Ingredient[]
}