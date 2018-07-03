package com.berga.layout;

public class Country {
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFileLoc1() {
        return FileLoc1;
    }

    public void setFileLoc1(String fileLoc1) {
        FileLoc1 = fileLoc1;
    }

    public Country(String name, String fileLoc1, String countryname) {
        Name = name;
        FileLoc1 = fileLoc1;
        Countryname = countryname;
    }
    public String getCountryname() {
        return Countryname;
    }

    public void setCountryname(String countryname) {
        Countryname = countryname;
    }

    public Country(){

    }

    private String Name;
    private String FileLoc1;
    private String Countryname;



}
