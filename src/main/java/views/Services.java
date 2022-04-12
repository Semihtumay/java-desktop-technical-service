/*
 * Created by JFormDesigner on Thu Apr 07 18:52:12 TRT 2022
 */

package views;

import java.awt.event.*;

import models.ServiceImpl;
import models.UserImpl;
import props.ComboItem;
import props.Service;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author unknown
 */
public class Services extends Base {
    int row = -1;
    int rowS = -1;
    int selectedId =0;
    ServiceImpl serviceImpl =  new ServiceImpl();
    Service service = new Service();
    Dashboard db = new Dashboard();
    public Services() {
        initComponents();
        lblName.setText("Dear. " + UserImpl.name);
        tblCustomerService.setModel(serviceImpl.serviceCustomerTable(null));
        fncCmbDaysAdd();
        fncCmbStatusAdd();
        tblUpdateDelete.setModel(serviceImpl.serviceUpdateDeleteTable(null));
    }

    private void thisWindowClosing(WindowEvent e) {
        new Dashboard().setVisible(true);
    }

    private void fncCmbDaysAdd(){
        for (int i = 0; i < 20; i++) {
            cmbDays.addItem(i);
        }
    }
    private void fncCmbStatusAdd(){
        cmbStatus.addItem(new ComboItem("status 0","0"));
        cmbStatus.addItem(new ComboItem("status 1","1"));
        cmbStatus.addItem(new ComboItem("status 2","2"));
        cmbStatus.addItem(new ComboItem("status 3","3"));
        cmbStatus.addItem(new ComboItem("status 4","4"));

    }

    public Service fncDataValid(){
        try {
            if ( txtTitle.getText().equals("") || txtTitle.getText().equals(null)) {
                txtTitle.requestFocus();
                lblError.setText("Title Empty");
            } else if (txtInfo.getText().equals("") || txtInfo.getText().equals(null)) {
                txtInfo.requestFocus();
                lblError.setText("Info Empty");
            } else if (cmbDays.getSelectedItem().equals("")) {
                cmbDays.requestFocus();
                lblError.setText("days Empty");
            } else if (cmbStatus.getSelectedItem().equals("")) {
                cmbStatus.requestFocus();
                lblError.setText("status Empty");
            } else if (txtPrice.getText().equals("")) {
                txtPrice.requestFocus();
                lblError.setText("price Empty");
            } else {
                String title = txtTitle.getText().toLowerCase(Locale.ROOT).trim();
                String info = txtInfo.getText().toLowerCase(Locale.ROOT).trim();
                int days = Integer.parseInt(String.valueOf(cmbDays.getSelectedItem()));
                //date
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
                LocalDate localDate = LocalDate.now();
                String date = dtf.format(localDate);
                //status
                int status = Integer.parseInt(((ComboItem)cmbStatus.getSelectedItem()).getValue());
                //date
                int price = Integer.parseInt(txtPrice.getText().toLowerCase(Locale.ROOT).trim());
                lblError.setText("");
                Service service = new Service(0, selectedId, title, info, days, date, status, price);
                return service;
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void textClear(){
        txtTitle.setText("");
        txtInfo.setText("");
        txtPrice.setText("");
    }
    public void rowValue(){
        int column=0;
        row=tblCustomerService.getSelectedRow();
        selectedId= (int) tblCustomerService.getValueAt(row,column);

        int cid= Integer.parseInt(String.valueOf(tblCustomerService.getValueAt(row,0)));
        String name= String.valueOf(tblCustomerService.getValueAt(row,1));
        String surname= String.valueOf(tblCustomerService.getValueAt(row,2));
        String email= String.valueOf(tblCustomerService.getValueAt(row,3));
        String phone= String.valueOf(tblCustomerService.getValueAt(row,4));
        String address= String.valueOf(tblCustomerService.getValueAt(row,5));
        System.out.println("Selected Sindex: "+selectedId);



    }
    public void rowSelect() {
        int column = 0;
        row = tblUpdateDelete.getSelectedRow();
        selectedId = (int) tblUpdateDelete.getValueAt(row,column);

        int sid = Integer.parseInt(String.valueOf(tblUpdateDelete.getValueAt(row,0)));
        int cid = Integer.parseInt(String.valueOf(tblUpdateDelete.getValueAt(row,1)));
        String name = String.valueOf(tblUpdateDelete.getValueAt(row,2));
        String surname = String.valueOf(tblUpdateDelete.getValueAt(row,3));
        String title = String.valueOf(tblUpdateDelete.getValueAt(row,4));
        String info = String.valueOf(tblUpdateDelete.getValueAt(row,5));
        int days = Integer.parseInt(String.valueOf(tblUpdateDelete.getValueAt(row,6)));
        String date = String.valueOf(tblUpdateDelete.getValueAt(row,7));
        String status = String.valueOf(tblUpdateDelete.getValueAt(row,8));
        int price = Integer.parseInt(String.valueOf(tblUpdateDelete.getValueAt(row,9)));
        System.out.println("selectedId "+ selectedId);

        txtTitle.setText(title);
        txtInfo.setText(info);
        cmbDays.getSelectedItem();
        txtPrice.setText(String.valueOf(price));
        cmbStatus.getSelectedItem();
        txtDate.setText(date);

    }

    private void txtCustomerSearchKeyReleased(KeyEvent e) {
        String txtSearch = txtCustomerSearch.getText().trim();
            tblCustomerService.setModel(serviceImpl.serviceCustomerTable(txtSearch));
    }

    private void btnAddClickService(ActionEvent e) {
        Service s = fncDataValid();
        if (s != null ) {
            int status = serviceImpl.serviceInsert(s);
            if (status >0) {
                System.out.println("Ekleme Başarlı");
                tblUpdateDelete.setModel(serviceImpl.serviceUpdateDeleteTable(null));
                textClear();
            }else {
                    lblError.setText("Insert Error");
                }
            }
        }
    private void btnUpdateClickService(ActionEvent e) {
        String title = txtTitle.getText();
        String info = txtInfo.getText();
        int days = Integer.parseInt((String) cmbDays.getSelectedItem());
        int price = Integer.parseInt(txtPrice.getText());

        Service service = new Service(selectedId,title,info,days,price);
        if (row!=-1){
            int answer=JOptionPane.showConfirmDialog(this,"Are you sure you want to update the customer?","Update Window",JOptionPane.YES_OPTION);
            if (answer==0){
                serviceImpl.serviceUpdate(service);
                tblCustomerService.setModel(serviceImpl.serviceCustomerTable(null));
                textClear();
                row=-1;

            }
        }else{
            JOptionPane.showMessageDialog(this,"Please choose.");

        }
    }

    private void btnDeleteClickService(ActionEvent e) {
        if (row != -1) {
            int answer=JOptionPane.showConfirmDialog(this,"Are you sure you want to delete the customer?","Delete Window",JOptionPane.YES_OPTION);

            if (answer==0) {
                serviceImpl.serviceDelete(selectedId);
                tblCustomerService.setModel(serviceImpl.serviceCustomerTable(null));
                textClear();
                row = -1;
            }
        }else {
            JOptionPane.showMessageDialog(this,"Please choose.");
        }
    }



        private void tblCustomerServiceKeyReleased(KeyEvent e) {
            rowValue();
        }

        private void tblCustomerServiceMouseClicked(MouseEvent e) { rowValue(); }

        private void tblUpdateDeleteKeyReleased(KeyEvent e) {
            rowSelect();
        }

        private void tblUpdateDeleteMouseClicked(MouseEvent e) {
            rowSelect();
        }






    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        lblService = new JLabel();
        lblName = new JLabel();
        label2 = new JLabel();
        txtCustomerSearch = new JTextField();
        scrollPane1 = new JScrollPane();
        tblCustomerService = new JTable();
        panel1 = new JPanel();
        label4 = new JLabel();
        btnAddClickService = new JButton();
        btnDeleteClickService = new JButton();
        btnUpdateClickService = new JButton();
        label7 = new JLabel();
        cmbDays = new JComboBox();
        cmbStatus = new JComboBox();
        label3 = new JLabel();
        txtTitle = new JTextField();
        label5 = new JLabel();
        scrollPane2 = new JScrollPane();
        txtInfo = new JTextArea();
        label6 = new JLabel();
        txtDate = new JTextField();
        label1 = new JLabel();
        txtPrice = new JTextField();
        lblError = new JLabel();
        scrollPane3 = new JScrollPane();
        tblUpdateDelete = new JTable();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        Container contentPane = getContentPane();

        //---- lblService ----
        lblService.setText("Technical Service");
        lblService.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblService.setHorizontalAlignment(SwingConstants.CENTER);
        lblService.setForeground(new Color(33, 17, 17));

        //---- lblName ----
        lblName.setText(" ");
        lblName.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
        lblName.setHorizontalAlignment(SwingConstants.RIGHT);
        lblName.setForeground(new Color(33, 17, 17));

        //---- label2 ----
        label2.setText("Customer Search");
        label2.setFont(new Font("Times New Roman", Font.BOLD, 16));

        //---- txtCustomerSearch ----
        txtCustomerSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtCustomerSearchKeyReleased(e);
            }
        });

        //======== scrollPane1 ========
        {

            //---- tblCustomerService ----
            tblCustomerService.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    tblCustomerServiceKeyReleased(e);
                }
            });
            tblCustomerService.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    tblCustomerServiceMouseClicked(e);
                }
            });
            scrollPane1.setViewportView(tblCustomerService);
        }

        //======== panel1 ========
        {

            //---- label4 ----
            label4.setText("Days");
            label4.setFont(new Font("Times New Roman", Font.BOLD, 14));

            //---- btnAddClickService ----
            btnAddClickService.setText("Add");
            btnAddClickService.setFont(new Font("Times New Roman", Font.BOLD, 14));
            btnAddClickService.addActionListener(e -> {
			btnAddClickService(e);
			btnAddClickService(e);
		});

            //---- btnDeleteClickService ----
            btnDeleteClickService.setText("Delete");
            btnDeleteClickService.setFont(new Font("Times New Roman", Font.BOLD, 14));
            btnDeleteClickService.addActionListener(e -> btnDeleteClickService(e));

            //---- btnUpdateClickService ----
            btnUpdateClickService.setText("Update");
            btnUpdateClickService.setFont(new Font("Times New Roman", Font.BOLD, 14));
            btnUpdateClickService.addActionListener(e -> btnUpdateClickService(e));

            //---- label7 ----
            label7.setText("Status");
            label7.setFont(new Font("Times New Roman", Font.BOLD, 14));

            //---- label3 ----
            label3.setText("Title");
            label3.setFont(new Font("Times New Roman", Font.BOLD, 14));

            //---- label5 ----
            label5.setText("Info");
            label5.setFont(new Font("Times New Roman", Font.BOLD, 14));

            //======== scrollPane2 ========
            {
                scrollPane2.setViewportView(txtInfo);
            }

            //---- label6 ----
            label6.setText("Date");
            label6.setFont(new Font("Times New Roman", Font.BOLD, 14));

            //---- label1 ----
            label1.setText("Price");
            label1.setFont(new Font("Times New Roman", Font.BOLD, 14));

            //---- lblError ----
            lblError.setText(" ");
            lblError.setForeground(Color.red);

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblError, GroupLayout.PREFERRED_SIZE, 239, GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(panel1Layout.createParallelGroup()
                                    .addComponent(label3, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label6, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label1, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panel1Layout.createParallelGroup()
                                    .addComponent(txtDate, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPrice, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTitle, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panel1Layout.createParallelGroup()
                                    .addComponent(label5, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label4, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label7, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel1Layout.createParallelGroup()
                                    .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbDays, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbStatus, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))))
                        .addGap(54, 54, 54)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(panel1Layout.createParallelGroup()
                                .addComponent(btnUpdateClickService)
                                .addComponent(btnDeleteClickService))
                            .addComponent(btnAddClickService, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39))
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panel1Layout.createParallelGroup()
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(btnAddClickService)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnUpdateClickService)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDeleteClickService))
                            .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label3)
                                    .addComponent(txtTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label5))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label6)
                                    .addComponent(txtDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label4)
                                    .addComponent(cmbDays, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label1)
                                    .addComponent(txtPrice, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label7)
                                    .addComponent(cmbStatus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblError)
                        .addGap(29, 29, 29))
            );
        }

        //======== scrollPane3 ========
        {

            //---- tblUpdateDelete ----
            tblUpdateDelete.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    tblUpdateDeleteKeyReleased(e);
                }
            });
            tblUpdateDelete.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    tblUpdateDeleteMouseClicked(e);
                }
            });
            scrollPane3.setViewportView(tblUpdateDelete);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(txtCustomerSearch)
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(lblService, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(lblName, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE))
                        .addComponent(scrollPane1)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(label2, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addComponent(panel1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(scrollPane3, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(lblService, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblName, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(label2)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(txtCustomerSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel1, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(scrollPane3, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                    .addGap(35, 35, 35))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel lblService;
    private JLabel lblName;
    private JLabel label2;
    private JTextField txtCustomerSearch;
    private JScrollPane scrollPane1;
    private JTable tblCustomerService;
    private JPanel panel1;
    private JLabel label4;
    private JButton btnAddClickService;
    private JButton btnDeleteClickService;
    private JButton btnUpdateClickService;
    private JLabel label7;
    private JComboBox cmbDays;
    private JComboBox cmbStatus;
    private JLabel label3;
    private JTextField txtTitle;
    private JLabel label5;
    private JScrollPane scrollPane2;
    private JTextArea txtInfo;
    private JLabel label6;
    private JTextField txtDate;
    private JLabel label1;
    private JTextField txtPrice;
    private JLabel lblError;
    private JScrollPane scrollPane3;
    private JTable tblUpdateDelete;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
