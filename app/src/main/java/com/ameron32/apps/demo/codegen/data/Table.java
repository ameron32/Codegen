package com.ameron32.apps.demo.codegen.data;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.BackendlessDataQuery;

public class Table
{
  private String objectId;
  private String Column;
  private java.util.Date created;
  private String ownerId;
  private java.util.Date updated;
  public String getObjectId()
  {
    return objectId;
  }

  public String getColumn()
  {
    return Column;
  }

  public void setColumn( String Column )
  {
    this.Column = Column;
  }

  public java.util.Date getCreated()
  {
    return created;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public java.util.Date getUpdated()
  {
    return updated;
  }


  public Table save()
  {
    return Backendless.Data.of( Table.class ).save( this );
  }

  public Future<Table> saveAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Table> future = new Future<Table>();
      Backendless.Data.of( Table.class ).save( this, future );

      return future;
    }
  }

  public void saveAsync( AsyncCallback<Table> callback )
  {
    Backendless.Data.of( Table.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( Table.class ).remove( this );
  }

  public Future<Long> removeAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Long> future = new Future<Long>();
      Backendless.Data.of( Table.class ).remove( this, future );

      return future;
    }
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( Table.class ).remove( this, callback );
  }

  public static Table findById( String id )
  {
    return Backendless.Data.of( Table.class ).findById( id );
  }

  public static Future<Table> findByIdAsync( String id )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Table> future = new Future<Table>();
      Backendless.Data.of( Table.class ).findById( id, future );

      return future;
    }
  }

  public static void findByIdAsync( String id, AsyncCallback<Table> callback )
  {
    Backendless.Data.of( Table.class ).findById( id, callback );
  }

  public static Table findFirst()
  {
    return Backendless.Data.of( Table.class ).findFirst();
  }

  public static Future<Table> findFirstAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Table> future = new Future<Table>();
      Backendless.Data.of( Table.class ).findFirst( future );

      return future;
    }
  }

  public static void findFirstAsync( AsyncCallback<Table> callback )
  {
    Backendless.Data.of( Table.class ).findFirst( callback );
  }

  public static Table findLast()
  {
    return Backendless.Data.of( Table.class ).findLast();
  }

  public static Future<Table> findLastAsync()
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<Table> future = new Future<Table>();
      Backendless.Data.of( Table.class ).findLast( future );

      return future;
    }
  }

  public static void findLastAsync( AsyncCallback<Table> callback )
  {
    Backendless.Data.of( Table.class ).findLast( callback );
  }

  public static BackendlessCollection<Table> find( BackendlessDataQuery query )
  {
    return Backendless.Data.of( Table.class ).find( query );
  }

  public static Future<BackendlessCollection<Table>> findAsync( BackendlessDataQuery query )
  {
    if( Backendless.isAndroid() )
    {
      throw new UnsupportedOperationException( "Using this method is restricted in Android" );
    }
    else
    {
      Future<BackendlessCollection<Table>> future = new Future<BackendlessCollection<Table>>();
      Backendless.Data.of( Table.class ).find( query, future );

      return future;
    }
  }

  public static void findAsync( BackendlessDataQuery query, AsyncCallback<BackendlessCollection<Table>> callback )
  {
    Backendless.Data.of( Table.class ).find( query, callback );
  }
}
