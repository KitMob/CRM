package net.brigs.crm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import net.brigs.crm.modules.Dashboard;

public class Login extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//TODO Аутентификация пользователя с возвратом уведомления об прохождения логирования (if true – intent, else false – not user)

        findViewById(R.id.accept_but).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Dashboard.class));
            }
        });
    }
}
