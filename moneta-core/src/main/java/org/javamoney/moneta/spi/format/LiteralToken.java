/*
  Copyright (c) 2012, 2020, Anatole Tresch, Werner Keil and others by the @author tag.

  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy of
  the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
  License for the specific language governing permissions and limitations under
  the License.
 */
package org.javamoney.moneta.spi.format;

import java.io.IOException;
import java.io.Serializable;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryParseException;

import static java.util.Objects.requireNonNull;

/**
 * {@link FormatToken} which adds an arbitrary literal constant value to the
 * output.
 * <p>
 * This class is thread safe, immutable and serializable.
 *
 * @author Anatole Tresch
 * @author Werner Keil
 */
final class LiteralToken implements FormatToken, Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -2528757575867480018L;
    /**
     * The literal part.
     */
    private final String token;

    /**
     * Creates a new {@link LiteralToken}.
     *
     * @param token The literal token part.
     */
    LiteralToken(String token) {
        this.token = requireNonNull(token, "Token is required.");
    }

    /**
     * Parses the literal from the current {@link ParseContext}.
     *
     * @see FormatToken#parse(ParseContext)
     */
    @Override
    public void parse(ParseContext context) throws MonetaryParseException {
        if (!context.consume(token)) {
            if(token.trim().isEmpty()){
                // assume this is ok
                return;
            }
            context.setError();
            context.setErrorMessage("Parse Error");
            throw new MonetaryParseException(context.getOriginalInput(), context.getErrorIndex());
        }
    }

    /**
     * Prints the amount to the {@link Appendable} given.
     *
     * @see FormatToken#print(Appendable, javax.money.MonetaryAmount)
     */
    @Override
    public void print(Appendable appendable, MonetaryAmount amount)
            throws IOException {
        appendable.append(this.token);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "LiteralToken [token=" + token + ']';
    }

}
