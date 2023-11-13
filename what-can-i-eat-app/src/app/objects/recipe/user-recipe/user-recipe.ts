import { PreparationStep } from "../../preparation-steps/preparation-step"
import { IngredientApi } from "../../ingredient/ingredient-api"

export interface UserRecipe {
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
    preparationSteps: PreparationStep[]
    ingredients: IngredientApi[],
}
