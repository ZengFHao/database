package model;

public class Reservations {
    private String custname;
    private int resvKey;

    private String detail;
    private int resvType;

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public int getResvKey() {
        return resvKey;
    }

    public void setResvKey(int resvKey) {
        this.resvKey = resvKey;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getResvType() {
        return resvType;
    }

    public void setResvType(int resvType) {
        this.resvType = resvType;
    }
}
