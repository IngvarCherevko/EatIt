package com.ingvar.androideat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ingvar.androideat.Common.Common;
import com.ingvar.androideat.model.User;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends Activity {

    EditText editPhone, editPassword;
    Button btnSingIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editPhone = (MaterialEditText) findViewById(R.id.editPhone);
        editPassword = (MaterialEditText) findViewById(R.id.editPassword);
        btnSingIn = (Button) findViewById(R.id.buttonSignIn);

        //initFirebase
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = firebaseDatabase.getReference("User");

        btnSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table_user.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child(editPhone.getText().toString()).exists()) {
                            User user = dataSnapshot.child(editPhone.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(editPassword.getText().toString())) {

                                Intent intentSignIn = new Intent(SignIn.this, Home.class);
                                Common.currentUser = user;
                                startActivity(intentSignIn);
                                finish();

                            } else {

                                Toast.makeText(SignIn.this, "Sing in failed !", Toast.LENGTH_SHORT).show();

                            }
                        }
                        else {
                            Toast.makeText(SignIn.this, "User not exist !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
