
package Doituong;


public class Doanhthu {
    String thoigian;
    int dien;
    int nuoc;
    int nha;
    int tongtien;

    public Doanhthu() {
    }

    public Doanhthu(String thoigian, int dien, int nuoc, int nha, int tongtien) {
        this.thoigian = thoigian;
        this.dien = dien;
        this.nuoc = nuoc;
        this.nha = nha;
        this.tongtien = tongtien;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
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

    public int getNha() {
        return nha;
    }

    public void setNha(int nha) {
        this.nha = nha;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }
    
}
