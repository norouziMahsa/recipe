import {Component, OnInit} from '@angular/core';
import {Recipe} from './recipe';
import {RecipeService} from './recipe.service';
import {HttpErrorResponse} from '@angular/common/http';
import {NgForm} from '@angular/forms';
import {Ingredient} from './ingredient';
import {TypeOfMeal} from './typeOfMeal';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  public recipes: Recipe[] = [];
  public title?: string;
  public editRecipe?: any;
  public ingredients: Ingredient[] = [];
  public selectedMealType: TypeOfMeal = TypeOfMeal.BREAKFAST;
  public typeOfMeals : string[] = [];

  //--pagination
  currentPage = 0; // Initial page number
  pageSize = 5; // Number of items per page
  totalItems = 0;
  totalPages = 0;


  constructor(private recipeService: RecipeService){}

  ngOnInit() {
    this.getRecipes();
    this.typeOfMeals = Object.values(TypeOfMeal);
  }

  public getRecipes(): void {
    this.recipeService.getRecipes(this.currentPage, this.pageSize)
      .subscribe((data: any) => {
        debugger;
        this.recipes = data.content;
        this.totalItems = data.totalElements;
        this.totalPages = Math.floor((this.totalItems - 1) / this.pageSize) + 1;
      });
  }

  public onupdateRecipe(recipe: Recipe): void {
    recipe.ingredients = this.ingredients;
    this.recipeService.updateRecipe(recipe, recipe.id).subscribe(
      (response: Recipe) => {
        console.log(response);
        this.getRecipes();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onAddRecipe(addForm: NgForm): void {

    addForm.value.ingredients = this.ingredients;
    this.recipeService.addRecipe(addForm.value).subscribe(
      (response?: Recipe) => {
        console.log(response);
        this.getRecipes();
        addForm.reset();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
      }
    );
  }

  public searchRecipes(key: string): void {
    console.log(key);

    this.recipeService.searchByName(key.toLowerCase(),0, 5)
      .subscribe((data: any) => {
        this.currentPage = 0;
        this.recipes = data.content;
        this.totalItems = data.totalElements;
        this.totalPages = Math.floor((this.totalItems - 1) / this.pageSize) + 1;
      });

  }


  public onOpenModal(recipe: any, mode?: string): void {
    debugger;
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      this.selectedMealType = TypeOfMeal.BREAKFAST;
      button.setAttribute('data-target', '#addRecipeModal');
    }
    if (mode === 'edit') {
      this.editRecipe = recipe;
      this.selectedMealType = recipe.typeOfMeal;
      this.ingredients = recipe.ingredients;
      debugger;
      button.setAttribute('data-target', '#updateRecipeModal');
    }
    container?.appendChild(button);
    button.click();
  }


  addIngredient(): void {
    const newIngredient: Ingredient = {
      name: '',
      amount: 0
    };
    this.ingredients.push(newIngredient);
  }

  removeIngredient(index: number): void {
    this.ingredients.splice(index, 1);
  }

  pageChanged(event: any) {
    this.currentPage = event.page;
    this.getRecipes();
  }

  previousPage() {
    debugger;
    if (this.currentPage >= 1) {
      this.currentPage--;
      this.getRecipes();
    }
  }

  nextPage() {
    debugger;
    if (this.currentPage < this.totalPages-1) {
      this.currentPage++;
      this.getRecipes();
    }
  }


}
