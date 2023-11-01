import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MAT_FORM_FIELD_DEFAULT_OPTIONS, MatFormFieldModule } from '@angular/material/form-field';
import { FeedPageComponent } from './pages/feed-page/feed-page.component';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { RecipeCreatorComponent } from './pages/recipe-creator/recipe-creator.component';
import { MatStepperModule } from '@angular/material/stepper';
import { NavBarComponent } from './common/nav-bar/nav-bar.component';
import { MatDialogModule } from '@angular/material/dialog';
import { DialogConfirmationComponent } from './common/dialog/dialog-confirmation/dialog-confirmation.component';
import { IngredientCreatorComponent } from './objects/ingredient/ingredient-creator/ingredient-creator.component';
import { RecipesViewFullComponent } from './pages/recipes-view-full/recipes-view-full.component';
import { RecipeItemCardComponent } from './pages/recipes-view-full/recipe-item-card/recipe-item-card.component';
import { RecipeItemOnClickComponent } from './objects/recipe/recipe-item-on-click/recipe-item-on-click.component';
import { MatSelectModule } from '@angular/material/select';
import { IngredientItemComponent } from './objects/ingredient/ingredient-item/ingredient-item.component';
import { RecipeItemComponent } from './objects/recipe/recipe-item/recipe-item.component';
import { IngredientsListComponent } from './pages/feed-page/ingredients-list/ingredients-list.component';
import { HttpClientModule } from '@angular/common/http';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { SnackbarSuccessComponent } from './common/dialog/snackbar-success/snackbar-success.component';
import { MAT_SNACK_BAR_DEFAULT_OPTIONS, MatSnackBarModule } from '@angular/material/snack-bar';
import { AdvertBannerComponent } from './pages/feed-page/advert-banner/advert-banner.component';
import { MatMenuModule } from '@angular/material/menu';
import { RecipesFavoriteComponent } from './pages/feed-page/recipes-favorite/recipes-favorite.component';
import { RecipeFavoriteItemComponent } from './pages/feed-page/recipes-favorite/recipe-favorite-item/recipe-favorite-item.component';
import { FeaturedMealComponent } from './pages/feed-page/featured-meal/featured-meal.component';
import { DailyTipComponent } from './pages/feed-page/daily-tip/daily-tip.component';
import { RecipesSearchBoxByIngredientsComponent } from './pages/feed-page/recipes-search-box-by-ingredients/recipes-search-box-by-ingredients.component';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { RecipesSearchDialogComponent } from './pages/feed-page/recipes-search-box-by-ingredients/recipes-search-dialog/recipes-search-dialog.component';
import { MatExpansionModule } from '@angular/material/expansion';


@NgModule({
  declarations: [
    AppComponent,
    FeedPageComponent,
    IngredientsListComponent,
    IngredientItemComponent,
    RecipeItemComponent,
    RecipeCreatorComponent,
    NavBarComponent,
    DialogConfirmationComponent,
    IngredientCreatorComponent,
    RecipesViewFullComponent,
    RecipeItemCardComponent,
    RecipeItemOnClickComponent,
    SnackbarSuccessComponent,
    AdvertBannerComponent,
    RecipesFavoriteComponent,
    RecipeFavoriteItemComponent,
    FeaturedMealComponent,
    DailyTipComponent,
    RecipesSearchBoxByIngredientsComponent,
    RecipesSearchDialogComponent,
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
    MatMenuModule,
    MatAutocompleteModule,
    MatExpansionModule
  ],
  providers: [
    {provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {appearance: 'outline'}},
    {provide: MAT_SNACK_BAR_DEFAULT_OPTIONS, useValue: {duration: 5000}}],
  bootstrap: [AppComponent]
})
export class AppModule { }
