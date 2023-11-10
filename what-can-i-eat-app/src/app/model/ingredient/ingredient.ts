export interface Ingredient {
    id: string;
    name: string;
    amount: number;
    type: string;
}

export interface IngredientsListPayload {
    recipeId: number,
    ingredientsList: Ingredient[];
}
