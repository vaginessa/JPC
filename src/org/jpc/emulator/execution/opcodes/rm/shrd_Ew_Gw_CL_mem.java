package org.jpc.emulator.execution.opcodes.rm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class shrd_Ew_Gw_CL_mem extends Executable
{
    final Pointer op1;
    final int op2Index;

    public shrd_Ew_Gw_CL_mem(int blockStart, int eip, int prefices, PeekableInputStream input)
    {
        super(blockStart, eip);
        int modrm = input.readU8();
        op1 = Modrm.getPointer(prefices, modrm, input);
        op2Index = Modrm.Gw(modrm);
    }

    public Branch execute(Processor cpu)
    {
        Reg op2 = cpu.regs[op2Index];
        if(cpu.r_cl.get8() != 0)
        {
            int shift = cpu.r_cl.get8() & 0x1f;
            if (shift <= 16)
                cpu.flagOp1 = op1.get16(cpu);
            else
                cpu.flagOp1 = op2.get16();
            cpu.flagOp2 = shift;
            long rot = ((long)op1.get16(cpu) << (2*16)) | ((0xFFFF&op2.get16()) << 16) | (0xFFFF&op1.get16(cpu));
            cpu.flagResult = (short)((int)(rot >> shift));
            op1.set16(cpu, (short)cpu.flagResult);
            cpu.flagIns = UCodes.SHRD16;
            cpu.flagStatus = OSZAPC;
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