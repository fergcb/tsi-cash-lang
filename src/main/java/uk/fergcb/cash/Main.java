package uk.fergcb.cash;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Instruction[] program = {
                new Instruction(InstructionType.PUSH, 10),
                new Instruction(InstructionType.CALL, 4 /* FIB */),
                new Instruction(InstructionType.PRINT),
                new Instruction(InstructionType.HALT),

                /* FIB */
                new Instruction(InstructionType.ARG),
                new Instruction(InstructionType.JZ, 21 /* if 0, return */),
                new Instruction(InstructionType.ARG),
                new Instruction(InstructionType.PUSH, 1),
                new Instruction(InstructionType.SUB),
                new Instruction(InstructionType.JZ, 21 /* if 1, return 1 */),
                new Instruction(InstructionType.ARG),
                new Instruction(InstructionType.PUSH, 1),
                new Instruction(InstructionType.SUB),
                new Instruction(InstructionType.ARG),
                new Instruction(InstructionType.PUSH, 2),
                new Instruction(InstructionType.SUB),
                new Instruction(InstructionType.CALL, 4 /* FIB(n-2) */),
                new Instruction(InstructionType.SWAP),
                new Instruction(InstructionType.CALL, 4 /* FIB(n-1) */),
                new Instruction(InstructionType.ADD),
                new Instruction(InstructionType.JMP, 22),


                new Instruction(InstructionType.ARG),
                new Instruction(InstructionType.RETURN, 1)
        };

        Machine vm = new Machine(Arrays.asList(program));
        vm.run();
    }

}
