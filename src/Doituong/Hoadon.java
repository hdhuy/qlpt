
package Doituong;

import java.sql.Date;

public class Hoadon {
    int mahoadon;
    int maphong;
    int manguoithue;
    int nha;
    int dien;
    int nuoc;
    int tongtien;
    Date date;

    public Hoadon() {
    }

    public Hoadon(int mahoadon, int maphong, int manguoithue, int nha, int dien, int nuoc, int tongtien, Date date) {
        this.mahoadon = mahoadon;
        this.maphong = maphong;
        this.manguoithue = manguoithue;
        this.nha = nha;
        this.dien = dien;
        this.nuoc = nuoc;
        this.tongtien = tongtien;
        this.date = date;
    }

   

    public int getMahoadon() {
        return mahoadon;
    }

    public void setMahoadon(int mahoadon) {
        this.mahoadon = mahoadon;
    }

    public int getMaphong() {
        return maphong;
    }

    public void setMaphong(int maphong) {
        this.maphong = maphong;
    }

    public int getManguoithue() {
        return manguoithue;
    }

    public void setManguoithue(int manguoithue) {
        this.manguoithue = manguoithue;
    }

    public int getNha() {
        return nha;
    }

    public void setNha(int nha) {
        this.nha = nha;
    }

    public int getDien() {
        return dien;
    }

    public void setDien(int dien) {
        this.dien = dien;
    }

    public int getNuoc() {
        return nuoc;
    }

    public void setNuoc(int nuoc) {
        this.nuoc = nuoc;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
    
    
}
