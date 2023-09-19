import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FeedPageComponent } from './pages/feed-page/feed-page.component';
import { RecipeCreatorComponent } from './pages/recipe-creator/recipe-creator.component';
import { RecipesViewFullComponent } from './pages/recipes-view-full/recipes-view-full.component';

const routes: Routes = [
  { path: '', component: FeedPageComponent },
  { path: 'recipe/add', component: RecipeCreatorComponent },
  { path: 'recipe/modify', component: RecipeCreatorComponent },
  { path: 'browse', component: RecipesViewFullComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
