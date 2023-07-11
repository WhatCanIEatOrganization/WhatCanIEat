import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MeasureUnit } from 'src/app/enums/MeasureUnit';

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
    preparationTime: new FormControl('', [Validators.required, Validators.min(1)]),
  })

  ingredientsFormGroup = this.formBuilder.group({
    ingredients: this.formBuilder.array([])
  });

  preparationStepsFormGroup = this.formBuilder.group({
    steps: this.formBuilder.array([])
  });

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.addIngredientInput();
    this.addStepInput();
  }

  public onFormSubmit(): void {
    
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
}
