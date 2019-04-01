/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Giaodienchinh;

import Doituong.Toanha;
import database.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class toanha extends javax.swing.JPanel {

    DefaultTableModel model;
    ArrayList<Toanha> tn = new database().SelectAll("toanha");

    /**
     * Creates new form toanha
     */
    public toanha() {
        initComponents();
        model = (DefaultTableModel) tbltoanha.getModel();
        loadDbToTable();
        filltable();
    }

    public void clearform() {
        txtmatoanha.setText("");
        txttentoanha.setText("");
        txtdiachi.setText("");
        txtsophong.setText("");
    }

    public void loadDbToTable() {
        try {

            database data = new database();
            Connection con = data.getKetnoi();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select * from toanha");

            while (rs.next()) {
                int matoanha = rs.getInt(1);
                String tentoanha = rs.getString(2);
                String diachi = rs.getString(3);
                int sophong = rs.getInt(4);
                Toanha d = new Toanha(matoanha, tentoanha, diachi, sophong);
                tn.add(d);

            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void themtoanha() {
        try {
            if (txtmatoanha.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Bạn không được để trống mã tòa nhà");
                txtmatoanha.requestFocus();
            } else if (!txtmatoanha.getText().matches("[0-9]{1,99}")) {
                JOptionPane.showMessageDialog(null, "Bạn nhập mã tòa nhà là các chữ số");
                txtmatoanha.requestFocus();
            } else if (txttentoanha.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Bạn không được để trống tên tòa nhà");
                txttentoanha.requestFocus();
            } else if (txtdiachi.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Bạn không được để trống địa chỉ");
                txtdiachi.requestFocus();
            } else if (txtsophong.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Bạn không được để trống số phòng");
                txtsophong.requestFocus();
            } else if (!txtsophong.getText().matches("[0-9]{1,99}")) {
                JOptionPane.showMessageDialog(null, "Bạn nhập số phòng là các chữ số");
                txtsophong.requestFocus();
            } else {
                database data = new database();
                Connection con = data.getKetnoi();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("Select * from toanha");
                int co = 0;
                while (rs.next()) {
                    if (rs.getString(1).equals(txtmatoanha.getText())) {
                        co = 1;
                        JOptionPane.showMessageDialog(null, "Mã tòa nhà đã tồn tại ");
                        break;
                    }
                }
                if (co == 0) {
                    st.execute("insert into toanha values ('" + txtmatoanha.getText() + "',N'" + txttentoanha.getText() + "',N'" + txtdiachi.getText() + "','" + txtsophong.getText() + "')");
                    JOptionPane.showMessageDialog(null, "Thêm tòa nhà thành công ");
                    filltable();
                    clearform();
                }
                con.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void suatoanha() {
   int index=tbltoanha.getSelectedRow();
      
        if (index==-1) {
            JOptionPane.showMessageDialog(null, "Bạn vẫn chưa chọn hàng cần cập nhật");
         
            return;
        }

        try {
            if (txtmatoanha.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Bạn không được để trống mã tòa nhà");
                txtmatoanha.requestFocus();
            } else if (!txtmatoanha.getText().matches("[0-9]{1,99}")) {
                JOptionPane.showMessageDialog(null, "Bạn nhập mã tòa nhà là các chữ số");
                txtmatoanha.requestFocus();
            } else if (txttentoanha.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Bạn không được để trống tên tòa nhà");
                txttentoanha.requestFocus();
            } else if (txtdiachi.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Bạn không được để trống địa chỉ");
                txtdiachi.requestFocus();
            } else if (txtsophong.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Bạn không được để trống số phòng");
                txtsophong.requestFocus();
            } else if (!txtsophong.getText().matches("[0-9]{1,99}")) {
                JOptionPane.showMessageDialog(null, "Bạn nhập số phòng là các chữ số");
                txtsophong.requestFocus();
            } else {
                database data = new database();
                Connection con = data.getKetnoi();
                // ArrayList<Toanha> tn = new database().SelectAll("toanha");

                String sql = "update toanha "
                        + "set tentoanha=N'" + txttentoanha.getText() + "',diachi= N'" + txtdiachi.getText() + "', "
                        + "sophong='" + txtsophong.getText() +"' where matoanha='" + txtmatoanha.getText()+ "' ";
                Statement stm = con.createStatement();
                stm.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Cập nhật thành công ");
                filltable();
                 loadDbToTable();
                con.close();
             clearform();
               
            }

        } catch (Exception e) {

        }
    }

    public void xoatoanha() {
        int index=tbltoanha.getSelectedRow();
        if (index==-1) {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn hàng cần xóa");
            txtmatoanha.requestFocus();
            
            
        } else {
            int ret = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa không", "Confim", JOptionPane.YES_NO_OPTION);
            if (ret != JOptionPane.YES_OPTION) {
                return;
            }
            try {
                database data = new database();
                Connection con = data.getKetnoi();
                ArrayList<Toanha> tn = new database().SelectAll("toanha");
                int row = tbltoanha.getSelectedRow();
                int matoanha = (int) tbltoanha.getValueAt(row, 0);
                String sql = "DELETE FROM toanha WHERE matoanha='" + matoanha + "';";
                Statement stm = con.createStatement();
                stm.executeUpdate(sql);
                filltable();
                clearform();
                loadDbToTable();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void filltable() {
        database data = new database();
        model = (DefaultTableModel) tbltoanha.getModel();
        model.setRowCount(0);
        try {
            ArrayList<Toanha> tn = data.SelectAll("toanha");
            for (Toanha check : tn) {
                model.addRow(new Object[]{check.getMatoanha(), check.getTentoanha(), check.getDiachi(),
                    check.getSophong()
                });
            }
            tbltoanha.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent lse) {
                    int index = tbltoanha.getSelectedRow();
                    if (index >= 0) {
                        txtmatoanha.setText(tbltoanha.getValueAt(index, 0).toString());
                        txttentoanha.setText(tbltoanha.getValueAt(index, 1).toString());
                        txtdiachi.setText(tbltoanha.getValueAt(index, 2).toString());
                        txtsophong.setText(tbltoanha.getValueAt(index, 3).toString());

                    }

                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không thể fill dữ liệu lên bảng " + e);
        }
    }

    public void timkiem() {
        if (txttimkiem.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn mã cần tìm kiếm,mời bạn nhập vào ");
            txttimkiem.requestFocus();

        } else {
            try {
                database data = new database();
                Connection con = data.getKetnoi();
                ArrayList<Toanha> tn = new database().SelectAll("toanha");
                String ma = txtmatoanha.getText();
                String sql = "select * from toanha where matoanha='" + ma + "'  ";
                Statement ps = con.createStatement();
                ResultSet rs = ps.executeQuery(sql);
                if (!rs.isBeforeFirst()) {
                    JOptionPane.showMessageDialog(null, "Không có mã tòa nhà cần tìm ");
                } else {
                    while (rs.next()) {
                        int matoanha = rs.getInt(1);
                        String tentoanha = rs.getString(2);
                        String diachi = rs.getString(3);
                        int sophong = rs.getInt(4);

                        Toanha d = new Toanha(matoanha, tentoanha, diachi, sophong);
                        tn.add(d);
                        JOptionPane.showMessageDialog(null, "thông tin tòa nhà như sau");
                        txtmatoanha.setText(ma);
                        txttentoanha.setText(tentoanha);
                        txtdiachi.setText(diachi);
                        //   txtsophong.setText((int)sophong);

                    }

                }
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Không thể tìm kiếm dữ liệu " + e);

            }
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbltoanha = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        txtmatoanha = new javax.swing.JTextField();
        txttentoanha = new javax.swing.JTextField();
        txtdiachi = new javax.swing.JTextField();
        txtsophong = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        normalTool = new javax.swing.JPanel();
        btnthem = new javax.swing.JButton();
        btnsua = new javax.swing.JButton();
        btnxoa = new javax.swing.JButton();
        btnlammoi = new javax.swing.JButton();
        addTool = new javax.swing.JPanel();
        btndongy = new javax.swing.JButton();
        btnhuy = new javax.swing.JButton();
        btnnhaplai = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        txttimkiem = new javax.swing.JTextField();
        btntimkiem = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        tbltoanha.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã tòa nhà", "Tên tòa nhà", "Địa chỉ", "Số phòng"
            }
        ));
        jScrollPane2.setViewportView(tbltoanha);

        jPanel18.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel39.setText("Mã tòa nhà:");

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel40.setText("Địa chỉ:");

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel41.setText("Tên tòa nhà:");

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel42.setText("Số phòng:");

        jPanel19.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel19.setLayout(new java.awt.CardLayout());

        btnthem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnthem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/new1.png"))); // NOI18N
        btnthem.setText("THÊM");
        btnthem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemActionPerformed(evt);
            }
        });

        btnsua.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnsua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/update.png"))); // NOI18N
        btnsua.setText("SỬA");
        btnsua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsuaActionPerformed(evt);
            }
        });

        btnxoa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnxoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/delete1.png"))); // NOI18N
        btnxoa.setText("XÓA");
        btnxoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaActionPerformed(evt);
            }
        });

        btnlammoi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnlammoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/refresh.png"))); // NOI18N
        btnlammoi.setText("LÀM MỚI");
        btnlammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlammoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout normalToolLayout = new javax.swing.GroupLayout(normalTool);
        normalTool.setLayout(normalToolLayout);
        normalToolLayout.setHorizontalGroup(
            normalToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(normalToolLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(normalToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnlammoi, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(btnsua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnthem, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(btnxoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        normalToolLayout.setVerticalGroup(
            normalToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(normalToolLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnlammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(btnsua, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel19.add(normalTool, "card2");

        btndongy.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btndongy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/new1.png"))); // NOI18N
        btndongy.setText("ĐỒNG Ý");
        btndongy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndongyActionPerformed(evt);
            }
        });

        btnhuy.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnhuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/delete1.png"))); // NOI18N
        btnhuy.setText("HỦY");
        btnhuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhuyActionPerformed(evt);
            }
        });

        btnnhaplai.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnnhaplai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/reload.png"))); // NOI18N
        btnnhaplai.setText("NHẬP LẠI");
        btnnhaplai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnhaplaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addToolLayout = new javax.swing.GroupLayout(addTool);
        addTool.setLayout(addToolLayout);
        addToolLayout.setHorizontalGroup(
            addToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addToolLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnnhaplai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnhuy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btndongy, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        addToolLayout.setVerticalGroup(
            addToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addToolLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btndongy, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnhuy, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnnhaplai, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel19.add(addTool, "card3");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel41, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtdiachi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                            .addComponent(txttentoanha)
                            .addComponent(txtsophong)))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtmatoanha, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(txtmatoanha, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txttentoanha, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtdiachi, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtsophong, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TÌM KIẾM", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel51.setText("Mã tòa nhà:");

        btntimkiem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btntimkiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search.png"))); // NOI18N
        btntimkiem.setText("Tìm");
        btntimkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntimkiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel51)
                .addGap(53, 53, 53)
                .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btntimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btntimkiem))
                .addGap(0, 3, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        jLabel1.setText("TÒA NHÀ");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 56, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btndongyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndongyActionPerformed
        try {
            normalTool.setVisible(true);
            addTool.setVisible(false);
            themtoanha();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btndongyActionPerformed

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
        try {
            normalTool.setVisible(false);
            addTool.setVisible(true);
          
        } catch (Exception e) {
        }

    }//GEN-LAST:event_btnthemActionPerformed

    private void btnhuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhuyActionPerformed
        try {
            normalTool.setVisible(true);
            addTool.setVisible(false);

        } catch (Exception e) {

        }
    }//GEN-LAST:event_btnhuyActionPerformed

    private void btnsuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsuaActionPerformed
        try {
            normalTool.setVisible(true);
            addTool.setVisible(false);
            suatoanha();
        } catch (Exception e) {
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_btnsuaActionPerformed

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
        xoatoanha();
// TODO add your handling code here:
    }//GEN-LAST:event_btnxoaActionPerformed

    private void btntimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntimkiemActionPerformed
        timkiem();

        // TODO add your handling code here:
    }//GEN-LAST:event_btntimkiemActionPerformed

    private void btnlammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlammoiActionPerformed
        clearform();      
        // TODO add your handling code here:
    }//GEN-LAST:event_btnlammoiActionPerformed

    private void btnnhaplaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnhaplaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnnhaplaiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel addTool;
    private javax.swing.JButton btndongy;
    private javax.swing.JButton btnhuy;
    private javax.swing.JButton btnlammoi;
    private javax.swing.JButton btnnhaplai;
    private javax.swing.JButton btnsua;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btntimkiem;
    private javax.swing.JButton btnxoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel normalTool;
    private javax.swing.JTable tbltoanha;
    private javax.swing.JTextField txtdiachi;
    private javax.swing.JTextField txtmatoanha;
    private javax.swing.JTextField txtsophong;
    private javax.swing.JTextField txttentoanha;
    private javax.swing.JTextField txttimkiem;
    // End of variables declaration//GEN-END:variables
}
