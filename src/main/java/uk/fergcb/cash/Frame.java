package uk.fergcb.cash;

import java.util.*;

public class Frame {
    final Stack<Integer> stack;

    final int returnAddress;

    public Frame(int returnAddress, List<Integer> args) {
        this.returnAddress = returnAddress;
        this.stack = new Stack<>();
        stack.addAll(args);
    }

    public Frame(int returnAddress) {
        this(returnAddress, new ArrayList<>());
    }
}
