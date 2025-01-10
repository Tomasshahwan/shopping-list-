package com.example.pro2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pro2.R;
import com.example.pro2.models.ProductData;

import java.util.ArrayList;

// Custom Adapter to display product data in RecyclerView
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<ProductData> productList; // List of product data

    // Constructor to initialize the adapter with the product list
    public CustomAdapter(ArrayList<ProductData> productList) {
        this.productList = productList != null ? productList : new ArrayList<>();
    }

    // ViewHolder class to hold the views for each item
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView productName;
        EditText quantity;

        public MyViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            productName = itemView.findViewById(R.id.productName);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }

    // Inflate the layout for each item in the RecyclerView
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false); // Ensure the correct XML layout file
        return new MyViewHolder(view);
    }

    // Bind the product data to the views in each item
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductData product = productList.get(position);
        holder.productName.setText(product.getProductName());
        holder.quantity.setText(String.valueOf(product.getQuantity()));
        holder.checkBox.setChecked(product.isChecked());

        // Listener for checkbox changes
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> product.setChecked(isChecked));

        // Listener for quantity changes
        holder.quantity.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                try {
                    int updatedQuantity = Integer.parseInt(holder.quantity.getText().toString());
                    product.setQuantity(updatedQuantity);
                } catch (NumberFormatException e) {
                    holder.quantity.setText(String.valueOf(product.getQuantity())); // Reset invalid input
                }
            }
        });
    }

    // Return the size of the product list
    @Override
    public int getItemCount() {
        return productList.size();
    }

    // Method to add a new product to the list
    public void addProduct(ProductData newProduct) {
        if (newProduct != null) {
            productList.add(newProduct);
            notifyItemInserted(productList.size() - 1); // Notify RecyclerView to add a new item
        }
    }

    // Method to remove a product from the list by position
    public void removeProduct(int position) {
        if (position >= 0 && position < productList.size()) {
            productList.remove(position);
            notifyItemRemoved(position);
        }
    }

    // Method to update the adapter with a new product list
    public void updateProductList(ArrayList<ProductData> newProductList) {
        this.productList = newProductList != null ? newProductList : new ArrayList<>();
        notifyDataSetChanged();
    }
}
