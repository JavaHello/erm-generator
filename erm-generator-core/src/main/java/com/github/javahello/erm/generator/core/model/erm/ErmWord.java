package com.github.javahello.erm.generator.core.model.erm;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author kaiv2
 *
 */
public class ErmWord {
	
	private String id;
	private Integer length;
	private Integer decimal;
	private String array;
	@XmlElement(name = "array_dimension")
	private String arrayDimension;
	private String unsigned;
	private String zerofill;
	private String binary;
	private String args;
	@XmlElement(name = "char_semantics")
	private String charSemantics;
	private String description;
	@XmlElement(name = "logical_name")
	private String logicalName;
	@XmlElement(name = "physical_name")
	private String physicalName;
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getDecimal() {
		return decimal;
	}

	public void setDecimal(Integer decimal) {
		this.decimal = decimal;
	}

	public String getArray() {
		return array;
	}

	public void setArray(String array) {
		this.array = array;
	}

	@XmlTransient
	public String getArrayDimension() {
		return arrayDimension;
	}

	public void setArrayDimension(String arrayDimension) {
		this.arrayDimension = arrayDimension;
	}

	public String getUnsigned() {
		return unsigned;
	}

	public void setUnsigned(String unsigned) {
		this.unsigned = unsigned;
	}

	public String getZerofill() {
		return zerofill;
	}

	public void setZerofill(String zerofill) {
		this.zerofill = zerofill;
	}

	public String getBinary() {
		return binary;
	}

	public void setBinary(String binary) {
		this.binary = binary;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

	@XmlTransient
	public String getCharSemantics() {
		return charSemantics;
	}

	public void setCharSemantics(String charSemantics) {
		this.charSemantics = charSemantics;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlTransient
	public String getLogicalName() {
		return logicalName;
	}

	public void setLogicalName(String logicalName) {
		this.logicalName = logicalName;
	}

	@XmlTransient
	public String getPhysicalName() {
		return physicalName;
	}

	public void setPhysicalName(String physicalName) {
		this.physicalName = physicalName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
