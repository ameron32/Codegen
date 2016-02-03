package com.ameron32.apps.demo.codegen.data.crud.delete;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.ameron32.apps.demo.codegen.R;

import java.util.Date;

public class DeleteSuccessActivity extends Activity
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
    String successInfo = String.format( successInfoTemplate, "deleted" );
    successInfoView.setText( successInfo );

    Long time = getIntent().getLongExtra( "time", new Date().getTime() );
    Date deletion = new Date( time );
    operationInfoView.setText( "Deletion time: " + deletion.toString() );
  }
}
