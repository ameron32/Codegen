package com.ameron32.apps.demo.codegen.data.crud.start;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.backendless.Backendless;
import com.ameron32.apps.demo.codegen.R;
import com.ameron32.apps.demo.codegen.data.crud.common.DataApplication;
import com.ameron32.apps.demo.codegen.data.crud.common.Defaults;

public class SelectTableActivity extends Activity
{
  private ListView tablesListView;

  public void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.select_table );

    Backendless.setUrl( Defaults.SERVER_URL );
    Backendless.initApp( getBaseContext(), getString(R.string.application_id), getString(R.string.secret_key), Defaults.VERSION );

    initUI();
  }

  private void initUI()
  {
    tablesListView = (ListView) findViewById( R.id.tablesList );

    String[] tables = new String[] { "Table" };

    ArrayAdapter adapter = new ArrayAdapter<String>( this, R.layout.list_item_with_arrow, R.id.itemName, tables );
    tablesListView.setAdapter( adapter );

    tablesListView.setOnItemClickListener( new AdapterView.OnItemClickListener()
    {
      @Override
      public void onItemClick( AdapterView<?> adapterView, View view, int i, long l )
      {
        TextView tableNameView = (TextView) view.findViewById( R.id.itemName );
        DataApplication dataApplication = (DataApplication) getApplication();
        dataApplication.setChosenTable( tableNameView.getText().toString() );

        startActivity( new Intent( SelectTableActivity.this, SelectTableOperationActivity.class ) );
      }
    } );
  }
}
