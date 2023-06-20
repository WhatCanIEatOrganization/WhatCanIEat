import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { IngredientListViewComponent } from './pages/home-page/ingredient-list-view/ingredient-list-view.component';
import { IngredientItemComponent } from './pages/home-page/ingredient-item/ingredient-item.component';
import { RecipesHistoryComponent } from './pages/home-page/recipes-history/recipes-history.component';
import { RecipeItemComponent } from './pages/home-page/recipe-item/recipe-item.component';
import { RecipeCreatorComponent } from './pages/home-page/recipe-creator/recipe-creator.component';
import { MatStepperModule } from '@angular/material/stepper';
import { NavBarComponent } from './common/nav-bar/nav-bar.component';
import { MatDialogModule } from '@angular/material/dialog';
import { DialogConfirmationComponent } from './common/dialog/dialog-confirmation/dialog-confirmation.component';



@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    IngredientListViewComponent,
    IngredientItemComponent,
    RecipesHistoryComponent,
    RecipeItemComponent,
    RecipeCreatorComponent,
    NavBarComponent,
    DialogConfirmationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    FormsModule,
    ReactiveFormsModule,
    MatStepperModule,
    MatDialogModule 
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
