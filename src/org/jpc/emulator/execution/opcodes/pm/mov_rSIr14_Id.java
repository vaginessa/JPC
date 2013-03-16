package org.jpc.emulator.execution.opcodes.pm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class mov_rSIr14_Id extends Executable
{
    final int immd;

    public mov_rSIr14_Id(int blockStart, int eip, int prefices, PeekableInputStream input)
    {
        super(blockStart, eip);
        immd = Modrm.Id(input);
    }

    public Branch execute(Processor cpu)
    {
        cpu.r_esi.set32(immd);
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