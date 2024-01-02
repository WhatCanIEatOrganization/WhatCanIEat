import { Ingredient } from "src/app/objects/ingredient/ingredient";
import { PreparationStep } from "../../../preparation-steps/preparation-step";

export interface RecipeChatGpt {
    id: number,
    name: string,
    description: string,
    favorite: boolean,
    source: string,
    preptime: number,
    waittime: number,
    cooktime: number,
    calories: number,
    imageUrl: string,
    ingredients: Ingredient[],
    preparationSteps: PreparationStep[]
}