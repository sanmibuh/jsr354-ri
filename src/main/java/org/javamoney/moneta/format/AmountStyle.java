/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.javamoney.moneta.format;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.money.MonetaryAdjuster;

public final class AmountStyle {

	private static final char[] EMPTY_CHAR_ARRAY = new char[0];
	private static final int[] EMPTY_INT_ARRAY = new int[0];
	private DecimalFormat format;
	private MonetaryAdjuster rounding;
	private int[] groupSizes;
	private char[] groupChars;

	private AmountStyle(DecimalFormat format, int[] groupSizes,
			char[] groupChars, MonetaryAdjuster rounding) {
		if (format == null) {
			throw new IllegalArgumentException("DecimalFormat required.");
		}
		this.groupSizes = groupSizes;
		this.groupChars = groupChars;
		this.rounding = rounding;
		this.format = format;
	}

	public MonetaryAdjuster getMoneyRounding() {
		return this.rounding;
	}

	public int[] getNumberGroupSizes() {
		if(this.groupSizes==null){
			return EMPTY_INT_ARRAY;
		}
		return this.groupSizes.clone();
	}

	public char[] getNumberGroupChars() {
		if(this.groupChars==null){
			return EMPTY_CHAR_ARRAY;
		}
		return this.groupChars.clone();
	}

	public String getPattern() {
		return this.format.toPattern();
	}
	
	public String getLocalizedPattern(String pattern) {
		return this.format.toLocalizedPattern();
	}

	public DecimalFormatSymbols getDecimalSymbols(){
		return this.format.getDecimalFormatSymbols();
	}
	
	public int getMaximumFractionDigits(){
		return this.format.getMaximumFractionDigits();
	}
	
	public int withMaximumIntegerDigits(){
		return this.format.getMaximumIntegerDigits();
	}
	
	public int getMinimumFractionDigits(){
		return this.format.getMinimumFractionDigits();
	}
	
	public int getMinimumIntegerDigits(){
		return this.format.getMinimumIntegerDigits();
	}
	
	public int getMultiplier(){
		return this.format.getMultiplier();
	}
	
	public String getNegativePrefix(){
		return this.format.getNegativePrefix();
	}
	
	public String getNegativeSuffix(){
		return this.format.getNegativeSuffix();
	}
	
	public String getPositivePrefix(){
		return this.format.getPositivePrefix();
	}
	
	public String getPositiveSuffix(){
		return this.format.getPositiveSuffix();
	}

	public RoundingMode getRounding(){
		return this.format.getRoundingMode();
	}
	
	public boolean isDecimalSeparatorAlwaysShown(){
		return this.format.isDecimalSeparatorAlwaysShown();
	}
	
	public boolean isParseIntegerOnly(){
		return this.format.isParseIntegerOnly();
	}

	public static final class Builder {

		private DecimalFormat format;
		private MonetaryAdjuster rounding;
		private int[] groupSizes;
		private char[] groupChars;

		public Builder(Locale locale) {
			if(locale==null){	
				throw new IllegalArgumentException("Locale required.");
			}
			this.format = (DecimalFormat) DecimalFormat.getInstance(locale);
		}
		
		public Builder withRounding(MonetaryAdjuster rounding) {
			this.rounding = rounding;
			return this;
		}

		public Builder withNumberGroupSizes(int... groupSizes) {
			this.groupSizes = groupSizes;
			return this;
		}

		public Builder withNumberGroupChars(char... groupChars) {
			this.groupChars = groupChars;
			return this;
		}

		public Builder withPattern(String pattern) {
			this.format.applyPattern(pattern);
			return this;
		}
		
		public Builder withLocalizedPattern(String pattern) {
			this.format.applyLocalizedPattern(pattern);
			return this;
		}

		public Builder withDecimalFormat(DecimalFormat format){
			if(format==null){	
				throw new IllegalArgumentException("format required.");
			}
			this.format = format;
			return this;
		}
		
		public Builder withDecimalSymbols(DecimalFormatSymbols symbols){
			if(symbols==null){	
				throw new IllegalArgumentException("symbols required.");
			}
			this.format.setDecimalFormatSymbols(symbols);
			return this;
		}
		
		public Builder withMaximumFractionDigits(int maxFractionDigits){
			this.format.setMaximumFractionDigits(maxFractionDigits);
			return this;
		}
		
		public Builder withMaximumIntegerDigits(int maxIntegerDigits){
			this.format.setMaximumIntegerDigits(maxIntegerDigits);
			return this;
		}
		
		public Builder withMinimumFractionDigits(int minFractionDigits){
			this.format.setMinimumFractionDigits(minFractionDigits);
			return this;
		}
		
		public Builder withMinimumIntegerDigits(int minIntegerDigits){
			this.format.setMinimumIntegerDigits(minIntegerDigits);
			return this;
		}
		
		public Builder withMultiplier(int multiplier){
			this.format.setMultiplier(multiplier);
			return this;
		}
		
		public Builder withNegativePrefix(String prefix){
			this.format.setNegativePrefix(prefix);
			return this;
		}
		
		public Builder withNegativeSuffix(String suffix){
			this.format.setNegativeSuffix(suffix);
			return this;
		}
		
		public Builder withPositivePrefix(String prefix){
			this.format.setPositivePrefix(prefix);
			return this;
		}
		
		public Builder withPositiveSuffix(String suffix){
			this.format.setPositiveSuffix(suffix);
			return this;
		}

		public Builder withRounding(RoundingMode roundingMode){
			this.format.setRoundingMode(roundingMode);
			return this;
		}
		
		public Builder withDecimalSeparatorAlwaysShown(boolean value){
			this.format.setDecimalSeparatorAlwaysShown(value);
			return this;
		}
		
		public Builder withParseIntegerOnly(boolean value){
			this.format.setParseIntegerOnly(value);
			return this;
		}

		public AmountStyle build() {
			return new AmountStyle(format, groupSizes, groupChars, rounding);
		}

		public Builder withCurrencyFormat(Locale locale) {
			if(locale==null){	
				throw new IllegalArgumentException("locale required.");
			}
			this.format = (DecimalFormat) DecimalFormat.getCurrencyInstance(locale);
			return this;
		}
		
	}

	DecimalFormat getDecimalFormat() {
		return this.format;
	}

}