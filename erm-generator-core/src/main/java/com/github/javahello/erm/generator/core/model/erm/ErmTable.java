package com.github.javahello.erm.generator.core.model.erm;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author kaiv2
 *
 */
public class ErmTable {

    private String id;

    @XmlElement(name = "physical_name")
    private String physicalName;

    @XmlElement(name = "logical_name")
    private String logicalName;

    private String description;

    @XmlElementWrapper(name = "columns")
    @XmlElement(name = "normal_column")
    private List<ErmColumn> columns;

    @XmlElementWrapper(name = "indexes")
    @XmlElement(name = "inidex")
    private List<ErmIndex> indexes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlTransient
    public String getPhysicalName() {
        return physicalName;
    }

    public void setPhysicalName(String physicalName) {
        this.physicalName = physicalName;
    }

    @XmlTransient
    public String getLogicalName() {
        return logicalName;
    }

    public void setLogicalName(String logicalName) {
        this.logicalName = logicalName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<ErmColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<ErmColumn> columns) {
        this.columns = columns;
    }

    @XmlTransient
    public List<ErmIndex> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<ErmIndex> indexes) {
        this.indexes = indexes;
    }

}
