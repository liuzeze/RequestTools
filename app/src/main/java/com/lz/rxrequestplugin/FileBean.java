package com.lz.rxrequestplugin;

public class FileBean {
    public String path = "";
    public String name = "";
    public boolean isFile = false;

    public FileBean(String path, String name, boolean isFile) {
        this.path = path;
        this.name = name;
        this.isFile = isFile;
    }
}
