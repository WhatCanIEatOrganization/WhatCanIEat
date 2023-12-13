import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, from, of } from 'rxjs';
import { Recipe } from 'src/app/model/recipe/recipe';
import { environment } from 'src/environments/environment';
import { RecipeItemApi } from './recipe-item-api/recipe-item-api';
import { UserRecipe } from './user-recipe/user-recipe';
import { Ingredient } from '../ingredient/ingredient';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {
  apiURL = environment.apiURL;

  constructor(
    private http: HttpClient,
    ) { }

  getRecipeList(): Observable<RecipeItemApi[]> {
    return this.http.get<RecipeItemApi[]>(`${this.apiURL}/v1/recipes`);
  }

  getRecipesPaginatedAndStorted(givenParams: any): Observable<RecipeItemApi[]>  {
    return this.http.get<RecipeItemApi[]>(`${this.apiURL}/v1/recipes`, {params: givenParams});
  }

  deleteRecipe(recipe: RecipeItemApi): Observable<RecipeItemApi> {
    let recipeId = recipe.id; 
    return this.http.delete<RecipeItemApi>(`${this.apiURL}/recipe/${recipeId}`);
  }

  createRecipe(recipe: UserRecipe): Observable<UserRecipe> {
    return this.http.post<UserRecipe>(`${this.apiURL}/v1/recipes`, recipe);
  }

  modifyRecipe(recipe: RecipeItemApi): Observable<RecipeItemApi> {
    return this.http.patch<RecipeItemApi>(`${this.apiURL}/recipe`, recipe);
  }

  getRandomRecipe(): Observable<RecipeItemApi> {
    return this.http.get<RecipeItemApi>(`${this.apiURL}//v1/recipes/rng`);
  }

  getRecipeById(recipeId: number): Observable<Recipe> {
    return this.http.get<Recipe>(`${this.apiURL}/recipe/${recipeId}`);
  }

  getFavoriteRecipes(): Observable<RecipeItemApi[]> {
    return this.http.get<RecipeItemApi[]>(`${this.apiURL}//v1/recipes/favorite`);
  }

  getRecipesByIngredients(searchFor: string[]): Observable<RecipeItemApi[]> {
    let ingredients = new HttpParams();
    ingredients = ingredients.append("ingredients", searchFor.join(', '));

    return this.http.get<RecipeItemApi[]>(`${this.apiURL}/v1/recipes/search`, {params:ingredients});
  }

  generateRecipeByIngredients(ingList: Ingredient[]): Observable<RecipeItemApi> {
    return this.http.post<RecipeItemApi>(`${this.apiURL}/v1/recipes/generate-recipe`, ingList);
  }
}
