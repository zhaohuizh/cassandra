/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.cassandra.serializers;

import org.apache.cassandra.utils.ByteBufferUtil;

import java.nio.ByteBuffer;

public class DoubleSerializer implements TypeSerializer<Double>
{
    public static final DoubleSerializer instance = new DoubleSerializer();

    public Double serialize(ByteBuffer bytes)
    {
        return ByteBufferUtil.toDouble(bytes);
    }

    public ByteBuffer deserialize(Double value)
    {
        return (value == null) ? ByteBufferUtil.EMPTY_BYTE_BUFFER : ByteBufferUtil.bytes(value);
    }

    public void validate(ByteBuffer bytes) throws MarshalException
    {
        if (bytes.remaining() != 8 && bytes.remaining() != 0)
            throw new MarshalException(String.format("Expected 8 or 0 byte value for a double (%d)", bytes.remaining()));
    }

    public String getString(ByteBuffer bytes)
    {
        if (bytes.remaining() == 0)
        {
            return "";
        }
        if (bytes.remaining() != 8)
        {
            throw new MarshalException("A double is exactly 8 bytes : " + bytes.remaining());
        }

        return String.valueOf(ByteBufferUtil.toDouble(bytes));
    }

    public String toString(Double value)
    {
        return value == null ? "" : value.toString();
    }

    public Class<Double> getType()
    {
        return Double.class;
    }
}