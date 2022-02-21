package cz.pikadorama.lakatos.app;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import java.io.IOException;

import cz.pikadorama.lakatos.R;
import cz.pikadorama.lakatos.app.adapter.TextAdapter;

public class MainActivity extends Activity {

    private static final int STORAGE_REQUEST_PERMISSION = 100;
    private static final int SETTINGS_REQUEST_PERMISSION = 200;

    private Ringtones ringtones;
    private Integer ringtoneType;
    private Sound sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new TextAdapter(getApplicationContext(), Sound.getMessages()));

        gridview.setOnItemClickListener((parent, v, position, id) -> {
            MediaPlayer mp = MediaPlayer.create(MainActivity.this, Sound.values()[position].getSoundId());
            mp.setOnCompletionListener(MediaPlayer::release);
            mp.start();
        });
        gridview.setOnItemLongClickListener((parent, view, position, id) -> {
            showActionDialog(Sound.values()[position]);
            return true;
        });

        this.ringtones = new Ringtones(this);
    }

    private void showActionDialog(final Sound sound) {
        String[] items = {
                getString(R.string.dialog_action_set_as_ringtone),
                getString(R.string.dialog_action_share)
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, (dialog, item) -> {
            switch (item) {
                case 0:
                    showRingtoneTypeDialog(sound);
                    break;
                case 1:
                    share(sound);
                    break;
            }
        });
        builder.show();
    }

    private void showRingtoneTypeDialog(final Sound sound) {
        final String[] items = {
                getString(R.string.dialog_action_ringtone_ringtone),
                getString(R.string.dialog_action_ringtone_notification),
                getString(R.string.dialog_action_ringtone_alarm)
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            final int[] ringtoneTypes = {
                    RingtoneManager.TYPE_RINGTONE,
                    RingtoneManager.TYPE_NOTIFICATION,
                    RingtoneManager.TYPE_ALARM
            };

            @Override
            public void onClick(DialogInterface dialog, int item) {
                Context appContext = getApplicationContext();

                ringtoneType = ringtoneTypes[item];
                MainActivity.this.sound = sound;

                if (ContextCompat.checkSelfPermission(appContext,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]
                            {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_REQUEST_PERMISSION);
                } else if (!Settings.System.canWrite(appContext)) {
                    showWriteSettingsPermissionDialog();
                } else {
                    setAsRingtone(sound, ringtoneTypes[item]);
                }
            }
        });
        builder.show();
    }

    private void share(Sound sound) {
        Uri soundUri = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/raw/" + sound.getSoundName());

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("audio/ogg").putExtra(Intent.EXTRA_STREAM, soundUri);

        startActivity(Intent.createChooser(share, "Vyberte, jak to chcete sdílet:"));
    }

    private void setAsRingtone(Sound sound, Integer type) {
        if (sound != null && type != null) {
            try {
                ringtones.setRingtone(sound, type);
                Toast.makeText(MainActivity.this, "Hláška úspěšně nastavena jako " + getRingtoneTypeName(type) + ".",
                        Toast.LENGTH_SHORT).show();

                MainActivity.this.sound = null;
                ringtoneType = null;
            } catch (SecurityException e) {
                Log.e("Lakatos", e.getMessage(), e);
                Toast.makeText(MainActivity.this, getText(R.string.error_ringtone_saved_or_not_set), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Log.e("Lakatos", e.getMessage(), e);
                Toast.makeText(MainActivity.this, "Bohužel se nepodařilo nastavit hlášku jako " + getRingtoneTypeName(type) + ".",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == STORAGE_REQUEST_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Super, aplikace má nyní oprávnění zápisu do úložiště!", Toast.LENGTH_SHORT).show();

                if (!Settings.System.canWrite(getApplicationContext())) {
                    showWriteSettingsPermissionDialog();
                } else {
                    setAsRingtone(sound, ringtoneType);
                }
            } else {
                Toast.makeText(MainActivity.this, "Aplikace nemá oprávnění zápisu do úložiště!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SETTINGS_REQUEST_PERMISSION) {
            setAsRingtone(sound, ringtoneType);
        }
    }

    private void showWriteSettingsPermissionDialog() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, SETTINGS_REQUEST_PERMISSION);
    }

    private String getRingtoneTypeName(int ringtoneType) {
        switch (ringtoneType) {
            case RingtoneManager.TYPE_RINGTONE:
                return "vyzvánění";
            case RingtoneManager.TYPE_NOTIFICATION:
                return "oznámení";
            case RingtoneManager.TYPE_ALARM:
                return "tón budíku";
            default:
                return null;
        }
    }
}
