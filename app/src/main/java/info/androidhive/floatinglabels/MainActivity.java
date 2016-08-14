package info.androidhive.floatinglabels;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText inputBill, inputTip, inputPPL;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword;
    private Button btnSignUp;
    double billBeforeTip = 0;
    double tipPercent = 0;
    double people = 0;
    double totalBill = 0;
    double pplAmt = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputBill = (EditText) findViewById(R.id.input_bill);
        inputTip = (EditText) findViewById(R.id.input_tip);
        inputPPL = (EditText) findViewById(R.id.input_PPL);
        btnSignUp = (Button) findViewById(R.id.btn_signup);

        inputBill.addTextChangedListener(new MyTextWatcher(inputBill));
        inputTip.addTextChangedListener(new MyTextWatcher(inputTip));
        inputPPL.addTextChangedListener(new MyTextWatcher(inputPPL));

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
    }

    /**
     * Validating form
     */
    private void submitForm() {
        if (!validateName()) {
            return;
        }

        if (!validateTip()) {
            return;
        }

        if (!validatePeople()) {
            return;
        }

         billBeforeTip = 0;
     tipPercent = 0;
         people = 0;
      totalBill = 0;
       pplAmt = 1;
        TextView totalBil = (TextView)findViewById(R.id.totalBill);
        TextView billBeforeTip = (TextView)findViewById(R.id.bill_before_tip);
        TextView tipAmt = (TextView)findViewById(R.id.tip);
        TextView pplAmt = (TextView)findViewById(R.id.tip);
        TextView bpp = (TextView) findViewById(R.id.billPerPerson);
        TextView tpp = (TextView) findViewById(R.id.tipPerPerson);
        EditText bill = (EditText) findViewById(R.id.input_bill);
        bill.setText("0");
        EditText tip = (EditText) findViewById(R.id.input_tip);
        tip.setText("0");
        EditText ppl = (EditText) findViewById(R.id.input_PPL);
        ppl.setText("1");
        tpp.setText("$0");
        bpp.setText("$0");
        tipAmt.setText("$0");
        billBeforeTip.setText("$0");
        totalBil.setText("$0");
        pplAmt.setText("$0");


    }

    private boolean validateName() {




        if (inputBill.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(inputBill);

            TextView totalBil = (TextView)findViewById(R.id.totalBill);
            TextView billBeforeTip = (TextView)findViewById(R.id.bill_before_tip);
            billBeforeTip.setText("$0");
            totalBil.setText("$0");
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
            TextView totalToPay = (TextView)findViewById(R.id.totalBill);
            TextView preTipBill = (TextView)findViewById(R.id.bill_before_tip);
            billBeforeTip = Double.parseDouble(inputBill.getText().toString());
            totalBill = Double.parseDouble(inputBill.getText().toString());
            totalBill = totalBill + (totalBill * (tipPercent/100));
            totalToPay.setText("$"+ totalBill);
            preTipBill.setText("$"+ billBeforeTip);
        }

        return true;
    }

    private boolean validateTip() {
        String tip = inputTip.getText().toString().trim();

        if (tip.isEmpty()) {
            inputLayoutEmail.setError(getString(R.string.err_msg_tip));
            requestFocus(inputTip);
            TextView tipAmt = (TextView)findViewById(R.id.tip);
            tipAmt.setText("$0");
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
            tipPercent = Double.parseDouble(inputTip.getText().toString());
            TextView tipAmt = (TextView)findViewById(R.id.tip);
            double value = (totalBill * (tipPercent/100));
            value = Double.parseDouble(new DecimalFormat("##.####").format(value));
            tipAmt.setText("$"+ value);

        }

        return true;
    }

    private boolean validatePeople() {
        if (inputPPL.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPPL);
            TextView pplAmt = (TextView)findViewById(R.id.tip);
            pplAmt.setText("1");
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
            if (!(inputBill.getText().toString().trim().isEmpty())) {
                 pplAmt = Double.parseDouble(inputPPL.getText().toString());
                TextView tpp = (TextView) findViewById(R.id.tipPerPerson);
                double value = ((totalBill * (tipPercent / 100) / pplAmt));
                value =Double.parseDouble(new DecimalFormat("##.####").format(value));
                tpp.setText("$" + value);
                double value1 = (totalBill / pplAmt);
                value1 =Double.parseDouble(new DecimalFormat("##.####").format(value1));
                TextView bpp = (TextView) findViewById(R.id.billPerPerson);
                bpp.setText("$" + value1);

            }
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {

            String tip = inputTip.getText().toString().trim();



            if (!(tip.isEmpty())) {

                inputLayoutEmail.setErrorEnabled(false);
                tipPercent = Double.parseDouble(inputTip.getText().toString());
                TextView tipAmt = (TextView) findViewById(R.id.tip);
                double value = (totalBill * (tipPercent / 100));
                value = Double.parseDouble(new DecimalFormat("##.####").format(value));
                tipAmt.setText("$" + value);
            }


            if(inputBill.getText().toString().trim().startsWith(".")){
                EditText bill = (EditText) findViewById(R.id.input_bill);
                bill.setText("1");
            }

            if(inputTip.getText().toString().trim().startsWith(".")){
                EditText email = (EditText) findViewById(R.id.input_tip);
                email.setText("0");
            }

            if(inputPPL.getText().toString().trim().startsWith(".") || inputPPL.getText().toString().trim().startsWith("0") ){
                EditText ppl = (EditText) findViewById(R.id.input_PPL);
                ppl.setText("1");
            }




            if (inputTip.getText().toString().trim().contains(".")){

                inputLayoutEmail.setError(getString(R.string.err_msg_tip));
            }

            if (tip.isEmpty()) {
                inputLayoutEmail.setError(getString(R.string.err_msg_tip));

                TextView tipAmt = (TextView)findViewById(R.id.tip);
                tipAmt.setText("$0");

            } else {
                inputLayoutEmail.setErrorEnabled(false);
                tipPercent = Double.parseDouble(inputTip.getText().toString());
                TextView tipAmt = (TextView)findViewById(R.id.tip);

                tipAmt.setText("$"+ ( (totalBill * (tipPercent/100))));

            }

            if (!(inputBill.getText().toString().trim().isEmpty())) {
                TextView totalToPay = (TextView) findViewById(R.id.totalBill);
                TextView preTipBill = (TextView) findViewById(R.id.bill_before_tip);
                billBeforeTip = Double.parseDouble(inputBill.getText().toString());
                totalBill = Double.parseDouble(inputBill.getText().toString());
                totalBill = totalBill + (totalBill * (tipPercent / 100));
                double value = totalBill;
                value =Double.parseDouble(new DecimalFormat("##.####").format(value));
                double value1 = (totalBill / pplAmt);
                value1 =Double.parseDouble(new DecimalFormat("##.####").format(value1));
                totalToPay.setText("$" + value);
                preTipBill.setText("$" + billBeforeTip);
                TextView bpp = (TextView) findViewById(R.id.billPerPerson);
                bpp.setText("$" + value1);
            }

            switch (view.getId()) {
                case R.id.input_bill:
                    validateName();
                    break;
                case R.id.input_tip:
                    validateTip();
                    break;
                case R.id.input_PPL:
                    validatePeople();
                    break;
            }
        }
    }
}
