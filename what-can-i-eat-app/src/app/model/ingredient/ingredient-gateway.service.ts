import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Ingredient } from './Ingredient';

@Injectable({
  providedIn: 'root'
})
export class IngredientGatewayService {

  constructor(
    private http: HttpClient,
  ) { }

  public getIngredients(): Observable<Ingredient[]> {
    return this.http.get<Ingredient[]>('/api/ingredients');
  }

  public updateIngredient(ingredient: Ingredient, id: number): Observable<Ingredient> {
    return this.http.patch<Ingredient>(`/api/ingredients/${id}`, ingredient);
  }

  public createIngredient(ingredient: Ingredient): Observable<Ingredient> {
    return this.http.post<Ingredient>('/api/ingredients', ingredient);
  }

  public deleteIngredient(id: number): Observable<unknown> {
    return this.http.delete<unknown>(`/api/ingredients/${id}`);
  }

}
