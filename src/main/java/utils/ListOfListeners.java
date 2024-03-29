package utils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ListOfListeners<T> {
    private final ArrayList<WeakReference<T>> listeners = new ArrayList<WeakReference<T>>();

    public synchronized final void add(T listener) {
        listeners.add(new WeakReference<T>(listener));
    }

    public synchronized final void remove(T listenerToRemove) {
        for (int i = listeners.size() - 1; i >= 0; --i) {
            T listener = listeners.get(i).get();
            if (listener == null || listener == listenerToRemove) {
                listeners.remove(i);
            }
        }
    }

    public synchronized final void clear() {
        listeners.clear();
    }

    protected synchronized final void callAllListeners(Function<T> e) {
        for (int i = listeners.size() - 1; i >= 0; --i) {
            T listener = listeners.get(i).get();
            if (listener == null) listeners.remove(i);
            else e.call(listener);
        }
    }

    protected synchronized final <Arg1> void callAllListeners(Function1<T, Arg1> e, Arg1 arg) {
        for (int i = listeners.size() - 1; i >= 0; --i) {
            T listener = listeners.get(i).get();
            if (listener == null) listeners.remove(i);
            else e.call(listener, arg);
        }
    }

    protected synchronized final <Arg1, Arg2> void callAllListeners(Function2<T, Arg1, Arg2> e, Arg1 arg1, Arg2 arg2) {
        for (int i = listeners.size() - 1; i >= 0; --i) {
            T listener = listeners.get(i).get();
            if (listener == null) listeners.remove(i);
            else e.call(listener, arg1, arg2);
        }
    }

    protected synchronized final <Arg1, Arg2, Arg3> void callAllListeners(Function3<T, Arg1, Arg2, Arg3> e, Arg1 arg1, Arg2 arg2, Arg3 arg3) {
        for (int i = listeners.size() - 1; i >= 0; --i) {
            T listener = listeners.get(i).get();
            if (listener == null) listeners.remove(i);
            else e.call(listener, arg1, arg2, arg3);
        }
    }

    protected static interface Function<Listener> {
        public void call(Listener listener);
    }

    protected static interface Function1<Listener, Arg1> {
        public void call(Listener listener, Arg1 arg1);
    }

    protected static interface Function2<Listener, Arg1, Arg2> {
        public void call(Listener listener, Arg1 arg1, Arg2 arg2);
    }

    protected static interface Function3<Listener, Arg1, Arg2, Arg3> {
        public void call(Listener listener, Arg1 arg1, Arg2 arg2, Arg3 arg3);
    }
}

