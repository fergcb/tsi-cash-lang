package uk.fergcb.cash;

public enum InstructionType {

    HALT(0x0000_0000),
    NOP(0x0000_0001),
    PUSH(0x0001_0000),
    POP(0x0001_0001),
    DUP(0x0001_0010),
    SWAP(0x0001_0011),
    ARG(0x0001_0100),
    ADD(0x0010_0000),
    SUB(0x0010_0010),
    MUL(0x0011_0000),
    DIV(0x0011_0010),
    MOD(0x0011_0011),
    CALL(0x0100_0000),
    RETURN(0x0100_0001),
    JMP(0x0100_0010),
    JZ(0x0100_0011),
    PRINT(0x1000_0000);


    public final short opcode;

    InstructionType(int opcode) {
        this.opcode = (short)opcode;
    }
}
