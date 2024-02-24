import java.util.Scanner;

public class BurgerShop {
    //Original Arrays
    public static String[] orderIdArray = new String[0];
    public static String[] phoneArray = new String[0];
    public static String[] customerNameArray = new String[0];
    public static int[] orderStatusArray = new int[0];
    public static double[] totalArray = new double[0];
    //Sorted Arrays
    public static double[] totalArraySorted = new double[0];
    public static String[] orderIdArraySorted = new String[0];
    public static String[] phoneArraySorted = new String[0];
    public static String[] customerNameArraySorted = new String[0];
    public static int[] orderStatusArraySorted = new int[0];

    public static int orderNumber = 0;
    public static final int PREPARING=0;
    public static final int DELIVERED=1;
    public static final int CANCEL=2;
    public static final double burgerPrice = 500;

    public static void main(String[] args) {
        do {
            homePage();
            int option = getOption();
            clearConsole();
            switch(option){
                case 1:placeOrder();break;
                case 2:bestCustomer();break;
                case 3:searchOrder();break;
                case 4:searchCustomer();break;
                case 5:viewOrders();break;
                case 6:updateOrder();break;
                case 7:exit();
            }
        } while (true);
    }

    public static  void homePage(){
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("|                             iHungry Burger                                   |");
        System.out.println("--------------------------------------------------------------------------------\n");
        System.out.println("\t[1] Place Order\t\t\t\t [2]Search Best Customer\n");
        System.out.println("\t[3] Search Order\t\t\t [4]Search Customer\n");
        System.out.println("\t[5] View Orders\t\t\t\t [6]Update Order Details\n");
        System.out.println("\t[7] Exit\n");
    }
    //Update Order///////////////////////////////////////////////////////////////////////////////////////
    private static void updateOrder() {
        Scanner input = new Scanner(System.in);

        l2:do {
            System.out.println(" --------------------------------------------------------------------------------");
            System.out.println("|                       UPDATE ORDER DETAILS                                      |");
            System.out.println(" --------------------------------------------------------------------------------\n");
            String orderId;
            int i;
            L1: do{
                System.out.print("Enter the order ID (press -1 to Exit) -> ");
                orderId = input.next();
                if(orderId.equals("-1")){
                    clearConsole(); return;
                }else if(orderId.charAt(0)!='B'){
                    System.out.println("\nInvalid input for Order ID...try again\n");
                    continue;
                }else if(!searchOrderId(orderId)){
                    System.out.println("\nThis order ID is not exist !!! Try Again...\n");
                    continue;
                }else{
                    i = getOrderIndex(orderId);
                    if(orderStatusArray[i]==1){
                        System.out.println("\nThe order is already delivered....You can't update this order\n");
                        continue L1;
                    }else if(orderStatusArray[i]==2){
                        System.out.println("\nThe order is already cancelled....You can't update this order\n");
                        continue L1;
                    }
                }
                break;
            }while(true);

            System.out.println("\n\t\tOrderID\t\t: "+orderIdArray[i]);
            System.out.println("\t\tCustomerID\t: "+phoneArray[i]);
            System.out.println("\t\tName\t\t: "+customerNameArray[i]);
            System.out.println("\t\tQuantity\t: "+totalArray[i]/500);
            System.out.println("\t\tOrderValue\t: "+totalArray[i]);
            System.out.print("\t\tOrderStatus\t: ");
            System.out.println(orderStatusArray[i]==0?"PREPARING":orderStatusArray[i]==1?"DELIVERED":"CANCELLED");
            System.out.println();
            System.out.println("What do you want to update\n");
            System.out.println("\t(01)Quantity\n");
            System.out.println("\t(02)Status\n");
            System.out.println();
            l1: do {
                System.out.print("Enter your option -> ");
                int op = input.nextInt();
                if(op<0||op>2){
                    System.out.println("\n\t\tInvalid input!!! Try Again...\n");
                    continue l1;
                }
                clearConsole();
                switch(op){
                    case 1:quantity(i);break;
                    case 2:statusUpdate(i);break;
                }
                do {
                    System.out.print("Do you want to update another order details (Y/N) -> ");
                    char nother = input.next().charAt(0);
                    if(Character.toUpperCase(nother)!='Y' && Character.toUpperCase(nother)!='N'){
                        System.out.println("\nInvalid input!!!...Try Again\n");
                    } else if(Character.toUpperCase(nother)=='Y'){clearConsole();continue l2;}
                    else{clearConsole();return;}
                    System.out.println();
                } while (true);
            } while (true);
        }while (true);
    }
    //Status update-------------------------------------------------------------------------------------------
    private static void statusUpdate(int i) {
        Scanner input = new Scanner(System.in);

        System.out.println("\t\tStatus Update");
        System.out.println("\t\t================\n");
        System.out.println("\t\tOrderID\t\t:"+orderIdArray[i]);
        System.out.println("\t\tCustomerID\t:"+phoneArray[i]);
        System.out.println("\t\tName\t\t:"+customerNameArray[i]);
        System.out.println("\t\tQuantity\t: "+totalArray[i]/500);
        System.out.println("\n\t\t[01] - DELIVERED\n");
        System.out.println("\t\t[02] - CANCEL");
        int status;
        do{
            System.out.print("\nEnter your Status value -> ");
            status = input.nextInt();
            if(status>2 || status<0){
                System.out.println("\n\t\tInvalid input...try again\n");
                continue;
            }
            break;
        }while(true);
        orderStatusArray[i] = status;
        if(status==2){
            totalArray[i] = 0;
        }
        System.out.println("\n\t\tStatus updated successfully....\n");
        System.out.print("New order status - ");
        System.out.println(status==1?"DELIVERED\n":"CANCEL\n");
    }

    //Quantity update-------------------------------------------------------------------------------------------
    private static void quantity(int i) {
        Scanner input = new Scanner(System.in);
        System.out.println("\t\tQuantity Update");
        System.out.println("\t\t================\n");
        System.out.println("\t\tOrderID\t\t: "+orderIdArray[i]);
        System.out.println("\t\tCustomerID\t: "+phoneArray[i]);
        System.out.println("\t\tName\t\t: "+customerNameArray[i]);
        System.out.println("\t\tQuantity\t: "+totalArray[i]/500);
        System.out.print("\nEnter your quantity update value -> ");
        int qty = input.nextInt();
        totalArray[i] = qty*500;
        System.out.println("\n\t\t Updated order quantity successfully....\n");
        System.out.println("\t\tNew order quantity\t "+qty);
        System.out.println("\t\tNew order Value\t\t "+totalArray[i]);
    }

    //View Orders/////////////////////////////////////////////////////////////////////////////////////////////
    private static void viewOrders() {
        Scanner input = new Scanner(System.in);
        l1:do {
            System.out.println(" --------------------------------------------------------------------------------");
            System.out.println("|                       VIEW ORDER DETAILS                                       |");
            System.out.println(" --------------------------------------------------------------------------------\n");
            System.out.println("\t\t\t[1] Delivered Orders\n");
            System.out.println("\t\t\t[2] Preparing Orders\n");
            System.out.println("\t\t\t[3] Cancelled Orders\n");
            System.out.println("\t\t\t[4] Exit\n");
            L1:do {
                System.out.print("Enter an option to continue > ");
                if(!input.hasNextInt()){
                    input.next();
                    System.out.println("\n\t\tInvalid input...Try again\n");
                    continue L1;
                }
                int option = input.nextInt();
                if(option<0 || option >4){
                    L2:do{
                        System.out.print("\n\tInvalid input!!! Do you want to enter again(Y/N) -> ");
                        char op = input.next().charAt(0);
                        if(Character.toUpperCase(op)!='Y' && Character.toUpperCase(op)!='N'){continue L2;}
                        else if(Character.toUpperCase(op)=='Y'){clearConsole();continue l1;}
                        else{clearConsole();return;}
                    }while(true);
                }else{
                    clearConsole();
                    switch (option){
                        case 1:deliverdOrders();break;
                        case 2:preparingOrders();break;
                        case 3:canceledOrders();break;
                        case 4:break l1;
                    }
                }
                break;
            } while (true);
        } while (true);
    }
    //Cancelled Orders---------------------------------------------------------------------------------------------------
    private static void canceledOrders() {
        Scanner input = new Scanner(System.in);

        l1:do {
            System.out.println("---------------------------------------------------------------------------------");
            System.out.println("|                         CANCELED ORDERS                                       |");
            System.out.println("---------------------------------------------------------------------------------\n");
            System.out.println("---------------------------------------------------------------------------------");
            System.out.printf("%15s %15s %10s %15s %15s\n","OrderID","CustomerID","Name","Quantity","OrderValue");
            System.out.println("---------------------------------------------------------------------------------");
            for(int i=0;i<orderStatusArray.length;i++){
                if(orderStatusArray[i] == 2) {
                    System.out.printf("%15s %15s %10s %15d %15.2f\n", orderIdArray[i], phoneArray[i], customerNameArray[i], (int) totalArray[i] / 500, totalArray[i]);
                    System.out.println("---------------------------------------------------------------------------------");
                }

            }
            char op;
            do {
                System.out.print("Do you want to go back (Y/N) -> ");
                op = input.next().charAt(0);
                if(Character.toUpperCase(op)!='Y' && Character.toUpperCase(op)!='N'){
                    System.out.println("\n\t\tInvalid input !!! TryAgain...\n");
                } else if (Character.toUpperCase(op)=='Y') {clearConsole();return;}
                else{clearConsole();continue l1;}
            } while (true);
        } while (true);
    }
    //Preparing Orders---------------------------------------------------------------------------------------------------
    private static void preparingOrders() {
        Scanner input = new Scanner(System.in);

        l1:do {
            System.out.println("---------------------------------------------------------------------------------");
            System.out.println("|                         PREPARING ORDERS                                       |");
            System.out.println("---------------------------------------------------------------------------------\n");
            System.out.println("---------------------------------------------------------------------------------");
            System.out.printf("%15s %15s %10s %15s %15s\n","OrderID","CustomerID","Name","Quantity","OrderValue");
            System.out.println("---------------------------------------------------------------------------------");
            for(int i=0;i<orderStatusArray.length;i++){
                if(orderStatusArray[i]==0){
                    System.out.printf("%15s %15s %10s %15d %15.2f\n",orderIdArray[i],phoneArray[i],customerNameArray[i],(int)totalArray[i]/500,totalArray[i]);
                    System.out.println("---------------------------------------------------------------------------------");
                }
            }
            char op;
            do {
                System.out.print("Do you want to go home page (Y/N) -> ");
                op = input.next().charAt(0);
                if(Character.toUpperCase(op)!='Y' && Character.toUpperCase(op)!='N'){
                    System.out.println("\n\t\tInvalid input !!! TryAgain...\n");
                } else if (Character.toUpperCase(op)=='Y') {clearConsole();return;}
                else{clearConsole();continue l1;}
            } while (true);
        } while (true);
    }
    //Delivered order details-----------------------------------------------------------------------------------------
    private static void deliverdOrders() {
        Scanner input = new Scanner(System.in);

        l1:do {
            System.out.println("---------------------------------------------------------------------------------");
            System.out.println("|                         DELIVERED ORDERS                                       |");
            System.out.println("---------------------------------------------------------------------------------\n");
            System.out.println("---------------------------------------------------------------------------------");
            System.out.printf("%15s %15s %10s %15s %15s\n","OrderID","CustomerID","Name","Quantity","OrderValue");
            System.out.println("---------------------------------------------------------------------------------");
            for(int i=0;i<orderStatusArray.length;i++){
                if(orderStatusArray[i]==1){
                    System.out.printf("%15s %15s %10s %15d %15.2f\n",orderIdArray[i],phoneArray[i],customerNameArray[i],(int)totalArray[i]/500,totalArray[i]);
                    System.out.println("---------------------------------------------------------------------------------");
                }
            }
            char op;
            do {
                System.out.print("Do you want to go home page (Y/N) -> ");
                op = input.next().charAt(0);
                if(Character.toUpperCase(op)!='Y' && Character.toUpperCase(op)!='N'){
                    System.out.println("\n\t\tInvalid input !!! TryAgain...\n");
                } else if (Character.toUpperCase(op)=='Y') {clearConsole();return;}
                else{clearConsole();continue l1;}
            } while (true);
        } while (true);
    }

    //Search Customer///////////////////////////////////////////////////////////////////////////////////////////////////
    private static void searchCustomer() {
        Scanner input = new Scanner(System.in);

        L1:do{
            System.out.println(" --------------------------------------------------------------------------------");
            System.out.println("|                       SEARCH CUSTOMER DETAILS                                  |");
            System.out.println(" --------------------------------------------------------------------------------");
            String phone;
            L2:do{
                System.out.print("\nEnter customer Id -> ");
                phone = input.next();
                if(phone.equals("-1")){
                    clearConsole();return;
                }else if(phone.charAt(0)!='0' || phone.length() !=10){
                    do{
                        System.out.print("\n\t\tInvalid input...Do you want to enter again(Y/N) -> ");
                        char op = input.next().charAt(0);
                        if(Character.toUpperCase(op)!='Y' && Character.toUpperCase(op)!='N'){
                            System.out.println("\nInvalid input !!! ...Try again\n");
                        }else if(Character.toUpperCase(op)=='Y'){
                            clearConsole();continue L1;
                        }else{clearConsole();return;}
                    }while(true);
                }
                break;
            }while(true);
            if(!searchPhone(phone)){
                System.out.println("\n\t\t This customer ID is not added yet....\n");
                do {
                    System.out.print("Do you want to search details of a another customer(Y/N) -> ");
                    char op = input.next().charAt(0);
                    if(Character.toUpperCase(op)!='Y' && Character.toUpperCase(op)!='N'){
                        System.out.println("\nInvalid input !!! ...Try again\n");
                    }else if(Character.toUpperCase(op)=='Y' ){clearConsole();continue L1;}
                    else{clearConsole();return;}
                } while (true);
            }else{
                int i = getPhoneIndex(phone);
                System.out.println("Customer ID\t - "+phoneArray[i]);
                System.out.println("Name\t\t- "+customerNameArray[i]);
                System.out.println();
                System.out.println("Customer Order Details");
                System.out.println("======================");
                System.out.println("");
                System.out.println("-----------------------------------------------------");
                System.out.printf("%-10s %-20s %-15s\n","OrderID","Order_Quantity","Total_value");
                System.out.println("-----------------------------------------------------");
                for(int j=0;j<phoneArray.length;j++){
                    if(phoneArray[j].equals(phone)){
                        getDetails(j);
                    }
                }
                System.out.println("-----------------------------------------------------");
                char op;
                do {
                    System.out.print("Do you want to search another customer details (Y/N) -> ");
                    op = input.next().charAt(0);
                    System.out.println();
                    if(Character.toUpperCase(op)!='Y' && Character.toUpperCase(op)!='N'){
                        System.out.println("\n\t\tInvalid input !!! Try Again...\n");
                    } else if (Character.toUpperCase(op)=='Y') {clearConsole();continue L1;}
                    else{clearConsole();return;}
                } while (true);
            }
        }while (true);
    }
    //Printing details-------------------------------------------------------------------------------------
    private static void getDetails(int j) {
        System.out.printf("%-10s %-20d %-15f\n",orderIdArray[j],(int)totalArray[j]/500,totalArray[j]);
    }

    //Search Order Details//////////////////////////////////////////////////////////////////////////////////////////////
    private static void searchOrder() {
        Scanner input = new Scanner(System.in);

        L1:do {
            System.out.println(" --------------------------------------------------------------------------------");
            System.out.println("|                       SEARCH ORDER DETAILS                                     |");
            System.out.println(" --------------------------------------------------------------------------------\n");
            System.out.print("Enter order Id -> ");
            String orderId = input.next();
            if(!searchOrderId(orderId)){
                do {
                    System.out.print("\n\t\tInvalid Order ID.Do you want to enter again?(Y/N) ->");
                    char op = input.next().charAt(0);
                    System.out.println();
                    if(Character.toUpperCase(op)!='Y' && Character.toUpperCase(op)!='N'){
                        System.out.println("\n\t\tInvalid input !!! ...Try again\n");
                    }else if (Character.toUpperCase(op)=='Y'){clearConsole();continue L1;}
                    else{clearConsole();return;}
                } while (true);
            }else{
                int i = getOrderIndex(orderId);
                System.out.println("--------------------------------------------------------------------------------------");
                System.out.printf("|%-10s %-15s %-10s %-13s %-17s %-15s|\n","OrderID","CustomerID","Name","Quantitiy","OrderValue","OrderStatus");
                System.out.println("--------------------------------------------------------------------------------------");
                System.out.printf("|%-10s %-15s %-10s %-13d %-17f",orderIdArray[i],phoneArray[i],customerNameArray[i],(int)totalArray[i]/500,totalArray[i]);
                System.out.println(orderStatusArray[i]==0?"PREPARING    |":orderStatusArray[i]==1?"DELIVERED    |":"CANCELLED    |");
                System.out.println("--------------------------------------------------------------------------------------");
                do {
                    System.out.print("Do you want to search another order detail (Y/N) -> ");
                    char another = input.next().charAt(0);
                    if(Character.toUpperCase(another)!='Y' && Character.toUpperCase(another)!='N'){
                        System.out.println("\n\t\tInvalid input !!! ...Try again\n");
                    }else if (Character.toUpperCase(another)=='Y'){clearConsole();continue L1;}
                    else{clearConsole();return;}
                } while (true);
            }
        } while (true);
    }
    //Get Order Index------------------------------------------------------------------------------
    private static int getOrderIndex(String orderId) {
        for(int i=0;i<orderIdArray.length;i++){
            if(orderId.equals(orderIdArray[i])){return i;}
        }
        return -1;
    }
    //search order ID----------------------------------------------------------------------------------
    private static boolean searchOrderId(String orderId) {
        for(int i=0;i<orderIdArray.length;i++){
            if(orderId.equals(orderIdArray[i])){return true;}
        }
        return false;
    }



    //BestCustomer//////////////////////////////////////////////////////////////////////////////////////////////
    private static void bestCustomer() {
        Scanner input = new Scanner(System.in);

        L1: do {
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("|                             BEST Customer                                    |");
            System.out.println("--------------------------------------------------------------------------------\n");
            copyArrays();
            removeDuplicates();
            sortArraysByTotal();

            System.out.println("-----------------------------------------------------------");
            System.out.printf("%-15s %-20s %s\n", "CustomerID", "Name", "Total");
            System.out.println("-----------------------------------------------------------");
            for(int i=0;i<totalArraySorted.length;i++){
                System.out.printf("%-15s %-20s %.2f\n",phoneArraySorted[i],customerNameArraySorted[i],totalArraySorted[i]);
                System.out.println("-----------------------------------------------------------");
            }
            char op;
            do {
                System.out.print("\t\t\t Do you want to go back to main menu? (Y/N) > ");
                op = input.next().charAt(0);
                if(Character.toUpperCase(op)!='Y' && Character.toUpperCase(op)!='N'){
                    System.out.println("\n\t\tInvalid input !!! Try Again...\n");
                } else if (Character.toUpperCase(op)=='Y') {clearConsole();return;}
                else {clearConsole();continue L1;}
            } while (true);
        } while (true);
    }

    private static void removeDuplicates() {
        for(int i =0;i<phoneArraySorted.length;i++){
            for(int j=i+1;j<phoneArraySorted.length;j++){
                if(phoneArraySorted[i].equals(phoneArraySorted[j])){
                    totalArraySorted[i] += totalArraySorted[j];
                    removeData(j--);
                }
            }
        }
    }
    private static void removeData(int index) {
        int size = phoneArraySorted.length-1;
        String[] tempPhone = new String[size];
        String[] tempOrderId = new String[size];
        String[] tempCusName = new String[size];
        int[] tempOrderStatus = new int[size];
        double[] tempTotal = new double[size];

        for (int i = index; i < phoneArraySorted.length-1; i++) {
            phoneArraySorted[i] = phoneArraySorted[i+1];
            orderIdArraySorted[i] = orderIdArraySorted[i+1];
            customerNameArraySorted[i] = customerNameArraySorted[i+1];
            orderStatusArraySorted[i] = orderStatusArraySorted[i+1];
            totalArraySorted[i] = totalArraySorted[i+1];
        }
        for (int i = 0; i < size; i++) {
            tempPhone[i] =phoneArraySorted[i];
            tempOrderId[i] =orderIdArraySorted[i];
            tempCusName[i] =customerNameArraySorted[i];
            tempOrderStatus[i] =orderStatusArraySorted[i];
            tempTotal[i] = totalArraySorted[i];
        }
        phoneArraySorted = tempPhone;
        orderIdArraySorted = tempOrderId;
        customerNameArraySorted = tempCusName;
        orderStatusArraySorted = tempOrderStatus;
        totalArraySorted = tempTotal;
    }



    //Coping data from Original arrays to sorted arrays---------------------------------------------------------------------------------
    private static void copyArrays() {
        int size = phoneArray.length;
        String[] tempPhoneSorted = new String[size];
        String[] tempCustomerNameArraySorted = new String[size];
        String[] tempOrderIdArraySorted = new String[size];
        int[] tempOrderStatusSorted = new int[size];
        double[] tempTotalArraySorted = new double[size];

        for (int i=0;i<size;i++){
            tempPhoneSorted[i] = phoneArray[i];
            tempCustomerNameArraySorted[i] = customerNameArray[i];
            tempOrderIdArraySorted[i] = orderIdArray[i];
            tempOrderStatusSorted[i] = orderStatusArray[i];
            tempTotalArraySorted[i] = totalArray[i];
        }

        phoneArraySorted = tempPhoneSorted;
        customerNameArraySorted = tempCustomerNameArraySorted;
        orderIdArraySorted = tempOrderIdArraySorted;
        orderStatusArraySorted = tempOrderStatusSorted;
        totalArraySorted = tempTotalArraySorted;
    }

    //Sorting by total-------------------------------------------------------------------------
    private static void sortArraysByTotal() {
        for(int i=0;i<totalArraySorted.length-1;i++){
            for(int j=0;j<totalArraySorted.length-1;j++ ){
                if(totalArraySorted[j]<totalArraySorted[j+1]) {
                    double tempTotal = totalArraySorted[j+1];
                    totalArraySorted[j+1] = totalArraySorted[j];
                    totalArraySorted[j] = tempTotal;

                    String tempOrderId = orderIdArraySorted[j+1];
                    orderIdArraySorted[j+1] = orderIdArraySorted[j];
                    orderIdArraySorted[j] = tempOrderId;

                    String tempCusName = customerNameArraySorted[j+1];
                    customerNameArraySorted[j+1] = customerNameArraySorted[j];
                    customerNameArraySorted[j] = tempCusName;

                    String tempPhone = phoneArraySorted[j+1];
                    phoneArraySorted[j+1] = phoneArraySorted[j];
                    phoneArraySorted[j] = tempPhone;

                    int tempStatus = orderStatusArraySorted[j+1];
                    orderStatusArraySorted[j+1] = orderStatusArraySorted[j];
                    orderStatusArraySorted[j] = tempStatus;
                }
            }
        }

    }

    //Place Order//////////////////////////////////////////////////////////////////////////////////////////////////
    private static void placeOrder() {
        Scanner input = new Scanner(System.in);

        L1: do {
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("|                               PLACE ORDER                                    |");
            System.out.println("--------------------------------------------------------------------------------\n");

            String orderId=getOrderId();
            System.out.println("ORDER ID - "+orderId);
            System.out.println("=================");

            String phone = getPhone();
            String name;
            char option;

            if (searchPhone(phone)){
                int i = getPhoneIndex(phone);
                printDetails(i);
                name = customerNameArray[getPhoneIndex(phone)];
            }else{
                System.out.print("\nCustomer Name\t\t-> ");
                name = input.next();
            }
            int qty = getBurgerQty();
            double price = calculatePrice(qty);

            System.out.println("\nTotal value\t\t : "+price);

            do {
                System.out.print("\nDo you want to place the order(Y/N) -> ");
                option = input.next().charAt(0);
                if(Character.toUpperCase(option)!='N' && Character.toUpperCase(option)!='Y'){
                    System.out.print("\n\t\tInvalid option...try again\n");
                    continue;
                }
                break;
            }while(true);

            if(Character.toUpperCase(option)=='Y'){
                extendArrays();
                orderIdArray[orderIdArray.length-1] = orderId;
                phoneArray[phoneArray.length-1] = phone;
                customerNameArray[customerNameArray.length-1] = name;
                totalArray[totalArray.length-1] = price;
                orderStatusArray[orderStatusArray.length-1] = PREPARING;
                System.out.println("\n\t\t\t Your order is entered to the system successfully...\n\n");
            }else{
                orderNumber--;
                clearConsole();
                break L1;
            }
            char nother;
            do {
                System.out.print("Do you want to place another order(Y/N) -> ");
                nother = input.next().charAt(0);
                if(Character.toUpperCase(nother)!='N' && Character.toUpperCase(nother)!='Y'){
                    System.out.print("\n\t\tInvalid option...try again\n");
                    continue;
                }
                break;
            }while(true);
            if(Character.toUpperCase(nother)=='Y'){
                System.out.println();
                clearConsole();
                continue L1;
            }
            clearConsole();
            break;
        } while (true);
    }
    //Calculate Price--------------------------------------------------------------------
    private static double calculatePrice(int qty) {
        return qty*burgerPrice;
    }
    //Get burger quantity------------------------------------------------------------------
    private static int getBurgerQty() {
        Scanner input = new Scanner(System.in);
        int qty = 0;
        do {
            System.out.print("\nEnter burger quantity\t-> ");
            if(!input.hasNextInt()){
                input.next();
                System.out.println("\n\tInvalid input....Try again");
                continue;
            }
            qty = input.nextInt();
            if(qty<0){
                System.out.println("\n\tInvalid input  for burger quantity...Try again\n");
                continue;
            }
            break;
        }while(true);
        return qty;
    }
    //PrintDetails----------------------------------------------
    private static void printDetails(int index) {
        if(index!=-1)System.out.println(customerNameArray[index]);
    }
    //Get Index------------------------------------------------
    private static int getPhoneIndex(String phone) {
        for (int i=0;i<phoneArray.length;i++){
            if(phone.equals(phoneArray[i])){return i;}
        }
        return -1;
    }
    //Extend Arrays-------------------------------------------
    private static void extendArrays() {
        String[] tempPhone = new String[phoneArray.length+1];
        String[] tempCustomerNameArray = new String[customerNameArray.length+1];
        String[] tempOrderIdArray = new String[orderIdArray.length+1];
        int[] tempOrderStatus = new int[orderStatusArray.length+1];
        double[] tempTotalArray = new double[totalArray.length+1];

        for (int i=0;i<phoneArray.length;i++){
            tempPhone[i] = phoneArray[i];
            tempCustomerNameArray[i] = customerNameArray[i];
            tempOrderStatus[i] = orderStatusArray[i];
            tempTotalArray[i] = totalArray[i];
        }

        phoneArray = tempPhone;
        customerNameArray = tempCustomerNameArray;
        orderIdArray = tempOrderIdArray;
        orderStatusArray = tempOrderStatus;
        totalArray = tempTotalArray;
    }
    //Get Main option-----------------------------------------------------
    private static int getOption() {
        Scanner input = new Scanner(System.in);
        int op;
        do {
            System.out.print("Enter an option to continue -> ");
            if(!input.hasNextInt()){
                System.out.println("\n\t\tInvalid input...Try again\n");
                input.next();
                continue;
            }
            op = input.nextInt();
            if(op<0 || op>7){
                System.out.print("\n\t\tInvalid Option !!!  try again......\n");
                continue;
            }
            break;
        }while(true);
        return op;
    }
    //Search Phone number----------------------------------------------
    private static boolean searchPhone(String phone) {
        for (int i=0;i<phoneArray.length;i++){
            if(phone.equals(phoneArray[i])){return true;}
        }
        return false;
    }
    //Input Phone Number-----------------------------------------------------
    private static String getPhone() {
        Scanner input =  new Scanner(System.in);
        String phone;
        do {
            System.out.print("\nEnter the phone number  -> ");
            phone = input.next();
            if(!validPhone(phone)){
                System.out.print("\n\tInvalid input fo phone number !!! Enter again...\n");
                continue;
            }
            break;
        } while (true);
        return phone;
    }
    //Validate phone Number------------------------------------------------------------
    private static boolean validPhone(String phone) {
        if(phone.charAt(0)!='0'){return false;}
        else if(phone.length()!=10){return false;}
        return true;
    }

    // Order ID-----------------------------------------------------------------
    private static String getOrderId() {
        return String.format("B%04d", ++orderNumber);
    }
    public static void exit(){
        clearConsole();
        System.out.println("\n\t\tYou left the program...\n");
        System.exit(0);
    }
    public final static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            e.printStackTrace();
// Handle any exceptions.
        }
    }
}
