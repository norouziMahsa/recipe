import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Recipe } from './recipe';
import { environment } from 'src/environments/environment';

@Injectable({providedIn: 'root'})
export class RecipeService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient){}

  public getRecipes(page: number, pageSize: number): Observable<Recipe[]> {
    //
    let params = new HttpParams()
      .set('page', page.toString())
      .set('pageSize', pageSize.toString());

    //
    return this.http.get<Recipe[]>(`${this.apiServerUrl}/recipe/all`, { params: params });
  }

  public searchByName(recipeName: string,page: number, pageSize: number): Observable<Recipe[]> {
    debugger;
    //
    let params = new HttpParams()
      .set('name', recipeName)
      .set('page', page.toString())
      .set('pageSize', pageSize.toString());

    //
    return this.http.get<Recipe[]>(`${this.apiServerUrl}/recipe/search`, { params: params });
  }


  public addRecipe(recipe: Recipe): Observable<Recipe> {
    debugger;
    return this.http.post<Recipe>(`${this.apiServerUrl}/recipe/add`, recipe);
  }

  public updateRecipe(recipe: Recipe , recipeId: number): Observable<Recipe> {
    debugger;
    return this.http.put<Recipe>(`${this.apiServerUrl}/recipe/${recipeId}`, recipe);
  }

  public deleteRecipe(recipeId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/recipe/delete/${recipeId}`);
  }
}
