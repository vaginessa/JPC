package org.jpc.emulator.execution.opcodes.rm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class lfs_o16_Gw_M extends Executable
{
    final int op1Index;
    final Address op2;

    public lfs_o16_Gw_M(int blockStart, int eip, int prefices, PeekableInputStream input)
    {
        super(blockStart, eip);
        int modrm = input.readU8();
        op1Index = Modrm.Gw(modrm);
        op2 = Modrm.getPointer(prefices, modrm, input);
    }

    public Branch execute(Processor cpu)
    {
        Reg op1 = cpu.regs[op1Index];
        int addr = op2.get(cpu) + op2.getBase(cpu);
        cpu.fs(0xFFFF & cpu.physicalMemory.getWord(addr+2));
        op1.set16(cpu.physicalMemory.getWord(addr));
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