import { Ingredient } from "src/app/objects/ingredient/ingredient";

// to be removed later
export interface RecipeOld {
    id?: number;
    name: string;
    description: string;
    preparationTime: number;
    favorite: boolean;
    ingredientList: Ingredient[];
}