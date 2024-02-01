INSERT INTO recipe (id,name, SERVING_NO, TIME_TO_COOK, COOKING_MINUTES, CUISINE_TYPE, DIFFICULTY_LEVEL, MEAL_TYPE, COST_TYPE, SPICE_TYPE)
VALUES (101,'Grilled Lemon Herb Chicken', 4, '25 minutes',25,'MEXICAN','EASY','LUNCH','LOW','MEDIUM');
INSERT INTO ingredients (id,ingredients_list)
VALUES (101,'4 boneless, skinless chicken breasts'),(101,'lemon juice'),(101,'olive oil'),(101,'minced garlic'),(101,'oregano'),(101,'salt'),(101,'pepper');
INSERT INTO instructions(id,instructions)
VALUES (101, 'In a bowl, mix lemon juice, olive oil, minced garlic, oregano, salt, and pepper.');
INSERT INTO recipe (id,name, SERVING_NO, TIME_TO_COOK, COOKING_MINUTES, CUISINE_TYPE, DIFFICULTY_LEVEL, MEAL_TYPE, COST_TYPE, SPICE_TYPE)
VALUES (102,'Vegetarian Quinoa Bowl', 4, '20 minutes',20,'ITALIAN','MODERATE','DINNER','MODERATE','MILD');

INSERT INTO ingredients (id,ingredients_list)
VALUES (102,'1 cup quinoa; 2 cups vegetable broth'),(102,'1 cup sun-dried tomatoes');
INSERT INTO instructions(id,instructions)
VALUES (102,'Cook quinoa in vegetable broth according to package instructions.');
INSERT INTO person(id, first_name, last_name, email)
VALUES (101, 'Dave', 'Dykes', 'dave@dave.com');
INSERT INTO rating(id, my_rating, favourite, person_id, recipe_id)
VALUES (101, 4, 0, 101, 101);
INSERT INTO rating(id, favourite, person_id, recipe_id)
VALUES (102, 1, 101, 102);