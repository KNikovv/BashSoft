package main.bg.softuni.dataStructures;

import java.util.Collection;

public interface SimpleOrderedBag<E extends Comparable<E>> extends Iterable<E> {

    void add(E element);

    void addAll(Collection<E> elements);

    int size();

    String joinWith(String joiner);

    boolean remove(E element);

    int capacity();
}
