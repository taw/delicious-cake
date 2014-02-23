package com.example.myandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.os.Handler;
import java.util.Random;

public class MyAndroidAppActivity extends Activity
{
  int balance = 1350;
  int current_purchase = 0;
  
  
  private String format_currency(int amount) {
    String out = "£" + (amount/100) + ".";
    amount %= 100;
    out += (amount/10);
    amount %= 10;
    out += amount;
    return out;
  }

  private void main_layout() {
    setContentView(R.layout.main);
    
    Button payButton = (Button)findViewById(R.id.pay_button);
    payButton.setOnClickListener(new OnClickListener() {
       public void onClick(View v) {
         pay_nfc_layout();
       }
     });
     
     ((Button)findViewById(R.id.charge_button)).setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
          charge_layout();
        };
     });

     TextView guiStr = (TextView)findViewById(R.id.balance);
     guiStr.setText("Your DeliciousCake account has " + format_currency(balance) + " on it.");
  }

  private void pay_nfc_layout() {
    setContentView(R.layout.wait_for_nfc);
    Runnable mUpdateUITimerTask = new Runnable() {
      public void run() {
        current_purchase = 1 + (new Random()).nextInt(500); 
        confirmation_layout();
      }
    };
    Handler mHandler = new Handler();
    mHandler.postDelayed(mUpdateUITimerTask, 2500);
  }

  private void charge_nfc_layout() {
    setContentView(R.layout.wait_for_nfc);
    Runnable mUpdateUITimerTask = new Runnable() {
      public void run() {
        main_layout();
      }
    };
    Handler mHandler = new Handler();
    mHandler.postDelayed(mUpdateUITimerTask, 2500);
  }
  
  private void charge_layout() {
    setContentView(R.layout.charge);
    ((Button)findViewById(R.id.confirm_charge)).setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
          EditText amount_box = (EditText)findViewById(R.id.amount_charged);
          current_purchase = (int)(Float.parseFloat(amount_box.getText().toString()) * 100.0);
          balance += current_purchase;
          charge_nfc_layout();
        };
    });
    ((Button)findViewById(R.id.cancel_charge)).setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
          main_layout();
        };
    });
  }
  
  private void confirmation_layout() {
    setContentView(R.layout.confirm_purchase);
    TextView guiStr = (TextView)findViewById(R.id.please_confirm_purchase);
    guiStr.setText("Please confirm purchase for " + format_currency(current_purchase));
    
    ((Button)findViewById(R.id.confirm_payment)).setOnClickListener(new OnClickListener() {
       public void onClick(View v) {
         balance -= current_purchase;
         main_layout();
       };
    });
    ((Button)findViewById(R.id.cancel_payment)).setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
          main_layout();
        };
    });
  }

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    main_layout();
  }
}
