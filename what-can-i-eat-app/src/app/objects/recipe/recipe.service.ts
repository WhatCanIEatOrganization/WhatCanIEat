import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, from, of } from 'rxjs';
import { Recipe } from 'src/app/model/recipe/recipe';
import { environment } from 'src/environments/environment';
import { RecipeItemApi } from './recipe-item-api/recipe-item-api';

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

  deleteRecipe(recipe: RecipeItemApi): Observable<RecipeItemApi> {
    let recipeId = recipe.id; 
    return this.http.delete<RecipeItemApi>(`${this.apiURL}/recipe/${recipeId}`);
  }

  createRecipe(recipe: Recipe): Observable<Recipe> {
    return this.http.post<Recipe>(`${this.apiURL}/recipe` , recipe);
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

  getFavoriteRecipes(): Observable<Recipe[]> {
    return this.http.get<Recipe[]>(`${this.apiURL}//v1/recipes/favorite`);
  }

  getRecipesByIngredients(searchFor: string[]): Observable<RecipeItemApi[]> {
    let ingredients = new HttpParams();
    ingredients = ingredients.append("ingredients", searchFor.join(', '));

    return this.http.get<RecipeItemApi[]>(`${this.apiURL}/v1/recipes/search`, {params:ingredients});
  }
}
