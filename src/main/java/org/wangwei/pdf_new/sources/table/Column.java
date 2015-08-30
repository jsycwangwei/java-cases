package org.wangwei.pdf_new.sources.table;

import org.wangwei.pdf.self.LocLayout;

/**
 * Column.java
 *
 * @author wangwei-ww
 */
public class Column {

    private String name;
    private float width;
    private LocLayout layout;

    public Column(String name, float width, LocLayout layout) {
        this.name = name;
        this.width = width;
        this.layout = layout;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public LocLayout getLayout() {
        return layout;
    }

    public void setLayout(LocLayout layout) {
        this.layout = layout;
    }
}
