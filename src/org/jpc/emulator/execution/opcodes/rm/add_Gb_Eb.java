package org.jpc.emulator.execution.opcodes.rm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class add_Gb_Eb extends Executable
{
    final int op1Index;
    final int op2Index;

    public add_Gb_Eb(int blockStart, int eip, int prefices, PeekableInputStream input)
    {
        super(blockStart, eip);
        int modrm = input.readU8();
        op1Index = Modrm.Gb(modrm);
        op2Index = Modrm.Eb(modrm);
    }

    public Branch execute(Processor cpu)
    {
        Reg op1 = cpu.regs[op1Index];
        Reg op2 = cpu.regs[op2Index];
        cpu.flagOp1 = (byte)op1.get8();
        cpu.flagOp2 = (byte)op2.get8();
        cpu.flagResult = (byte)(cpu.flagOp1 + cpu.flagOp2);
        op1.set8((byte)cpu.flagResult);
        cpu.flagIns = UCodes.ADD8;
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