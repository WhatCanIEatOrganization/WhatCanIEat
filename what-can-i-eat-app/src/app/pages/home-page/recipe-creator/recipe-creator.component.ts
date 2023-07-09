import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-recipe-creator',
  templateUrl: './recipe-creator.component.html',
  styleUrls: ['./recipe-creator.component.scss']
})
export class RecipeCreatorComponent implements OnInit {
  generalInformationForm = new FormGroup({
    recipeName: new FormControl('s', Validators.required),
    description: new FormControl('s', Validators.required),
    preparationTime: new FormControl('s', Validators.required),
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
    
  }

  get ingredientsAsFormArray(): any {
    return this.ingredientsFormGroup.controls['ingredients'] as FormArray;
  }

  public addIngredientInput(): void {
    const ingredientsForm = this.formBuilder.group({
      ingredientName: ['', Validators.required],
      amount: ['1'],
      type: [''],
    })
    this.ingredientsAsFormArray.push(ingredientsForm);
  }
  
  public removeInput(i: number): void {
    this.ingredientsAsFormArray.removeAt(i);
  }
}
