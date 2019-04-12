/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Giaodienchinh;

import Doituong.*;
import database.database;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class phong extends javax.swing.JPanel {

    DefaultTableModel model;
    ArrayList<Phong> listP = new ArrayList();
    ArrayList<Toanha> listT = new ArrayList<>();
    ArrayList<Nguoithue> listN = new ArrayList<>();
    database data = new database();
    Connection con = data.getKetnoi();
    ArrayList<truyxuat> truyxuat = new ArrayList<>();

    public phong() {
        initComponents();
        model = (DefaultTableModel) tblPhongtro.getModel();
        reload();
    }

    /*HUẾ---     */
    void capnhatdulieuDATA() {
        listP = null;
        listN = null;
        listT = null;
        listP = data.SelectAll("phong");
        listN = data.SelectAll("nguoithue");
        listT = data.SelectAll("toanha");
    }

    void xoaTrang() {
        txtMatoanha.setText("");
        txtMaphong.setText("");
        txtTentoanha.setText("");
        txtTenchuphong.setText("");

        txtTimtoanha.setText("");
        txtTimnguoithue.setText("");
        cboDiachiemail.removeAllItems();
        cboDiachiemail.addItem("<Phòng trống>");
        cboDiachitoanha.removeAllItems();
        cboDiachitoanha.addItem("<chưa có thông tin>");

        txtTenphong.setText("");
        txtDientich.setValue(0);
        txtSoluongxe.setValue(0);
        txtGia.setValue(0);
    }

    void reload() {
        try {
            model.setRowCount(0);
            //xoaTrang();
            capnhatdulieuDATA();
            for (Phong show : timPhong(txtTim.getText())) {
                doDuLieuBang(show);
            }
        } catch (Exception e) {
        }
    }

    ArrayList<Phong> timPhong(String value) {
        ArrayList<Phong> p = new ArrayList<>();
        try {
            if (value.equals("")) {
                p = listP;
            }
            for (Phong check : listP) {
                String maphong = check.getMaphong() + "";
                String tenphong = check.getTenphong() + "";
                if (maphong.equalsIgnoreCase(value) || tenphong.equalsIgnoreCase(value)) {
                    p.add(check);
                }
            }
            if (p.size() == 0) {
                JOptionPane.showMessageDialog(this, "Không có phòng này !");
            }
        } catch (Exception e) {
        }
        return p;
    }

    Toanha timToaNha(Phong p) {
        Toanha toanha = null;
        for (Toanha check : listT) {
            if (check.getMatoanha() == p.getMatoanha()) {
                toanha = check;
            }
        }
        return toanha;
    }

    Nguoithue timNguoiThue(Phong p) {
        Nguoithue nguoithue = null;
        int mant = p.getManguoithue();
        for (Nguoithue check : listN) {
            if (check.getManguoithue() == mant) {
                nguoithue = check;
            }
        }
        return nguoithue;
    }

    void doDuLieuBang(Phong p) {
        try {
            Toanha t = timToaNha(p);
            Nguoithue n = phong.this.timNguoiThue(p);
            truyxuat tx = new truyxuat(p, t, n);
            model.addRow(new Object[]{p.getMaphong(), p.getTenphong(), t.getTentoanha(), n.getTennguoithue(), t.getDiachi()});
            truyxuat.add(tx);
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(this, "Lỗi đổ dữ liệu bảng : "+e);
        }
    }

    void doDuLieuText() {
        int row = tblPhongtro.getSelectedRow();
        String value = tblPhongtro.getValueAt(row, 0) + "";
        try {
            int ma = Integer.parseInt(value);
            for (truyxuat check : truyxuat) {
                if (check.getPhong().getMaphong() == ma) {
                    Phong p = check.getPhong();
                    Nguoithue n = check.getNguoithue();
                    Toanha t = check.getToanha();

                    txtMatoanha.setText(p.getMatoanha() + "");
                    txtMaphong.setText(p.getMaphong() + "");
                    txtTentoanha.setText(t.getTentoanha());
                    txtTenchuphong.setText(n.getTennguoithue());
                    txtTenphong.setText(p.getTenphong());
                    txtDientich.setValue(p.getDientich());
                    txtSoluongxe.setValue(p.getSoluongxe());
                    txtGia.setValue(p.getGiaphong());
                }
            }
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(this, "Lỗi chọn mã phòng: " + e);
        }
    }

    //THÊM SỬA XÓA
    void xoa(String ma) {
        int is = JOptionPane.showConfirmDialog(this, "Xác nhận xóa phòng này !");
        if (is == 0) {
            for (Phong check : listP) {
                String get = check.getMaphong() + "";
                if (get.equals(ma)) {
                    String sql = "DELETE FROM phong WHERE maphong='" + ma + "';";
                    try {
                        Statement stm = con.createStatement();
                        stm.executeQuery(sql);
                    } catch (Exception e) {

                    }
                }
            }
        }
        capnhatdulieuDATA();
        xoaTrang();
    }

    //TÌM DỮ LIỆU TÒA NHÀ VÀ NGƯỜI THUÊ, ĐỔ VÀO COMBO BOX
    ArrayList<Toanha> timListToaNha(String value) {
        ArrayList<Toanha> t = new ArrayList<>();
        try {
            for (Toanha check : listT) {
                String ma = check.getMatoanha() + "";
                String ten = check.getTentoanha() + "";
                if (ma.equalsIgnoreCase(value) || ten.equalsIgnoreCase(value)) {
                    t.add(check);
                }
            }
            if (t.size() == 0) {
                JOptionPane.showMessageDialog(this, "Không có tòa nhà này !");
            }
        } catch (Exception e) {
        }
        return t;
    }

    ArrayList<Nguoithue> timListNguoiThue(String value) {
        ArrayList<Nguoithue> n = new ArrayList<>();
        try {
            if (value.equals("")) {
                value = "1";
            }
            for (Nguoithue check : listN) {
                String ma = check.getManguoithue() + "";
                String ten = check.getTennguoithue() + "";
                if (ma.equalsIgnoreCase(value) || ten.equalsIgnoreCase(value)) {
                    n.add(check);
                }
            }
            if (n.size() == 0) {
                JOptionPane.showMessageDialog(this, "Không có khách hàng này !");
            }
        } catch (Exception e) {
        }
        return n;
    }

    //ĐỔ VÀO CBO
    void doDuLieuDiaChiVaoCOMBOBOX(ArrayList<Toanha> t) {
        cboDiachitoanha.removeAllItems();
        for (Toanha show : t) {
            cboDiachitoanha.addItem(show.getDiachi());
        }
        if (cboDiachitoanha.getItemCount() == 0) {
            cboDiachitoanha.addItem("<chưa có thông tin>");
        }
    }

    void doDuLieuEmailVaoCOMBOBOX(ArrayList<Nguoithue> n) {
        cboDiachiemail.removeAllItems();
        for (Nguoithue show : n) {
            cboDiachiemail.addItem(show.getEmail());
        }
//        if(cboDiachiemail.getItemCount()==0){
//            cboDiachitoanha.addItem("<chưa có thông tin>");
//        }
    }

    //LẤY MÃ TỪ ĐỊA CHỈ
    int layMaToaNhaTu_COMBO_BOX(String diachi) {
        int ma = -1;
        try {
            for (Toanha check : listT) {
                if (check.getDiachi().equals(diachi)) {
                    ma = check.getMatoanha();
                }
            }
        } catch (Exception e) {
        }
        return ma;
    }

    int layMaNguoiThueTu_COMBO_BOX(String email) {
        int ma = 1;
        try {

            for (Nguoithue check : listN) {
                if (check.getEmail().equals(email)) {
                    ma = check.getManguoithue();
                }
            }
        } catch (Exception e) {
        }
        return ma;
    }

    //THÊM SỬA XÓA THANH LÍ
    String checkNull() {
        String noti = "";
        try {
            if (cboDiachitoanha.getItemCount() == 0 || cboDiachitoanha.getSelectedItem().toString().equals("<chưa có thông tin>")) {
                noti += "tòa nhà rỗng";
            }
            if (txtTenphong.getText().equals("")) {
                noti += ", phòng rỗng";
            }
            if ((int) txtSoluongxe.getValue() < 0) {
                noti += ", số lượng xe không thể nhỏ hơn 0";
            }
            if ((int) txtDientich.getValue() <= 0) {
                noti += ", diện tích không thể nhỏ hơn hoặc bằng 0";
            }
            if ((int) txtGia.getValue() <= 0) {
                noti += ", bạn có chắc phòng này giá bằng 0";
            }
            if (noti != "") {
                noti = "Bạn đang gặp lỗi: " + noti + " ?";
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi tạo thông báo ! " + e);
        }
        return noti;
    }

    void them(String matn, String mant) {
        String now = LocalDate.now().toString();

        String tenphong = txtTenphong.getText();

        String matoanha = matn;
        String manguoithue = mant;

        String dientich = txtDientich.getValue() + "";
        String giaphong = txtGia.getValue() + "";
        String soluongnguoi = "1";
        String sodiencu = "0";
        String sodienmoi = "0";
        String sonuocmoi = "0";
        String sonuoccu = "0";
        String thoigianthanhtoan = now;
        String soluongxe = txtSoluongxe.getValue() + "";
        if (checkNull() == "") {
            JOptionPane.showMessageDialog(this, "ĐÃ THÊM THÀNH CÔNG PHÒNG: " + tenphong);
            try {
                String sql = "INSERT INTO dbo.PHONG (TenPhong, MaToaNha, MaNguoiThue, DienTich, GiaPhong, SoLuongNguoi, SoDienCu, SoDienMoi, SoNuocMoi, SoNuocCu, ThoiGianThanhToan, SoLuongXe)"
                        + " VALUES (N'" + tenphong + "', " + matoanha + "," + manguoithue + "," + dientich + ", " + giaphong + ","
                        + " " + soluongnguoi + ", " + sodiencu + "," + sodienmoi + ", " + sonuocmoi + ", " + sonuoccu + ", '" + thoigianthanhtoan + "', " + soluongxe + ");";

                Statement stm = con.createStatement();
                stm.executeQuery(sql);

            } catch (Exception e) {
            }
        } else {
            JOptionPane.showMessageDialog(this, checkNull());
        }

    }

    void sua(String matn, String mant) {
        String maphong = txtMaphong.getText();

        String tenphong = txtTenphong.getText();

        String matoanha = matn;
        String manguoithue = mant;

        String dientich = txtDientich.getValue() + "";
        String giaphong = txtGia.getValue() + "";
        String soluongnguoi = "1";

        String soluongxe = txtSoluongxe.getValue() + "";
        String sql = "UPDATE PHONG "
                + "SET TenPhong = N'" + tenphong + "'"
                + ", MaToaNha = " + matoanha + ""
                + ", MaNguoiThue = " + manguoithue + ""
                + ", DienTich = " + dientich + ""
                + ", GiaPhong = " + giaphong + ""
                + ", SoLuongNguoi = " + soluongnguoi + ""
                + ", SoLuongXe = " + soluongxe + ""
                + " WHERE maphong=" + maphong + ";";
        //String noti=;
        if (checkNull() == "") {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công phòng: " + tenphong);
            try {
                Statement stm = con.createStatement();
                stm.executeUpdate(sql);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "lỗi sửa" + e);
            }
        } else {
            JOptionPane.showMessageDialog(this, checkNull());
        }
    }

    void thanhli(String maphong) {

        String manguoithue = "";
        for (Phong check : listP) {
            String get = check.getMaphong() + "";
            if (get.equals(maphong)) {
                manguoithue = check.getManguoithue() + "";
            }
        }
        int is = JOptionPane.showConfirmDialog(this, "Thanh lí bao gồm: bỏ trống phòng và xóa thông tin khách hàng phòng đó !");
        if (is == 0) {
            String updatephong = "update phong set manguoithue=1 where maphong=" + maphong + ";";
            String xoanguoithue = "DELETE FROM nguoithue WHERE manguoithue=" + manguoithue + "";
            try {
                Statement stm = con.createStatement();
                stm.executeUpdate(updatephong);
                if (!manguoithue.equals("1")) {
                    stm.executeQuery(xoanguoithue);
                }
            } catch (Exception e) {
//                JOptionPane.showMessageDialog(this, "Lỗi thanh lí"+e);
            }
        } else {
            JOptionPane.showMessageDialog(this, checkNull());
        }
    }

    static class truyxuat {

        Phong phong;
        Toanha toanha;
        Nguoithue nguoithue;

        public truyxuat() {
        }

        public truyxuat(Phong phong, Toanha toanha, Nguoithue nguoithue) {
            this.phong = phong;
            this.toanha = toanha;
            this.nguoithue = nguoithue;
        }

        public Phong getPhong() {
            return phong;
        }

        public void setPhong(Phong phong) {
            this.phong = phong;
        }

        public Toanha getToanha() {
            return toanha;
        }

        public void setToanha(Toanha toanha) {
            this.toanha = toanha;
        }

        public Nguoithue getNguoithue() {
            return nguoithue;
        }

        public void setNguoithue(Nguoithue nguoithue) {
            this.nguoithue = nguoithue;
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Phong = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPhongtro = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        TOOL = new javax.swing.JPanel();
        normalTool = new javax.swing.JPanel();
        btnTHEM = new javax.swing.JButton();
        btnSUA = new javax.swing.JButton();
        btnXOA = new javax.swing.JButton();
        btnTHANHLY = new javax.swing.JButton();
        editTool = new javax.swing.JPanel();
        btnCAPNHAT = new javax.swing.JButton();
        btnDATLAI = new javax.swing.JButton();
        btnHUY = new javax.swing.JButton();
        addTool = new javax.swing.JPanel();
        btnTHEM2 = new javax.swing.JButton();
        btnDATLAI1 = new javax.swing.JButton();
        btnHUY1 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtGia = new javax.swing.JSpinner();
        txtSoluongxe = new javax.swing.JSpinner();
        jLabel18 = new javax.swing.JLabel();
        txtTenphong = new javax.swing.JTextField();
        txtDientich = new javax.swing.JSpinner();
        ThongTinMaPhong = new javax.swing.JPanel();
        normalTopPanl = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        txtMatoanha = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        txtMaphong = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        txtTentoanha = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        txtTenchuphong = new javax.swing.JTextField();
        editTopPanel = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtTimtoanha = new javax.swing.JTextField();
        btnTimtoanha = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        cboDiachitoanha = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        txtTimnguoithue = new javax.swing.JTextField();
        cboDiachiemail = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        btnTimnguoithue = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        txtTim = new javax.swing.JTextField();
        btnTimkiem = new javax.swing.JButton();

        tblPhongtro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã phòng", "Tên phòng", "Tên tòa nhà", "Tên chủ phòng", "Địa chỉ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPhongtro.getTableHeader().setReorderingAllowed(false);
        tblPhongtro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPhongtroMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPhongtro);
        if (tblPhongtro.getColumnModel().getColumnCount() > 0) {
            tblPhongtro.getColumnModel().getColumn(2).setResizable(false);
        }

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        TOOL.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        TOOL.setLayout(new java.awt.CardLayout());

        btnTHEM.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTHEM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/new1.png"))); // NOI18N
        btnTHEM.setText("THÊM");
        btnTHEM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTHEMActionPerformed(evt);
            }
        });

        btnSUA.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSUA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/update.png"))); // NOI18N
        btnSUA.setText("SỬA");
        btnSUA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSUAActionPerformed(evt);
            }
        });

        btnXOA.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXOA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/delete1.png"))); // NOI18N
        btnXOA.setText("XÓA");
        btnXOA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXOAActionPerformed(evt);
            }
        });

        btnTHANHLY.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTHANHLY.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/thanhli.png"))); // NOI18N
        btnTHANHLY.setText("THANH LÝ");
        btnTHANHLY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTHANHLYActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout normalToolLayout = new javax.swing.GroupLayout(normalTool);
        normalTool.setLayout(normalToolLayout);
        normalToolLayout.setHorizontalGroup(
            normalToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, normalToolLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(normalToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnTHANHLY, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXOA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSUA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTHEM, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        normalToolLayout.setVerticalGroup(
            normalToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(normalToolLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(btnTHEM, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSUA, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnXOA, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTHANHLY, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        TOOL.add(normalTool, "card2");

        btnCAPNHAT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCAPNHAT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/reload.png"))); // NOI18N
        btnCAPNHAT.setText("SỬA XONG");
        btnCAPNHAT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCAPNHATActionPerformed(evt);
            }
        });

        btnDATLAI.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDATLAI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/cancel.png"))); // NOI18N
        btnDATLAI.setText("ĐẶT LẠI");
        btnDATLAI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDATLAIActionPerformed(evt);
            }
        });

        btnHUY.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnHUY.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/delete1.png"))); // NOI18N
        btnHUY.setText("HỦY");
        btnHUY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHUYActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout editToolLayout = new javax.swing.GroupLayout(editTool);
        editTool.setLayout(editToolLayout);
        editToolLayout.setHorizontalGroup(
            editToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editToolLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(editToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCAPNHAT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDATLAI, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                    .addComponent(btnHUY, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        editToolLayout.setVerticalGroup(
            editToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editToolLayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(btnCAPNHAT)
                .addGap(18, 18, 18)
                .addComponent(btnDATLAI)
                .addGap(18, 18, 18)
                .addComponent(btnHUY)
                .addContainerGap(69, Short.MAX_VALUE))
        );

        TOOL.add(editTool, "card3");

        btnTHEM2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTHEM2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/new1.png"))); // NOI18N
        btnTHEM2.setText("THÊM XONG");
        btnTHEM2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTHEM2ActionPerformed(evt);
            }
        });

        btnDATLAI1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDATLAI1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/cancel.png"))); // NOI18N
        btnDATLAI1.setText("ĐẶT LẠI");
        btnDATLAI1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDATLAI1ActionPerformed(evt);
            }
        });

        btnHUY1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnHUY1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/delete1.png"))); // NOI18N
        btnHUY1.setText("HỦY");
        btnHUY1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHUY1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addToolLayout = new javax.swing.GroupLayout(addTool);
        addTool.setLayout(addToolLayout);
        addToolLayout.setHorizontalGroup(
            addToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addToolLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnHUY1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDATLAI1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTHEM2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14))
        );
        addToolLayout.setVerticalGroup(
            addToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addToolLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(btnTHEM2)
                .addGap(18, 18, 18)
                .addComponent(btnDATLAI1)
                .addGap(18, 18, 18)
                .addComponent(btnHUY1)
                .addContainerGap(73, Short.MAX_VALUE))
        );

        TOOL.add(addTool, "card4");

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setText("Diện tích:");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setText("Số lượng xe:");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel22.setText("Giá:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setText("Tên phòng:");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTenphong, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoluongxe, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(35, 35, 35)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtGia, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                    .addComponent(txtDientich))
                .addGap(113, 113, 113))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTenphong, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtDientich, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSoluongxe, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        ThongTinMaPhong.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ThongTinMaPhong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ThongTinMaPhong.setLayout(new java.awt.CardLayout());

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel36.setText("Mã tòa nhà");

        txtMatoanha.setEditable(false);

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel37.setText("Mã phòng");

        txtMaphong.setEditable(false);

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel38.setText("Tên tòa nhà");

        txtTentoanha.setEditable(false);

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel39.setText("Tên chủ phòng");

        txtTenchuphong.setEditable(false);

        javax.swing.GroupLayout normalTopPanlLayout = new javax.swing.GroupLayout(normalTopPanl);
        normalTopPanl.setLayout(normalTopPanlLayout);
        normalTopPanlLayout.setHorizontalGroup(
            normalTopPanlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(normalTopPanlLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(normalTopPanlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(normalTopPanlLayout.createSequentialGroup()
                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtMaphong))
                    .addGroup(normalTopPanlLayout.createSequentialGroup()
                        .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtMatoanha, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                .addGroup(normalTopPanlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(normalTopPanlLayout.createSequentialGroup()
                        .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtTenchuphong))
                    .addGroup(normalTopPanlLayout.createSequentialGroup()
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtTentoanha, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );
        normalTopPanlLayout.setVerticalGroup(
            normalTopPanlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, normalTopPanlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(normalTopPanlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(normalTopPanlLayout.createSequentialGroup()
                        .addGroup(normalTopPanlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTentoanha, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(normalTopPanlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenchuphong, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(normalTopPanlLayout.createSequentialGroup()
                        .addGroup(normalTopPanlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMatoanha, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(normalTopPanlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaphong, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        ThongTinMaPhong.add(normalTopPanl, "card3");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setText("NHẬP TÊN/MÃ TÒA NHÀ: ");

        txtTimtoanha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btnTimtoanha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTimtoanha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search.png"))); // NOI18N
        btnTimtoanha.setMargin(new java.awt.Insets(2, 5, 2, 14));
        btnTimtoanha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimtoanhaActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setText("ĐỊA CHỈ:");

        cboDiachitoanha.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<chưa có thông tin>" }));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel23.setText("NHẬP TÊN/MÃ NGƯỜI THUÊ: ");

        txtTimnguoithue.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        cboDiachiemail.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Phòng trống>" }));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel24.setText("EMAIL:");

        btnTimnguoithue.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTimnguoithue.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search.png"))); // NOI18N
        btnTimnguoithue.setMargin(new java.awt.Insets(2, 5, 2, 14));
        btnTimnguoithue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimnguoithueActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout editTopPanelLayout = new javax.swing.GroupLayout(editTopPanel);
        editTopPanel.setLayout(editTopPanelLayout);
        editTopPanelLayout.setHorizontalGroup(
            editTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editTopPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(editTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(editTopPanelLayout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cboDiachitoanha, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(editTopPanelLayout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimtoanha, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTimtoanha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addGroup(editTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(editTopPanelLayout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cboDiachiemail, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(editTopPanelLayout.createSequentialGroup()
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimnguoithue, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTimnguoithue)
                .addGap(14, 14, 14))
        );
        editTopPanelLayout.setVerticalGroup(
            editTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editTopPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(editTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editTopPanelLayout.createSequentialGroup()
                        .addGroup(editTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTimnguoithue, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTimnguoithue, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(editTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(editTopPanelLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(cboDiachiemail, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(editTopPanelLayout.createSequentialGroup()
                        .addGroup(editTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTimtoanha, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTimtoanha, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(editTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(editTopPanelLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(cboDiachitoanha, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ThongTinMaPhong.add(editTopPanel, "card2");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ThongTinMaPhong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ThongTinMaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(TOOL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TOOL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        jLabel1.setText("PHÒNG TRỌ");

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setName("Tìm kiếm"); // NOI18N

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel50.setText("Tên/mã phòng:");

        txtTim.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btnTimkiem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTimkiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search.png"))); // NOI18N
        btnTimkiem.setText("Tìm ");
        btnTimkiem.setMargin(new java.awt.Insets(2, 5, 2, 14));
        btnTimkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimkiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(btnTimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTimkiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTim, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2))
        );

        javax.swing.GroupLayout PhongLayout = new javax.swing.GroupLayout(Phong);
        Phong.setLayout(PhongLayout);
        PhongLayout.setHorizontalGroup(
            PhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PhongLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PhongLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        PhongLayout.setVerticalGroup(
            PhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PhongLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Phong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Phong, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnTHEMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTHEMActionPerformed
        try {
            doDuLieuEmailVaoCOMBOBOX(timListNguoiThue(txtTimnguoithue.getText()));
            normalTool.setVisible(false);
            addTool.setVisible(true);
            editTool.setVisible(false);

            editTopPanel.setVisible(true);
            normalTopPanl.setVisible(false);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnTHEMActionPerformed

    private void tblPhongtroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhongtroMouseClicked
        doDuLieuText();
    }//GEN-LAST:event_tblPhongtroMouseClicked

    private void btnTimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimkiemActionPerformed
        capnhatdulieuDATA();
        xoaTrang();
        String value = txtTim.getText();
        ArrayList<Phong> p = timPhong(value);
        truyxuat.clear();
        reload();
    }//GEN-LAST:event_btnTimkiemActionPerformed

    private void btnTimtoanhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimtoanhaActionPerformed
        reload();
        doDuLieuDiaChiVaoCOMBOBOX(timListToaNha(txtTimtoanha.getText()));
    }//GEN-LAST:event_btnTimtoanhaActionPerformed

    private void btnSUAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSUAActionPerformed
        doDuLieuEmailVaoCOMBOBOX(timListNguoiThue(txtTimnguoithue.getText()));
        
        txtTimtoanha.setText(txtTentoanha.getText());
        doDuLieuDiaChiVaoCOMBOBOX(timListToaNha(txtTimtoanha.getText()));
        
        txtTimnguoithue.setText(txtTenchuphong.getText());
        doDuLieuEmailVaoCOMBOBOX(timListNguoiThue(txtTimnguoithue.getText()));
        
        normalTool.setVisible(false);
        addTool.setVisible(false);
        editTool.setVisible(true);

        editTopPanel.setVisible(true);
        normalTopPanl.setVisible(false);
    }//GEN-LAST:event_btnSUAActionPerformed

    private void btnHUYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHUYActionPerformed
        normalTool.setVisible(true);
        addTool.setVisible(false);
        editTool.setVisible(false);

        editTopPanel.setVisible(false);
        normalTopPanl.setVisible(true);
    }//GEN-LAST:event_btnHUYActionPerformed

    private void btnHUY1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHUY1ActionPerformed
        normalTool.setVisible(true);
        addTool.setVisible(false);
        editTool.setVisible(false);

        editTopPanel.setVisible(false);
        normalTopPanl.setVisible(true);
    }//GEN-LAST:event_btnHUY1ActionPerformed

    private void btnTimnguoithueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimnguoithueActionPerformed
        reload();
        doDuLieuEmailVaoCOMBOBOX(timListNguoiThue(txtTimnguoithue.getText()));
    }//GEN-LAST:event_btnTimnguoithueActionPerformed

    private void btnTHEM2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTHEM2ActionPerformed
        if (layMaToaNhaTu_COMBO_BOX(cboDiachitoanha.getSelectedItem().toString()) != -1) {
            String matoanha = layMaToaNhaTu_COMBO_BOX(cboDiachitoanha.getSelectedItem().toString()) + "";
            String manguoithue = layMaNguoiThueTu_COMBO_BOX(cboDiachiemail.getSelectedItem().toString()) + "";
            them(matoanha, manguoithue);
            //cập nhật ngay sau đó
            reload();
        } else {
            JOptionPane.showMessageDialog(this, "Chọn tòa nhà trước khi thêm phòng !");
        }

    }//GEN-LAST:event_btnTHEM2ActionPerformed

    private void btnXOAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXOAActionPerformed
        xoa(txtMaphong.getText());
        //cập nhật ngay sau đó
        reload();
    }//GEN-LAST:event_btnXOAActionPerformed

    private void btnCAPNHATActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCAPNHATActionPerformed
        try {
            String matoanha = layMaToaNhaTu_COMBO_BOX(cboDiachitoanha.getSelectedItem().toString()) + "";
            String manguoithue = layMaNguoiThueTu_COMBO_BOX(cboDiachiemail.getSelectedItem().toString()) + "";
            model.setRowCount(0);
            sua(matoanha, manguoithue);
            //cập nhật ngay sau đó
            
            reload();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi nút sửa: " + e);
        }

    }//GEN-LAST:event_btnCAPNHATActionPerformed

    private void btnDATLAIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDATLAIActionPerformed
        xoaTrang();
    }//GEN-LAST:event_btnDATLAIActionPerformed

    private void btnDATLAI1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDATLAI1ActionPerformed
        xoaTrang();
    }//GEN-LAST:event_btnDATLAI1ActionPerformed

    private void btnTHANHLYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTHANHLYActionPerformed
        if (!txtMaphong.getText().equals("")) {
            thanhli(txtMaphong.getText());
            reload();
        } else {
            JOptionPane.showMessageDialog(this, "Hãy chọn một phòng để thanh lí !");
        }


    }//GEN-LAST:event_btnTHANHLYActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Phong;
    private javax.swing.JPanel TOOL;
    private javax.swing.JPanel ThongTinMaPhong;
    private javax.swing.JPanel addTool;
    private javax.swing.JButton btnCAPNHAT;
    private javax.swing.JButton btnDATLAI;
    private javax.swing.JButton btnDATLAI1;
    private javax.swing.JButton btnHUY;
    private javax.swing.JButton btnHUY1;
    private javax.swing.JButton btnSUA;
    private javax.swing.JButton btnTHANHLY;
    private javax.swing.JButton btnTHEM;
    private javax.swing.JButton btnTHEM2;
    private javax.swing.JButton btnTimkiem;
    private javax.swing.JButton btnTimnguoithue;
    private javax.swing.JButton btnTimtoanha;
    private javax.swing.JButton btnXOA;
    private javax.swing.JComboBox<String> cboDiachiemail;
    private javax.swing.JComboBox<String> cboDiachitoanha;
    private javax.swing.JPanel editTool;
    private javax.swing.JPanel editTopPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel normalTool;
    private javax.swing.JPanel normalTopPanl;
    private javax.swing.JTable tblPhongtro;
    private javax.swing.JSpinner txtDientich;
    private javax.swing.JSpinner txtGia;
    private javax.swing.JTextField txtMaphong;
    private javax.swing.JTextField txtMatoanha;
    private javax.swing.JSpinner txtSoluongxe;
    private javax.swing.JTextField txtTenchuphong;
    private javax.swing.JTextField txtTenphong;
    private javax.swing.JTextField txtTentoanha;
    private javax.swing.JTextField txtTim;
    private javax.swing.JTextField txtTimnguoithue;
    private javax.swing.JTextField txtTimtoanha;
    // End of variables declaration//GEN-END:variables
}
