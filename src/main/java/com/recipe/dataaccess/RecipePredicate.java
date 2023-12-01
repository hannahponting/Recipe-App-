package com.recipe.dataaccess;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import com.recipe.entities.Recipe;

import static org.apache.commons.lang3.StringUtils.isNumeric;

public class RecipePredicate {
    private SearchCriteria criteria;

    public RecipePredicate(SearchCriteria param) {
        this.criteria = param;
    }

    public BooleanExpression getPredicate() {
        PathBuilder<Recipe> entityPath = new PathBuilder<>(Recipe.class, "recipe");

        if (isNumeric(criteria.getValue().toString())) {
            NumberPath<Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
            int value = Integer.parseInt(criteria.getValue().toString());
            switch (criteria.getOperation()) {
                case "=":
                    return path.eq(value);
                case ">=":
                    return path.goe(value);
                case "<=":
                    return path.loe(value);
            }
        } else {
            StringPath path = entityPath.getString(criteria.getKey());
            if (criteria.getOperation().equalsIgnoreCase("=")) {
                return path.containsIgnoreCase(criteria.getValue().toString());
            }
        }
        return null;
    }

}
