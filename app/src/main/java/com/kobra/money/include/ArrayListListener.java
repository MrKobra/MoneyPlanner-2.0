package com.kobra.money.include;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ArrayListListener<E> implements Iterable<E> {
    private ArrayList<E> list;
    private AddListener<E> addListener;

    public ArrayListListener() {
        list = new ArrayList<>();
    }

    public void setAddListener(AddListener<E> addListener) {
        this.addListener = addListener;
    }

    @NonNull
    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    @Override
    public void forEach(@NonNull Consumer<? super E> action) {
        list.forEach(action);
    }

    @NonNull
    @Override
    public Spliterator<E> spliterator() {
        return list.spliterator();
    }

    public void add(E item) {
        list.add(item);
        if(addListener != null) addListener.onAdd(item);
    }

    public void add(int index, E item) {
        list.add(index, item);
        if(addListener != null) addListener.onAdd(item);
    }

    public E get(int index) {
        return list.get(index);
    }

    public void clear() {
        list.clear();
    }

    public void remove(int index) {
        list.remove(index);
    }

    public int size() {
        return list.size();
    }

    public interface AddListener<E> {
        void onAdd(E item);
    }
}
