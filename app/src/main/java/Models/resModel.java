package Models;

public class resModel {
    String imageUri,phone,name,pincode;

    public resModel() {
    }

    public resModel(String imageUri, String phone, String name, String pincode) {
        this.imageUri = imageUri;
        this.phone = phone;
        this.name = name;
        this.pincode = pincode;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
