package com.yong.lgefirmware;

import android.os.*;
import android.util.Log;
import android.view.*;
import android.widget.*;
import java.net.*;
import java.util.*;
import android.app.*;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity
{
	int result = 0;
	
	String inputDevice = "";
	String outputDevice = "";
	String outputOperator = "";
	String outputChipset = "";
	String outputUpdate = "";
	String urlParse = "https://9to5lg.com/test/go.php?country=KR";
	String urlHK = "https://9to5lg.com/test/go.php?country=HK";
	String urlKR = "https://9to5lg.com/test/go.php?country=KR";
	String urlUS = "https://9to5lg.com/test/go.php?country=US";
	
	EditText mainInput;
	RadioButton radioHK, radioKR, radioUS;
	RadioGroup countryRadio;
	ResultAdapter resultAdapter;
	RecyclerView mRecyclerView;
	RecyclerView.LayoutManager mLayoutManager;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		countryRadio = findViewById(R.id.mainCountryRadio);
		mainInput = findViewById(R.id.mainDeviceInput);
		mLayoutManager = new LinearLayoutManager(this);
		mRecyclerView = findViewById(R.id.resultRecycler);
		mRecyclerView.setLayoutManager(mLayoutManager);
		radioHK = findViewById(R.id.countryHK);
		radioKR = findViewById(R.id.countryKR);
		radioUS = findViewById(R.id.countryUS);
		
		countryRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
			public void onCheckedChanged(RadioGroup group, int checkedId){
				switch(checkedId){
					case R.id.countryHK:
						urlParse = urlHK;
						break;
					case R.id.countryKR:
						urlParse = urlKR;
						break;
					case R.id.countryUS:
						urlParse = urlUS;
						break;
				}
			}
		});
    }
	
	public void check(View v){
		inputDevice = mainInput.getText().toString().replaceAll("-", "");
		new parseTask().execute();
	}
	
	private class parseTask extends AsyncTask
	{
		ProgressDialog progressDialog= new ProgressDialog(MainActivity.this);
		
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			
			progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
			progressDialog.setMessage(getResources().getString(R.string.parse_loading));
			progressDialog.show();
		}

		@Override
		protected Object doInBackground(Object[] p1)
		{
			ArrayList<ResultRecycler> resultArray = new ArrayList<>();
			Source targetUrl;
			
			try{
				result = 0;
				targetUrl = new Source(new URL(urlParse));
				
				List<Element> list = targetUrl.getAllElements(HTMLElementName.TR);
				for(int i=1; i<list.size(); i++){
					Element tdDevice = list.get(i).getAllElements(HTMLElementName.TD).get(0);
					Element tdOperator = list.get(i).getAllElements(HTMLElementName.TD).get(1);
					Element tdChipset = list.get(i).getAllElements(HTMLElementName.TD).get(3);
					Element tdUpdate = list.get(i).getAllElements(HTMLElementName.TD).get(5);

					outputDevice = tdDevice.getTextExtractor().toString();
					outputOperator= tdOperator.getTextExtractor().toString();
					outputChipset = tdChipset.getTextExtractor().toString();
					outputUpdate = tdUpdate.getTextExtractor().toString();
					
					if(inputDevice.equals("") || outputDevice.contains(inputDevice)){
						if(outputChipset.equals("QCT")){
							outputChipset = getResources().getString(R.string.chipset_qct);
						}else if(outputChipset.equals("MTK")){
							outputChipset = getResources().getString(R.string.chipset_mtk);
						}
						if(urlParse.equals(urlKR)){
							switch(outputOperator){
								case "SKT":
									outputOperator = getResources().getString(R.string.operator_skt);
									break;
								case "KTF":
									outputOperator = getResources().getString(R.string.operator_kt);
									break;
								case "LGT":
									outputOperator = getResources().getString(R.string.operator_lgu);
									break;
								case "KOR":
									outputOperator = getResources().getString(R.string.operator_user);
									break;
							}
						}
						resultArray.add(new ResultRecycler(outputDevice, getResources().getString(R.string.result_operator) + " : " + outputOperator, getResources().getString(R.string.result_chipset) + " : " + outputChipset, getResources().getString(R.string.result_update) + " : " + outputUpdate));
						resultAdapter = new ResultAdapter(resultArray);
						result += 1;
					}
				}
				if(result == 0){
					resultArray.add(new ResultRecycler(getResources().getString(R.string.result_no), "", "", ""));
					resultAdapter = new ResultAdapter(resultArray);
				}
			}catch(Exception e){
				Log.e("Exception", e.toString());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Object result)
		{
			super.onPostExecute(result);
			mRecyclerView.setAdapter(resultAdapter);
			progressDialog.dismiss();
		}
	}
}
