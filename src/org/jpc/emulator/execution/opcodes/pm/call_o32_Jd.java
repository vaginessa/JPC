package org.jpc.emulator.execution.opcodes.pm;

import org.jpc.emulator.execution.*;
import org.jpc.emulator.execution.decoder.*;
import org.jpc.emulator.processor.*;
import org.jpc.emulator.processor.fpu64.*;
import static org.jpc.emulator.processor.Processor.*;

public class call_o32_Jd extends Executable
{
    final int jmp;
    final int blockLength;
    final int instructionLength;

    public call_o32_Jd(int blockStart, int eip, int prefices, PeekableInputStream input)
    {
        super(blockStart, eip);
        jmp = Modrm.Jd(input);
        instructionLength = (int)input.getAddress()-eip;
        blockLength = (int)input.getAddress()-blockStart;
    }

    public Branch execute(Processor cpu)
    {
                
        cpu.eip += blockLength;
        if ((cpu.r_esp.get32() < 4) && (cpu.r_esp.get32() > 0))
	    throw ProcessorException.STACK_SEGMENT_0;
        int tmpEip = cpu.eip + jmp;
        cpu.cs.checkAddress(tmpEip);
        cpu.push32(cpu.eip);
        cpu.eip = tmpEip;
        return Branch.T1;
    }

    public boolean isBranch()
    {
        return true;
    }

    public String toString()
    {
        return this.getClass().getName();
    }
}