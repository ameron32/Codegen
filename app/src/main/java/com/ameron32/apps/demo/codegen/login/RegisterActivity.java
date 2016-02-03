package com.ameron32.apps.demo.codegen.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.ameron32.apps.demo.codegen.R;
import com.backendless.Backendless;
import com.backendless.BackendlessUser;

public class RegisterActivity extends Activity
{
  private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat( "yyyy/MM/dd" );

  private EditText emailField;
  private EditText nameField;
  private EditText passwordField;

  private Button registerButton;

  private String email;
  private String name;
  private String password;

  private KingsburgUser user;

  public void onCreate( Bundle savedInstanceState )
  {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.register );

    initUI();
  }

  private void initUI()
  {emailField = (EditText) findViewById( R.id.emailField );nameField = (EditText) findViewById( R.id.nameField );passwordField = (EditText) findViewById( R.id.passwordField );

    registerButton = (Button) findViewById( R.id.registerButton );

    registerButton.setOnClickListener( new View.OnClickListener()
    {
      @Override
      public void onClick( View view )
      {
        onRegisterButtonClicked();
      }
    } );
  }

  private void onRegisterButtonClicked()
  {
    String emailText = emailField.getText().toString().trim();
    String nameText = nameField.getText().toString().trim();
    String passwordText = passwordField.getText().toString().trim();

    if ( emailText.isEmpty() )
    {
      showToast( "Field 'email' cannot be empty." );
      return;
    }

    if ( passwordText.isEmpty() )
    {
      showToast( "Field 'password' cannot be empty." );
      return;
    }

    if( !emailText.isEmpty() )
    {
      email = emailText;
    }

    if( !nameText.isEmpty() )
    {
      name = nameText;
    }

    if( !passwordText.isEmpty() )
    {
      password = passwordText;
    }

    user = new KingsburgUser();

    if( email != null )
    {
      user.setEmail( email );
    }

    if( name != null )
    {
      user.setName( name );
    }

    if( password != null )
    {
      user.setPassword( password );
    }

    Backendless.UserService.register( user, new DefaultCallback<BackendlessUser>( RegisterActivity.this )
    {
      @Override
      public void handleResponse( BackendlessUser response )
      {
        super.handleResponse( response );
        startActivity( new Intent( RegisterActivity.this, RegistrationSuccessActivity.class ) );
        finish();
      }
    } );
  }

  private void showToast( String msg )
  {
    Toast.makeText( this, msg, Toast.LENGTH_SHORT ).show();
  }
}