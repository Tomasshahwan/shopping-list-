package com.example.pro2.fragment;

import static com.example.pro2.fragment.Fragment3.ARG_PARAM1;
import static com.example.pro2.fragment.Fragment3.ARG_PARAM2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pro2.R;
import com.example.pro2.activitys.MainActivity;
import com.example.pro2.adapters.CustomAdapter;
import com.example.pro2.models.ProductData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fragment2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText productNameEditText, quantityEditText;
    private Button addButton, exitButton, deleteButton;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private ArrayList<ProductData> productList;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    public Fragment2() {
        // Required empty public constructor
    }

    public static Fragment2 newInstance(String param1, String param2) {
        Fragment2 fragment = new Fragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_2, container, false);

        // Initialize views
        productNameEditText = v.findViewById(R.id.productName);
        quantityEditText = v.findViewById(R.id.quantity);
        addButton = v.findViewById(R.id.addButton);
        deleteButton = v.findViewById(R.id.button);
        recyclerView = v.findViewById(R.id.rvcon);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference("shoppingList").child(currentUser.getUid());
            retrieveShoppingList();
        }
        // Initialize product list and adapter
        productList = new ArrayList<>();
        adapter = new CustomAdapter(productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Add button functionality
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productName = productNameEditText.getText().toString().trim();
                String quantityString = quantityEditText.getText().toString().trim();

                if (!productName.isEmpty() && !quantityString.isEmpty()) {
                    try {
                        int quantity = Integer.parseInt(quantityString);
                        ProductData newProduct = new ProductData(productName, quantity, false);
                        adapter.addProduct(newProduct);

                        // Clear input fields
                        productNameEditText.setText("");
                        quantityEditText.setText("");
                    } catch (NumberFormatException e) {
                        Toast.makeText(getContext(), "Invalid quantity", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        

        // Delete everything functionality
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productList.clear();
                adapter.updateProductList(productList);
                Toast.makeText(getContext(), "All products deleted", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    private void retrieveShoppingList() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ProductData item = snapshot.getValue(ProductData.class);
                    productList.add(item);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Failed to load shopping list.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onPause() {
        super.onPause();
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.saveShoppingListToDatabase(productList, databaseReference);
        }
    }
}
