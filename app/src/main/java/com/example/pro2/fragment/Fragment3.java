package com.example.pro2.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pro2.R;
import com.example.pro2.activitys.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment3 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    static final String ARG_PARAM1 = "param1";
    static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment3.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment3 newInstance(String param1, String param2) {
        Fragment3 fragment = new Fragment3();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_3, container, false);

        // מציאת רכיבי ממשק
        EditText passwordField = v.findViewById(R.id.onepass);
        EditText confirmPasswordField = v.findViewById(R.id.twopass);
        EditText emailhere = v.findViewById(R.id.theemail);
        Button button1 = v.findViewById(R.id.registerfinish);
        Button buttun2 = v.findViewById(R.id.backbut);


        // מאזין ללחיצה על הכפתור
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = passwordField.getText().toString();
                String confirmPassword = confirmPasswordField.getText().toString();
                String email = emailhere.getText().toString();

               if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                }
                else if (password.equals(confirmPassword)) {
                   MainActivity mainActivity = (MainActivity) getActivity();
                   mainActivity.reg();
                   mainActivity.addDATA();
                   Navigation.findNavController(v).navigate(R.id.action_fragment3_to_fragment1);
                } else {
                    Toast.makeText(getContext(), "The passwords do not match. try again", Toast.LENGTH_SHORT).show();
                }
            }


        });
        buttun2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Navigation.findNavController(v).navigate(R.id.action_fragment3_to_fragment1);
            }
        });

        return v;
    }
}