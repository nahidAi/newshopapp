package button.prg.com.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Setting extends AppCompatActivity{
	private SeekBar seekBar;
	private TextView text;
	private Database db;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_setting );
		seekBar = (SeekBar) findViewById( R.id.seekBar );
		text = (TextView) findViewById( R.id.text_setting );
		ImageButton imageButton = (ImageButton) findViewById(R.id.image_save_setting);
		ImageView imageView = (ImageView) findViewById(R.id.image_setting);

		db=new Database( Setting.this );
		int value = db.returnsize();
		seekBar.setProgress( value );
		text.setTextSize( value );
		imageView.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick (View view) {
				Intent intent=new Intent( Setting.this,MainActivity.class );
				startActivity( intent );

			}
		} );

		imageButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick (View view) {
				db.updatesize( seekBar.getProgress() );
				db.close();
				String note="تغییرات با موفقیت اعمال شد";
				Toast.makeText( Setting.this, note, Toast.LENGTH_SHORT ).show();


			}
		} );
		seekBar.setMax( 50 );
		seekBar.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener(){
			@Override
			public void onProgressChanged (SeekBar seekBar, int i, boolean b) {
				text.setTextSize( i );

			}

			@Override
			public void onStartTrackingTouch (SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch (SeekBar seekBar) {

			}
		} );
	}
}
