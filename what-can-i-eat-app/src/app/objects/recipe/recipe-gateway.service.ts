import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Ingredient } from '../ingredient/ingredient';
import { RecipeChatGpt } from './models/recipe-chatgpt/recipe-chatgpt';

@Injectable({
  providedIn: 'root'
})
export class RecipeGatewayService {
  apiURL = environment.apiURL;

  constructor(
    private http: HttpClient,
  ) { }

  generateRecipeByIngredients(ingList: Ingredient[]): Observable<RecipeChatGpt> {
    return this.http.post<RecipeChatGpt>(`${this.apiURL}/v1/recipes/generate-recipe`, ingList);
  }
}
