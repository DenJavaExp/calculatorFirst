package com.example.calc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.radiobutton.MaterialRadioButton;

public class MainActivity extends AppCompatActivity {

    private static final String NameSharedPreference = "CALC";

    private static final String appTheme = "APP_THEME";
    private static final int MyCoolCodeStyle = 0;
    private static final int AppThemeLightCodeStyle = 1;
    private static final int AppThemeCodeStyle = 2;
    private static final int AppThemeDarkCodeStyle = 3;


    TextView resultField;
    EditText numberField;
    TextView operationField;
    Double operand = null;
    String lastOperation = "=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getAppTheme(R.style.MyCoolStyle));
        setContentView(R.layout.activity_main);
        initThemeChooser();
        resultField = findViewById(R.id.resultField);
        numberField = findViewById(R.id.numberField);
        operationField = findViewById(R.id.operationField);
    }

    private void initThemeChooser() {
        initRadioButton(findViewById(R.id.radioButtonMyCoolStyle),
                MyCoolCodeStyle);
        initRadioButton(findViewById(R.id.radioButtonMaterialDark),
                AppThemeDarkCodeStyle);
        initRadioButton(findViewById(R.id.radioButtonMaterialLight),
                AppThemeLightCodeStyle);
        initRadioButton(findViewById(R.id.radioButtonMaterialLightDarkAction),
                AppThemeCodeStyle);
        RadioGroup rg = findViewById(R.id.radioButtons);

        ((MaterialRadioButton)rg.getChildAt(getCodeStyle
                (MyCoolCodeStyle))).setChecked(true);
    }

    private void initRadioButton(View button, final int codeStyle) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAppTheme(codeStyle);
                recreate();
            }
        });
    }

    private int getAppTheme(int codeStyle) {
        return codeStyleToStyleId(getCodeStyle(codeStyle));
    }

    private int getCodeStyle(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference,
                MODE_PRIVATE);
        return sharedPref.getInt(appTheme, codeStyle);
    }


    private void setAppTheme(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(appTheme, codeStyle);
        editor.apply();

    }

    private int codeStyleToStyleId(int codeStyle) {
        switch (codeStyle) {
            case AppThemeCodeStyle:
                return R.style.Theme_Calc;
            case AppThemeDarkCodeStyle:
                return R.style.AppThemeDark;
            case AppThemeLightCodeStyle:
                return R.style.AppThemeLight;
            default:
                return R.style.MyCoolStyle;
        }
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("OPERATION", lastOperation);
        if(operand!=null)
            outState.putDouble("OPERAND", operand);
        super.onSaveInstanceState(outState);
    }

    public void onNumberClick(View view){
        Button button =(Button) view;
        numberField.append((button.getText()));

        if(lastOperation.equals("=") && operand!=null){
            operand = null;
        }


    }
}