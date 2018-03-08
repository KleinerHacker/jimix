package org.pcsoft.app.jimix.commons.util;

import java.util.Comparator;
import java.util.function.Function;

public final class ComparatorUtils {
    public enum ComparatorDirection {
        GoToTop(-1),
        GoToBottom(1);

        private final int comparatorValue;

        ComparatorDirection(int comparatorValue) {
            this.comparatorValue = comparatorValue;
        }
    }

    @SuppressWarnings("unchecked")
    @SafeVarargs
    public static <T> Comparator<T> chainedComparator(Comparator<T>... comparators) {
        return org.apache.commons.collections.ComparatorUtils.chainedComparator(comparators);
    }

    public static <T, R extends Comparable<R>> Comparator<T> compareWithNull(Function<T, R> func, ComparatorDirection nullValueDirection) {
        return compareWithNull(func, Comparable::compareTo, nullValueDirection);
    }

    public static <T, R> Comparator<T> compareWithNull(Function<T, R> func, Comparator<R> valueComparator, ComparatorDirection nullValueDirection) {
        return (o1, o2) -> {
            final R v1 = func.apply(o1);
            final R v2 = func.apply(o2);

            if (v1 == null && v2 == null)
                return 0;
            if (v1 == null)
                return nullValueDirection.comparatorValue;
            if (v2 == null)
                return -nullValueDirection.comparatorValue;

            return valueComparator.compare(v1, v2);
        };
    }

    private ComparatorUtils() {
    }
}
