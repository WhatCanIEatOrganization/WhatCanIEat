import { PreparationStep } from "../../preparation-steps/preparation-step"
import { IngredientApi } from "../../ingredient/ingredient-api"

export interface UserRecipe {
    name: string,
    description: string,
    favorite: true,
    source: string,
    preptime: number,
    imageUrl: string,
    ingredients: IngredientApi[],
    preparationSteps: PreparationStep[]
}

