package com.example.tests;

import com.example.Feline;
import com.example.Lion;
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
public class LionTest {

    @Mock
    private Feline feline;

    @Test
    public void testGetKittensCallsGetKittensOnFeline() throws Exception {
        when(feline.getKittens()).thenReturn(3);
        Lion lion = new Lion("Самец", feline);
        assertEquals(3, lion.getKittens());
        verify(feline, times(1)).getKittens();
    }

    @Test
    public void testGetFoodCallsEatMeatOnFeline() throws Exception {
        List<String> expectedFood = Arrays.asList("Антилопы", "Зебры");
        when(feline.eatMeat()).thenReturn(expectedFood);
        Lion lion = new Lion("Самец", feline);
        List<String> actualFood = lion.getFood();
        assertEquals(expectedFood, actualFood);
        verify(feline, times(1)).eatMeat();
    }

    @Test(expected = Exception.class)
    public void testGetFoodPropagatesExceptionFromFeline() throws Exception {
        when(feline.eatMeat()).thenThrow(new Exception("Ошибка при получении еды"));
        Lion lion = new Lion("Самка", feline);
        lion.getFood();
    }

    @RunWith(Parameterized.class)
    public static class LionConstructorParameterizedTest {

        @Parameterized.Parameter
        public String sex;

        @Parameterized.Parameter(1)
        public boolean expectedHasMane;

        @Parameterized.Parameter(2)
        public Class<? extends Exception> expectedException;

        @Parameterized.Parameters(name = "Пол: {0}, Ожидается грива: {1}, Ожидается исключение: {2}")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Самец", true, null},
                    {"Самка", false, null},
                    {"самец", true, null},
                    {"самка", false, null},
                    {"Неизвестный", false, Exception.class},
                    {"", false, Exception.class},
                    {null, false, Exception.class},
                    {"Male", false, Exception.class},
                    {"Female", false, Exception.class}
            });
        }

        @Test
        public void testLionConstructorWithDifferentSexes() throws Exception {
            Feline feline = mock(Feline.class);

            if (expectedException != null) {
                try {
                    new Lion(sex, feline);
                    fail("Ожидалось исключение для пола: " + sex);
                } catch (Exception e) {
                    assertTrue("Неверный тип исключения для пола: " + sex,
                            expectedException.isInstance(e));
                }
            } else {
                Lion lion = new Lion(sex, feline);
                assertEquals("Грива должна соответствовать полу: " + sex,
                        expectedHasMane, lion.doesHaveMane());
            }
        }
    }

    @RunWith(Parameterized.class)
    public static class LionGetKittensParameterizedTest {

        @Parameterized.Parameter
        public int kittensCount;

        @Parameterized.Parameter(1)
        public String sex;

        @Parameterized.Parameters(name = "Котят: {0}, Пол: {1}")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {0, "Самец"},
                    {1, "Самец"},
                    {3, "Самец"},
                    {5, "Самка"},
                    {10, "Самка"},
                    {100, "Самец"}
            });
        }

        @Test
        public void testGetKittensWithDifferentCounts() throws Exception {
            Feline feline = mock(Feline.class);
            when(feline.getKittens()).thenReturn(kittensCount);

            Lion lion = new Lion(sex, feline);
            int actualKittens = lion.getKittens();

            assertEquals("Количество котят должно быть " + kittensCount + " для пола: " + sex,
                    kittensCount, actualKittens);
            verify(feline, times(1)).getKittens();
        }
    }
}