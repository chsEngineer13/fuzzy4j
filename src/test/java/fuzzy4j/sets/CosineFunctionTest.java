/*
 * Copyright (c) 2012, Søren Atmakuri Davidsen
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package fuzzy4j.sets;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * @author Soren A. Davidsen <soren@tanesha.net>
 */
public class CosineFunctionTest {

    CosineFunction impl;

    @Test
    public void membership_outside() {
        impl = new CosineFunction(1.0, 1.0);

        assertEquals(0.0, impl.apply(10));
        assertEquals(0.0, impl.apply(-10));
    }

    @Test
    public void testMembership_01() throws Exception {

        impl = new CosineFunction(1.0, 1.0);

        assertEquals(0.0, impl.apply(0.0));
        assertEquals(1.0, impl.apply(1.0));
        assertEquals(0.0, impl.apply(2.0));
    }

    @Test
    public void testMembership_02() throws Exception {

        impl = new CosineFunction(2.0, 5.0);

        assertEquals(0.0, impl.apply(3.0), 0.001);
        assertEquals(0.0, impl.apply(7.0), 0.001);
        assertEquals(1.0, impl.apply(5.0), 0.001);
        assertTrue(impl.apply(4.5) < impl.apply(4.6));
        assertTrue(impl.apply(5.6) > impl.apply(5.7));

    }

}
