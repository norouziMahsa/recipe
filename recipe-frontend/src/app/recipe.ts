import {Ingredient} from './ingredient';

export interface Recipe {
  id: number;
  name: string;
  description: string;
  ingredients: Ingredient[];
  timeRequiredToCook: number;
  typeOfMeal: string;
}





