import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-recipe-creator',
  templateUrl: './recipe-creator.component.html',
  styleUrls: ['./recipe-creator.component.scss']
})
export class RecipeCreatorComponent implements OnInit {
  generalInformationForm = new FormGroup({
    recipeName: new FormControl(''),
    description: new FormControl(''),
    preparationTime: new FormControl(''),
    // recipeImage: new FormControl('')
  })

  ingredientsFormGroup = this.formBuilder.group({
    ingredients: this.formBuilder.array([])
  });

  preparationStepsFormGroup = this.formBuilder.group({
    step: [''],
  })

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.addIngredientInput();
  }

  public onFormSubmit(): void {
    console.log(this.generalInformationForm);
  }

  get ingredientsAsFormArray(): any {
    return this.ingredientsFormGroup.controls['ingredients'] as FormArray;
  }

  public addIngredientInput(): void {
    const ingredientsForm = this.formBuilder.group({
      ingredientName: [''],
      amount: ['1'],
      type: [''],
    })
    this.ingredientsAsFormArray.push(ingredientsForm);
  }
  
  public removeInput(i: number): void {
    this.ingredientsAsFormArray.removeAt(i);
  }
}
