package com.example.pro2.models;

public class ProductData {
    private String productName; // שם המוצר
    private int quantity;       // כמות
    private boolean isChecked;  // האם המוצר מסומן

    // בנאי
    public ProductData(String productName, int quantity, boolean isChecked) {
        this.productName = productName;
        this.quantity = quantity;
        this.isChecked = isChecked;
    }
    public ProductData() {
    }
    // גטרים וסטרים
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}

