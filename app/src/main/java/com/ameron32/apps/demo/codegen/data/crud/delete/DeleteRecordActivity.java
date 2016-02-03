package com.ameron32.apps.demo.codegen.data.crud.delete;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.ameron32.apps.demo.codegen.data.*;
import com.ameron32.apps.demo.codegen.R;
import com.ameron32.apps.demo.codegen.data.crud.common.DataApplication;
import com.ameron32.apps.demo.codegen.data.crud.common.DefaultCallback;
import com.ameron32.apps.demo.codegen.data.crud.common.SendEmailActivity;

public class DeleteRecordActivity extends Activity
{
  private TextView titleView;
  private EditText codeView;
  private Button runCodeButton, sendCodeButton;

  private String code;
  private String table;

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
    codeView = (EditText) findViewById( R.id.sampleCodeEdit );
    runCodeButton = (Button) findViewById( R.id.runCodeButton );
    sendCodeButton = (Button) findViewById( R.id.sendCodeButton );

    String titleTemplate = getResources().getString( R.string.delete_title_template );
    String title = String.format( titleTemplate, table );
    titleView.setText( title );

    if( table.equals( "Table" ) )
    {
      code = getTableDeletionCode();
    }

    codeView.setText( code );

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
      deleteTableRecord();
    }
  }

  private void onSendCodeButtonClicked()
  {
    Intent nextIntent = new Intent( getBaseContext(), SendEmailActivity.class );
    nextIntent.putExtra( "code", code);
    nextIntent.putExtra( "table", table);
    nextIntent.putExtra( "method", "Delete");
    startActivity( nextIntent);
  }

  private String getTableDeletionCode()
  {
    return "public void remove( Table table )\n"
            + "{\n"
            + "  table.removeAsync( new AsyncCallback<Long>()\n"
            + "  {\n"
            + "    @Override\n"
            + "    public void handleResponse( Long deletionTime )\n"
            + "    {\n"
            + "      Toast.makeText( getBaseContext(), \"Deletion time: \" + new Date( deletionTime ).toString(), Toast.LENGTH_SHORT ).show();\n"
            + "    }\n"
            + "    @Override\n"
            + "    public void handleFault( BackendlessFault fault )\n"
            + "    {\n"
            + "      Toast.makeText( getBaseContext(), fault.getMessage(), Toast.LENGTH_SHORT ).show();\n"
            + "    }\n"
            + "  }\n"
            + "}";
  }

  private void deleteTableRecord()
  {
    Table.findFirstAsync( new DefaultCallback<Table>(DeleteRecordActivity.this) {
      @Override
      public void handleResponse(Table response) {
        super.handleResponse(response);
        Table firstTable = response;
        firstTable.removeAsync(new DefaultCallback<Long>(DeleteRecordActivity.this) {
          @Override
          public void handleResponse(Long response) {
            super.handleResponse(response);
            Intent nextIntent = new Intent(getBaseContext(), DeleteSuccessActivity.class);
            nextIntent.putExtra("time", response);
            startActivity(nextIntent);
          }
        });
      }
    });
  }
}
