package com.ameron32.apps.demo.codegen.data.crud.create;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.ameron32.apps.demo.codegen.R;

public class CreateSuccessActivity extends Activity
{
  private TextView successInfoView;
  private TextView operationInfoView;

  public void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.sample_success );

    initUI();
  }

  private void initUI()
  {
    successInfoView = (TextView) findViewById( R.id.successInfo );
    operationInfoView = (TextView) findViewById( R.id.operationInfo );

    String successInfoTemplate = getResources().getString( R.string.success_info_template );
    String successInfo = String.format( successInfoTemplate, "created" );
    successInfoView.setText( successInfo );
  }
}
