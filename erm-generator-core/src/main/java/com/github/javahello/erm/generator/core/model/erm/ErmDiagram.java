package com.github.javahello.erm.generator.core.model.erm;

import java.util.List;


/**
 * @author kaiv2
 */
//@XmlRootElement(name = "diagram")
public class ErmDiagram {

    private String fileName;
    private ErmSetting settings;

    //	@XmlElementWrapper(name = "dictionary")
//	@XmlElement(name = "word")
    private List<ErmWord> wordList;

    //	@XmlElementWrapper(name = "contents")
//	@XmlElement(name = "table")
    private List<ErmTable> tables;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ErmSetting getSettings() {
        return settings;
    }

    public void setSettings(ErmSetting settings) {
        this.settings = settings;
    }

    //    @XmlTransient
    public List<ErmWord> getWordList() {
        return wordList;
    }

    public void setWordList(List<ErmWord> wordList) {
        this.wordList = wordList;
    }

    //    @XmlTransient
    public List<ErmTable> getTables() {
        return tables;
    }

    public void setTables(List<ErmTable> tables) {
        this.tables = tables;
    }

}
