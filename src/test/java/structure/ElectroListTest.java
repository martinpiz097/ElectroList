/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structure;

import com.github.martinpiz097.electrolist.structure.ElectroList;
import org.junit.jupiter.api.*;

import java.util.*;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author martin
 */
public class ElectroListTest {

    private final List<String> electroList = new ElectroList<>();
    private static final int size = 10;

    @BeforeEach
    public void loadDefaultData() {
        for (int i = 0; i < size; i++) {
            electroList.add(String.valueOf(i));
        }
    }

    @AfterEach
    public void clearData() {
        electroList.clear();
    }

    @DisplayName("ElecroList add test")
    @Test
    public void add() {
        assertFalse(electroList.isEmpty());
        for (int i = 0; i < size; i++) {
            electroList.add(String.valueOf(i));
        }
        assertFalse(electroList.isEmpty());
    }

    @DisplayName("get middle")
    @Test
    public void getMiddle() {
        String middle = electroList.get(size / 2);
        assertNotNull(middle);
        assertEquals("5", middle);
    }

    @DisplayName("set middle")
    @Test
    public void setMiddle() {
        electroList.set(size / 2, "XD");
        assertEquals("XD", electroList.get(size / 2));
    }

    @DisplayName("remove middle")
    @Test
    public void removeMiddle() {
        electroList.remove(size / 2);
        assertEquals(size - 1, electroList.size());
    }

    @DisplayName("filter lambda seq")
    @Test
    public void filterLambdaSeq() {
        Predicate<String> filter = (element) -> element.equalsIgnoreCase("XD");

        long count = electroList.stream().filter(element -> {
            boolean result = filter.test(element);
            assertFalse(result);
            System.out.println("Filter result: " + result + " for element " + element);
            return result;
        }).count();

        assertEquals(0, count);
    }

    @DisplayName("filter lambda parallel")
    @Test
    public void filterLambdaParallel() {
        Predicate<String> filter = (element) -> element.equalsIgnoreCase("1");

        long count = electroList.parallelStream().filter(element -> {
            boolean result = filter.test(element);
            System.out.println("Parallel filter result: " + result + " for element " + element);
            return result;
        }).count();

        assertEquals(1, count);
    }

    @DisplayName("map lambda sequential")
    @Test
    public void mapLambdaSeq() {
        electroList.stream().map(element -> element.concat(" XD"))
                .forEach(element -> {
                    assertTrue(element.endsWith(" XD"));
                    System.out.println("Map Sequential Element: " + element);
                });
    }

    @DisplayName("map lambda parallel")
    @Test
    public void mapLambdaParallel() {
        electroList.parallelStream().map(element -> element.concat(" XD"))
                .forEach(element -> {
                    assertTrue(element.endsWith(" XD"));
                    System.out.println("Map Parallel Element: " + element);
                });
    }


}
