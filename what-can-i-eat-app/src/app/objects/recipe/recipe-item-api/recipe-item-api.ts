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
    ingredients: string[],
    preparationSteps: string[]
}