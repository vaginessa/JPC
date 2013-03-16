package org.jpc.emulator.execution.opcodes.rm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class idiv_Ed_mem extends Executable
{
    final Pointer op1;

    public idiv_Ed_mem(int blockStart, int eip, int prefices, PeekableInputStream input)
    {
        super(blockStart, eip);
        int modrm = input.readU8();
        op1 = Modrm.getPointer(prefices, modrm, input);
    }

    public Branch execute(Processor cpu)
    {
        if (op1.get32(cpu) == 0)
            throw ProcessorException.DIVIDE_ERROR;
        long ldiv = (((0xffffffffL & cpu.r_edx.get32())) << 32 ) | (0xffffffffL & cpu.r_eax.get32());
        cpu.r_eax.set32((int)(ldiv/op1.get32(cpu)));
        cpu.r_edx.set32((int)(ldiv % op1.get32(cpu)));
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