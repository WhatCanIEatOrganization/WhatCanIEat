import { Ingredient } from "src/app/objects/ingredient/ingredient";

export interface Recipe {
    id?: number;
    name: string;
    description: string;
    preparationTime: number;
    favorite: boolean;
    ingredientList: Ingredient[];
}