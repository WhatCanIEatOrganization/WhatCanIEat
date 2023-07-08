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
    this.ingredientList = this.addDummyIngredients();
  }

  public onAddIngredient(): void {
    let newIngredient: Ingredient = {
      name: this.value,
      amount: this.amount,
      amountType: this.type,
    }

    this.ingredientList.push(newIngredient);
    this.value = '';
    this.amount = 1;

    console.log(this.ingredientList);
  }

  private addDummyIngredients(): Ingredient[] {
    let firstIngredient: Ingredient = {
      name: 'Marchewka',
      amount: 0,
      amountType: ''
    }

    let secondIngredient: Ingredient = {
      name: 'Truskawki',
      amount: 0,
      amountType: ''
    }

    let thirdIngredient: Ingredient = {
      name: 'Seler',
      amount: 5,
      amountType: ''
    }

    let fourthIngredient: Ingredient = {
      name: 'Ziemniaki',
      amount: 0,
      amountType: ''
    }

    let list: Ingredient[] = [firstIngredient, secondIngredient, thirdIngredient, fourthIngredient];

    return list;
  }
}
