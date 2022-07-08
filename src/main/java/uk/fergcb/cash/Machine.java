package uk.fergcb.cash;

import java.util.*;

public class Machine {

    final Stack<Frame> callStack = new Stack<>();;
    final List<Instruction> program;

    int ip = 0;

    public Machine(Collection<Instruction> instructions) {
        this.program = new ArrayList<>(instructions);
        callStack.push(new Frame(-1));
    }

    public void run() {
        boolean shouldHalt;

        do {
            Instruction inst = program.get(ip++);
            shouldHalt = execute(inst);
        } while (!shouldHalt && ip < program.size());

        System.out.println(callStack.peek().stack);
    }

    public boolean execute(Instruction inst) {
        Frame frame = callStack.peek();
        // System.out.printf("%s %d %s\n", inst.type(), inst.payload(), frame.stack);
        switch(inst.type()) {
            case HALT -> {
                return true;
            }
            case NOP -> {
                return false;
            }
            case PUSH -> {
                frame.stack.push(inst.payload());
            }
            case POP -> {
                frame.stack.pop();
            }
            case DUP -> {
                frame.stack.push(frame.stack.peek());
            }
            case SWAP -> {
                int a = frame.stack.pop();
                int b = frame.stack.pop();
                frame.stack.push(a);
                frame.stack.push(b);
            }
            case ARG -> {
                Frame parent = callStack.get(callStack.size() - 2);
                int offset = inst.payload() + 1;
                int arg = parent.stack.get(parent.stack.size() - offset);
                frame.stack.push(arg);
            }
            case ADD -> {
                int a = frame.stack.pop();
                int b = frame.stack.pop();
                frame.stack.push(a + b);
            }
            case SUB -> {
                int a = frame.stack.pop();
                int b = frame.stack.pop();
                frame.stack.push(b - a);
            }
            case MUL -> {
                int a = frame.stack.pop();
                int b = frame.stack.pop();
                frame.stack.push(a * b);
            }
            case DIV -> {
                int a = frame.stack.pop();
                int b = frame.stack.pop();
                frame.stack.push(b / a);
            }
            case MOD -> {
                int a = frame.stack.pop();
                int b = frame.stack.pop();
                frame.stack.push(b % a);
            }
            case CALL -> {
                int funcAddress = inst.payload();
                callStack.push(new Frame(ip));
                ip = funcAddress;
            }
            case RETURN -> {
                Frame parent = callStack.get(callStack.size() - 2);
                int argCount = inst.payload();
                for (int i = 0; i < argCount; i++) {
                    parent.stack.pop();
                }
                if (!frame.stack.isEmpty()) {
                    parent.stack.push(frame.stack.pop());
                }
                ip = frame.returnAddress;
                callStack.pop();
            }
            case JMP -> {
                ip = inst.payload();
            }
            case JZ -> {
                if (frame.stack.pop() != 0) return false;
                ip = inst.payload();
            }
            case PRINT -> {
                System.out.println(frame.stack.pop());
            }
        }

        // System.out.println(callStack.peek().stack);

        return false;
    }

}
