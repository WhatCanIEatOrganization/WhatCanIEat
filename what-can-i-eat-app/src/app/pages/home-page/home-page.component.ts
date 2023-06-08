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

  constructor(
  ) { }

  ngOnInit(): void {
    this.ingredientList = this.addDummyIngredients();
  }

  public onInput(): void {
    console.log(this.value);

    let newIngredient: Ingredient = {
      name: this.value,
      amount: 0,
      amountType: ''
    }

    this.ingredientList.push(newIngredient);
    this.value = '';

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
