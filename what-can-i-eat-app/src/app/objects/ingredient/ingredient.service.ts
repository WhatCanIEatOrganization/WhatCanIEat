import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Ingredient, IngredientPayLoad } from './ingredient';

@Injectable({
  providedIn: 'root'
})
export class IngredientService {
  apiURL = environment.apiURL;

  constructor(private http: HttpClient) { }

  getIngredientList(): Observable<Ingredient[]> {
    return this.http.get<Ingredient[]>(`https://j9kvt6f27i.execute-api.eu-central-1.amazonaws.com/Stage/ingredients`);
  }

  getIngredientsByIds(numbers: number[]): Observable<Ingredient[]> {
    let ids = new HttpParams();
    ids = ids.append("ids", numbers.join(', '));

    return this.http.get<Ingredient[]>(`${this.apiURL}/v1/recipes/ingredients`, {params: ids});
  }

  deleteIngredient(ingredient: Ingredient): Observable<Ingredient> {
    return this.http.delete<Ingredient>(`https://j9kvt6f27i.execute-api.eu-central-1.amazonaws.com/Stage/ingredients/${ingredient.id}`);
  }

  createIngredient(ingredient: Ingredient): Observable<Ingredient> {

    let ing: IngredientPayLoad = {
      name: ingredient.name,
      type: ingredient.type.toString()
    }

    return this.http.post<Ingredient>(`https://j9kvt6f27i.execute-api.eu-central-1.amazonaws.com/Stage/ingredients`, ing);
  }

  modifyIngredient(ingredient: Ingredient): Observable<Ingredient> {
    return this.http.patch<Ingredient>(`https://j9kvt6f27i.execute-api.eu-central-1.amazonaws.com/Stage/ingredients/${ingredient.id}`, ingredient);
  }


  getIngredientTagsList(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiURL}/v2/basic-ingredients`);
  }
}
