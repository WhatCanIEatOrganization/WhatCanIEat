import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EMPTY, Observable, catchError, from, map, of } from 'rxjs';
import { environment } from 'src/environments/environment';
import { RecipeItemApi } from './recipe-item-api/recipe-item-api';
import { UserRecipe } from './user-recipe/user-recipe';
import { Ingredient } from '../ingredient/ingredient';
import { RecipeGatewayService } from './recipe-gateway.service';
import { Recipe } from './models/recipe/recipe';
import { IngredientService } from '../ingredient/ingredient.service';
import { RecipeOld } from 'src/app/model/recipe/recipe';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {
  apiURL = environment.apiURL;

  constructor(
    private http: HttpClient,
    private recipeGatewayService: RecipeGatewayService,
    private ingredientService: IngredientService
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

  modifyRecipe(recipe: RecipeOld): Observable<RecipeOld> {
    return this.http.patch<RecipeOld>(`${this.apiURL}/recipe`, recipe);
  }

  getRandomRecipe(): Observable<RecipeItemApi> {
    return this.http.get<RecipeItemApi>(`${this.apiURL}//v1/recipes/rng`);
  }

  getRecipeById(recipeId: number): Observable<RecipeOld> {
    return this.http.get<RecipeOld>(`${this.apiURL}/recipe/${recipeId}`);
  }

  getFavoriteRecipes(): Observable<RecipeItemApi[]> {
    return this.http.get<RecipeItemApi[]>(`${this.apiURL}//v1/recipes/favorite`);
  }

  getRecipesByIngredients(searchFor: string[]): Observable<RecipeItemApi[]> {
    let ingredients = new HttpParams();
    ingredients = ingredients.append("ingredients", searchFor.join(', '));

    return this.http.get<RecipeItemApi[]>(`${this.apiURL}/v1/recipes/search`, {params:ingredients});
  }

  generateRecipeByIngredients(ingList: Ingredient[]): Observable<Recipe> {
        return this.recipeGatewayService.generateRecipeByIngredients(ingList).pipe(map(
      (val) => {
        console.log(val);
        let recipe: Recipe = {
          ...val
        }
        return recipe;
      }
    ),
    catchError((err, caught) => {

      return EMPTY;
    }))}
}
