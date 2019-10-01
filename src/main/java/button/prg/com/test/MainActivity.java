package button.prg.com.test;

import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import button.prg.com.test.util.IabHelper;
import button.prg.com.test.util.IabResult;
import button.prg.com.test.util.Inventory;
import button.prg.com.test.util.Purchase;

public class MainActivity extends AppCompatActivity implements IabHelper.OnIabSetupFinishedListener,
        IabHelper.QueryInventoryFinishedListener {
    private Button showNotification;
    Uri path = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    FragNav frag;
    IabHelper iabHelper;
    private static final String BOOK_TEST_Premium = "bookTest_premium";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showNotificationAndToolbar();
        checkMyAccount();
    }


    @Override
    public void onIabSetupFinished(IabResult result) {

        if (result.isSuccess()) {
            List<String> details = new ArrayList<>();
            details.add(BOOK_TEST_Premium);
            iabHelper.queryInventoryAsync(true, details, this);
        } else {
            Toast.makeText(this, " ارتباط برقرار نشد...", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void onQueryInventoryFinished(IabResult result, Inventory inv) {
        if (result.isSuccess()) {
            Purchase purchase = inv.getPurchase(BOOK_TEST_Premium);
            if (purchase != null) {
                changeToPremiumAccant();

            } else {
                Toast.makeText(this, "خریدی صورت نگرفته است", Toast.LENGTH_SHORT).show();
            }
            // progressDialog.hide();

        }

    }

    public void checkMyAccount() {
        iabHelper = new IabHelper(this, "MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwD+o7/lKkBaVXF8jJogrtlVcssVO4dj6uSD/5qPASuywL+Ffvxg7kq1PxSvtLJ2a1V1dGeqTGZk5MOcw0im8lDKamYaKsqIOQMjEZMQ7jGNSB67GeePfr0rj1K1teGxadTj5xfw/GEMYtldTRfUJci5QI1pXgPbDkRYSPGJOeOHIyyvSqajVGH4WRspx/UhAoaxSvhUK/9Avr/MFf2j4QUxUXkyhB9Ccb6u/Zt10m8CAwEAAQ==");
        iabHelper.startSetup(this);
    }

    public void changeToPremiumAccant() {
        showNotification.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (iabHelper != null) {
            iabHelper.dispose();
            iabHelper = null;
        }
    }

    public void purchasePremiumAccant() {
        iabHelper.launchPurchaseFlow(this, BOOK_TEST_Premium, 101, new IabHelper.OnIabPurchaseFinishedListener() {
            @Override
            public void onIabPurchaseFinished(IabResult result, Purchase info) {
                if (result.isSuccess()) {
                    if (info != null) {
                        changeToPremiumAccant();
                    } else {
                        Toast.makeText(MainActivity.this, " متاسفانه خرید انجام نشد", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101) {
            iabHelper.handleActivityResult(requestCode, resultCode, data);
        } else {
            super.onActivityResult(requestCode, resultCode, data);

        }

    }

    private void showNotificationAndToolbar() {
        showNotification = (Button) findViewById(R.id.showNotification);
        showNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                purchasePremiumAccant();
               /* Notification notification = new Notification.Builder(MainActivity.this)
                        .setTicker("نوتیف")
                        .setContentTitle("تایتل")
                        //.setContentText("این یک تست است...")
                        .setStyle(new Notification.BigTextStyle().bigText("ورم ایپسوم متن ساختگی با تولید سادگی نامفهوم از صنعت چاپ و با استفاده از طراحان گرافیک است. چاپگرها و متون بلکه روزنامه و مجله در ستون و سطرآنچنان که لازم است"))
                        .setSmallIcon(R.drawable.ic_contact_mail)
                        .setAutoCancel(true)
                        .setSound(path)
                        .build();
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(0, notification);*/
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("قدرتمندترین ماشین پولسازی دنیا");
        setSupportActionBar(toolbar);

        frag = (FragNav) getSupportFragmentManager().findFragmentById(R.id.Nov_frag);
        frag.install((DrawerLayout) findViewById(R.id.draw_layout), toolbar);

    }
}
