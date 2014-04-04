package com.nextgen.tacky.basic;

/**
 * Created by maes on 30/10/13.
 */
public class MainItem {

    private String name;
    private String visualization;

    public MainItem(String name, String visualization) {
        this.name = name;
        this.visualization = visualization;
    }

    public String getName() {
        return name;
    }

    public String getVisualization() {
        return visualization;
    }

    protected void setVisualization(String visualization) {
        this.visualization = visualization;
    }
}
