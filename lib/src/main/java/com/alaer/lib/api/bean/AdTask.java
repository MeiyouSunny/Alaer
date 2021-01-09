package com.alaer.lib.api.bean;

import java.io.Serializable;

public class AdTask implements Serializable {

    public int id;
    public String name;
    public int status;
    public int completeStatus;
    public int times;
    public int remain;
    public int takt;
    public float value;
    public String note;
    public int action;
    public String type;
    public String nextTime;

}
