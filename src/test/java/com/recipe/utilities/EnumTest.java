package com.recipe.utilities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EnumTest {



    @Test
    void testOfCostEnum(){
        String low =Cost.LOW.getName();
        String lunch = MealTime.LUNCH.getName();
        String easy = Difficulty.EASY.getName();
        String mild = SpiceLevel.MILD.getName();

        assertEquals("Low", low);
        assertEquals("Lunch", lunch);
        assertEquals("Easy", easy);
        assertEquals("Mild", mild);

    }

}