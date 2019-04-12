
package Doituong;

import java.sql.Date;

public class Nguoithue {
    int manguoithue;
    String tennguoithue;
    String ngayvao;
    String ngaysinh;
    int gioitinh;
    String email;
    String quequan;
    int sdt;
    String cmnd;

    public Nguoithue() {
    }

    public Nguoithue(int manguoithue, String tennguoithue, String ngayvao, String ngaysinh, int gioitinh, String email, String quequan, int sdt, String cmnd) {
        this.manguoithue = manguoithue;
        this.tennguoithue = tennguoithue;
        this.ngayvao = ngayvao;
        this.ngaysinh = ngaysinh;
        this.gioitinh = gioitinh;
        this.email = email;
        this.quequan = quequan;
        this.sdt = sdt;
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

    public int getSdt() {
        return sdt;
    }

    public void setSdt(int sdt) {
        this.sdt = sdt;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    
}
