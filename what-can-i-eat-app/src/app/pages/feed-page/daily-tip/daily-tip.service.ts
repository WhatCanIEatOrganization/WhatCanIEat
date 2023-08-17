import { Injectable } from '@angular/core';

export interface DailyTip {
  title: string,
  text: string,
}

@Injectable({
  providedIn: 'root'
})
export class DailyTipService {

  constructor() { }

  generateDailyTip(): DailyTip {
    let tip1: DailyTip = {
      title: 'Base your meals on higher fibre starchy carbohydrates!',
      text: 'Starchy carbohydrates should make up just over a third of the food you eat. They include potatoes, bread, rice, pasta and cereals Choose higher fibre or wholegrain varieties, such as wholewheat pasta, brown rice or potatoes with their skins on. '
    }

    let tip2: DailyTip = {
      title: 'Eat lots of fruit and veg!',
      text: "It's recommended that you eat at least 5 portions of a variety of fruit and veg every day. They can be fresh, frozen, canned, dried or juiced. Getting your 5 A Day is easier than it sounds. Why not chop a banana over your breakfast cereal, or swap your usual mid-morning snack for a piece of fresh fruit?"
    }

    let tip3: DailyTip = {
      title: 'Eat more fish, including a portion of oily fish!',
      text: 'Fish is a good source of protein and contains many vitamins and minerals. Aim to eat at least 2 portions of fish a week, including at least 1 portion of oily fish. Oily fish are high in omega-3 fats, which may help prevent heart disease. '
    }

    let tip4: DailyTip = {
      title: 'Cut down on saturated fat and sugar!',
      text: "You need some fat in your diet, but it's important to pay attention to the amount and type of fat you're eating. There are 2 main types of fat: saturated and unsaturated. Too much saturated fat can increase the amount of cholesterol in the blood, which increases your risk of developing heart disease."
    }

    let tip5: DailyTip = {
      title: 'Eating sugar',
      text: 'Regularly consuming foods and drinks high in sugar increases your risk of obesity and tooth decay. Sugary foods and drinks are often high in energy (measured in kilojoules or calories), and if consumed too often can contribute to weight gain. They can also cause tooth decay, especially if eaten between meals.'
    }

    let tip6: DailyTip = {
      title: 'Eat less salt: no more than 6g a day for adults!',
      text: 'Eating too much salt can raise your blood pressure. People with high blood pressure are more likely to develop heart disease or have a stroke. Even if you do not add salt to your food, you may still be eating too much. About three-quarters of the salt you eat is already in the food when you buy it, such as breakfast cereals, soups, breads and sauces.'
    }

    let tip7: DailyTip = {
      title: 'Get active and be a healthy weight!',
      text: "As well as eating healthily, regular exercise may help reduce your risk of getting serious health conditions. It's also important for your overall health and wellbeing. Being overweight or obese can lead to health conditions, such as type 2 diabetes, certain cancers, heart disease and stroke. Being underweight could also affect your health."
    }

    let tip8: DailyTip = {
      title: 'Do not get thirsty!',
      text: 'You need to drink plenty of fluids to stop you getting dehydrated. The government recommends drinking 6 to 8 glasses every day. This is in addition to the fluid you get from the food you eat. All non-alcoholic drinks count, but water, lower fat milk and lower sugar drinks, including tea and coffee, are healthier choices. '
    }

    let tip9: DailyTip = {
      title: 'Do not skip breakfast!',
      text: "Some people skip breakfast because they think it'll help them lose weight. But a healthy breakfast high in fibre and low in fat, sugar and salt can form part of a balanced diet, and can help you get the nutrients you need for good health. A wholegrain lower sugar cereal with semi-skimmed milk and fruit sliced over the top is a tasty and healthier breakfast."
    }

    let tips: DailyTip[] = [tip1, tip2, tip3, tip4, tip5, tip6, tip7, tip8, tip9]

    return tips[Math.floor(Math.random()*tips.length)];
  }
}
