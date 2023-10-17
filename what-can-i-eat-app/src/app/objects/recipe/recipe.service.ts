import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, from, of } from 'rxjs';
import { Recipe } from 'src/app/model/recipe/recipe';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {
  apiURL = environment.apiURL;

  constructor(
    private http: HttpClient,
    ) { }

  getRecipeList(): Observable<Recipe[]> {
    return this.http.get<Recipe[]>(`${this.apiURL}/v1/recipes`);
  }

  deleteRecipe(recipe: Recipe): Observable<Recipe> {
    let recipeId = recipe.id; 
    return this.http.delete<Recipe>(`${this.apiURL}/recipe/${recipeId}`);
  }

  createRecipe(recipe: Recipe): Observable<Recipe> {
    return this.http.post<Recipe>(`${this.apiURL}/recipe` , recipe);
  }

  modifyRecipe(recipe: Recipe): Observable<Recipe> {
    return this.http.patch<Recipe>(`${this.apiURL}/recipe`, recipe);
  }

  getRandomRecipe(): Observable<Recipe> {
    return this.http.get<Recipe>(`${this.apiURL}/recipe/rng`);
  }

  getRecipeById(recipeId: number): Observable<Recipe> {
    return this.http.get<Recipe>(`${this.apiURL}/recipe/${recipeId}`);
  }

  getFavoriteRecipes(): Observable<Recipe[]> {
    return this.http.get<Recipe[]>(`${this.apiURL}/recipe/favorite`);
  }
}
