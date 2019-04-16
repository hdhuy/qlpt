/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Giaodienchinh;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class hoadon extends javax.swing.JPanel {

    Connection con = new database.database().getKetnoi();
    String headDetail[] = {"Mã Hóa Đơn", "Mã Phòng", "Mã Người Thuê",
        "Tên Người Thuê", "Tên Phòng", "Tiền Nhà", "Số điện sử dùng", "Giá điện",
        "Số nước sử dụng", "Giá nước", "Số lượng xe", "Tiền xe", "Tổng Tiền", "Ngày Thanh Toán"};
    DefaultTableModel modelInvoice = new DefaultTableModel(headDetail, 0);

    public hoadon() {
        initComponents();
        loadDataToTableDetail();
        searchInformation();
    }

    void xoahd() {
        int row = tblInvoice.getSelectedRow();
        String ma = tblInvoice.getValueAt(row, 0).toString() + "";
        String sql = "delete from hoadon where mahoadon=" + ma;
        int is = JOptionPane.showConfirmDialog(cboSelection, "Xóa hóa đơn này ?");
        if (is == 0) {
            try {
                Statement stm = con.createStatement();
                stm.executeQuery(sql);
            } catch (Exception e) {
            }
        }

    }

    private void loadDataToTableDetail() {
        String sql = "SELECT * FROM HOADON";
        modelInvoice.setRowCount(0);
        try {

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Vector data = new Vector();
                data.add(rs.getInt(1));
                data.add(rs.getInt(2));
                data.add(rs.getInt(3));
                data.add(rs.getString(4));
                data.add(rs.getString(5));
                data.add(rs.getInt(6));
                data.add(rs.getInt(7));
                data.add(rs.getInt(8));
                data.add(rs.getInt(9));
                data.add(rs.getInt(10));
                data.add(rs.getInt(11));
                data.add(rs.getInt(12));
                data.add(rs.getInt(13));
                data.add(rs.getDate(14));

                modelInvoice.addRow(data);
            }
            tblInvoice.setModel(modelInvoice);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi đổ dữ liệu bảng:" + e);
        }
    }

    private void searchInformation() {
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                modelInvoice.setRowCount(0);
                String sql = "";
                try {

                    String selection = (String) cboSelection.getSelectedItem();
                    if (selection.equals("Mã hóa đơn")) {
                        sql = "SELECT * FROM HOADON WHERE mahoadon = " + txtSearch.getText();

                    } else if (selection.equals("Tên người thuê")) {
                        sql = "SELECT * FROM HOADON WHERE [TenNguoiThue] LIKE '%" + txtSearch.getText() + "%'";

                    } else if (selection.equals("Tên phòng")) {
                        sql = "SELECT * FROM HOADON WHERE [TenPhong] LIKE '%" + txtSearch.getText() + "%'";

                    } else if (selection.equals("Ngày")) {
                        sql = "SELECT * FROM HOADON where DAY([ThoiGian]) = " + txtSearch.getText();

                    } else if (selection.equals("Tháng")) {
                        sql = "SELECT * FROM HOADON where MONTH([ThoiGian]) = " + txtSearch.getText();

                    } else if (selection.equals("Năm")) {
                        sql = "SELECT * FROM HOADON where YEAR([ThoiGian]) = " + txtSearch.getText();
                    }

                    PreparedStatement prst = con.prepareStatement(sql);
                    ResultSet rs = prst.executeQuery();

                    while (rs.next()) {
                        Vector data = new Vector();
                        data.add(rs.getInt(1));
                        data.add(rs.getInt(2));
                        data.add(rs.getInt(3));
                        data.add(rs.getString(4));
                        data.add(rs.getString(5));
                        data.add(rs.getInt(6));
                        data.add(rs.getInt(7));
                        data.add(rs.getInt(8));
                        data.add(rs.getInt(9));
                        data.add(rs.getInt(10));
                        data.add(rs.getInt(11));
                        data.add(rs.getInt(12));
                        data.add(rs.getInt(13));
                        data.add(rs.getDate(14));

                        modelInvoice.addRow(data);
                    }

                    tblInvoice.setModel(modelInvoice);

                } catch (SQLException ex) {
                }
                if (txtSearch.getText().trim().length() == 0) {
                    loadDataToTableDetail();
                }
            }

        });

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel9 = new javax.swing.JPanel();
        jButton15 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cboSelection = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnXoa = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInvoice = new javax.swing.JTable();

        jButton15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/print.png"))); // NOI18N
        jButton15.setText("IN HÓA ĐƠN");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TÌM KIẾM", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Nhập thông tin");

        cboSelection.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mã hóa đơn", "Tên phòng", "Tên người thuê", "Ngày", "Tháng", "Năm" }));
        cboSelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSelectionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cboSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        jLabel2.setText("HÓA ĐƠN");

        btnXoa.setText("XÓA HÓA ĐƠN");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 751, Short.MAX_VALUE)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tblInvoice.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Tên người thuê", "Tên phòng", "Tiền nhà", "Số điện sử dụng", "Giá điện", "Số nước sử dụng", "Giá nước", "Tổng tiền", "Ngày", "Số Lượng Xe", "Tiền xe"
            }
        ));
        jScrollPane1.setViewportView(tblInvoice);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton15)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)))
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboSelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSelectionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboSelectionActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        xoahd();
        loadDataToTableDetail();
        searchInformation();
    }//GEN-LAST:event_btnXoaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox cboSelection;
    private javax.swing.JButton jButton15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblInvoice;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
