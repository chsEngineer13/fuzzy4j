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
package fuzzy4j.aggregation;

/**
 * Dombi class union operator [1].
 *
 * To get a dombi class intersection operator, use {@link fuzzy4j.aggregation.DombiUnion#duality()}.
 *
 * Defined as:
 *  <code>h_D(p, a, b) = 1 / 1 + (a/(1-a))^p + (b/(1-b))^p)^(-1/p)</code>
 *
 * [1] J. Dombi, "A general class of fuzzy operators, the DeMorgan class of fuzzy operators and fuzziness measures induced by fuzzy operators",
 *      Fuzzy Sets and Systems 8, pp. 149-163, 1982.
 *
 * @author Soren A. Davidsen <soren@tanesha.net>
 */
public class DombiUnion implements Norm {


    public static final ParametricFactory<DombiUnion> BY_EXPONENT = new ParametricFactory<DombiUnion>() {
        @Override
        public DombiUnion create(double... params) {
            double p = params[0];
            if (p < 0.)
                throw new IllegalArgumentException("p < 0");
            else
                return new DombiUnion(p);
        }
    };

    public static final ParametricFactory<DombiUnion> BY_DRASTICALITY = new ParametricFactory<DombiUnion>() {
        @Override
        public DombiUnion create(double... params) {
            double d = params[0];
            if (d < 0.0 || d > 1.0)
                throw new IllegalArgumentException("drasticality \\not\\in [0, 1]");

            if (d == 0.0)
                return new DombiUnion(Double.POSITIVE_INFINITY);
            else {
                double p = (1.0 - d) / d;
                return new DombiUnion(p);
            }
        }
    };

    private double p;

    private DombiUnion(double p) {
        this.p = p;
    }

    @Override
    public double apply(double... values) {

        if (Double.isInfinite(p))
            return Maximum.INSTANCE.apply(values);

        double s_x = 0.0;
        for (double x : values)
            if (x == 1.0)
                return 1.0;
            else
                s_x += Math.pow(x / (1. - x), p);

        if (s_x == 0.0)
            return 0.0;
        else
            return 1.0 / (1.0 + Math.pow(s_x, -1.0 / p));
    }

    @Override
    public Type type() {
        return Type.T_CONORM;
    }

    @Override
    public Norm duality() {
        return DombiIntersection.BY_EXPONENT.create(p);
    }

    @Override
    public String toString() {
        return type() + "_dombi_" + p;
    }

}
