package com.ameron32.apps.demo.codegen.data.crud.retrieve;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.backendless.BackendlessCollection;
import com.ameron32.apps.demo.codegen.data.*;
import com.ameron32.apps.demo.codegen.R;
import com.ameron32.apps.demo.codegen.data.crud.common.DataApplication;
import com.ameron32.apps.demo.codegen.data.crud.common.DefaultCallback;
import com.ameron32.apps.demo.codegen.data.crud.common.SendEmailActivity;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RetrieveRecordActivity extends Activity
{
  private TextView titleView;
  private EditText codeView;
  private Button runCodeButton, sendCodeButton;

  private String code;
  private String table;
  private String type;

  private static BackendlessCollection resultCollection;
  private static Object resultObject;

  private String selectedProperty;
  private String selectedRelatedTable;
  private String selectedRelatedProperty;
  private String relation;
  private String[] relatedProperties;

  public static BackendlessCollection getResultCollection()
  {
    return resultCollection;
  }

  public static Object getResultObject()
  {
    return resultObject;
  }

  public void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.sample_code_template );

    DataApplication dataApplication = (DataApplication) getApplication();
    table = dataApplication.getChosenTable();

    type = getIntent().getStringExtra( "type" );

    initUI();
  }

  private void initUI()
  {
    titleView = (TextView) findViewById( R.id.sampleCodeTitle );
    codeView = (EditText) findViewById( R.id.sampleCodeEdit );
    runCodeButton = (Button) findViewById( R.id.runCodeButton );
    sendCodeButton = (Button) findViewById( R.id.sendCodeButton );

    String titleTemplate = getResources().getString( R.string.retrieve_title_template );
    String title = String.format( titleTemplate, table );
    titleView.setText( title );
    if( table.equals( "Table" ) )
    {
      code = getTableRetrievalCode();
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
      retrieveTableRecord();
    }
  }

  private void onSendCodeButtonClicked()
  {
    Intent nextIntent = new Intent( getBaseContext(), SendEmailActivity.class );
    nextIntent.putExtra( "code", code );
    nextIntent.putExtra( "table", table );
    nextIntent.putExtra( "method", type );
    startActivity( nextIntent );
  }

  private String getTableRetrievalCode()
  {
    if( type.equals( "Basic Find" ) )
    {
      return getBasicTableRetrievalCode();
    }
    else if( type.equals( "Find First" ) )
    {
      return getFirstTableRetrievalCode();
    }
    else if( type.equals( "Find Last" ) )
    {
      return getLastTableRetrievalCode();
    }
    else if( type.equals( "Find with Sort" ) )
    {
      return getSortedTableRetrievalCode();
    }
    else if( type.equals( "Find with Relations" ) )
    {
      return getRelatedTableRetrievalCode();
    }
    return null;
  }

  private void retrieveTableRecord()
  {
    if( type.equals( "Basic Find" ) )
    {
      retrieveBasicTableRecord();
    }
    else if( type.equals( "Find First" ) )
    {
      retrieveFirstTableRecord();
    }
    else if( type.equals( "Find Last" ) )
    {
      retrieveLastTableRecord();
    }
    else if( type.equals( "Find with Sort" ) )
    {
      retrieveSortedTableRecord();
    }
    else if( type.equals( "Find with Relations" ) )
    {
      retrieveRelatedTableRecord();
    }
  }

  private String getBasicTableRetrievalCode()
  {
    return "BackendlessDataQuery query = new BackendlessDataQuery();\n"
            + "Table.findAsync( query, new AsyncCallback<BackendlessCollection<Table>>()\n"
            + "{\n"
            + "  @Override\n"
            + "  public void handleResponse( BackendlessCollection<Table> response )\n"
            + "  {\n"
            + "    Table firstTable = response.getCurrentPage().get( 0 );\n"
            + "  }\n"
            + "  @Override\n"
            + "  public void handleFault( BackendlessFault fault )\n"
            + "  {\n"
            + "    Toast.makeText( getBaseContext(), fault.getMessage(), Toast.LENGTH_SHORT ).show();\n"
            + "  }\n"
            + "} );";
  }

  private void retrieveBasicTableRecord()
  {
    BackendlessDataQuery query = new BackendlessDataQuery();
    Table.findAsync( query, new DefaultCallback<BackendlessCollection<Table>>( RetrieveRecordActivity.this )
    {
      @Override
      public void handleResponse( BackendlessCollection<Table> response )
      {
        super.handleResponse( response );

        resultCollection = response;

        AlertDialog.Builder builder = new AlertDialog.Builder( RetrieveRecordActivity.this );
        builder.setTitle( "Property to show:" );
        final String[] properties = { "objectId", "Column", "created", "ownerId", "updated" };
        builder.setItems( properties, new DialogInterface.OnClickListener()
        {
          @Override
          public void onClick( DialogInterface dialogInterface, int i )
          {
            Intent nextIntent = new Intent( RetrieveRecordActivity.this, ShowByPropertyActivity.class );
            nextIntent.putExtra( "type", type );
            nextIntent.putExtra( "property", properties[ i ] );
            startActivity( nextIntent );
            dialogInterface.cancel();
          }
        } );
        builder.create().show();
      }
    } );
  }

  private String getFirstTableRetrievalCode()
  {
    return "Table.findFirstAsync( new AsyncCallback<Table>()\n"
            + "{\n"
            + "  @Override\n"
            + "  public void handleResponse( Table object )\n"
            + "  {\n"
            + "    //work with the object\n"
            + "  }\n"
            + "  @Override\n"
            + "  public void handleFault( BackendlessFault fault )\n"
            + "  {\n"
            + "    Toast.makeText( getBaseContext(), fault.getMessage(), Toast.LENGTH_SHORT ).show();\n"
            + "  }\n"
            + "} );";
  }

  private void retrieveFirstTableRecord()
  {
    Table.findFirstAsync( new DefaultCallback<Table>( RetrieveRecordActivity.this )
    {
      @Override
      public void handleResponse( Table response )
      {
        super.handleResponse( response );
        resultObject = response;
        Intent nextIntent = new Intent( RetrieveRecordActivity.this, ShowEntityActivity.class );
        nextIntent.putExtra( "type", type );
        startActivity( nextIntent );
      }
    } );
  }

  private String getLastTableRetrievalCode()
  {
    return "Table.findLastAsync( new AsyncCallback<Table>()\n"
            + "{\n"
            + "  @Override\n"
            + "  public void handleResponse( Table object )\n"
            + "  {\n"
            + "    //work with the object\n"
            + "  }\n"
            + "  @Override\n"
            + "  public void handleFault( BackendlessFault fault )\n"
            + "  {\n"
            + "    Toast.makeText( getBaseContext(), fault.getMessage(), Toast.LENGTH_SHORT ).show();\n"
            + "  }\n"
            + "} );";
  }

  private void retrieveLastTableRecord()
  {
    Table.findLastAsync( new DefaultCallback<Table>( RetrieveRecordActivity.this )
    {
      @Override
      public void handleResponse( Table response )
      {
        super.handleResponse( response );
        resultObject = response;
        Intent nextIntent = new Intent( RetrieveRecordActivity.this, ShowEntityActivity.class );
        nextIntent.putExtra( "type", type );
        startActivity( nextIntent );
      }
    } );
  }

  private String getSortedTableRetrievalCode()
  {
    return "QueryOptions queryOptions = new QueryOptions();\n"
            + "List<String> sortByProperties = new ArrayList<String>();\n"
            + "sortByProperties.add( \"someProperty\" );\n"
            + "queryOptions.setSortBy( sortByProperties );\n"
            + "BackendlessDataQuery query = new BackendlessDataQuery(  );\n"
            + "query.setQueryOptions( queryOptions );\n"
            + "Table.findAsync( query, new AsyncCallback<BackendlessCollection<Table>>()\n"
            + "{\n"
            + "  @Override\n"
            + "  public void handleResponse( BackendlessCollection<Table> response )\n"
            + "  {\n"
            + "    Table firstSortedTable = response.getCurrentPage().get( 0 );\n"
            + "  }\n"
            + "  @Override\n"
            + "  public void handleFault( BackendlessFault fault )\n"
            + "  {\n"
            + "    Toast.makeText( getBaseContext(), fault.getMessage(), Toast.LENGTH_SHORT ).show();\n"
            + "  }\n"
            + "} );";
  }

  private void retrieveSortedTableRecord()
  {
    List<CharSequence> selectedItems = getIntent().getCharSequenceArrayListExtra( "selectedProperties" );
    QueryOptions queryOptions = new QueryOptions();
    List<String> sortByProperties = Arrays.asList( selectedItems.toArray( new String[ selectedItems.size() ] ) );
    queryOptions.setSortBy( sortByProperties );
    BackendlessDataQuery query = new BackendlessDataQuery();
    query.setQueryOptions( queryOptions );
    Table.findAsync( query, new DefaultCallback<BackendlessCollection<Table>>( RetrieveRecordActivity.this )
    {
      @Override
      public void handleResponse( BackendlessCollection<Table> response )
      {
        super.handleResponse( response );

        resultCollection = response;

        AlertDialog.Builder builder = new AlertDialog.Builder( RetrieveRecordActivity.this );
        builder.setTitle( "Property to show:" );
        final String[] properties = { "objectId", "Column", "created", "ownerId", "updated" };
        builder.setItems( properties, new DialogInterface.OnClickListener()
        {
          @Override
          public void onClick( DialogInterface dialogInterface, int i )
          {
            Intent nextIntent = new Intent( RetrieveRecordActivity.this, ShowByPropertyActivity.class );
            nextIntent.putExtra( "type", type );
            nextIntent.putExtra( "property", properties[ i ] );
            startActivity( nextIntent );
            dialogInterface.cancel();
          }
        } );
        builder.create().show();
      }
    } );
  }

  private String getRelatedTableRetrievalCode()
  {
    return "QueryOptions queryOptions = new QueryOptions();\n"
            + "List<String> relationsToFind = new ArrayList<String>();\n"
            + "queryOptions.setRelated( relationsToFind );\n"
            + "BackendlessDataQuery query = new BackendlessDataQuery();\n"
            + "query.setQueryOptions( queryOptions );\n"
            + "Table.findAsync( query, new AsyncCallback<BackendlessCollection<Table>>()\n"
            + "{\n"
            + "  @Override\n"
            + "  public void handleResponse( BackendlessCollection<Table> collection )\n"
            + "  {\n"
            + "    //work with objects\n"
            + "  }\n"
            + "  public void handleFault( BackendlessFault fault )\n"
            + "  {\n"
            + "    Toast.makeText( getBaseContext(), fault.getMessage(), Toast.LENGTH_SHORT ).show();\n"
            + "  }\n"
            + "}";
  }

  private void retrieveRelatedTableRecord()
  {
    final List<CharSequence> selectedRelations = getIntent().getCharSequenceArrayListExtra( "selectedRelations" );
    final List<CharSequence> selectedRelatedTables = getIntent().getCharSequenceArrayListExtra( "selectedRelatedTables" );
    final String[] selectedRelationsArray = selectedRelations.toArray( new String[ selectedRelations.size() ] );
    final String[] selectedRelatedTablesArray = selectedRelatedTables.toArray( new String[ selectedRelatedTables.size() ] );
    QueryOptions queryOptions = new QueryOptions();
    List<String> relationsToFind = Arrays.asList( selectedRelationsArray );
    queryOptions.setRelated( relationsToFind );
    BackendlessDataQuery query = new BackendlessDataQuery();
    query.setQueryOptions( queryOptions );
    Table.findAsync( query, new DefaultCallback<BackendlessCollection<Table>>( RetrieveRecordActivity.this )
    {
      @Override
      public void handleResponse( BackendlessCollection<Table> response )
      {
        super.handleResponse( response );
        resultCollection = response;

        AlertDialog.Builder builder = new AlertDialog.Builder( RetrieveRecordActivity.this );
        builder.setTitle( "Property to show:" );
        final String[] properties = { "objectId", "Column", "created", "ownerId", "updated" };
        builder.setItems( properties, new DialogInterface.OnClickListener()
        {
          @Override
          public void onClick( DialogInterface dialogInterface, int i )
          {
            selectedProperty = properties[ i ];

            AlertDialog.Builder builder = new AlertDialog.Builder( RetrieveRecordActivity.this );
            builder.setTitle( "Related table to show:" );
            builder.setItems( selectedRelatedTablesArray, new DialogInterface.OnClickListener()
            {
              @Override
              public void onClick( DialogInterface dialogInterface, int i )
              {
                selectedRelatedTable = selectedRelatedTablesArray[ i ];
                relation = selectedRelationsArray[ i ];
                if( selectedRelatedTable.equals( "GeoPoint" ) )
                {
                  relatedProperties = new String[] { "Latitude", "Longitude", "Metadata" };
                }
                dialogInterface.cancel();

                AlertDialog.Builder builder = new AlertDialog.Builder( RetrieveRecordActivity.this );
                builder.setTitle( "Related property to show:" );
                if( selectedRelatedTable.equals( "Table" ) )
                {
                  relatedProperties = new String[] { "objectId", "Column", "created", "ownerId", "updated" };
                }

                builder.setItems( relatedProperties, new DialogInterface.OnClickListener()
                {
                  @Override
                  public void onClick( DialogInterface dialogInterface, int i )
                  {
                    selectedRelatedProperty = relatedProperties[ i ];
                    dialogInterface.cancel();
                    Intent nextIntent = new Intent( RetrieveRecordActivity.this, ShowEntityActivity.class );
                    nextIntent.putExtra( "type", type );
                    nextIntent.putExtra( "property", selectedProperty );
                    nextIntent.putExtra( "relation", relation );
                    nextIntent.putExtra( "relatedTable", selectedRelatedTable );
                    nextIntent.putExtra( "relatedProperty", selectedRelatedProperty );
                    startActivity( nextIntent );
                    dialogInterface.cancel();
                  }
                } );
                builder.create().show();
              }
            } );
            builder.create().show();
          }
        } );
        builder.create().show();
      }
    } );
  }
}
