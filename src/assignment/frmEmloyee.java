package assignment;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import javax.swing.*;
import javax.swing.JTable.*;
import javax.swing.table.DefaultTableModel;

public class frmEmloyee extends javax.swing.JFrame {

    /**
     * Creates new form frmEmloyee
     */
    public frmEmloyee() {
        initComponents();

        new Thread() {
            public void run() {
                //hiển thị h hệ thống ra label
                //nếu h/p/s nhỏ hơn 9 thì cộng thêm số 0 
                try {
                    while (true) {
                        Calendar calendar = Calendar.getInstance();
                        String hour = (calendar.getTime().getHours() > 9)
                                ? "" + calendar.getTime().getHours() + ""
                                : "0" + calendar.getTime().getHours();
                        String minute = (calendar.getTime().getMinutes() > 9)
                                ? "" + calendar.getTime().getMinutes() + ""
                                : "0" + calendar.getTime().getMinutes();
                        String second = (calendar.getTime().getSeconds() > 9)
                                ? "" + calendar.getTime().getSeconds() + ""
                                : "0" + calendar.getTime().getSeconds();
                        lblDongHo.setText(hour + ":" + minute + ":" + second);
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
                .start();
    }

    @SuppressWarnings("unchecked")
    List<Employee> list = new ArrayList<>();
    //khai báo biến currentIndex cho biết đang ở Index của nhân viên nào
    int currentIndex = 0;
    String input;
    int chon;

    //8 chuc nang
    public void addEmployee() {
//        Employee emp = new Employee();
        //1. Khai bao mot doi tuong  nhan vien
        Employee emp = new Employee();
        try {
            emp.setMaNV(txtMANHANVIEN.getText());
            emp.setHoTen(txtHOTEN.getText());
            emp.setTuoi(Integer.parseInt(txtTUOI.getText()));
            emp.setEmail(txtEMAIL.getText());
            emp.setLuong(Integer.parseInt(txtLUONG.getText()));
            //2 them nhan vien vao list
            list.add(emp);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

    }

    //dien du lieu vao luoi
    public void fillToTable() {
        DefaultTableModel model = (DefaultTableModel) tblEMPLOYEE.getModel();
        model.setRowCount(0);
        //2. duyệt qua danh sách nhân viên list, lấy từng nhân viên thêm vào table
        for (Employee emp : list) {
            Object[] row = new Object[]{emp.getMaNV(), emp.getHoTen(), emp.getTuoi(), emp.getEmail(), emp.getLuong()};
            model.addRow(row);
        }

    }

    //hiển thị chi tiết
    public void showDEtail() {
        //xác định nhân viên hiện hành dựa vào currentIIndex
        Employee emp = list.get(currentIndex);
        //xuất thông tin chi tiết của emp lên trên các txt
        txtMANHANVIEN.setText(emp.getMaNV());
        txtHOTEN.setText(emp.getHoTen());
        txtTUOI.setText("" + emp.getTuoi());
        txtEMAIL.setText(emp.getEmail());
        txtLUONG.setText("" + emp.getLuong());
        this.displayStatus();
        //get
//         //1. lấy ra index của hàng vừa click vào
//        int index = tblEMPLOYEE.getSelectedRow();
//        //2. dựa vào index ta lấy ra đc sinh viên trong danh sách
//        Employee emp = list.get(index);
//        txtMANHANVIEN.setText(emp.getMaNV());
//        txtHOTEN.setText(emp.getHoTen());
//        txtTUOI.setText(""+emp.getTuoi());
//        txtEMAIL.setText(emp.getEmail());
//        txtLUONG.setText(""+emp.getLuong());
    }

    public void removeEmployee() {
        // xác định mã số cần xóa
        input = JOptionPane.showInputDialog(this, "Mời bạn nhập mã nhân viên muốn xóa.");

        // duyệt trong danh sách list tìm ra nhân viên cần xóa
        for (Employee emp : list) {
            if (emp.getMaNV().toString().equalsIgnoreCase(input)) {

                //3 xóa khỏi danh sách list
                list.remove(emp);
                txtEMAIL.setText(" ");
                txtMANHANVIEN.setText(" ");
                txtLUONG.setText(" ");
                txtTUOI.setText(" ");
                txtHOTEN.setText(" ");

                //4 thoát khỏi vòng lặp for
                break;

            }
        }

    }

    public void saveFile() {
        //1 luu ca danh sach xuong o D

        XFile.writeObject("d:/Hocsinh.dat", list);

    }

    public void openFile() {
        list = (ArrayList<Employee>) XFile.readObject("d:/Hocsinh.dat");
    }

    public void findEmployee() {
        //A. tìm kiếm theo mã maNV
        //B.tìm kiếm theo tên HoTen

        input = JOptionPane.showInputDialog(this, "Mời bạn nhập mã nhân viên cần tìm");

        if (input.length() > 0) {
            //1 xác định mã số

            //2 duyệt tronh danh sách nhân viên list có tìm thấy maNV
            for (Employee emp : list) {
                if (emp.getMaNV().toString().equalsIgnoreCase(input)) {
                    //3 xd vị trí
                    currentIndex = list.indexOf(emp);
                    //4 hiển thị chi tiết
                    this.showDEtail();
                    this.displayStatus();
                    break;
                }
            }
        }

//        else if(txtHOTEN.getText().length() > 0) {
//            //1 xác định mã số
//            String HoTen = txtHOTEN.getText().toString();
//            //2 duyệt tronh danh sách nhân viên list có tìm thấy maNV
//            for (Employee emp : list) {
//                if (emp.getMaNV().equalsIgnoreCase(HoTen)) {
//                    //3 xd vị trí
//                    currentIndex = list.indexOf(emp);
//                    //4 hiển thị chi tiết
//                    this.showDEtail();
//                    this.displayStatus();
//                    break;
//                } 
//            }
//        }
    }

    //hiển thị trạng thái đang ở record of all
    public void displayStatus() {
        lblStatus.setText("Record " + (currentIndex + 1) + "of" + list.size());

    }

    public void Saveas() {
        JFileChooser chon = new JFileChooser();
        int x = chon.showSaveDialog(this);
        if (x == JFileChooser.APPROVE_OPTION) {
            File taptin = chon.getSelectedFile();
            String duongdan = taptin.getAbsolutePath();
            JOptionPane.showMessageDialog(this, duongdan);

            try {
                FileOutputStream out = new FileOutputStream(taptin);
                ObjectOutputStream obj = new ObjectOutputStream(out);
                obj.writeObject(list);
                out.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "loi" + ex.toString());
            }
        }

    }

    public void Canhbao() {

        chon = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thực hiện thao tác?");

    }

    public void testMNV() {
        try {

        } catch (Exception e) {
        }

    }

    //test sắp xếp

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnNEW = new javax.swing.JButton();
        btnSAVE = new javax.swing.JButton();
        btnDELETE = new javax.swing.JButton();
        btnFIND = new javax.swing.JButton();
        btnOPEN = new javax.swing.JButton();
        btnEXIT = new javax.swing.JButton();
        btnNETXMAX = new javax.swing.JButton();
        btnNEXT = new javax.swing.JButton();
        btnPRE = new javax.swing.JButton();
        btnPREMIN = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblDongHo = new javax.swing.JLabel();
        txtMANHANVIEN = new javax.swing.JTextField();
        txtHOTEN = new javax.swing.JTextField();
        txtTUOI = new javax.swing.JTextField();
        txtEMAIL = new javax.swing.JTextField();
        txtLUONG = new javax.swing.JTextField();
        lblStatus = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblEMPLOYEE = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnSAVEAS = new javax.swing.JButton();

        jMenu1.setText("jMenu1");

        jMenu2.setText("jMenu2");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(210, 247, 230));

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        btnNEW.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assignment/icon-MOI.png"))); // NOI18N
        btnNEW.setText("New");
        btnNEW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNEWActionPerformed(evt);
            }
        });

        btnSAVE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assignment/icon-capnhat.png"))); // NOI18N
        btnSAVE.setText("Save");
        btnSAVE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSAVEActionPerformed(evt);
            }
        });

        btnDELETE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assignment/icon-xoa.png"))); // NOI18N
        btnDELETE.setText("Delete");
        btnDELETE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDELETEActionPerformed(evt);
            }
        });

        btnFIND.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assignment/icon-TIMTHEODIEM.png"))); // NOI18N
        btnFIND.setText("Find");
        btnFIND.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFINDActionPerformed(evt);
            }
        });

        btnOPEN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assignment/icon-TIMTHEOTEN.png"))); // NOI18N
        btnOPEN.setText("Open");
        btnOPEN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOPENActionPerformed(evt);
            }
        });

        btnEXIT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assignment/icon-xoa.png"))); // NOI18N
        btnEXIT.setText("Exit");
        btnEXIT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEXITActionPerformed(evt);
            }
        });

        btnNETXMAX.setText(">|");
        btnNETXMAX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNETXMAXActionPerformed(evt);
            }
        });

        btnNEXT.setText(">>");
        btnNEXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNEXTActionPerformed(evt);
            }
        });

        btnPRE.setText("<<");
        btnPRE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPREActionPerformed(evt);
            }
        });

        btnPREMIN.setText("|<");
        btnPREMIN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPREMINActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 255));
        jLabel1.setText("QUẢN LÝ NHÂN VIÊN");

        lblDongHo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblDongHo.setForeground(new java.awt.Color(255, 51, 51));
        lblDongHo.setText("10:20 AM");

        txtMANHANVIEN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMANHANVIENActionPerformed(evt);
            }
        });

        txtHOTEN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHOTENActionPerformed(evt);
            }
        });

        txtEMAIL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEMAILActionPerformed(evt);
            }
        });

        txtLUONG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLUONGActionPerformed(evt);
            }
        });

        lblStatus.setForeground(new java.awt.Color(255, 0, 51));
        lblStatus.setText("Record: 1 of 10");

        tblEMPLOYEE.setBackground(new java.awt.Color(204, 255, 204));
        tblEMPLOYEE.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "MÃ", "HỌ VÀ TÊN", "TUỔI", "EMAIL", "LƯƠNG"
            }
        ));
        tblEMPLOYEE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEMPLOYEEMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblEMPLOYEE);

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 255));
        jLabel3.setText("MÃ NHÂN VIÊN");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 255));
        jLabel2.setText("HỌ VÀ TÊN");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 255));
        jLabel4.setText("TUỔI");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 255));
        jLabel5.setText("EMAIL");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("LƯƠNG");

        btnSAVEAS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assignment/icon-MOI.png"))); // NOI18N
        btnSAVEAS.setText("Save As");
        btnSAVEAS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSAVEASActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDongHo)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnSAVE, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSAVEAS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnFIND, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnDELETE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnNEW, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnOPEN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(btnEXIT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(65, 65, 65))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(203, 203, 203)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnPREMIN)
                                        .addComponent(jLabel6)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(40, 40, 40)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtMANHANVIEN, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtHOTEN, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtTUOI, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtEMAIL, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(txtLUONG, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(103, 103, 103)
                                                .addComponent(lblStatus))))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnPRE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnNEXT)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnNETXMAX))))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(lblDongHo)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtMANHANVIEN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNEW)
                            .addComponent(btnOPEN))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtHOTEN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtTUOI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEMAIL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtLUONG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPREMIN)
                            .addComponent(btnPRE)
                            .addComponent(btnNEXT)
                            .addComponent(btnNETXMAX)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSAVE)
                            .addComponent(btnSAVEAS))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnFIND)
                            .addComponent(btnDELETE))
                        .addGap(18, 18, 18)
                        .addComponent(btnEXIT)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSAVEASActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSAVEASActionPerformed

        this.Saveas();

        // TODO add your handling code here:
    }//GEN-LAST:event_btnSAVEASActionPerformed

    private void tblEMPLOYEEMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEMPLOYEEMouseClicked
        currentIndex = tblEMPLOYEE.getSelectedRow();
        this.showDEtail();
        this.displayStatus();

        // TODO add your handling code here:
    }//GEN-LAST:event_tblEMPLOYEEMouseClicked

    private void txtLUONGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLUONGActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_txtLUONGActionPerformed

    private void txtEMAILActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEMAILActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEMAILActionPerformed

    private void txtHOTENActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHOTENActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHOTENActionPerformed

    private void txtMANHANVIENActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMANHANVIENActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_txtMANHANVIENActionPerformed

    private void btnPREMINActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPREMINActionPerformed
        //Về nhân viên đầu tiên
        currentIndex = 0;
        //hiện chi tiết lên
        this.showDEtail();
        //Hiện thị trạng thái
        this.displayStatus();

        btnPREMIN.setEnabled(false);
        btnPRE.setEnabled(false);

        btnNETXMAX.setEnabled(true);
        btnNEXT.setEnabled(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_btnPREMINActionPerformed

    private void btnPREActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPREActionPerformed

        if (currentIndex - 1 >= 0) {
            currentIndex = currentIndex - 1;
            this.showDEtail();
            this.displayStatus();
            btnNEXT.setEnabled(true);
            btnNETXMAX.setEnabled(true);

        } else {
            this.showDEtail();
            this.displayStatus();
            btnPRE.setEnabled(false);
            btnPREMIN.setEnabled(false);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_btnPREActionPerformed

    private void btnNEXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNEXTActionPerformed
        // TODO add your handling code here
        if (currentIndex + 1 <= list.size() - 1) {
            currentIndex = currentIndex + 1;
            this.showDEtail();
            this.displayStatus();

            btnPRE.setEnabled(true);
            btnPREMIN.setEnabled(true);
        } else {
            this.showDEtail();
            this.displayStatus();
            btnNEXT.setEnabled(false);
            btnNETXMAX.setEnabled(false);

        }
    }//GEN-LAST:event_btnNEXTActionPerformed

    private void btnNETXMAXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNETXMAXActionPerformed

        currentIndex = list.size() - 1;
        this.showDEtail();
        this.displayStatus();
        btnNETXMAX.setEnabled(false);
        btnNEXT.setEnabled(false);

        btnPRE.setEnabled(true);
        btnPREMIN.setEnabled(true);

    }//GEN-LAST:event_btnNETXMAXActionPerformed

    private void btnEXITActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEXITActionPerformed
        this.Canhbao();
        if (chon == 0) {
            System.exit(0);
        } else {

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_btnEXITActionPerformed

    private void btnOPENActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOPENActionPerformed
        this.openFile();
        this.fillToTable();
        this.showDEtail();
        //        this.displayStatus();

        // TODO add your handling code here:
    }//GEN-LAST:event_btnOPENActionPerformed

    private void btnFINDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFINDActionPerformed

        this.findEmployee();

        // TODO add your handling code here:
    }//GEN-LAST:event_btnFINDActionPerformed

    private void btnDELETEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDELETEActionPerformed

        this.removeEmployee();
        this.Canhbao();
        try {
            if (chon == 0) {

                this.fillToTable();
            }
        } catch (Exception e) {
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_btnDELETEActionPerformed

    private void btnSAVEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSAVEActionPerformed

        for (Employee emp : list) {
            if (emp.getMaNV().equalsIgnoreCase(txtMANHANVIEN.getText())) {
                JOptionPane.showMessageDialog(this, "Mã nhân viên đã tồn tại\n"
                        + "Vui lòng nhập lại mã nhân viên!");
                txtMANHANVIEN.requestFocus();
                return;

            }
        }
        String mail = txtEMAIL.getText();

        String Email = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        if (Integer.parseInt(txtTUOI.getText()) < 17 || Integer.parseInt(txtTUOI.getText()) > 65) {
            JOptionPane.showMessageDialog(this, "Tuổi phải từ 17-65!");
            txtTUOI.requestFocus();
            return;

        } else if (!(mail.matches(Email))) {

            JOptionPane.showMessageDialog(this, "Sai định dạng Gmail!");
            txtEMAIL.requestFocus();
            return;

        } else if (Integer.parseInt(txtLUONG.getText()) < 5000000) {

            JOptionPane.showMessageDialog(this, "Lương phải từ 5 triệu!");
            txtLUONG.requestFocus();
            return;

        } else {
            mail = Email;
            this.addEmployee(); //them
            this.fillToTable(); // bang
            this.saveFile();//save
            JOptionPane.showMessageDialog(this, "Lưu thành công!");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_btnSAVEActionPerformed

    private void btnNEWActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNEWActionPerformed
        txtMANHANVIEN.setText("");
        txtHOTEN.setText("");
        txtTUOI.setText("");
        txtEMAIL.setText("");
        txtLUONG.setText("");
        txtMANHANVIEN.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_btnNEWActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmEmloyee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmEmloyee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmEmloyee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmEmloyee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmEmloyee().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDELETE;
    private javax.swing.JButton btnEXIT;
    private javax.swing.JButton btnFIND;
    private javax.swing.JButton btnNETXMAX;
    private javax.swing.JButton btnNEW;
    private javax.swing.JButton btnNEXT;
    private javax.swing.JButton btnOPEN;
    private javax.swing.JButton btnPRE;
    private javax.swing.JButton btnPREMIN;
    private javax.swing.JButton btnSAVE;
    private javax.swing.JButton btnSAVEAS;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lblDongHo;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JTable tblEMPLOYEE;
    private javax.swing.JTextField txtEMAIL;
    private javax.swing.JTextField txtHOTEN;
    private javax.swing.JTextField txtLUONG;
    private javax.swing.JTextField txtMANHANVIEN;
    private javax.swing.JTextField txtTUOI;
    // End of variables declaration//GEN-END:variables
}
