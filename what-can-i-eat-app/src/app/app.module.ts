import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MAT_FORM_FIELD_DEFAULT_OPTIONS, MatFormFieldModule } from '@angular/material/form-field';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { RecipesHistoryComponent } from './pages/home-page/recipes-history/recipes-history.component';
import { RecipeCreatorComponent } from './pages/recipe-creator/recipe-creator.component';
import { MatStepperModule } from '@angular/material/stepper';
import { NavBarComponent } from './common/nav-bar/nav-bar.component';
import { MatDialogModule } from '@angular/material/dialog';
import { DialogConfirmationComponent } from './common/dialog/dialog-confirmation/dialog-confirmation.component';
import { IngredientCreatorComponent } from './objects/ingredient/ingredient-creator/ingredient-creator.component';
import { RecipesViewFullComponent } from './pages/recipes-view-full/recipes-view-full.component';
import { RecipeItemCardComponent } from './pages/recipes-view-full/recipe-item-card/recipe-item-card.component';
import { RecipeItemOnClickComponent } from './pages/recipes-view-full/recipe-item-on-click/recipe-item-on-click.component';
import { MatSelectModule } from '@angular/material/select';
import { IngredientItemComponent } from './objects/ingredient/ingredient-item/ingredient-item.component';
import { RecipeItemComponent } from './objects/recipe/recipe-item/recipe-item.component';
import { IngredientListViewComponent } from './pages/home-page/ingredient-list-view/ingredient-list-view.component';
import { HttpClientModule } from '@angular/common/http';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { SnackbarSuccessComponent } from './common/dialog/snackbar-success/snackbar-success.component';
import { MAT_SNACK_BAR_DEFAULT_OPTIONS, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatMenuModule } from '@angular/material/menu';




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
    DialogConfirmationComponent,
    IngredientCreatorComponent,
    RecipesViewFullComponent,
    RecipeItemCardComponent,
    RecipeItemOnClickComponent,
    SnackbarSuccessComponent
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
    MatDialogModule,
    MatInputModule,
    MatFormFieldModule,
    MatSelectModule,
    HttpClientModule,
    MatProgressSpinnerModule,
    ScrollingModule,
    MatSnackBarModule,
    MatMenuModule
  ],
  providers: [
    {provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {appearance: 'outline'}},
    {provide: MAT_SNACK_BAR_DEFAULT_OPTIONS, useValue: {duration: 5000}}],
  bootstrap: [AppComponent]
})
export class AppModule { }
