package org.jpc.emulator.execution.opcodes.pm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class and_o32_rAX_Id extends Executable
{
    final int immd;

    public and_o32_rAX_Id(int blockStart, int eip, int prefices, PeekableInputStream input)
    {
        super(blockStart, eip);
        immd = Modrm.Id(input);
    }

    public Branch execute(Processor cpu)
    {
        cpu.of = cpu.af = cpu.cf = false;
        cpu.flagResult = (cpu.r_eax.get32() & immd);
        cpu.r_eax.set32(cpu.flagResult);
        cpu.flagStatus = SZP;
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