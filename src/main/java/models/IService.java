package models;

import props.Customer;
import props.Service;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface IService {
    int serviceInsert(Service service);
    int serviceDelete(int sid);
    int serviceUpdate (Service service);
    DefaultTableModel serviceCustomerTable(String data);
    DefaultTableModel serviceUpdateDeleteTable(String data);
    List<Customer> serviceCustomerList();
    List<Service> serviceList();
    List<Service> archiveServiceList();

}
