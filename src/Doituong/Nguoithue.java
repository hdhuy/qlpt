
package Doituong;

import java.sql.Date;

public class Nguoithue {
    int manguoithue;
    String tennguoithue;
    String ngayra;
    String ngayvao;
    String ngaysinh;
    int gioitinh;
    String email;
    String quequan;
    String sdt;
    float sodu;
    String cmnd;

    public Nguoithue() {
    }

    public Nguoithue(int manguoithue, String tennguoithue, String ngayra, String ngayvao, String ngaysinh, int gioitinh, String email, String quequan, String sdt, float sodu, String cmnd) {
        this.manguoithue = manguoithue;
        this.tennguoithue = tennguoithue;
        this.ngayra = ngayra;
        this.ngayvao = ngayvao;
        this.ngaysinh = ngaysinh;
        this.gioitinh = gioitinh;
        this.email = email;
        this.quequan = quequan;
        this.sdt = sdt;
        this.sodu = sodu;
        this.cmnd = cmnd;
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

    public String getNgayra() {
        return ngayra;
    }

    public void setNgayra(String ngayra) {
        this.ngayra = ngayra;
    }

    public String getNgayvao() {
        return ngayvao;
    }

    public void setNgayvao(String ngayvao) {
        this.ngayvao = ngayvao;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public int getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(int gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuequan() {
        return quequan;
    }

    public void setQuequan(String quequan) {
        this.quequan = quequan;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public float getSodu() {
        return sodu;
    }

    public void setSodu(float sodu) {
        this.sodu = sodu;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }
    
    
}
