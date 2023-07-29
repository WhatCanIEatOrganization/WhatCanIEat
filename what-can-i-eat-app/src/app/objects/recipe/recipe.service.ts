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
    return this.http.get<Recipe[]>(`${this.apiURL}/recipe`);
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
    let rcp: Recipe = {
      name: 'Lasagna',
      description: 'Smthth',
      preparationTime: 30,
      favorite: true,
      ingredientList: []
    }

    let rcp2: Recipe = {
      name: 'Tortilla',
      description: '',
      preparationTime: 2,
      favorite: true,
      ingredientList: []
    }

    let rcp3: Recipe = {
      name: 'Pizza',
      description: 'x',
      preparationTime: 1,
      favorite: false,
      ingredientList: []
    }

    let list: Recipe[] = [];
    list.push(rcp);
    list.push(rcp2);
    list.push(rcp3);
    // return this.http.get<Recipe[]>(`${this.apiURL}/recipe/favorite`);
    return of(list);
  }
}
