package info.getdrip.beta.yevsikov_homework_011_01;

        import android.app.Activity;
        import android.content.SharedPreferences;
        import android.content.SharedPreferences.Editor;
        import android.os.Bundle;
        import android.os.Environment;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.Toast;

        import java.io.BufferedReader;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.OutputStream;
        import java.io.OutputStreamWriter;


public class MainActivity extends Activity implements OnClickListener {

    private EditText etText;
    private Button btnSave, btnLoad;
    private CheckBox chb_001;

    private SharedPreferences sPref;

    final String SAVED_TEXT = "saved_text";
    final String APP_NAME = "Lesson1";

    private final static String FILENAME = "sample.txt"; // имя файла



    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etText = (EditText) findViewById(R.id.etText);

        chb_001 = (CheckBox) findViewById(R.id.chb_001);
        chb_001.setOnClickListener(this);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        btnLoad = (Button) findViewById(R.id.btnLoad);
        btnLoad.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadSettings();  //task 1 - Loading checkBox state
        openFile(FILENAME); //task 2 - Loading file to EditText
    }


    @Override
    protected void onStop() {
        super.onStop();

        saveSettings();
        saveFile(FILENAME);  //task 2 - Saving text from EditText to file

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.chb_001:
              saveSettings(); //task 1 - saving checkBox state
                break;

            default:
                break;
        }
    }

    void saveSettings() {
        sPref = getSharedPreferences(APP_NAME, MODE_PRIVATE);
        Editor ed = sPref.edit();

        ed.putBoolean("isChecked",chb_001.isChecked());

        ed.commit();

    }

    void loadSettings() {
        sPref = getSharedPreferences(APP_NAME, MODE_PRIVATE);

        boolean isCh = sPref.getBoolean("isChecked", false);
        chb_001.setChecked(isCh);

       // Toast.makeText(this, "Text loaded", Toast.LENGTH_SHORT).show();
    }

/*
    public boolean isExternalStorageWritable()
    {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state))
        {
            return true;
        }
        return false;
    }
*/

    // Метод для открытия файла
    private void openFile(String fileName) {
        try {
            InputStream inputStream = openFileInput(fileName);

            if (inputStream != null) {
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(isr);
                String line;
                StringBuilder builder = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    builder.append(line + "\n");
                }

                inputStream.close();
                etText.setText(builder.toString());
            }
        } catch (Throwable t) {
            Toast.makeText(getApplicationContext(),
                    "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    // Метод для сохранения файла
    private void saveFile(String fileName) {
        try {
            OutputStream outputStream = openFileOutput(fileName, 0);
            OutputStreamWriter osw = new OutputStreamWriter(outputStream);
            osw.write(etText.getText().toString());
            osw.close();
        } catch (Throwable t) {
            Toast.makeText(getApplicationContext(),
                    "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }

}