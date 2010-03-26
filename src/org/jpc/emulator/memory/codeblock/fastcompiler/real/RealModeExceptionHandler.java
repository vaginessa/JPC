/*
    JPC: An x86 PC Hardware Emulator for a pure Java Virtual Machine
    Release Version 2.4

    A project from the Physics Dept, The University of Oxford

    Copyright (C) 2007-2010 The University of Oxford

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License version 2 as published by
    the Free Software Foundation.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 
    Details (including contact information) can be found at: 

    javapc.sourceforge.net
             or
    www-jpc.physics.ox.ac.uk

    Conceived and Developed by:
    Rhys Newman, Ian Preston, Chris Dennis

    End of licence header
*/

package org.jpc.emulator.memory.codeblock.fastcompiler.real;

import java.io.*;
import java.util.Map;

import org.jpc.classfile.ClassFile;
import org.jpc.emulator.processor.*;

import org.jpc.emulator.memory.codeblock.fastcompiler.ExceptionHandler;

import static org.jpc.classfile.JavaOpcode.*;
/**
 * 
 * @author Chris Dennis
 */
public class RealModeExceptionHandler extends ExceptionHandler
{
    public RealModeExceptionHandler(int lastX86Position, RealModeRPNNode initialNode, Map stateMap)
    {
	super(lastX86Position, initialNode, stateMap);
    }

    protected void writeHandlerRoutine(OutputStream byteCodes, ClassFile cf) throws IOException
    {
	byteCodes.write(ALOAD_1);
	byteCodes.write(SWAP);
	byteCodes.write(INVOKEVIRTUAL);
	try {
	    int cpIndex = cf.addToConstantPool(Processor.class.getDeclaredMethod("handleRealModeException", new Class[] {ProcessorException.class}));
	    if (cpIndex > 0xffff)
		throw new IllegalStateException("Compilation ran out of constant pool slots");
	    byteCodes.write(cpIndex >>> 8);
	    byteCodes.write(cpIndex & 0xff);
	} catch (NoSuchMethodException e) {
            throw new IllegalStateException(e);
	}
    }
}