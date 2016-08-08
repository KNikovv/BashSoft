package bg.softuni.dataStructures;


import main.bg.softuni.dataStructures.SimpleOrderedBag;
import main.bg.softuni.dataStructures.SimpleSortedList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleOrderedBagTests {

    private SimpleOrderedBag<String> names;

    @Before
    public void setUp() {
        this.names = new SimpleSortedList<>(String.class);
    }

    //region Constructor
    @Test
    public void testEmptyCtor() {
        this.names = new SimpleSortedList<>(String.class);

        Assert.assertEquals(16, this.names.capacity());
        Assert.assertEquals(0, this.names.size());
    }

    @Test
    public void testCtorWithInitialCapacityShouldBeCorrect() {
        this.names = new SimpleSortedList<>(String.class, 20);

        Assert.assertEquals(20, this.names.capacity());
        Assert.assertEquals(0, this.names.size());
    }

    @Test
    public void testCtorWithInitialComparer() {
        this.names = new SimpleSortedList<>(String.class, "ascending");

        Assert.assertEquals(16, this.names.capacity());
        Assert.assertEquals(0, this.names.size());
    }

    @Test
    public void testCtorWithAllParams() {
        this.names = new SimpleSortedList<String>(String.class, "ascending", 30);

        Assert.assertEquals(30, this.names.capacity());
        Assert.assertEquals(0, this.names.size());
    }
    //endregion

    //region Adding
    @Test
    public void testAddingShouldIncreaseSize() {
        this.names.add("Pesho");
        Assert.assertEquals(1, this.names.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullShouldThrowException() {
        this.names.add(null);
    }

    @Test
    public void testAddUnsortedDataIsHeldSorted() {
        this.names.add("Rosen");
        this.names.add("Georgi");
        this.names.add("Balkan");

        String[] expected = {"Balkan", "Georgi", "Rosen"};
        int index = 0;
        for (String name : this.names) {
            Assert.assertEquals(expected[index++], name);
        }
    }

    @Test
    public void testAddingMoreThanInitialCapacity() {
        for (int i = 0; i < 17; i++) {
            this.names.add("Pesho");
        }

        Assert.assertFalse(this.names.capacity() == 16);
        Assert.assertEquals(17, this.names.size());
    }

    @Test
    public void testAddingAllFromCollectionIncreasesSize() {
        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList("Pesho", "Kiro"));
        this.names.addAll(list);

        Assert.assertEquals(2, this.names.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddingAllFromNullThrowsException() {
        this.names.addAll(Arrays.asList("Pesho", "Kiro", null));
    }

    @Test
    public void testAddAllKeepsSorted() {
        this.names.addAll(Arrays.asList("Rosen", "Georgi", "Balkan"));

        String[] expected = {"Balkan", "Georgi", "Rosen"};
        int index = 0;
        for (String name : this.names) {
            Assert.assertEquals(expected[index++], name);
        }
    }

    @Test
    public void testRemoveValidElementDecreasesSize() {
        this.names.addAll(Arrays.asList("Kircho", "Penka"));

        this.names.remove("Penka");

        Assert.assertEquals(1, this.names.size());
    }

    @Test
    public void testRemoveValidElementRemovesSelectedOne() {
        this.names.addAll(Arrays.asList("Ivan", "Nasko"));

        this.names.remove("Ivan");
        for (String name : this.names) {
            Assert.assertEquals("Nasko", name);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemovingNullThrowsException() {
        this.names.add("Pesho");
        this.names.remove(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testJoinWithNull() {
        this.names.addAll(Arrays.asList("PESHO", "ROYAL", "SLAV", "TOSHKO"));
        this.names.joinWith(null);
    }

    @Test
    public void testJoinWorksFine() {
        this.names.addAll(Arrays.asList("PESHO", "ROYAL", "SLAV", "TOSHKO"));
        String actual = this.names.joinWith(", ");

        String expected = "PESHO, ROYAL, SLAV, TOSHKO";

        Assert.assertEquals(expected, actual);
    }
}
