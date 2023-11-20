import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { BasicIngredient } from 'src/app/model/basic-ingredient/basicIngredient';
import { Ingredient } from 'src/app/model/ingredient/ingredient';
import { Recipe } from 'src/app/model/recipe/recipe';
import { environment } from 'src/environments/environment';
import { IngredientApi } from './ingredient-api';

@Injectable({
  providedIn: 'root'
})
export class IngredientService {
  apiURL = environment.apiURL;

  constructor(private http: HttpClient) { }

  getIngredientList(): Observable<Ingredient[]> {
    return this.http.get<Ingredient[]>(`https://j9kvt6f27i.execute-api.eu-central-1.amazonaws.com/Stage/ingredients`);
  }

  getIngredientsByIds(numbers: number[]): Observable<IngredientApi[]> {
    let ids = new HttpParams();
    ids = ids.append("ids", numbers.join(', '));

    return this.http.get<IngredientApi[]>(`${this.apiURL}/v1/recipes/ingredients`, {params: ids});
  }

  deleteIngredient(ingredient: Ingredient): Observable<Ingredient> {
    return this.http.delete<Ingredient>(`https://j9kvt6f27i.execute-api.eu-central-1.amazonaws.com/Stage/ingredients/${ingredient.id}`);
  }

  createIngredient(ingredient: Ingredient): Observable<IngredientApi> {

    let ing: IngredientApi = {
      id: '',
      name: ingredient.name,
      description: '',
      amountType: ingredient.amount.toString()
    }

    return this.http.post<IngredientApi>(`https://j9kvt6f27i.execute-api.eu-central-1.amazonaws.com/Stage/ingredients`, ing);
  }

  modifyIngredient(ingredient: Ingredient): Observable<Ingredient> {
    return this.http.patch<Ingredient>(`https://j9kvt6f27i.execute-api.eu-central-1.amazonaws.com/Stage/ingredients/${ingredient.id}`, ingredient);
  }


  getIngredientTagsList(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiURL}/v2/basic-ingredients`);
  }
}
