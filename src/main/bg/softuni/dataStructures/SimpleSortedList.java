package main.bg.softuni.dataStructures;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class SimpleSortedList<E extends Comparable<E>> implements SimpleOrderedBag<E> {

    private static final int DEFAULT_SIZE = 16;
    private static final String DEFAULT_SORT = "ascending";
    private E[] innerCollection;
    private int size;
    private String sortType;

    public SimpleSortedList(Class<E> type, String sortType, int capacity) {
        this.initializeInnerCollection(type, capacity);
        this.setSortType(sortType);
    }

    public SimpleSortedList(Class<E> type, int capacity) {
        this(type, DEFAULT_SORT, capacity);
    }

    public SimpleSortedList(Class<E> type, String sortType) {
        this(type, sortType, DEFAULT_SIZE);
    }

    public SimpleSortedList(Class<E> type) {
        this(type, DEFAULT_SORT, DEFAULT_SIZE);
    }

    private void setSortType(String sortType) {
        if (!sortType.equalsIgnoreCase("ascending") && !sortType.equalsIgnoreCase("descending")) {
            throw new IllegalArgumentException("Invalid sort type");
        }
        this.sortType = sortType;
    }

    @Override
    public void add(E element) {
        if (element == null) {
            throw new IllegalArgumentException();
        }
        if (this.size() == this.innerCollection.length) {
            this.resize();
        }

        this.innerCollection[this.size()] = element;
        this.size++;
        this.innerCollection = this.insertionSort(this.innerCollection, this.size(), this.sortType);
    }

    @Override
    public void addAll(Collection<E> elements) {
        if (this.size() + elements.size() >= this.innerCollection.length) {
            this.multiResize(elements);
        }

        for (E element : elements) {
            if (element == null) {
                throw new IllegalArgumentException();
            }
            this.innerCollection[this.size()] = element;
            this.size++;
        }

        this.innerCollection = this.insertionSort(this.innerCollection, this.size(), this.sortType);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public String joinWith(String joiner) {
        if (joiner == null) {
            throw new IllegalArgumentException();
        }
        StringBuilder output = new StringBuilder();

        for (E element : this) {
            output.append(element).append(joiner);
        }

        output.setLength(output.length() - joiner.length());
        return output.toString();
    }

    @Override
    public Iterator<E> iterator() {

        return new Iterator<E>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return this.index < size();
            }

            @Override
            public E next() {
                return innerCollection[this.index++];
            }
        };
    }

    @Override
    public int capacity() {
        return this.innerCollection.length;
    }

    @Override
    public boolean remove(E elementToRemove) {
        if (elementToRemove == null) {
            throw new IllegalArgumentException();
        }
        int removedIndex = -1;
        removedIndex = this.tryToRemove(elementToRemove, removedIndex);
        if (removedIndex != -1) {
            this.assignNewIndexes(removedIndex);
            this.size--;
            return true;
        }

        return false;
    }


    @SuppressWarnings("unchecked")
    private void initializeInnerCollection(Class<E> type, int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity cannot be negative or zero");
        }

        this.innerCollection = (E[]) Array.newInstance(type, capacity);
    }

    private void resize() {
        E[] newCollection = Arrays.copyOf(this.innerCollection, this.innerCollection.length * 2);
        this.innerCollection = newCollection;
    }

    private void multiResize(Collection<E> elements) {
        int newSize = this.innerCollection.length * 2;
        while (this.size() + elements.size() >= newSize) {
            newSize *= 2;
        }

        E[] newCollection = Arrays.copyOf(this.innerCollection, this.innerCollection.length * 2);
        this.innerCollection = newCollection;
    }

    private E[] insertionSort(E[] innerCollection, int size, String sortType) {
        E temp;
        for (int i = 1; i < size; i++) {
            for (int j = i; j > 0; j--) {
                if (sortType.equals("ascending") ?
                        innerCollection[j].compareTo(innerCollection[j - 1]) <= 0 :
                        innerCollection[j].compareTo(innerCollection[j - 1]) > 0) {
                    temp = innerCollection[j];
                    innerCollection[j] = innerCollection[j - 1];
                    innerCollection[j - 1] = temp;
                } else {
                    break;
                }
            }
        }
        return innerCollection;
    }

    private int tryToRemove(E elementToRemove, int removedIndex) {
        for (int index = 0; index < this.size(); index++) {
            E currentElement = this.innerCollection[index];
            if (elementToRemove.compareTo(currentElement) == 0) {
                removedIndex = index;
                this.innerCollection[index] = null;
                break;
            }
        }
        return removedIndex;
    }

    private void assignNewIndexes(int removedIndex) {
        System.arraycopy(this.innerCollection,
                removedIndex + 1,
                this.innerCollection,
                removedIndex,
                this.size() - 1 - removedIndex);
        this.innerCollection[this.size() - 1] = null;
    }
}