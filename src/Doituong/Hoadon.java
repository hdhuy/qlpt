
package Doituong;

import java.sql.Date;

public class Hoadon {
    int mahoadon;
    int maphong;
    int manguoithue;
    String tennguoithue;
    String tenphong;
    int tiennha;
    int sodiensudung;
    int giadien;
    int sonuocsudung;
    int gianuoc;
    int soluongxe;
    int tienxe;
    int tongtien;
    Date thoigian;

    public Hoadon() {
    }

    public Hoadon(int mahoadon, int maphong, int manguoithue, String tennguoithue, String tenphong, int tiennha, int sodiensudung, int giadin, int sonuocsudung, int gianuoc, int soluongxe, int tienxe, int tongtien, Date thoigian) {
        this.mahoadon = mahoadon;
        this.maphong = maphong;
        this.manguoithue = manguoithue;
        this.tennguoithue = tennguoithue;
        this.tenphong = tenphong;
        this.tiennha = tiennha;
        this.sodiensudung = sodiensudung;
        this.giadien = giadin;
        this.sonuocsudung = sonuocsudung;
        this.gianuoc = gianuoc;
        this.soluongxe = soluongxe;
        this.tienxe = tienxe;
        this.tongtien = tongtien;
        this.thoigian = thoigian;
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

    public String getTennguoithue() {
        return tennguoithue;
    }

    public void setTennguoithue(String tennguoithue) {
        this.tennguoithue = tennguoithue;
    }

    public String getTenphong() {
        return tenphong;
    }

    public void setTenphong(String tenphong) {
        this.tenphong = tenphong;
    }

    public int getTiennha() {
        return tiennha;
    }

    public void setTiennha(int tiennha) {
        this.tiennha = tiennha;
    }

    public int getSodiensudung() {
        return sodiensudung;
    }

    public void setSodiensudung(int sodiensudung) {
        this.sodiensudung = sodiensudung;
    }

    public int getGiadien() {
        return giadien;
    }

    public void setGiadien(int giadien) {
        this.giadien = giadien;
    }

    public int getSonuocsudung() {
        return sonuocsudung;
    }

    public void setSonuocsudung(int sonuocsudung) {
        this.sonuocsudung = sonuocsudung;
    }

    public int getGianuoc() {
        return gianuoc;
    }

    public void setGianuoc(int gianuoc) {
        this.gianuoc = gianuoc;
    }

    public int getSoluongxe() {
        return soluongxe;
    }

    public void setSoluongxe(int soluongxe) {
        this.soluongxe = soluongxe;
    }

    public int getTienxe() {
        return tienxe;
    }

    public void setTienxe(int tienxe) {
        this.tienxe = tienxe;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }

    public Date getThoigian() {
        return thoigian;
    }

    public void setThoigian(Date thoigian) {
        this.thoigian = thoigian;
    }

    
}
