package com.example.tests;

import com.example.Feline;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class FelineTest {

    private final int inputKittens;
    private final int expectedKittens;

    public FelineTest(int inputKittens, int expectedKittens) {
        this.inputKittens = inputKittens;
        this.expectedKittens = expectedKittens;
    }

    @Parameters(name = "getKittens({0}) = {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {0, 0},
                {1, 1},
                {3, 3},
                {5, 5},
                {10, 10},
                {100, 100},
                {-1, -1},
                {-5, -5}
        });
    }

    @Test
    public void testGetKittensWithParameter() {
        Feline feline = new Feline();
        int actual = feline.getKittens(inputKittens);
        assertEquals("Feline.getKittens(" + inputKittens + ") должен возвращать " + expectedKittens,
                expectedKittens, actual);
    }

    @Test
    public void testEatMeatReturnsCorrectFood() throws Exception {
        Feline feline = new Feline();
        List<String> expectedFood = Arrays.asList("Животные", "Птицы", "Рыба");
        List<String> actualFood = feline.eatMeat();
        assertEquals("Feline.eatMeat() должен возвращать правильную еду для хищника", expectedFood, actualFood);
    }

    @Test
    public void testGetFamilyReturnsCorrectFamily() {
        Feline feline = new Feline();
        String expected = "Кошачьи";
        String actual = feline.getFamily();
        assertEquals("Feline.getFamily() должен возвращать 'Кошачьи'", expected, actual);
    }

    @Test
    public void testGetKittensWithoutParametersReturnsOne() {
        Feline feline = new Feline();
        int expected = 1;
        int actual = feline.getKittens();
        assertEquals("Feline.getKittens() без параметров должен возвращать 1", expected, actual);
    }
}