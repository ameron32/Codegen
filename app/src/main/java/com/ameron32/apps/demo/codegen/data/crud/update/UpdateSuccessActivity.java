package com.ameron32.apps.demo.codegen.data.crud.update;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.ameron32.apps.demo.codegen.R;

import java.util.Date;

public class UpdateSuccessActivity extends Activity
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
    String successInfo = String.format( successInfoTemplate, "updated" );
    successInfoView.setText( successInfo );
  }
}
