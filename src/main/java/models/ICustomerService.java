package models;

import props.CustomerService;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface ICustomerService {
    List<CustomerService> customerServiceList(int inpStatus);
    DefaultTableModel serviceCustomerTable(String data);
}
