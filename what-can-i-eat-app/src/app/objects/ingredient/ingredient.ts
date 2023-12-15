export interface Ingredient {
    id: string;
    name: string;
    type: string;
    amountWithUnit?: string
}

export interface IngredientPayLoad {
    name: string;
    type: string;
}

