package net.sporix.scurrencyconverter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;



import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class MainActivityCurrency extends Activity {
	
	private String [] arrayCurrency = {"AED", "ANG", "ARS", "AUD", "BDT", "BGN", "BHD", "BND", "BOB", "BRL",  "BWP", "CAD",
			"CHF", "CLP", "CNY", "COP", "CRC", "CZK", "DKK", "DOP", "DZD", "EEK", "EGP",
			"EUR", "FJD", "GBP", "HKD",
			"HNL", "HRK", "HUF", "IDR", "ILS", "INR", "JMD", "JOD", "JPY",
			"KES", "KRW", "KWD", "KYD", "KZT", "LBP", "LKR", "LTL",
			"LVL", "MAD", "MDL", "MKD", "MUR", "MVR", "MXN", "MYR",
			"NAD", "NGN", "NIO", "NOK", "NPR", "NZD", "OMR", "PEN", "PGK", "PHP", "PKR", "PLN", "PYG",
			"QAR", "RON", "RSD", "RUB", "SAR", "SCR", "SEK", "SGD", "SKK", "SLL", "SVC", "THB", "TND", "TRY", "TTD", "TWD", "TZS",
			"UAH", "UGX", "USD", "UYU", "UZS", "VEF", "VND", "XOF", "YER",
			"ZAR", "ZMK"};
	private Spinner spinnerFrom, spinnerTo;
	private ArrayAdapter<String> dataAdapter = null;
	private ArrayAdapter<String> dataAdapterFavourite = null;
	private EditText txtFrom;
	private TextView txtTo;
	private ImageButton btnConvert;
	private ProgressDialog dialog = null;
	private String fromName;
	private String toName;
	private String outPut;
	private ListView listCurrency;
	private List<String> listFavourite = new ArrayList<String>(); 
	private Spinner spin1;
	private Spinner spin2;
	private Spinner spin3;
	private Spinner spin4;
	private Button btnCancelSetting;
	private Button btnOkSetting;
	private ImageButton btnSetting;
	private Bundle bundle;
	private boolean isSettingPage = false;
	
	
	
	
	private int [] arrayImage = {R.drawable.aed, R.drawable.ang, R.drawable.ars, R.drawable.aud, R.drawable.bdt, R.drawable.bgn,
			R.drawable.bhd, R.drawable.bnd, R.drawable.bob, R.drawable.brl, R.drawable.bwp, R.drawable.cad,
			R.drawable.chf, R.drawable.clp, R.drawable.cny, R.drawable.cop, R.drawable.crc, R.drawable.czk, R.drawable.dkk, R.drawable.dop,
			R.drawable.dzd, R.drawable.eek, R.drawable.egp, R.drawable.eur, R.drawable.fjd, R.drawable.gbp, R.drawable.hkd,
			R.drawable.hnl, R.drawable.hrk, R.drawable.huf, R.drawable.idr, R.drawable.ils, R.drawable.inr, R.drawable.jmd, R.drawable.jod, R.drawable.jpy,
			R.drawable.kes, R.drawable.krw, R.drawable.kwd, R.drawable.kyd, R.drawable.kzt, R.drawable.lbp, R.drawable.lkr, R.drawable.ltl, R.drawable.lvl,
			R.drawable.mad, R.drawable.mdl, R.drawable.mkd, R.drawable.mur, R.drawable.mvr, R.drawable.mxn, R.drawable.myr,
			R.drawable.nad,R.drawable.ngn, R.drawable.nio, R.drawable.nok, R.drawable.npr, R.drawable.nzd, R.drawable.omr, R.drawable.pen, R.drawable.pgk, R.drawable.php, R.drawable.pkr, R.drawable.pln, R.drawable.pyg,
			R.drawable.qar, R.drawable.ron, R.drawable.rsd, R.drawable.rub, R.drawable.sar, R.drawable.scr, R.drawable.sek, R.drawable.sgd, R.drawable.skk, R.drawable.sll, R.drawable.svc, R.drawable.thb, R.drawable.tnd, R.drawable.try_, R.drawable.ttd, R.drawable.twd,
			R.drawable.tzs, R.drawable.uah, R.drawable.ugx, R.drawable.usd, R.drawable.uyu, R.drawable.uzs, R.drawable.vef, R.drawable.vnd, R.drawable.xof,
			R.drawable.yer, R.drawable.zar, R.drawable.zmk };
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = savedInstanceState;
        
        if(getPreferencesDateFirstTime("FIRST") == true)
        {
        	setContentView(R.layout.layout_setting);
        	initSettings();
        	SavePreferencesFirstTime("FIRST", false);
        	
        	isSettingPage = true;
        	
        }
        else
        {
        	setContentView(R.layout.layout_main);
        	initApp();
        	
        	isSettingPage = false;
        }
        	
        
       
        
        
        Log.v("array size", "name array: "+arrayCurrency.length);
        Log.v("array size", "image array: "+arrayImage.length);
        
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.layout_main, menu);
        return true;
    }
    
    
    private void initApp()
    {
    	btnSetting = (ImageButton)this.findViewById(R.id.btnSetting);
    	
    	spinnerFrom = (Spinner)this.findViewById(R.id.spinnerFrom);
    	spinnerTo = (Spinner)this.findViewById(R.id.spinnerTo);
    	
    	dataAdapter = new MyAdapter(MainActivityCurrency.this, R.layout.row, arrayCurrency);
    	
    	spinnerFrom.setAdapter(dataAdapter);
    	spinnerTo.setAdapter(dataAdapter);
    	
    	
    	spinnerFrom.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				setFromName(arrayCurrency[position]);
				
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
    	
    	spinnerTo.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				setToName(arrayCurrency[position]);
				
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
    	
    	txtFrom = (EditText)this.findViewById(R.id.txtFrom);
    	txtTo = (TextView)this.findViewById(R.id.txtTo);
    	
    	btnConvert = (ImageButton)this.findViewById(R.id.btnConvert);
    	
    	btnConvert.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				onBtnConvertClicked();
			}
		});
    	
    	listCurrency = (ListView)this.findViewById(R.id.listCurrency);
    	
    	listFavourite.clear();
    	if(getPreferencesDate("CUR1").length() <= 0)
		{
			SavePreferences("CUR1", "GBP");
			listFavourite.add(getPreferencesDate("CUR1")+" = "+convertToUsd(getPreferencesDate("CUR1")));
		}
		else
		{
			listFavourite.add(getPreferencesDate("CUR1")+" = "+convertToUsd(getPreferencesDate("CUR1")));
		}
		if(getPreferencesDate("CUR2").length() <= 0)
		{
			SavePreferences("CUR2", "USD");
			listFavourite.add(getPreferencesDate("CUR2")+" = "+convertToUsd(getPreferencesDate("CUR2")));
		}
		else
		{
			listFavourite.add(getPreferencesDate("CUR2")+" = "+convertToUsd(getPreferencesDate("CUR2")));
		}
		if(getPreferencesDate("CUR3").length() <= 0)
		{
			SavePreferences("CUR3", "JPY");
			listFavourite.add(getPreferencesDate("CUR3")+" = "+convertToUsd(getPreferencesDate("CUR3")));
			
		}
		else
		{
			listFavourite.add(getPreferencesDate("CUR3")+" = "+convertToUsd(getPreferencesDate("CUR3")));
		}
		if(getPreferencesDate("CUR4").length() <= 0)
		{
			SavePreferences("CUR4", "AUD");
			listFavourite.add(getPreferencesDate("CUR4")+" = "+convertToUsd(getPreferencesDate("CUR4")));
		}
		else
		{
			listFavourite.add(getPreferencesDate("CUR4")+" = "+convertToUsd(getPreferencesDate("CUR4")));
		}
    	
    	/*if(getPreferencesDateFirstTime("FIRST") == true)
    	{
    		listFavourite.clear();
    		
    		SavePreferences("CUR1", "GBP");
    		SavePreferences("CUR2", "USD");
    		SavePreferences("CUR3", "JPY");
    		SavePreferences("CUR4", "AUD");
    		
    		
    		listFavourite.add(getPreferencesDate("CUR1"));
    		listFavourite.add(getPreferencesDate("CUR2"));
    		listFavourite.add(getPreferencesDate("CUR3"));
    		listFavourite.add(getPreferencesDate("CUR4"));
    	}
    	else
    	{
    		listFavourite.clear();
    		
    		SavePreferences("CUR1", "GBP");
    		SavePreferences("CUR2", "USD");
    		SavePreferences("CUR3", "JPY");
    		SavePreferences("CUR4", "AUD");
    		
    		listFavourite.add(getPreferencesDate("CUR1"));
    		listFavourite.add(getPreferencesDate("CUR2"));
    		listFavourite.add(getPreferencesDate("CUR3"));
    		listFavourite.add(getPreferencesDate("CUR4"));
    	}*/
    	
    	
    	
    	String[] stockArr = new String[listFavourite.size()];
       
        stockArr = listFavourite.toArray(stockArr);
        
        
        dataAdapterFavourite = new MobileArrayAdapter(this, stockArr);
       
        listCurrency.setAdapter(dataAdapterFavourite);
       
        
        btnSetting.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				initSettings();
				
				isSettingPage = true;				
			}
		});
        
    }
    
    private void initSettings()
    {
    	MainActivityCurrency.this.setContentView(R.layout.layout_setting);
    	
    	
    	spin1 = (Spinner)this.findViewById(R.id.spin1);
    	spin2 = (Spinner)this.findViewById(R.id.spin2);
    	spin3 = (Spinner)this.findViewById(R.id.spin3);
    	spin4 = (Spinner)this.findViewById(R.id.spin4);
    	
    	btnCancelSetting = (Button)this.findViewById(R.id.btnCancelSetting);
    	btnOkSetting = (Button)this.findViewById(R.id.btnOkSetting);
    	
    	dataAdapter = new MyAdapter(MainActivityCurrency.this, R.layout.row, arrayCurrency);
    	
    	spin1.setAdapter(dataAdapter);
    	spin2.setAdapter(dataAdapter);
    	spin3.setAdapter(dataAdapter);
    	spin4.setAdapter(dataAdapter);
    	
    	int spinnerPosition1 = dataAdapter.getPosition(getPreferencesDate("CUR1"));
    	int spinnerPosition2 = dataAdapter.getPosition(getPreferencesDate("CUR2"));
    	int spinnerPosition3 = dataAdapter.getPosition(getPreferencesDate("CUR3"));
    	int spinnerPosition4 = dataAdapter.getPosition(getPreferencesDate("CUR4"));
    	
    	spin1.setSelection(spinnerPosition1);
    	spin2.setSelection(spinnerPosition2);
    	spin3.setSelection(spinnerPosition3);
    	spin4.setSelection(spinnerPosition4);
    	
    	
    	
    	
    	
    	
    	btnCancelSetting.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				listFavourite.clear();
				if(getPreferencesDate("CUR1").length() <= 0)
				{
					SavePreferences("CUR1", "GBP");
					listFavourite.add(getPreferencesDate("CUR1")+" = "+convertToUsd(getPreferencesDate("CUR1")));
				}
				else
				{
					listFavourite.add(getPreferencesDate("CUR1")+" = "+convertToUsd(getPreferencesDate("CUR1")));
				}
				if(getPreferencesDate("CUR2").length() <= 0)
				{
					SavePreferences("CUR2", "USD");
					listFavourite.add(getPreferencesDate("CUR2")+" = "+convertToUsd(getPreferencesDate("CUR2")));
				}
				else
				{
					listFavourite.add(getPreferencesDate("CUR2")+" = "+convertToUsd(getPreferencesDate("CUR2")));
				}
				if(getPreferencesDate("CUR3").length() <= 0)
				{
					SavePreferences("CUR3", "JPY");
					listFavourite.add(getPreferencesDate("CUR3")+" = "+convertToUsd(getPreferencesDate("CUR3")));
					
				}
				else
				{
					listFavourite.add(getPreferencesDate("CUR3")+" = "+convertToUsd(getPreferencesDate("CUR3")));
				}
				if(getPreferencesDate("CUR4").length() <= 0)
				{
					SavePreferences("CUR4", "AUD");
					listFavourite.add(getPreferencesDate("CUR4")+" = "+convertToUsd(getPreferencesDate("CUR4")));
				}
				else
				{
					listFavourite.add(getPreferencesDate("CUR4")+" = "+convertToUsd(getPreferencesDate("CUR4")));
				}
				
				
				MainActivityCurrency.this.onCreate(bundle);
				
			}
		});
    	
    	btnOkSetting.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				listFavourite.clear();
				
				SavePreferences("CUR1", spin1.getSelectedItem().toString());
				SavePreferences("CUR2", spin2.getSelectedItem().toString());
				SavePreferences("CUR3", spin3.getSelectedItem().toString());
				SavePreferences("CUR4", spin4.getSelectedItem().toString());
				
				listFavourite.add(getPreferencesDate("CUR1"));
				listFavourite.add(getPreferencesDate("CUR2"));
				listFavourite.add(getPreferencesDate("CUR3"));
				listFavourite.add(getPreferencesDate("CUR4"));
				
				MainActivityCurrency.this.onCreate(bundle);
			}
		});
    	
    }
    
    private void onBtnConvertClicked()
    {
    	new RequestTask().execute("http://sporix.net/mobileapp/currency/index.php?amount="+(txtFrom.getText().toString())+"&from="+getFromName()+"&to="+getToName()).toString();
    	
    }
    
    private void initDialog()
	{
		if(dialog == null)
		{
			dialog = new ProgressDialog(this);
	        dialog.setMessage("Loading, please wait");
	        dialog.show();
	    }
	}
    
    private void dismissDialog()
    {
    	if(dialog != null)
        {
        	dialog.dismiss();
        	dialog = null;
        }
    }
    
    private void setFromName(String _fromName)
    {
    	this.fromName = _fromName;
    }
    private String getFromName()
    {
    	return this.fromName;
    }
    private void setToName(String _toName)
    {
    	this.toName = _toName;
    }
    private String getToName()
    {
    	return this.toName;
    }
    private void setOutPut(String _outPut)
    {
    	this.outPut = _outPut;
    }
    private String getOutPut()
    {
    	return this.outPut;
    }
    private void SavePreferences(String key, String value)
	{
        SharedPreferences sharedPreferences = this.getSharedPreferences("net.sporix.scurrencyconverter", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putString(key, value);
        editor.putString(key, value);
        editor.commit();
    }
	private void SavePreferencesFirstTime(String key, boolean value)
	{
        SharedPreferences sharedPreferences = this.getSharedPreferences("net.sporix.scurrencyconverter", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putString(key, value);
        editor.putBoolean(key, value);
        editor.commit();
    }
	private boolean getPreferencesDateFirstTime(String key)
	{
        SharedPreferences sharedPreferences = this.getSharedPreferences("net.sporix.scurrencyconverter", Context.MODE_PRIVATE);
        boolean strSavedMem1 = sharedPreferences.getBoolean(key, true);
       
        return strSavedMem1;
        
    }
	private String getPreferencesDate(String key)
	{
        SharedPreferences sharedPreferences = this.getSharedPreferences("net.sporix.scurrencyconverter", Context.MODE_PRIVATE);
        String strSavedMem1 = sharedPreferences.getString(key, "");
       
        return strSavedMem1;
        
    }
    private class RequestTask extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            
            
           
            
            try {
                response = httpclient.execute(new HttpGet(uri[0]));
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    out.close();
                    responseString = out.toString();
                    
                    Log.v("output", "output: "+responseString);
                    
                    setOutPut(responseString);
                    
                } 
                
               else{
                    //Closes the connection.
            	  
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                    
                   
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return responseString;
        }
        
        @Override    
        protected void onPreExecute() 
        {       
            super.onPreExecute();
            initDialog();
        }
        
        
        
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Do anything with response..
            dismissDialog();
            
            txtTo.setText(getOutPut());
        }
    }
    
    private class RequestTask2 extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            
            
           
            
            try {
                response = httpclient.execute(new HttpGet(uri[0]));
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    out.close();
                    responseString = out.toString();
                    
                    Log.v("output", "output: "+responseString);
                    
                    setOutPut(responseString);
                    
                } 
                
               else{
                    //Closes the connection.
            	  
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                    
                   
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return responseString;
        }
        
        @Override    
        protected void onPreExecute() 
        {       
            super.onPreExecute();
            initDialog();
        }
        
        
        
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Do anything with response..
            dismissDialog();
            
        }
    }
    
    

    public class MyAdapter extends ArrayAdapter<String>{
    	 
        public MyAdapter(Context context, int textViewResourceId,   String[] objects) {
            super(context, textViewResourceId, objects);
        }
 
        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }
 
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }
 
        public View getCustomView(int position, View convertView, ViewGroup parent) {
 
            LayoutInflater inflater=getLayoutInflater();
            View row=inflater.inflate(R.layout.row, parent, false);
            TextView label=(TextView)row.findViewById(R.id.txtListItem);
            label.setText(arrayCurrency[position]);
 
           
 
            ImageView icon=(ImageView)row.findViewById(R.id.image);
            icon.setImageResource(arrayImage[position]);
 
            return row;
            }
        }
    
    private String convertToUsd(String value)
	{
		 
		 AsyncTask<String, String, String> str = new RequestTask2().execute("http://sporix.net/mobileapp/currency/index.php?amount=1&from=USD&to="+value);
		 String str2 = "";
	
		 try {
			Log.v("inside", "convertToUsd: "+ str.get());
			str2 = str.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		 return str2;
	 }
    
    private void exitPopUp()
	{
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);     
		alt_bld.setMessage("Would you like to exit SCUrrency Converter?");
		alt_bld.setTitle("SCUrrency Converter");
		alt_bld.setCancelable(false);
//		/alt_bld.setIcon(R.drawable.icon);
		
		alt_bld.setNegativeButton("No", new DialogInterface.OnClickListener() {
		      public void onClick(DialogInterface dialog, int which) {

		                        // TODO Auto-generated method stub
		    	  dialog.dismiss();
		          

		    } });
		
		alt_bld.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		      public void onClick(DialogInterface dialog, int which) {

		                        // TODO Auto-generated method stub
		    	  
		             MainActivityCurrency.this.finish();          

		    } });
		
		
		
		
		
		alt_bld.show();
		
		
		
		//AlertDialog alert = alt_bld.create();

	}
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
    	super.onKeyDown(keyCode, event);
	    
	    if(keyCode == KeyEvent.KEYCODE_BACK)
	    {
	    	if(isSettingPage == true)
	    	{
	    		MainActivityCurrency.this.onCreate(bundle);
	    		
	    	}
	    	else
		    {
		    	exitPopUp();
		    	
		    }
	    }
	    
	    	

	    return false;
	    
    }
   
    
}
