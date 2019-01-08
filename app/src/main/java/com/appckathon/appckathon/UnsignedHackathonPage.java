package com.appckathon.appckathon;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import java.nio.channels.Channel;
import android.app.PendingIntent;

public class UnsignedHackathonPage extends AppCompatActivity {

    private TextView hackathonName;
    private TextView managerName;
    private TextView description;
    private ListView teams_list;
    private Button joinHackathon;
    private ImageView image;
    private final String CHANNEL = "1";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unsigned_hackathon_page);
        final Hackathon hackathon = (Hackathon) getIntent().getSerializableExtra("hackathon");
        hackathonName = (TextView) findViewById(R.id.hackathon_name);
        managerName = (TextView) findViewById(R.id.hackathon_manager_name);
        description = (TextView) findViewById(R.id.hackathon_description);
        image = (ImageView) findViewById(R.id.hackathon_image);
        joinHackathon = (Button) findViewById(R.id.join_hackathon);

        //set text for these TextViews
        hackathonName.setText(hackathon.getName());
        managerName.setText(hackathon.getManagername());
        description.setText(hackathon.getDescription());

        String[] teams_arr = new String[hackathon.getTeamNames().size()];
        teams_arr = hackathon.getTeamNames().toArray(teams_arr);
        teams_list = (ListView) findViewById(R.id.hackathon_teams_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, teams_arr);
        teams_list.setAdapter(adapter);


        joinHackathon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinHackathon(hackathon);
            }
        });


        //notification
        createChannel();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationManager mNotificationManager=getSystemService(NotificationManager.class);
        String id = CHANNEL;
        CharSequence name = "channel 1";
        String description = "New order alerts in channel 1";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
        mChannel.setDescription(description);

        mNotificationManager.createNotificationChannel(mChannel);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void joinHackathon(Hackathon hackathon) {
        addNotification(CHANNEL);
        String currUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("users")
                .child(currUserID).child("hackathons").child("participating").child(hackathon.getName()).setValue(hackathon);
        //once succesfully added user toast + move to home page
        Toast.makeText(UnsignedHackathonPage.this, "joined hackathon successfully! ", Toast.LENGTH_SHORT).show();
        //navigate back to home page

        startActivity(new Intent(UnsignedHackathonPage.this, HomePage.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void addNotification(String channel)
    {
        Notification.Builder notificationBuilder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            notificationBuilder = new Notification.Builder(this, channel);
        } else {
            //noinspection deprecation
            notificationBuilder = new Notification.Builder(this);
        }

        Intent landingIntent = new Intent(this, LoginPage.class);
        PendingIntent pendingLandingIntent = PendingIntent.getActivity(this, 0, landingIntent,0);

        Notification notification = notificationBuilder
                .setContentTitle("Congratulations for joining "+hackathonName.getText()+"!")
                .setSmallIcon(R.drawable.ic_launcher_round)
                .setContentIntent(pendingLandingIntent)
                .setContentText("You can view the hackathon in \"participating\" tab in home page").build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify((int) System.currentTimeMillis(), notification);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(UnsignedHackathonPage.this, HomePage.class));
        finish();
    }
}
