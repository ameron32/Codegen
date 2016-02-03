package com.ameron32.apps.demo.codegen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ameron32.apps.demo.codegen.data.crud.start.SelectTableActivity;
import com.ameron32.apps.demo.codegen.login.LoginActivity;
import com.ameron32.apps.demo.codegen.messaging.ChooseNicknameActivity;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });
  }

  @Override
  protected void onStart() {
    super.onStart();
    RecyclerView r = (RecyclerView) findViewById(R.id.recycler);
    r.setLayoutManager(new LinearLayoutManager(this));
    r.setAdapter(new ActivityAdapter(this));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }



  public static class ActivityAdapter
      extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    private final ActivityPack[] packs;
    private final Context context;

    public ActivityAdapter(Context context) {
      this.context = context;
      packs = new ActivityPack[] {
          new ActivityPack("CRUD", SelectTableActivity.class),
          new ActivityPack("Login", LoginActivity.class),
          new ActivityPack("Messaging", ChooseNicknameActivity.class)
      };
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_line, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
      String activityName = packs[position].name;
      holder.tv.setText(activityName);
      holder.tv.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (context instanceof Activity) {
            Activity a = (Activity) context;
            a.startActivity(new Intent(context, packs[position].activity));
          }
        }
      });
    }

    @Override
    public int getItemCount() {
      return packs.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

      TextView tv;

      public ViewHolder(View itemView) {
        super(itemView);
        tv = (TextView) itemView.findViewById(R.id.name);
      }
    }



    public static class ActivityPack {

      String name;
      Class<? extends Activity> activity;

      public ActivityPack(String name, Class<? extends Activity> activity) {
        this.name = name;
        this.activity = activity;
      }
    }
  }
}
