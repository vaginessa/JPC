package org.jpc.emulator.execution.opcodes.rm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class fsubp_ST1_ST4 extends Executable
{

    public fsubp_ST1_ST4(int blockStart, int eip, int prefices, PeekableInputStream input)
    {
        super(blockStart, eip);
        int modrm = input.readU8();
    }

    public Branch execute(Processor cpu)
    {
        double freg0 = cpu.fpu.ST(1);
        double freg1 = cpu.fpu.ST(4);
        if ((freg0 == Double.NEGATIVE_INFINITY && freg1 == Double.NEGATIVE_INFINITY) || (freg0 == Double.POSITIVE_INFINITY && freg1 == Double.POSITIVE_INFINITY)) 
		    cpu.fpu.setInvalidOperation();
        cpu.fpu.setST(1,  freg0-freg1);
        cpu.fpu.pop();
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