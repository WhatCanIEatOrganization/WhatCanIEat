export interface Ingredient {
    id: number;
    name: string;
    amount: number;
    type: string;
}

export interface IngredientsListPayload {
    recipeId: number,
    ingredientsList: Ingredient[];
}
