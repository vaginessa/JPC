package org.jpc.emulator.execution.opcodes.vm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class add_Ed_Id extends Executable
{
    final int op1Index;
    final int immd;

    public add_Ed_Id(int blockStart, int eip, int prefices, PeekableInputStream input)
    {
        super(blockStart, eip);
        int modrm = input.readU8();
        op1Index = Modrm.Ed(modrm);
        immd = Modrm.Id(input);
    }

    public Branch execute(Processor cpu)
    {
        Reg op1 = cpu.regs[op1Index];
        cpu.flagOp1 = op1.get32();
        cpu.flagOp2 = immd;
        cpu.flagResult = (cpu.flagOp1 + cpu.flagOp2);
        op1.set32(cpu.flagResult);
        cpu.flagIns = UCodes.ADD32;
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