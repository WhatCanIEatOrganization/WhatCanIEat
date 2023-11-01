import { PreparationStep } from "../../preparation-steps/preparation-step";

export interface RecipeItemApi {
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
    ingredients: number[],
    preparationSteps: PreparationStep[]
}