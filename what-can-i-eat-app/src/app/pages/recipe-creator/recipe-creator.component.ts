import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MeasureUnit } from 'src/app/enums/MeasureUnit';
import { Recipe } from 'src/app/model/recipe/recipe';
import { RecipeService } from 'src/app/objects/recipe/recipe.service';
import { IngredientService } from 'src/app/objects/ingredient/ingredient.service';
import { ActivatedRoute } from '@angular/router';
import { StepperOrientation } from '@angular/cdk/stepper';
import { BreakpointObserver } from '@angular/cdk/layout';
import { CustomBreakpoints } from 'src/app/common/custom-breakpoints/custom-breakpoints';
import { UserRecipe } from 'src/app/objects/recipe/user-recipe/user-recipe';
import { IngredientApi } from 'src/app/objects/ingredient/ingredient-api';
import { PreparationStep } from 'src/app/objects/preparation-steps/preparation-step';

@Component({
  selector: 'app-recipe-creator',
  templateUrl: './recipe-creator.component.html',
  styleUrls: ['./recipe-creator.component.scss']
})
export class RecipeCreatorComponent implements OnInit {
  MeasureUnit = MeasureUnit;
  selectedUnit: string = MeasureUnit.Gram;
  passedRecipeId: number | undefined;
  orientation!: StepperOrientation;
  wideScreen!: boolean;

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
    private activatedRoute: ActivatedRoute,
    private breakpointObserver: BreakpointObserver,
    ) { }

  ngOnInit(): void {
    this.setRecipeByUrlId();
    this.addIngredientInput();
    this.addStepInput();
    this.subscribeToBreakPointsObserver();
  }

  private subscribeToBreakPointsObserver(): void {
    this.breakpointObserver.observe([
      CustomBreakpoints.XSmall,
      CustomBreakpoints.Small
    ]).subscribe(result => {
      this.orientation = result.matches ? 'vertical' : 'horizontal';
      this.wideScreen = !result.matches;
    });
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
    let recipe: UserRecipe = {
      name: this.generalInformationForm.value.recipeName!,
      description: this.generalInformationForm.value.description!,
      favorite: true,
      source: '',
      preptime: this.generalInformationForm.value.preparationTime!,
      imageUrl: '',
      preparationSteps: this.createPreparationSteps(),
      ingredients: this.createIngredientsList()
    }

    this.recipeService.createRecipe(recipe).subscribe({
      next: (value: UserRecipe) => {
        console.log(value);
      },
      error: () => {
        console.log("Error");
      }
    });
  }

  public createPreparationSteps(): PreparationStep[] {
    return Object.keys(this.stepsAsFormArray.controls).map(key => {
      return {
        step: this.stepsAsFormArray.controls[key].get("step").value,
      };
    });
  }

  public createIngredientsList(): IngredientApi[] {
    return Object.keys(this.ingredientsAsFormArray.controls).map(key => {
      return {
        id: "",
        name: this.ingredientsAsFormArray.controls[key].get("ingredientName").value,
        description: '',
        imageUrl: '',
        amountType: this.ingredientsAsFormArray.controls[key].get("amount").value,
      };
    });
  }
}
