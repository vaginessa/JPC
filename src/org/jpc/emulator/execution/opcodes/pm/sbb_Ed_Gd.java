package org.jpc.emulator.execution.opcodes.pm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class sbb_Ed_Gd extends Executable
{
    final int op1Index;
    final int op2Index;

    public sbb_Ed_Gd(int blockStart, int eip, int prefices, PeekableInputStream input)
    {
        super(blockStart, eip);
        int modrm = input.readU8();
        op1Index = Modrm.Ed(modrm);
        op2Index = Modrm.Gd(modrm);
    }

    public Branch execute(Processor cpu)
    {
        Reg op1 = cpu.regs[op1Index];
        Reg op2 = cpu.regs[op2Index];
        int add = (cpu.cf()? 1: 0);
        cpu.flagOp1 = op1.get32();
        cpu.flagOp2 = op2.get32();
        cpu.flagResult = (cpu.flagOp1 - (cpu.flagOp2 + add));
        op1.set32(cpu.flagResult);
        cpu.flagIns = UCodes.SBB32;
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