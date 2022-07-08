package uk.fergcb.cash;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Heap {

    private final int maxSize;
    private final int[] memory;
    public final List<Span> availableSpans;

    public Heap(int maxSize) {
        this.maxSize = maxSize;
        this.memory = new int[maxSize];
        this.availableSpans = new ArrayList<>();
        availableSpans.add(new Span(0, maxSize - 1));
    }

    public int allocate(int size, int[] data) {
        for (Span span : availableSpans) {
            if (span.length() < size) continue;
            final int offset = span.start;
            span.start += size;
            write(offset, size, data);
            collapseSpans();
            return offset;
        }

        throw new OutOfMemoryError("Not enough free memory on heap.");
    }

    public int allocate(int size) {
        return allocate(size, new int[size]);
    }

    public void deallocate(int offset, int size) {
        availableSpans.add(new Span(offset, offset + size - 1));
        collapseSpans();
    }

    public void write (int offset, int size, int[] data) {
        System.arraycopy(data, 0, memory, offset, size);
    }

    public int[] read(int offset, int size) {
        int[] data = new int[size];
        System.arraycopy(memory, 0, data, 0, size);
        return data;
    }

    private void collapseSpans() {
        if (availableSpans.size() == 0) return;

        Stack<Span> stack = new Stack<>();

        availableSpans.sort((a, b) -> (int)Math.signum(a.start - b.start));

        stack.push(availableSpans.get(0));

        for (int i = 1; i < availableSpans.size(); i++) {
            Span top = stack.peek();
            Span current = availableSpans.get(i);
            if (top.end < current.start) {
                stack.push(current);
            } else if (top.end < current.end) {
                top.end = current.end;
            }
        }

        availableSpans.clear();
        availableSpans.addAll(stack);
    }

    private static class Span {
        int start, end;

        public Span(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int length() {
            return (end - start) + 1;
        }

        public String toString() {
            return String.format("[%d, %d]", start, end);
        }
    }
}
