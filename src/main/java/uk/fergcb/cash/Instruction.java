package uk.fergcb.cash;

public record Instruction(InstructionType type, int payload) {
    public Instruction(InstructionType type) {
        this(type, 0);
    }
}
