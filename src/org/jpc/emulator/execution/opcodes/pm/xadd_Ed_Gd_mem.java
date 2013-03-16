package org.jpc.emulator.execution.opcodes.pm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class xadd_Ed_Gd_mem extends Executable
{
    final Pointer op1;
    final int op2Index;

    public xadd_Ed_Gd_mem(int blockStart, int eip, int prefices, PeekableInputStream input)
    {
        super(blockStart, eip);
        int modrm = input.readU8();
        op1 = Modrm.getPointer(prefices, modrm, input);
        op2Index = Modrm.Gd(modrm);
    }

    public Branch execute(Processor cpu)
    {
        Reg op2 = cpu.regs[op2Index];
            int tmp1 = op1.get32(cpu);
        int tmp2 = op2.get32();
        cpu.flagOp1 = tmp1;
        cpu.flagOp2 = tmp2;
        cpu.flagResult = (cpu.flagOp1 + cpu.flagOp2);
        op1.set32(cpu, cpu.flagResult);
        cpu.flagIns = UCodes.ADD32;
        cpu.flagStatus = OSZAPC;
        op2.set32( tmp1);
        op1.set32(cpu,  (tmp1+tmp2));
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