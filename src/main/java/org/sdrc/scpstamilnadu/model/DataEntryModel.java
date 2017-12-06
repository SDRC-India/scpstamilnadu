package org.sdrc.scpstamilnadu.model;
/**
 * 
 * @author Pratyush(paratyush@sdrc.co.in)
 *
 */
public class DataEntryModel {

	private int denominatorValue;
	private int indicatorId;
	private int numeratorValue;
	private String percentage;
	
	public int getDenominatorValue() {
		return denominatorValue;
	}
	public void setDenominatorValue(int denominatorValue) {
		this.denominatorValue = denominatorValue;
	}
	public int getIndicatorId() {
		return indicatorId;
	}
	public void setIndicatorId(int indicatorId) {
		this.indicatorId = indicatorId;
	}
	public int getNumeratorValue() {
		return numeratorValue;
	}
	public void setNumeratorValue(int numeratorValue) {
		this.numeratorValue = numeratorValue;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DataEntryModel [denominatorValue=");
		builder.append(denominatorValue);
		builder.append(", indicatorId=");
		builder.append(indicatorId);
		builder.append(", numeratorValue=");
		builder.append(numeratorValue);
		builder.append(", percentage=");
		builder.append(percentage);
		builder.append("]");
		return builder.toString();
	}
	
	
}
