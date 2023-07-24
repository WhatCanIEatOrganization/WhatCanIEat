import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Ingredient, IngredientsListPayload } from 'src/app/model/ingredient/ingredient';
import { Recipe } from 'src/app/model/recipe/recipe';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class IngredientService {
  apiURL = environment.apiURL;

  constructor(private http: HttpClient) { }

  getIngredientList(): Observable<Ingredient[]> {
    return this.http.get<Ingredient[]>(`${this.apiURL}/ingredient`);
  }

  deleteIngredient(ingredient: Ingredient): Observable<Ingredient> {
    let ingredientId = ingredient.id;
    return this.http.delete<Ingredient>(`${this.apiURL}/ingredient/${ingredientId}`);
  }

  createIngredient(ingredient: Ingredient): Observable<Ingredient> {
    return this.http.post<Ingredient>(`${this.apiURL}/ingredient` , ingredient);
  }

  modifyIngredient(ingredient: Ingredient): Observable<Ingredient> {
    return this.http.patch<Ingredient>(`${this.apiURL}/ingredient`, ingredient);
  }

  postIngredientsList(ingredientsListPayload: IngredientsListPayload): Observable<Recipe> {
    return this.http.post<Recipe>(`${this.apiURL}/recipe/list` , ingredientsListPayload);
  }

}
