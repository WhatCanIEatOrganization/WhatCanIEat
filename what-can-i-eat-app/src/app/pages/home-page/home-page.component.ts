import { Component, OnInit } from '@angular/core';
import { Ingredient } from 'src/app/model/ingredient/ingredient';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {
  value = '';
  ingredientList: Ingredient[] = [];
  secondStep = false;
  amount = 1;
  type = "gram";

  constructor(
  ) { }

  ngOnInit(): void {
    
  }

  public onAddIngredient(): void {
    let newIngredient: Ingredient = {
      id: 1,
      name: this.value,
      amount: this.amount,
      unitMeasure: this.type,
    }

    this.ingredientList.push(newIngredient);
    this.value = '';
    this.amount = 1;

    console.log(this.ingredientList);
  }
}
