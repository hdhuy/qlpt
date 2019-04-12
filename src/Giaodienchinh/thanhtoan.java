/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Giaodienchinh;

import Doituong.*;
import database.database;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class thanhtoan extends javax.swing.JPanel {

    DefaultTableModel model;

    ArrayList<Phong> listP = new ArrayList<>();
    ArrayList<Dichvu> listD = new ArrayList<>();
    ArrayList<Nguoithue> listN = new ArrayList<>();

    database data = new database();
    Connection con = data.getKetnoi();
    int tongtien = 0;

    public thanhtoan() {
        initComponents();
        model = (DefaultTableModel) tblPhongtro.getModel();
        reload();
    }

    void reload() {
        clearAll();
        model.setRowCount(0);
        capnhatdulieuDATA();
        doDuLieuBang(timPhong(txtTim.getText()));
    }

    void capnhatdulieuDATA() {
        listP = null;
        listD = null;
        listN=null;
        listP = data.SelectAll("phong");
        listD = data.SelectAll("dichvu");
        listN=data.SelectAll("nguoithue");
    }

    /* HUY  ------ */
    ArrayList<Phong> timPhong(String value) {
        ArrayList<Phong> p = new ArrayList<>();
        ArrayList<Phong> p2 = new ArrayList<>();
        //p = null;
        String loc = cboLocketqua.getSelectedItem().toString() + "";
        try {
            p = listP;
            if (loc.equals("Tất cả")) {
                if (value.equals("")) {
                    p2 = p;
                }
                for (Phong check : p) {
                    String maphong = check.getMaphong() + "";
                    String tenphong = check.getTenphong() + "";
                    if (maphong.equalsIgnoreCase(value) || tenphong.equalsIgnoreCase(value)) {
                        p2.add(check);
                    }
                }
            }
            if (loc.equals("Chưa thanh toán")) {
                for (Phong check : p) {
                    LocalDate lich = check.getThoigianthanhtoan().toLocalDate();
                    boolean tt = true;
                    if (lich.getYear() == LocalDate.now().getYear()) {
                        if (lich.getMonthValue() < LocalDate.now().getMonthValue()) {
                            tt = false;
                        }
                    } else {
                        if (lich.getYear() < LocalDate.now().getYear()) {
                            tt = false;
                        }
                    }
                    if (tt == false) {
                        if (value.equals("")) {
                            p2.add(check);
                        }
                        String maphong = check.getMaphong() + "";
                        String tenphong = check.getTenphong() + "";
                        if (maphong.equalsIgnoreCase(value) || tenphong.equalsIgnoreCase(value)) {
                            p2.add(check);
                        }
                    }
                }
            }
            if (loc.equals("Đã thanh toán")) {
                for (Phong check : p) {
                    LocalDate lich = check.getThoigianthanhtoan().toLocalDate();
                    boolean tt = true;
                    if (lich.getYear() == LocalDate.now().getYear()) {
                        if (lich.getMonthValue() < LocalDate.now().getMonthValue()) {
                            tt = false;
                        }
                    } else {
                        if (lich.getYear() < LocalDate.now().getYear()) {
                            tt = false;
                        }
                    }
                    if (tt == true) {
                        if (value.equals("")) {
                            p2.add(check);
                        }
                        String maphong = check.getMaphong() + "";
                        String tenphong = check.getTenphong() + "";
                        if (maphong.equalsIgnoreCase(value) || tenphong.equalsIgnoreCase(value)) {
                            p2.add(check);
                        }
                    }
                }
            }
            if (p2.size() == 0) {
                JOptionPane.showMessageDialog(this, "Không có phòng này !");
            }
        } catch (Exception e) {
        }
        return p2;
    }

    void clearAll() {
        txtTiendien.setText("");
        txtTiennuoc.setText("");
        txtLichthanhtoan.setText("");
        txtTiennha.setText("");
        txtTienxe.setText("");
        txtTongtt.setText("");
    }

    void doDuLieuBang(ArrayList<Phong> p) {
        model.setRowCount(0);
        for (Phong show : p) {
            model.addRow(new Object[]{show.getMatoanha(), show.getTenphong(), show.getMaphong()});
        }
    }

    void doDuLieuText(Phong p) {
        int sodien = p.getSodienmoi() - p.getSodiencu();
        int sonuoc = p.getSonuocmoi() - p.getSonuoccu();
        int soxe = p.getSoluongxe();

        int giadien = listD.get(0).getGiadien();
        int gianuoc = listD.get(0).getGianuoc();

        int tiendien = sodien * giadien;
        int tiennuoc = sonuoc * gianuoc;
        int tienxe = listD.get(0).getGiaxe();
        int tienphong = p.getGiaphong();

        Date d = p.getThoigianthanhtoan();
        LocalDate l = d.toLocalDate();
        txtLichthanhtoan.setText(l.toString());

        tongtien = tiendien + tiennuoc + tienphong + tienxe*p.getSoluongxe();

        int nam = LocalDate.now().getYear();
        int thang = LocalDate.now().getMonthValue();
        if (l.getYear() >= nam) {
            if (l.getMonthValue() >= thang) {
                tongtien = 0;
                tiendien = 0;
                tiennuoc = 0;
                tienxe = 0;
                tienphong = 0;
            }
        }

        txtTongtt.setText(tongtien + " đ");
        txtTiendien.setText(tiendien + " đ");
        txtTiennuoc.setText(tiennuoc + " đ");
        txtTienxe.setText(tienxe + " đ");
        txtTiennha.setText(tienphong + " đ");
    }

    Phong chonPhong() {
        int row = tblPhongtro.getSelectedRow();
        String value = tblPhongtro.getValueAt(row, 2) + "";
        Phong p = new Phong();
        try {
            for (Phong check : listP) {
                String maphong = check.getMaphong() + "";
                String tenphong = check.getTenphong() + "";
                if (maphong.equalsIgnoreCase(value)) {
                    p = check;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Chọn phòng sai" + e);
        }
        return p;
    }

    Nguoithue chonNguoiThue(Phong p) {

        String mant = p.getManguoithue() + "";
        Nguoithue n = new Nguoithue();
        try {
            for (Nguoithue check : listN) {
                String ma = check.getManguoithue() + "";
                if (ma.equalsIgnoreCase(mant)) {
                    n = check;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Chọn phòng sai" + e);
        }
        return n;
    }

    void thanhtoan(Phong p) {
        String today = LocalDate.now().toString();
        String sql = "UPDATE [quanliphongtro].[dbo].[PHONG] "
                + "SET ThoiGianThanhToan = '" + today + "' WHERE "
                + "Maphong = '" + p.getMaphong() + "'";
        try {
            Statement stm = con.createStatement();
            if (tongtien == 0) {
                JOptionPane.showMessageDialog(this, "Phòng này đã thanh toán !");
            } else {
                int xacnhantt = JOptionPane.showConfirmDialog(this, "Thanh toán cho phòng: " + p.getTenphong() + " Tổng tiền: "
                        + tongtien);
                if (xacnhantt == 0) {
                    stm.executeUpdate(sql);
                    capnhatdulieuDATA();
                }
            }

        } catch (Exception e) {
            System.out.println("thanh toán bị lỗi: " + e);
            JOptionPane.showMessageDialog(this, "thanh toán bị lỗi: " + e);
        }
    }

    void luuHoaDon(Phong p, Nguoithue n) {
        String maphong = p.getMaphong() + "";
        String manguoithue = n.getManguoithue() + "";
        String tennguoithue = n.getTennguoithue() + "";
        String tenphong = p.getTenphong() + "";
        int tiennha = p.getGiaphong();
        int sodiensudung = p.getSodienmoi() - p.getSodiencu();
        int giadien = listD.get(0).getGiadien();
        int sonuocsudung = p.getSonuocmoi() - p.getSonuoccu();
        int gianuoc = listD.get(0).getGianuoc();
        int soluongxe = p.getSoluongxe();
        int tienxe = soluongxe * listD.get(0).getGiaxe();
        int tong = tiennha + (sodiensudung * giadien) + (sonuocsudung + gianuoc) + tienxe;
        String thoigian = LocalDate.now().toString();
        //
        String nam=LocalDate.now().getYear()+"";
        String thang=LocalDate.now().getMonthValue()+"";
        //
        String update="update DOANHTHU set thang"+thang+" += "+tong+" where Nam="+nam+";";
//        JOptionPane.showMessageDialog(btnTIMKIEM, update);
        String sql = "INSERT INTO dbo.HOADON (MaPhong, MaNguoiThue, TenNguoiThue, TenPhong, TienNha, SoDienSuDung, GiaDien, SoNuocSuDung, GiaNuoc, SoLuongXe, TienXe, TongTien, ThoiGian) "
                + "VALUES (" + maphong + "," + manguoithue + ", N'" + tennguoithue + "', '" + tenphong + "',"
                + " " + tiennha + "," + sodiensudung + "," + giadien + "," + sonuocsudung + "," + gianuoc + "," + soluongxe + ","
                + "" + tienxe + "," + tong + ", '" + thoigian + "');";
        try {
            Statement stm = con.createStatement();
            stm.executeQuery(sql);
            stm.executeUpdate(update);
        } catch (Exception e) {
        }
    }
    void themDoanhthu(){
        String tong=txtTongtt.getText();
        String nam=LocalDate.now().getYear()+"";
        String thang=LocalDate.now().getMonthValue()+"";
        //
        String update="update DOANHTHU set thang"+thang+" += "+tong+" where Nam="+nam+";";
        try {
            Statement stm = con.createStatement();
            stm.executeUpdate(update);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(btnTIMKIEM, "lỗi up"+e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        btnThanhtoan = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtTiendien = new javax.swing.JTextField();
        txtTiennuoc = new javax.swing.JTextField();
        txtTiennha = new javax.swing.JTextField();
        txtTongtt = new javax.swing.JTextField();
        txtLichthanhtoan = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        txtTienxe = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblPhongtro = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txtTim = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnTIMKIEM = new javax.swing.JButton();
        cboLocketqua = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();

        jPanel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnThanhtoan.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnThanhtoan.setForeground(new java.awt.Color(204, 0, 0));
        btnThanhtoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/payy.png"))); // NOI18N
        btnThanhtoan.setText("THANH TOÁN");
        btnThanhtoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhtoanActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel26.setText("CÁC KHOẢN THANH TOÁN THÁNG NÀY");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel27.setText("Tiền điện:");

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel28.setText("Tiền nước:");

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel29.setText("Tiền nhà:");

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(204, 0, 0));
        jLabel30.setText("Tổng thanh toán:");

        txtTiendien.setEditable(false);

        txtTiennuoc.setEditable(false);

        txtTiennha.setEditable(false);

        txtTongtt.setEditable(false);
        txtTongtt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTongtt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTongttActionPerformed(evt);
            }
        });

        txtLichthanhtoan.setEditable(false);

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel31.setText("Lịch thanh toán:");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel32.setText("Tiền xe:");

        txtTienxe.setEditable(false);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(67, 67, 67)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTiennuoc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                            .addComponent(txtTiennha)
                            .addComponent(txtTiendien)))
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67)
                        .addComponent(txtTienxe)))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(0, 152, Short.MAX_VALUE)
                        .addComponent(btnThanhtoan))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(txtLichthanhtoan))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel30)
                        .addGap(24, 24, 24)
                        .addComponent(txtTongtt)))
                .addGap(52, 52, 52))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                .addGap(17, 17, 17)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(txtTiendien, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTiennuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTiennha, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTienxe, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(0, 47, Short.MAX_VALUE)
                                .addComponent(btnThanhtoan, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtLichthanhtoan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTongtt, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        tblPhongtro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã tòa nhà", "Tên phòng", "Mã phòng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPhongtro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPhongtroMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tblPhongtro);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TÌM KIẾM", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Tên/Mã phòng");

        btnTIMKIEM.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTIMKIEM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search.png"))); // NOI18N
        btnTIMKIEM.setText("Tìm kiếm");
        btnTIMKIEM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTIMKIEMActionPerformed(evt);
            }
        });

        cboLocketqua.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboLocketqua.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Chưa thanh toán", "Đã thanh toán" }));
        cboLocketqua.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboLocketquaItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnTIMKIEM, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(cboLocketqua, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboLocketqua, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnTIMKIEM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addComponent(txtTim)
        );

        jLabel2.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        jLabel2.setText("THANH TOÁN");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(22, 22, 22))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTongttActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongttActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTongttActionPerformed

    private void btnTIMKIEMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTIMKIEMActionPerformed
        reload();
    }//GEN-LAST:event_btnTIMKIEMActionPerformed

    private void tblPhongtroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhongtroMouseClicked
        Phong p = chonPhong();
        doDuLieuText(p);
    }//GEN-LAST:event_tblPhongtroMouseClicked

    private void btnThanhtoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhtoanActionPerformed
        try {
            Phong p = chonPhong();
            if (p == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng dưới bảng !");
            }
            thanhtoan(p);
            Nguoithue n=chonNguoiThue(p);
            luuHoaDon(p, n);
//            themDoanhthu();
            reload();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnThanhtoanActionPerformed

    private void cboLocketquaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboLocketquaItemStateChanged
        reload();
    }//GEN-LAST:event_cboLocketquaItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTIMKIEM;
    private javax.swing.JButton btnThanhtoan;
    private javax.swing.JComboBox<String> cboLocketqua;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTable tblPhongtro;
    private javax.swing.JTextField txtLichthanhtoan;
    private javax.swing.JTextField txtTiendien;
    private javax.swing.JTextField txtTiennha;
    private javax.swing.JTextField txtTiennuoc;
    private javax.swing.JTextField txtTienxe;
    private javax.swing.JTextField txtTim;
    private javax.swing.JTextField txtTongtt;
    // End of variables declaration//GEN-END:variables
}
