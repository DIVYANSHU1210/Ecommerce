package com.example.ecomm;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import java.io.IOException;

public class Ecommerse extends Application {
    private final int width = 500, height = 400, headerLine = 50;   //1
    ProductList productList = new ProductList();
    Pane bodyPane;

    Order order = new Order();

    ObservableList<Product> cartItemList = FXCollections.observableArrayList();

    Button signInButton = new Button("Sign In");

    Label welcomeLabel = new Label("Welcome Customer");

    Button placeOrderButton = new Button("Place Order");


    Button ordersButton = new Button("Orders");

    Button signupButton = new Button("Sign Up");

    Customer loggedInCustomer = null;


    private void addItemsToCart(Product product){
        if(cartItemList.contains(product))
            return;
        cartItemList.add(product);
        System.out.println("Product in Cart "+ cartItemList.stream().count());
    }
    private GridPane headerBar(){
        TextField searchBar = new TextField();
        Button searchButton = new Button("Search");
        Button cartButton = new Button ("cart");



        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(productList.productInCart(cartItemList));
            }
        });


        ordersButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(order.getOrders(loggedInCustomer));
            }
        });

        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int orderCount = 0;
                if(!cartItemList.isEmpty() && loggedInCustomer!= null){
                    orderCount = order.placeOrderMultipleProducts(cartItemList, loggedInCustomer);
                }
                if(orderCount > 0){
                    //
                    showDialogue("Order for " + orderCount + " products placed Successfully" );
                    cartItemList.clear();
                }
                else{
                    //
                    showDialogue("please login");
                }
            }
        });


        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(searchBar.getText() != ""){
                    String searchprod = searchBar.getText();
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(productList.getSearchedProducts(searchprod));

                }
                else{
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(productList.getAllProducts());
                }
            }
        });

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(loginPage());
            }
        });






        GridPane header = new GridPane();
        header.setHgap(10);
        header.add(searchBar, 0 , 0);
        header.add(searchButton, 1, 0);
        header.add(signInButton, 2 ,0);
        header.add(welcomeLabel, 3,0);
        header.add(cartButton, 4, 0);
        header.add(ordersButton, 5, 0);

        return header;
    }

    private void showDialogue(String message){
        Dialog<String> dialog = new Dialog<String>();
        //Setting the title
        dialog.setTitle("Order Status");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        //Setting the content of the dialog
        dialog.setContentText(message);
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(type);

            dialog.showAndWait();

    }

    private GridPane footerBar(){
        Button buyNowButton = new Button("Buy Now");
        Button addtocartButton = new Button("add to cart");
        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                boolean orderStatus = false;
                if(product != null && loggedInCustomer!= null){
                    orderStatus = order.placeOrder(loggedInCustomer, product);
                }
                if(orderStatus == true){
                    //
                    showDialogue("Order Successful");
                }
                else{
                    //
                    showDialogue("please login or signup");
                }
            }
        });

        addtocartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                addItemsToCart(product);
            }
        });
        GridPane footer = new GridPane();
        footer.setHgap(10);
        footer.setTranslateY(headerLine + height);
        footer.add(buyNowButton, 0, 0);
        footer.add(addtocartButton, 1, 0);
        footer.add(placeOrderButton, 2, 0);

        return footer;
    }

    private GridPane signupPage(){
        Label userLabel = new Label("User Name");
        TextField userName = new TextField();
        userName.setPromptText("Enter User Name");
        Label passLabel = new Label ("Password");
        Label confpass = new Label ("Conf. Password");
        PasswordField password = new PasswordField();
        password.setPromptText("Confirm Password");
        PasswordField confpassword = new PasswordField();
        confpassword.setPromptText("Enter Password");
        Label useremail = new Label("email");
        TextField email = new TextField();
        email.setPromptText("Enter email id");
        Label mobile = new Label("Phone");
        TextField mobilenum = new TextField();
        mobilenum.setPromptText("Enter Phone no.");
        Label userAddress = new Label ("Address");
        TextField address = new TextField();
        address.setPromptText("Enter your address");

        signupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String user = userName.getText();
                String pass = password.getText();
                String confpass= confpassword.getText();
                String eml = email.getText();
                String add = address.getText();
                String mono = mobilenum.getText();
                if(pass.equals(confpass) && signup.validateEmail(eml)){
                    Login.signUp(eml, user, add, pass, mono);
                    showDialogue("Signup Successfull!! \n Please Login");
                }
                else{
                    showDialogue("Signup Failed \n please try again");
                }
            }
        });


        GridPane signupPane = new GridPane();
        signupPane.setTranslateY(50);
        signupPane.setVgap(10);
        signupPane.setHgap(10);
        signupPane.add(userLabel, 0, 0);
        signupPane.add(userName, 1, 0);
        signupPane.add(passLabel, 0, 1);
        signupPane.add(password, 1, 1);
        signupPane.add(confpass , 0, 2);
        signupPane.add(confpassword, 1, 2);
        signupPane.add(useremail , 0, 3);
        signupPane.add(email, 1, 3);
        signupPane.add(mobile , 0, 4);
        signupPane.add(mobilenum, 1, 4);
        signupPane.add(userAddress , 0, 5);
        signupPane.add(address, 1, 5);
        signupPane.add(signupButton,0, 6);

        return signupPane;

    }
    private GridPane loginPage(){
        Label userLabel = new Label("User Name");
        Label passLabel = new Label ("Password");
        TextField userName = new TextField();
        userName.setPromptText("Enter User Name");
        PasswordField password = new PasswordField();
        password.setPromptText("Enter Password");
        Button loginButton = new Button("Login");
        Label messageLabel = new Label("Login- Message");
        Button accountcreate = new Button("Create Account");


        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String user = userName.getText();
                String pass = password.getText();
                loggedInCustomer = Login.customerLogin(user, pass);
                if(loggedInCustomer != null){
                    messageLabel.setText("Login Successful");
                    welcomeLabel.setText("welcome " + loggedInCustomer.getName() );
                }
                else {
                    messageLabel.setText("Login Failed!!");
                }
            }
        });

        accountcreate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(signupPage());
            }
        });

        GridPane loginPane = new GridPane();
        loginPane.setTranslateY(50);
        loginPane.setVgap(10);
        loginPane.setHgap(10);
        loginPane.add(userLabel, 0, 0);
        loginPane.add(userName, 1, 0);
        loginPane.add(passLabel, 0, 1);
        loginPane.add(password, 1, 1);
        loginPane.add(loginButton,0, 2);
        loginPane.add(messageLabel, 1, 2);
        loginPane.add(accountcreate, 0, 3);

        return loginPane;
    }
    private Pane createContent(){
        Pane root = new Pane();
        root.setPrefSize(width, height+2*headerLine);

        bodyPane = new Pane();
        bodyPane.setPrefSize(width, height);
        bodyPane.setTranslateY(headerLine);
        bodyPane.setTranslateX(10);

        bodyPane.getChildren().add(loginPage());
        root.getChildren().addAll(headerBar()
//                , loginPage()
//                , productList.getAllProducts()
                 , bodyPane
                , footerBar()
        );

        return root;
    }
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Ecommerce.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        stage.setTitle("ECommerce");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
