export interface Ingredient {
    id: number;
    name: string;
    amount: number;
    unitMeasure: string;
}

export interface IngredientsListPayload {
    recipeId: number,
    ingredientsList: Ingredient[];
}