package com.ameron32.apps.demo.codegen.data.crud.start;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.ameron32.apps.demo.codegen.R;
import com.ameron32.apps.demo.codegen.data.crud.common.DataApplication;
import com.ameron32.apps.demo.codegen.data.crud.create.CreateRecordActivity;
import com.ameron32.apps.demo.codegen.data.crud.delete.DeleteRecordActivity;
import com.ameron32.apps.demo.codegen.data.crud.retrieve.SelectRetrievalTypeActivity;
import com.ameron32.apps.demo.codegen.data.crud.update.UpdateRecordActivity;

public class SelectTableOperationActivity extends Activity
{
  private ListView tableOperationsView;
  private TextView titleView;

  public void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.select_table_operation );

    initUI();
  }

  private void initUI()
  {
    tableOperationsView = (ListView) findViewById( R.id.tableOperationsList );
    titleView = (TextView) findViewById( R.id.tableOperationsTitle );

    String titleTemplate = getResources().getString( R.string.table_operations_title_template );
    DataApplication dataApplication = (DataApplication) getApplication();
    titleView.setText( String.format( titleTemplate, dataApplication.getChosenTable() ) );

    String[] tableOperations = getResources().getStringArray( R.array.table_operations );
    ArrayAdapter adapter = new ArrayAdapter( this, R.layout.list_item_with_arrow, R.id.itemName, tableOperations );
    tableOperationsView.setAdapter( adapter );

    tableOperationsView.setOnItemClickListener( new AdapterView.OnItemClickListener()
    {
      @Override
      public void onItemClick( AdapterView<?> adapterView, View view, int i, long l )
      {
        TextView chosenOperationView = (TextView) view.findViewById( R.id.itemName );
        String chosenOperation = chosenOperationView.getText().toString();

        if( chosenOperation.equals( "Create" ) )
        {
          startActivity( new Intent( SelectTableOperationActivity.this, CreateRecordActivity.class ) );
        }
        else if( chosenOperation.equals( "Retrieve" ) )
        {
          startActivity( new Intent( SelectTableOperationActivity.this, SelectRetrievalTypeActivity.class ) );
        }
        else if( chosenOperation.equals( "Update" ) )
        {
          startActivity( new Intent( SelectTableOperationActivity.this, UpdateRecordActivity.class ) );
        }
        else if( chosenOperation.equals( "Delete" ) )
        {
          startActivity( new Intent( SelectTableOperationActivity.this, DeleteRecordActivity.class ) );
        }
      }
    } );
  }
}
