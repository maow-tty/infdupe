package dev.maow.infdupe.util;

import java.util.function.Supplier;

public final class Lazy<T> {
    private final Supplier<T> supplier;
    private boolean evaluated;
    private T value;

    public Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
        this.evaluated = false;
        this.value = null;
    }

    public T get() {
        if (!evaluated) {
            this.value = supplier.get();
            this.evaluated = true;
        }
        return this.value;
    }
}