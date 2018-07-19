package com.yaleiden.archeryscore;

/**
 * Created by Yale on 6/11/2015.
 */
public class ArcherSelectedData {

    private Long rowid;
    private String name;
    private String lastTarget;

    /**
     *
     * @param inRowid _id in db
     * @param inName archer name
     * @param inLastTarget last target scored
     */
    public void setData(Long inRowid, String inName, String inLastTarget) {
        rowid = inRowid;
        name = inName;
        lastTarget = inLastTarget;
    }

    public Long getRowid() {
        return rowid;
    }

    public String getName() {
        return name;
    }

    public String getLastTarget() {
        return lastTarget;
    }

    public void setNull(){
        rowid = null;
        name = null;
        lastTarget = null;
    }

}
