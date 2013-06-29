package net.sporix.scurrencyconverter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class MainActivitySCurrency extends Activity {

private static final int SPLASH_DISPLAY_TIME = 4500; /* 3 seconds */

public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
    
    
    setContentView(R.layout.splash);

    new Handler().postDelayed(new Runnable() {

        public void run() {

            Intent mainIntent = new Intent(MainActivitySCurrency.this,
                    MainActivityCurrency.class);
            MainActivitySCurrency.this.finish();
            MainActivitySCurrency.this.startActivity(mainIntent);

           
          //  overridePendingTransition(R.anim.mainfadein,
	                    //R.anim.splashfadeout);
	        }
	    }, SPLASH_DISPLAY_TIME);
	}


	@Override
	public void onBackPressed() 
	{
		
	}
}