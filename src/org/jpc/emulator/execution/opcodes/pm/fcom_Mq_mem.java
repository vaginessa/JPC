package org.jpc.emulator.execution.opcodes.pm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class fcom_Mq_mem extends Executable
{
    final Pointer op1;

    public fcom_Mq_mem(int blockStart, int eip, int prefices, PeekableInputStream input)
    {
        super(blockStart, eip);
        int modrm = input.readU8();
        op1 = Modrm.getPointer(prefices, modrm, input);
    }

    public Branch execute(Processor cpu)
    {
                            int newcode = 0xd;
        double freg0 = cpu.fpu.ST(0);
        double freg1 = op1.getF64(cpu);
        if (Double.isNaN(freg0) || Double.isNaN(freg1))
            cpu.fpu.setInvalidOperation();
        else {
            if (freg0 > freg1) newcode = 0;
            else if (freg0 < freg1) newcode = 1;
            else newcode = 8;
        }
        cpu.fpu.conditionCode &= 2;
        cpu.fpu.conditionCode |= newcode;
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