package com.example.ecomm;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class Order {

     TableView<Product>orderTable;
    public  boolean placeOrder(Customer customer,Product product){
        try{

            String placeOrder =   "insert into orders (customer_id, product_id, status) values(" + customer.getId() + "," + product.getId() + ", 'Ordered');" ;
            databaseConnection dbconn = new databaseConnection();
            return dbconn.insertUpdate(placeOrder);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public  int placeOrderMultioleProducts(ObservableList<Product> productObservableList, Customer customer){
        int count = 0;
        for(Product product :productObservableList){
            if(placeOrder(customer, product))
                count++;
        }
        return count;
    }


    public  Pane getOrders(){
        ObservableList<Product> productList =   Product.getAllProducts();

        return createTablefromlist(productList);
    }



    public  Pane createTablefromlist(ObservableList<Product> productList){
        TableColumn id = new TableColumn("Id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name = new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price = new TableColumn("Price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

//        ObservableList<Product> data = FXCollections.observableArrayList();
//        data.addAll(new Product(123, "Laptop", 234.5),
//                new Product(123, "Laptop", 234.5)
//        );


//        ObservableList<Product> productList =   Product.getAllProducts();

        orderTable = new TableView<>();
        orderTable.setItems(productList);
        orderTable.getColumns().addAll(id, name, price);

        Pane tablePane = new Pane();
        tablePane.getChildren().add(orderTable);

        return tablePane;
    }

    public  Pane getOrders(Customer customer){
        String order = "select orders.oid , products.name, products.price from orders inner join products on orders.product_id = products.pid where customer_id = "+customer.getId();
        ObservableList<Product> orderlist = Product.getProducts(order);

        return createTablefromlist(orderlist);
    }
}
