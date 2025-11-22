package com.example.tests;

import com.example.Cat;
import com.example.Feline;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CatTest {

    @Mock
    private Feline feline;

    @Test
    public void testGetSoundReturnsMeow() {
        Cat cat = new Cat(feline);
        assertEquals("Cat.getSound() должен возвращать 'Мяу'", "Мяу", cat.getSound());
    }

    @Test(expected = Exception.class)
    public void testGetFoodPropagatesExceptionFromFeline() throws Exception {
        when(feline.eatMeat()).thenThrow(new Exception("Ошибка получения еды"));
        Cat cat = new Cat(feline);
        cat.getFood();
    }

    @RunWith(Parameterized.class)
    public static class CatGetFoodParameterizedTest {

        @Parameterized.Parameter
        public List<String> expectedFood;

        @Parameterized.Parameter(1)
        public String testName;

        @Parameterized.Parameters(name = "{1}")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {Arrays.asList("Животные", "Птицы", "Рыба"), "Стандартная еда хищника"},
                    {Arrays.asList("Мыши", "Птицы"), "Еда маленького хищника"},
                    {Arrays.asList("Рыба"), "Только рыба"},
                    {Arrays.asList("Корм", "Мясо"), "Домашняя еда"},
                    {Arrays.asList(), "Пустой список еды"},
                    {Arrays.asList("Одно блюдо"), "Одно блюдо"}
            });
        }

        @Test
        public void testGetFoodWithDifferentFoodTypes() throws Exception {
            Feline feline = mock(Feline.class);
            when(feline.eatMeat()).thenReturn(expectedFood);

            Cat cat = new Cat(feline);
            List<String> actualFood = cat.getFood();

            assertEquals("Еда должна совпадать для теста: " + testName, expectedFood, actualFood);
            verify(feline, times(1)).eatMeat();
        }
    }
}