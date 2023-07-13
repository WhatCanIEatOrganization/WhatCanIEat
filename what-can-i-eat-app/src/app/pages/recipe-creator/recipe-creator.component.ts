import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MeasureUnit } from 'src/app/enums/MeasureUnit';
import { Recipe } from 'src/app/model/recipe/recipe';
import { HttpResponse } from '@angular/common/http';
import { RecipeService } from 'src/app/objects/recipe/recipe.service';

@Component({
  selector: 'app-recipe-creator',
  templateUrl: './recipe-creator.component.html',
  styleUrls: ['./recipe-creator.component.scss']
})
export class RecipeCreatorComponent implements OnInit {
  MeasureUnit = MeasureUnit;
  selectedUnit: string = MeasureUnit.Gram;
  
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

  constructor(private formBuilder: FormBuilder, private recipeService: RecipeService) { }

  ngOnInit(): void {
    this.addIngredientInput();
    this.addStepInput();
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
      id: 0,
      name: this.generalInformationForm.value.recipeName!,
      description: this.generalInformationForm.value.recipeName!,
      preparationTime: this.generalInformationForm.value.preparationTime!
    }

    this.recipeService.createRecipe(recipe).subscribe((value: HttpResponse<Recipe>) => {
    });
  }
}
