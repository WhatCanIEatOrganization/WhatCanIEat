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
    ingredientName: [''],
    amount: [''],
    type: [''],
    ingredients: this.formBuilder.array([])
  });

  preparationStepsFormGroup = this.formBuilder.group({
    step: [''],
  })

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit(): void {
  }

  public onFormSubmit(): void {
    console.log(this.generalInformationForm);
  }

  public getIngredientsAsFormArray(): any {
    return this.ingredientsFormGroup.get('ingredients') as FormArray;
  }

  public ingredients(): any {
    return this.formBuilder.group({
      ingredients: this.formBuilder.control(''),
    });
  }

  public addInput(): void {
    // this.getIngredientsAsFormArray(this.ingredients());
    // https://blog.knoldus.com/adding-elements-dynamically-in-angular/
  }
}
