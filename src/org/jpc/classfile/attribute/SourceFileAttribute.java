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

package org.jpc.classfile.attribute;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Class attribute indicating a mapping to a source file name.
 * @author Mike Moleschi
 */
public class SourceFileAttribute extends AttributeInfo
{
    private int sourceFileIndex;

    SourceFileAttribute(DataInputStream in, int index) throws IOException
    {
        super(in, index);
        sourceFileIndex = in.readUnsignedShort();
    }

    public void write(DataOutputStream out) throws IOException
    {
        super.write(out);
        out.writeShort(sourceFileIndex);
    }
}