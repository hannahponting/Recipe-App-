-- Insert or update recipe with id 101
MERGE INTO recipe AS target
USING (VALUES (101,'Grilled Lemon Herb Chicken', 4, '25 minutes',25,'MEXICAN','EASY','LUNCH','LOW','MEDIUM')) AS source (id, name, SERVING_NO, TIME_TO_COOK, COOKING_MINUTES, CUISINE_TYPE, DIFFICULTY_LEVEL, MEAL_TYPE, COST_TYPE, SPICE_TYPE)
ON target.id = source.id
WHEN NOT MATCHED THEN INSERT (id, name, SERVING_NO, TIME_TO_COOK, COOKING_MINUTES, CUISINE_TYPE, DIFFICULTY_LEVEL, MEAL_TYPE, COST_TYPE, SPICE_TYPE)
VALUES (source.id, source.name, source.SERVING_NO, source.TIME_TO_COOK, source.COOKING_MINUTES, source.CUISINE_TYPE, source.DIFFICULTY_LEVEL, source.MEAL_TYPE, source.COST_TYPE, source.SPICE_TYPE);

-- Insert or update ingredients for recipe with id 101
MERGE INTO ingredients AS target
USING (VALUES (101,'4 boneless, skinless chicken breasts'),(101,'lemon juice'),(101,'olive oil'),(101,'minced garlic'),(101,'oregano'),(101,'salt'),(101,'pepper')) AS source (id, ingredients_list)
ON target.id = source.id AND target.ingredients_list = source.ingredients_list
WHEN NOT MATCHED THEN INSERT (id, ingredients_list) VALUES (source.id, source.ingredients_list);

-- Insert or update instructions for recipe with id 101
MERGE INTO instructions AS target
USING (VALUES (101, 'In a bowl, mix lemon juice, olive oil, minced garlic, oregano, salt, and pepper.')) AS source (id, instructions)
ON target.id = source.id AND target.instructions = source.instructions
WHEN NOT MATCHED THEN INSERT (id, instructions) VALUES (source.id, source.instructions);

-- Insert or update recipe with id 102
MERGE INTO recipe AS target
USING (VALUES (102,'Vegetarian Quinoa Bowl', 4, '20 minutes',20,'ITALIAN','MODERATE','DINNER','MODERATE','MILD')) AS source (id, name, SERVING_NO, TIME_TO_COOK, COOKING_MINUTES, CUISINE_TYPE, DIFFICULTY_LEVEL, MEAL_TYPE, COST_TYPE, SPICE_TYPE)
ON target.id = source.id
WHEN NOT MATCHED THEN INSERT (id, name, SERVING_NO, TIME_TO_COOK, COOKING_MINUTES, CUISINE_TYPE, DIFFICULTY_LEVEL, MEAL_TYPE, COST_TYPE, SPICE_TYPE)
VALUES (source.id, source.name, source.SERVING_NO, source.TIME_TO_COOK, source.COOKING_MINUTES, source.CUISINE_TYPE, source.DIFFICULTY_LEVEL, source.MEAL_TYPE, source.COST_TYPE, source.SPICE_TYPE);

-- Insert or update ingredients for recipe with id 102
MERGE INTO ingredients AS target
USING (VALUES (102,'1 cup quinoa; 2 cups vegetable broth'),(102,'1 cup sun-dried tomatoes')) AS source (id, ingredients_list)
ON target.id = source.id AND target.ingredients_list = source.ingredients_list
WHEN NOT MATCHED THEN INSERT (id, ingredients_list) VALUES (source.id, source.ingredients_list);

-- Insert or update instructions for recipe with id 102
MERGE INTO instructions AS target
USING (VALUES (102,'Cook quinoa in vegetable broth according to package instructions.')) AS source (id, instructions)
ON target.id = source.id AND target.instructions = source.instructions
WHEN NOT MATCHED THEN INSERT (id, instructions) VALUES (source.id, source.instructions);

-- Insert or update person with id 101
MERGE INTO person AS target
USING (VALUES (101, 'Dave', 'Dykes', 'dave@dave.com')) AS source (id, first_name, last_name, email)
ON target.id = source.id
WHEN NOT MATCHED THEN INSERT (id, first_name, last_name, email) VALUES (source.id, source.first_name, source.last_name, source.email);

-- Insert or update rating with id 101
MERGE INTO rating AS target
USING (VALUES (101, 4, 0, 101, 101)) AS source (id, my_rating, favourite, person_id, recipe_id)
ON target.id = source.id AND target.person_id = source.person_id AND target.recipe_id = source.recipe_id
WHEN NOT MATCHED THEN INSERT (id, my_rating, favourite, person_id, recipe_id) VALUES (source.id, source.my_rating, source.favourite, source.person_id, source.recipe_id);

-- Insert or update rating with id 102
MERGE INTO rating AS target
USING (VALUES (102, 1, 101, 102)) AS source (id, favourite, person_id, recipe_id)
ON target.id = source.id AND target.person_id = source.person_id AND target.recipe_id = source.recipe_id
WHEN NOT MATCHED THEN INSERT (id, favourite, person_id, recipe_id) VALUES (source.id, source.favourite, source.person_id, source.recipe_id);
