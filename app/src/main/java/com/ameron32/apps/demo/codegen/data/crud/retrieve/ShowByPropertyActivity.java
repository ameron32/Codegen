package com.ameron32.apps.demo.codegen.data.crud.retrieve;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.backendless.BackendlessCollection;
import com.ameron32.apps.demo.codegen.data.*;
import com.ameron32.apps.demo.codegen.R;
import com.ameron32.apps.demo.codegen.data.crud.common.DataApplication;
import com.ameron32.apps.demo.codegen.data.crud.common.DefaultCallback;

public class ShowByPropertyActivity extends Activity
{
  private TextView titleView;
  private TextView propertyView;
  private ListView entitiesView;
  private Button nextPageButton, previousPageButton;

  private String type;
  private String table;
  private String property;
  private BackendlessCollection collection;
  private String[] items;

  private int currentPage;
  private int totalPages;

  public void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.show_by_property );

    DataApplication dataApplication = (DataApplication) getApplication();
    table = dataApplication.getChosenTable();

    collection = RetrieveRecordActivity.getResultCollection();
    currentPage = 1;
    totalPages = (int) Math.ceil( ((double) collection.getTotalObjects()) / collection.getCurrentPage().size() );

    initUI();
    initList();
    initButtons();
  }

  private void initUI()
  {
    titleView = (TextView) findViewById( R.id.showByPropertyTitle );
    propertyView = (TextView) findViewById( R.id.propertyName );
    entitiesView = (ListView) findViewById( R.id.entitiesList );
    previousPageButton = (Button) findViewById( R.id.previousPageButton );
    nextPageButton = (Button) findViewById( R.id.nextPageButton );

    Intent intent = getIntent();
    type = intent.getStringExtra( "type" );
    property = intent.getStringExtra( "property" );

    if( type.equals( "Basic Find" ) )
    {
      titleView.setText( "Basic Find" );
    }
    else if( type.equals( "Find with Sort" ) )
    {
      titleView.setText( "Sorted Find" );
    }
    propertyView.setText( property + ":" );

    previousPageButton.setOnClickListener( new View.OnClickListener()
    {
      @Override
      public void onClick( View view )
      {
        collection.previousPage( new DefaultCallback<BackendlessCollection>( ShowByPropertyActivity.this )
        {
          @Override
          public void handleResponse( BackendlessCollection response )
          {
            currentPage--;
            collection = response;
            initList();
            initButtons();
            super.handleResponse( response );
          }
        } );
      }
    } );

    nextPageButton.setOnClickListener( new View.OnClickListener()
    {
      @Override
      public void onClick( View view )
      {
        collection.nextPage( new DefaultCallback<BackendlessCollection>( ShowByPropertyActivity.this )
        {
          @Override
          public void handleResponse( BackendlessCollection response )
          {
            currentPage++;
            collection = response;
            initList();
            initButtons();
            super.handleResponse( response );
          }
        } );
      }
    } );
  }

  private void initList()
  {
    items = new String[ collection.getCurrentPage().size() ];

    if( table.equals( "Table" ) )
    {
      if( property.equals( "objectId" ) )
      {
        for( int i = 0; i < collection.getCurrentPage().size(); i++ )
        {
          items[ i ] = String.valueOf( ((Table) collection.getCurrentPage().get( i )).getObjectId() );
        }
      }
      else if( property.equals( "Column" ) )
      {
        for( int i = 0; i < collection.getCurrentPage().size(); i++ )
        {
          items[ i ] = String.valueOf( ((Table) collection.getCurrentPage().get( i )).getColumn() );
        }
      }
      else if( property.equals( "created" ) )
      {
        for( int i = 0; i < collection.getCurrentPage().size(); i++ )
        {
          items[ i ] = String.valueOf( ((Table) collection.getCurrentPage().get( i )).getCreated() );
        }
      }
      else if( property.equals( "ownerId" ) )
      {
        for( int i = 0; i < collection.getCurrentPage().size(); i++ )
        {
          items[ i ] = String.valueOf( ((Table) collection.getCurrentPage().get( i )).getOwnerId() );
        }
      }
      else if( property.equals( "updated" ) )
      {
        for( int i = 0; i < collection.getCurrentPage().size(); i++ )
        {
          items[ i ] = String.valueOf( ((Table) collection.getCurrentPage().get( i )).getUpdated() );
        }
      }
    }

    ListAdapter adapter = new ArrayAdapter( this, android.R.layout.simple_list_item_1, items );
    entitiesView.setAdapter( adapter );
  }

  private void initButtons()
  {
    previousPageButton.setEnabled( currentPage != 1 );
    nextPageButton.setEnabled( currentPage != totalPages );
  }
}
