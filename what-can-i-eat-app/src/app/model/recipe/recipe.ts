import { Ingredient } from "../ingredient/ingredient";

export interface Recipe {
    id?: number;
    name: string;
    description: string;
    preparationTime: number;
    ingredientList: Ingredient[];
}