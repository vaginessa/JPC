package org.jpc.emulator.execution.opcodes.pm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class cmp_Ew_Ib extends Executable
{
    final int op1Index;
    final int immb;

    public cmp_Ew_Ib(int blockStart, int eip, int prefices, PeekableInputStream input)
    {
        super(blockStart, eip);
        int modrm = input.readU8();
        op1Index = Modrm.Ew(modrm);
        immb = Modrm.Ib(input);
    }

    public Branch execute(Processor cpu)
    {
        Reg op1 = cpu.regs[op1Index];
        cpu.flagOp1 = op1.get16();
        cpu.flagOp2 = immb;
        cpu.flagResult = (short)(cpu.flagOp1 - cpu.flagOp2);
        cpu.flagIns = UCodes.SUB16;
        cpu.flagStatus = OSZAPC;
        return Branch.None;
    }

    public boolean isBranch()
    {
        return false;
    }

    public String toString()
    {
        return this.getClass().getName();
    }
}