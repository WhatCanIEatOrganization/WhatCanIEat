import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { RecipeCreatorComponent } from './pages/home-page/recipe-creator/recipe-creator.component';
import { RecipesViewFullComponent } from './pages/recipes-view-full/recipes-view-full.component';

const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'add', component: RecipeCreatorComponent },
  { path: 'browse', component: RecipesViewFullComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
