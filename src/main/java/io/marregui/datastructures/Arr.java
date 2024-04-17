package io.marregui.datastructures;

import io.marregui.ThrLocal;

import java.io.Closeable;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Arr implements Closeable {
    private int[] arr = new int[64];
    private int idx; // insert position
    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);
    private final Lock rdLock = lock.readLock();
    private final Lock wrLock = lock.writeLock();


    public void insert(int value, int i) {
        if (i < 0) {
            throw new IndexOutOfBoundsException(i);
        }
        wrLock.lock();
        try {
            if (i >= arr.length) {
                throw new IndexOutOfBoundsException(i);
            }
            if (i == idx) {
                arr[i] = value;
            } else {
                System.arraycopy(arr, i, arr, i + 1, arr.length - i - 1);
                arr[i] = value;
            }
            idx++;
        } finally {
            wrLock.unlock();
        }
    }

    public void append(int value) {
        wrLock.lock();
        try {
            if (idx >= arr.length) {
                int[] arr2 = new int[arr.length * 2];
                System.arraycopy(arr, 0, arr2, 0, arr.length);
                arr = arr2;
            }
            arr[idx++] = value;
        } finally {
            wrLock.unlock();
        }
    }

    public int remove(int i) {
        if (i < 0) {
            throw new IndexOutOfBoundsException(i);
        }
        int v;
        wrLock.lock();
        try {
            if (i >= arr.length) {
                throw new IndexOutOfBoundsException(i);
            }
            v = arr[i];
            if (i != idx - 1) {
                System.arraycopy(arr, i + 1, arr, i, arr.length - i - 1);
            }
            idx--;
        } finally {
            wrLock.unlock();
        }
        return v;
    }

    public int getfirst() {
        rdLock.lock();
        if (idx < 1) {
            throw new IndexOutOfBoundsException();
        }
        int v;
        try {
            v = arr[0];
        } finally {
            rdLock.unlock();
        }
        return v;
    }

    public int getLast() {
        rdLock.lock();
        if (idx < 1) {
            throw new IndexOutOfBoundsException();
        }
        int v;
        try {
            v = arr[idx - 1];
        } finally {
            rdLock.unlock();
        }
        return v;
    }

    public int get(int i) {
        if (i < 0) {
            throw new IndexOutOfBoundsException(i);
        }
        rdLock.lock();
        if (i >= idx) {
            throw new IndexOutOfBoundsException(i);
        }
        int v;
        try {
            v = arr[i];
        } finally {
            rdLock.unlock();
        }
        return v;
    }

    public void sort() {
        wrLock.lock();
        try {
            sort(arr, 0, idx - 1);
        } finally {
            wrLock.unlock();
        }
    }

    static void sort(int[] a, int lo, int hi) {
        if (lo < hi) {
            int pivot = a[lo];
            int i = lo - 1;
            int j = hi + 1;
            while (i < j) {
                do {
                    i++;
                }
                while (i < hi && a[i] < pivot);

                do {
                    j--;
                } while (j > lo && a[j] > pivot);

                if (i < j) {
                    int tmp = a[j];
                    a[j] = a[i];
                    a[i] = tmp;
                }
            }

            sort(a, lo, j);
            sort(a, j + 1, hi);
        }
    }

    public int size() {
        int i;
        rdLock.lock();
        try {
            i = idx;
        } finally {
            rdLock.unlock();
        }
        return i;
    }

    public int capacity() {
        int i;
        rdLock.lock();
        try {
            i = arr.length;
        } finally {
            rdLock.unlock();
        }
        return i;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Arr that) {
            rdLock.lock();
            try {
                return idx == that.idx && Arrays.equals(arr, 0, idx, that.arr, 0, that.idx);
            } finally {
                rdLock.unlock();
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        rdLock.lock();
        try {
            int result = 1;
            for (int i = 0; i < idx; i++) {
                int e = arr[i];
                result = 31 * result + e;
            }
            return result;
        } finally {
            rdLock.unlock();
        }
    }

    @Override
    public String toString() {
        rdLock.lock();
        String str;
        try {
            int size = size();
            if (size == 0) {
                str = "[](size=0, buff=" + arr.length + ')';
            } else {
                StringBuilder sb = ThrLocal.getThreadLocalStringBuilder();
                sb.setLength(0);
                sb.append('[');
                int limit = size % 20;
                for (int i = 0; i < limit; i++) {
                    sb.append(arr[i]).append(", ");
                }
                if (size > 0) {
                    sb.setLength(sb.length() - 2);
                }
                if (size > limit) {
                    sb.append("...");
                }
                sb.append("](size=").append(size).append(", buff=").append(arr.length).append(')');
                str = sb.toString();
            }
        } finally {
            rdLock.unlock();
        }
        return str;
    }

    @Override
    public void close() {
        wrLock.lock();
        try {
            idx = 0;
            arr = null;
        } finally {
            wrLock.unlock();
        }
    }
}
