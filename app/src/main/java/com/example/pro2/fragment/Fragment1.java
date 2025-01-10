package com.example.pro2.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.pro2.activitys.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pro2.R;
import com.google.android.material.navigation.NavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;






    public Fragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment1 newInstance(String param1, String param2) {
        Fragment1 fragment = new Fragment1();
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
        View v = inflater.inflate(R.layout.fragment_1, container, false);

        // מציאת רכיבי ממשק
        EditText thispass = v.findViewById(R.id.passin);
        EditText thisemail = v.findViewById(R.id.emailin);
        Button thisbutton = v.findViewById(R.id.loginbutton);
        Button button2 = v.findViewById(R.id.registerbutton); // כפתור הרישום

        // מאזין ללחיצה על כפתור הכניסה
        thisbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = thispass.getText().toString().trim();
                String email = thisemail.getText().toString().trim();

                // בדיקת שדות ריקים
                if (email.isEmpty()) {
                    Toast.makeText(getContext(), "Email field cannot be empty.", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(getContext(), "Password field cannot be empty.", Toast.LENGTH_SHORT).show();
                } else {
                    // אם הכל תקין, ביצוע כניסה
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.login(new MainActivity.LoginCallback() {
                        @Override
                        public void onLoginResult(boolean success) {
                            if (success) {
                                Navigation.findNavController(v).navigate(R.id.action_fragment1_to_fragment2);
                            } else {
                                Toast.makeText(getContext(), "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        // מאזין ללחיצה על כפתור הרישום
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_fragment1_to_fragment3);
            }
        });

        return v;
    }
    }
