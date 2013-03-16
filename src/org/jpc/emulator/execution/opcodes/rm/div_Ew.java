package org.jpc.emulator.execution.opcodes.rm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class div_Ew extends Executable
{
    final int op1Index;

    public div_Ew(int blockStart, int eip, int prefices, PeekableInputStream input)
    {
        super(blockStart, eip);
        int modrm = input.readU8();
        op1Index = Modrm.Ew(modrm);
    }

    public Branch execute(Processor cpu)
    {
        Reg op1 = cpu.regs[op1Index];
        if (op1.get16() == 0)
            throw ProcessorException.DIVIDE_ERROR;
        long ldiv = (((long)(0xFFFF&cpu.r_edx.get16())) << 16 ) | (0xFFFF& cpu.r_eax.get16());
        cpu.r_eax.set16((short) (ldiv/(0xFFFF& op1.get16())));
        cpu.r_edx.set16((short) (ldiv % (0xFFFF& op1.get16())));
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