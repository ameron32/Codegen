package com.ameron32.apps.demo.codegen.data.crud.create;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.ameron32.apps.demo.codegen.data.*;
import com.ameron32.apps.demo.codegen.R;
import com.ameron32.apps.demo.codegen.data.crud.common.DataApplication;
import com.ameron32.apps.demo.codegen.data.crud.common.DefaultCallback;
import com.ameron32.apps.demo.codegen.data.crud.common.SendEmailActivity;
import com.backendless.geo.GeoPoint;

import java.util.ArrayList;
import java.util.Collections;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CreateRecordActivity extends Activity
{
  private TextView titleView;
  private EditText codeEdit;
  private Button runCodeButton, sendCodeButton;

  private String code;
  private String table;


  class CreateTableRecordTask extends AsyncTask<Void, Void, Table>
  {
    Table table = new Table();

    @Override
    protected void onPreExecute()
    {
      table.setColumn( UUID.randomUUID().toString() );
    }

    @Override
    protected Table doInBackground( Void... voids )
    {
      return table.save();
    }
  };


  public void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.sample_code_template );

    DataApplication dataApplication = (DataApplication) getApplication();
    table = dataApplication.getChosenTable();

    initUI();
  }

  private void initUI()
  {
    titleView = (TextView) findViewById( R.id.sampleCodeTitle );
    codeEdit = (EditText) findViewById( R.id.sampleCodeEdit );
    runCodeButton = (Button) findViewById( R.id.runCodeButton );
    sendCodeButton = (Button) findViewById( R.id.sendCodeButton );

    String titleTemplate = getResources().getString( R.string.create_record_title_template );
    String title = String.format( titleTemplate, table );
    titleView.setText( title );

    if( table.equals( "Table" ) )
    {
      code = getTableCreationCode();
    }

    codeEdit.setText( code );

    runCodeButton.setOnClickListener( new View.OnClickListener()
    {
      @Override
      public void onClick( View view )
      {
        onRunCodeButtonClicked();
      }
    } );

    sendCodeButton.setOnClickListener( new View.OnClickListener()
    {
      @Override
      public void onClick( View view )
      {
        onSendCodeButtonClicked();
      }
    } );
  }

  private void onRunCodeButtonClicked()
  {
    if( table.equals( "Table" ) )
    {
      createTableRecord();
    }
  }

  private void onSendCodeButtonClicked()
  {
    Intent nextIntent = new Intent( getBaseContext(), SendEmailActivity.class );
    nextIntent.putExtra( "code", code );
    nextIntent.putExtra( "table", table );
    nextIntent.putExtra( "method", "Create" );
    startActivity( nextIntent );
  }

  private String getTableCreationCode()
  {
    return "Table table = new Table();\n"
            + "table.setColumn( UUID.randomUUID().toString() );\n"
            + "table.saveAsync( new AsyncCallback<Table>()\n"
            + "{\n"
            + "  @Override\n"
            + "  public void handleResponse( Table savedTable )\n"
            + "  {\n"
            + "    table = savedTable;\n"
            + "  }\n"
            + "  @Override\n"
            + "  public void handleFault( BackendlessFault fault )\n"
            + "  {\n"
            + "    Toast.makeText( getBaseContext(), fault.getMessage(), Toast.LENGTH_SHORT ).show();\n"
            + "  }\n"
            + "});";
  }

  private void createTableRecord()
  {
    Table table = null;

    try
    {
      table = new CreateTableRecordTask().execute().get( 30, TimeUnit.SECONDS );
    }
    catch ( InterruptedException | ExecutionException | TimeoutException e )
    {
      Toast.makeText( this, e.getMessage(), Toast.LENGTH_SHORT ).show();
      return;
    }

    Intent nextIntent = new Intent( CreateRecordActivity.this, CreateSuccessActivity.class );
    startActivity( nextIntent );
  }
}
