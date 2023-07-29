import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MeasureUnit } from 'src/app/enums/MeasureUnit';
import { Recipe } from 'src/app/model/recipe/recipe';
import { HttpResponse } from '@angular/common/http';
import { RecipeService } from 'src/app/objects/recipe/recipe.service';
import { Ingredient, IngredientsListPayload } from 'src/app/model/ingredient/ingredient';
import { IngredientService } from 'src/app/objects/ingredient/ingredient.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-recipe-creator',
  templateUrl: './recipe-creator.component.html',
  styleUrls: ['./recipe-creator.component.scss']
})
export class RecipeCreatorComponent implements OnInit {
  MeasureUnit = MeasureUnit;
  selectedUnit: string = MeasureUnit.Gram;
  passedRecipeId: number | undefined;
  
  generalInformationForm = new FormGroup({
    recipeName: new FormControl('', Validators.required),
    description: new FormControl('', Validators.required),
    preparationTime: new FormControl(0, [Validators.required, Validators.min(1)]),
  })

  ingredientsFormGroup = this.formBuilder.group({
    ingredients: this.formBuilder.array([])
  });

  preparationStepsFormGroup = this.formBuilder.group({
    steps: this.formBuilder.array([])
  });

  constructor(
    private formBuilder: FormBuilder, 
    private recipeService: RecipeService,
    private ingredientsService: IngredientService,
    private activatedRoute: ActivatedRoute
    ) { }

  ngOnInit(): void {
    this.setRecipeByUrlId();
    this.addIngredientInput();
    this.addStepInput();
  }

  setFormsWithRecipeInfo(recipe: Recipe): void {
    this.generalInformationForm.setValue({
      recipeName: recipe.name,
      description: recipe.description,
      preparationTime: recipe.preparationTime,
    });
  }

  // need any tip how to solve this better 
  setRecipeByUrlId(): void {
    this.activatedRoute.queryParams.subscribe(params => {
      const recipeId = params['recipeId'];
      if(recipeId != undefined) {
        this.recipeService.getRecipeById(recipeId).subscribe((val) => {
          this.setFormsWithRecipeInfo(val);
          this.passedRecipeId = val.id!;
        })
      }
    });
  }

  get ingredientsAsFormArray(): any {
    return this.ingredientsFormGroup.controls['ingredients'] as FormArray;
  }

  public addIngredientInput(): void {
    const ingredientsForm = this.formBuilder.group({
      ingredientName: ['', Validators.required],
      amount: ['1', Validators.min(1)],
      type: [''],
    })
    this.ingredientsAsFormArray.push(ingredientsForm);
  }
  
  public removeInput(i: number): void {
    this.ingredientsAsFormArray.removeAt(i);
  }

  public addStepInput(): void {
    const stepForm = this.formBuilder.group({
      step: ['', Validators.required],
    })
    this.stepsAsFormArray.push(stepForm);
  }

  get stepsAsFormArray(): any {
    return this.preparationStepsFormGroup.controls['steps'] as FormArray;
  }

  public removeStepInput(i: number): void {
    this.stepsAsFormArray.removeAt(i);
  }

  public createRecipe() {
    let recipe: Recipe = {
      id: this.passedRecipeId,
      name: this.generalInformationForm.value.recipeName!,
      description: this.generalInformationForm.value.description!,
      preparationTime: this.generalInformationForm.value.preparationTime!,
      favorite: false,
      ingredientList: this.createIngredientsList(),
    }

    this.recipeService.createRecipe(recipe).subscribe({
      next: (value: Recipe) => {
        let ingListPayload = this.ingredientsListToPayload(value.id!, this.createIngredientsList());
        this.ingredientsService.postIngredientsList(ingListPayload).subscribe((val) => {
        })
      },
      error: () => {
        console.log("Error");
      }
    });
  }

  public ingredientsListToPayload(recipeId: number, ingredientsList: Ingredient[]): IngredientsListPayload {
    return {
      recipeId: recipeId,
      ingredientsList: ingredientsList,
    }
  }

  public createIngredientsList(): Ingredient[] {
    return Object.keys(this.ingredientsAsFormArray.controls).map(key => {
      return {
        id: 0,
        name: this.ingredientsAsFormArray.controls[key].get("ingredientName").value,
        amount: this.ingredientsAsFormArray.controls[key].get("amount").value,
        unitMeasure: this.ingredientsAsFormArray.controls[key].get("type").value,
      };
    });   
  }
}
