package Models;

public class trackModel {
    String price,quantity,status,address,order_id,title,order_date;
    public trackModel(){
    }

    public trackModel(String price, String quantity, String status, String address, String order_id, String title, String order_date) {
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.address = address;
        this.order_id = order_id;
        this.title = title;
        this.order_date = order_date;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
