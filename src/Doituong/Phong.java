package Doituong;

import java.sql.Date;

public class Phong {

    int maphong;
    String tenphong;
    int matoanha;
    int manguoithue;
    int dientich;
    int giaphong;
    int soluongnguoi;
    int sodiencu;
    int sodienmoi;
    int sonuocmoi;
    int sonuoccu;
    Date thoigianthanhtoan;
    int soluongxe;

    String lichcapnhatSoDien;
    String lichcapnhatSoNuoc;

    public Phong() {
    }

    public Phong(int maphong, String tenphong, int matoanha, int manguoithue, int dientich, int giaphong, int soluongnguoi, int sodiencu, int sodienmoi, int sonuocmoi, int sonuoccu, Date thoigianthanhtoan, int soluongxe, String licnhapsopdien, String lichnhapsonuoc) {
        this.maphong = maphong;
        this.tenphong = tenphong;
        this.matoanha = matoanha;
        this.manguoithue = manguoithue;
        this.dientich = dientich;
        this.giaphong = giaphong;
        this.soluongnguoi = soluongnguoi;
        this.sodiencu = sodiencu;
        this.sodienmoi = sodienmoi;
        this.sonuocmoi = sonuocmoi;
        this.sonuoccu = sonuoccu;
        this.thoigianthanhtoan = thoigianthanhtoan;
        this.soluongxe = soluongxe;
        this.lichcapnhatSoDien = licnhapsopdien;
        this.lichcapnhatSoNuoc = lichnhapsonuoc;
    }

    public int getMaphong() {
        return maphong;
    }

    public void setMaphong(int maphong) {
        this.maphong = maphong;
    }

    public String getTenphong() {
        return tenphong;
    }

    public void setTenphong(String tenphong) {
        this.tenphong = tenphong;
    }

    public int getMatoanha() {
        return matoanha;
    }

    public void setMatoanha(int matoanha) {
        this.matoanha = matoanha;
    }

    public int getManguoithue() {
        return manguoithue;
    }

    public void setManguoithue(int manguoithue) {
        this.manguoithue = manguoithue;
    }

    public int getDientich() {
        return dientich;
    }

    public void setDientich(int dientich) {
        this.dientich = dientich;
    }

    public int getGiaphong() {
        return giaphong;
    }

    public void setGiaphong(int giaphong) {
        this.giaphong = giaphong;
    }

    public int getSoluongnguoi() {
        return soluongnguoi;
    }

    public void setSoluongnguoi(int soluongnguoi) {
        this.soluongnguoi = soluongnguoi;
    }

    public int getSodiencu() {
        return sodiencu;
    }

    public void setSodiencu(int sodiencu) {
        this.sodiencu = sodiencu;
    }

    public int getSodienmoi() {
        return sodienmoi;
    }

    public void setSodienmoi(int sodienmoi) {
        this.sodienmoi = sodienmoi;
    }

    public int getSonuocmoi() {
        return sonuocmoi;
    }

    public void setSonuocmoi(int sonuocmoi) {
        this.sonuocmoi = sonuocmoi;
    }

    public int getSonuoccu() {
        return sonuoccu;
    }

    public void setSonuoccu(int sonuoccu) {
        this.sonuoccu = sonuoccu;
    }

    public Date getThoigianthanhtoan() {
        return thoigianthanhtoan;
    }

    public void setThoigianthanhtoan(Date thoigianthanhtoan) {
        this.thoigianthanhtoan = thoigianthanhtoan;
    }

    public int getSoluongxe() {
        return soluongxe;
    }

    public void setSoluongxe(int soluongxe) {
        this.soluongxe = soluongxe;
    }

    public String getLichcapnhatSoDien() {
        return lichcapnhatSoDien;
    }

    public void setLichcapnhatSoDien(String lichcapnhatSoDien) {
        this.lichcapnhatSoDien = lichcapnhatSoDien;
    }

    public String getLichcapnhatSoNuoc() {
        return lichcapnhatSoNuoc;
    }

    public void setLichcapnhatSoNuoc(String lichcapnhatSoNuoc) {
        this.lichcapnhatSoNuoc = lichcapnhatSoNuoc;
    }

}
