import { Component, OnInit } from '@angular/core';
import { Ingredient } from 'src/app/objects/ingredient/ingredient';

@Component({
  selector: 'app-feed-page',
  templateUrl: './feed-page.component.html',
  styleUrls: ['./feed-page.component.scss']
})
export class FeedPageComponent implements OnInit {
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
      id:'',
      name: this.value,
      type: this.amount.toString(),
    }

    this.ingredientList.push(newIngredient);
    this.value = '';
    this.amount = 1;

    console.log(this.ingredientList);
  }
}
