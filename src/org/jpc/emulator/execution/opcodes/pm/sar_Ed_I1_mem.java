package org.jpc.emulator.execution.opcodes.pm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class sar_Ed_I1_mem extends Executable
{
    final Pointer op1;

    public sar_Ed_I1_mem(int blockStart, int eip, int prefices, PeekableInputStream input)
    {
        super(blockStart, eip);
        int modrm = input.readU8();
        op1 = Modrm.getPointer(prefices, modrm, input);
    }

    public Branch execute(Processor cpu)
    {
        if(1 != 0)
        {
            boolean inOF = cpu.of();
            cpu.flagOp1 = op1.get32(cpu);
            cpu.flagOp2 = 1;
            cpu.flagResult = (cpu.flagOp1 >> cpu.flagOp2);
            op1.set32(cpu, cpu.flagResult);
            cpu.flagIns = UCodes.SAR32;
            cpu.flagStatus = OSZAPC;
            if (cpu.flagOp2 == 1)
                cpu.of(false);
            else
                cpu.of(inOF);
        }
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