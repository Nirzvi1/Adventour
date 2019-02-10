package com.adventour.adventour.data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by FIXIT on 2019-02-09.
 */

public class Pair<T> implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;

    public T first;
    public T second;

    public Pair(T p1, T p2) {
        first = p1;
        second = p2;
    }

}
