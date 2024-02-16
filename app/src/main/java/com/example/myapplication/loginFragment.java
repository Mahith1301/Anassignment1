package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.temporal.TemporalAccessor;


public class loginFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        Button login = view.findViewById(R.id.login);
        Button signup = view.findViewById(R.id.signup);
        EditText uname1 = view.findViewById(R.id.editTextmail);
        EditText password1 = view.findViewById(R.id.editTextPassword);


        login.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("Range")
            @Override
            public void onClick(View view) {
                String uname = uname1.getText().toString();
                String password = password1.getText().toString();

//                String cId = id.getText().toString();
//                String cPassword = password.getText().toString();

                if (uname.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
                } else {

                    String select = "id =? AND password=?";
                    String args[] = {uname, password};

                    Cursor cursor = getActivity().getContentResolver().query(menuorder.CONTENT_URI,
                            null, select, args, null);

                    if (cursor.moveToFirst()) {
                        String i, p;
                        while (!cursor.isAfterLast()) {
                            i = cursor.getString(cursor.getColumnIndex(menuorder.id));
                            p = cursor.getString(cursor.getColumnIndex(menuorder.password));
                            if (uname.equals(i) && password.equals(p)) {
                                replaceFragment(new menuFragment());
                                Toast.makeText(getActivity().getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        }
                    } else {
                        android.widget.Toast.makeText(getActivity().getApplicationContext(), "Invalid login Details", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uname2 = uname1.getText().toString();
                String password2 = password1.getText().toString();

                if (uname2.isEmpty() || password2.isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
                } else {

                    ContentValues values = new ContentValues();

                    // fetching text from user
                    values.put(menuorder.id, uname2);
                    values.put(menuorder.password, password2);

                    // inserting into database through content URI
                    getActivity().getContentResolver().insert(menuorder.CONTENT_URI, values);

                    // displaying a toast message
                    android.widget.Toast.makeText(getActivity().getApplicationContext(), "New Record Inserted", Toast.LENGTH_SHORT).show();
                }

                uname1.setText("");
                password1.setText("");

            }
        });


        return view;
    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FrameLayout, fragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }




}

