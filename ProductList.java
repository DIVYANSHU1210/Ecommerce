package com.example.ecomm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class ProductList {

    public TableView<Product> productTable;
    public Pane getAllProducts(){
        ObservableList<Product> productList =   Product.getAllProducts();

        return createTablefromlist(productList);
    }

    public Pane getSearchedProducts(String searchprod){
        ObservableList<Product> productList =   Product.getSearchedProducts(searchprod);

        return createTablefromlist(productList);
    }



    public Pane createTablefromlist(ObservableList<Product> productList){
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

        productTable = new TableView<>();
        productTable.setItems(productList);
        productTable.getColumns().addAll(id, name, price);

        Pane tablePane = new Pane();
        tablePane.getChildren().add(productTable);

        return tablePane;
    }

    public Pane productInCart(ObservableList<Product> productList){

        return createTablefromlist(productList);
    }
    public Product getSelectedProduct(){
        //getting selected itom
        return productTable.getSelectionModel().getSelectedItem();
    }

}
