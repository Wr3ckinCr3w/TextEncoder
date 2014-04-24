package com.csab.TextEncoder;

import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.Method;
import android.content.Context;
import android.content.Intent;
import android.content.ActivityNotFoundException;
import android.text.ClipboardManager;
import android.os.Bundle;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MainActivity extends SherlockActivity {

    private ClipboardManager mClipboard;
    private Spinner mInputTypeSpinner, mTargetTypeSpinner;
    private EditText mInputEditText, mOutputEditText;
    private String mInputTextString;
    private Map<Integer, String> mClassMap = new HashMap<>();
    private boolean mIsInitialSelection = true;
    private static final int ASCII_ID = 0;
    private static final int BINARY_ID = 1;
    private static final int DECIMAL_ID = 2;
    private static final int HEX_ID = 3;
    private static final int OCTAL_ID = 4;
    private static final int BASE64_ID = 5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Load HashMaps
        loadHashMaps();

        // Initialize UI objects
        mClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        mInputTypeSpinner = (Spinner) findViewById(R.id.input_spinner);
        mTargetTypeSpinner = (Spinner) findViewById(R.id.target_spinner);
        mInputEditText = (EditText) findViewById(R.id.input_text);
        mOutputEditText = (EditText) findViewById(R.id.output_text);
        Button convertButton = (Button) findViewById(R.id.convert_button);
        Button topCopyButton = (Button) findViewById(R.id.copy_button1);
        Button bottomCopyButton = (Button) findViewById(R.id.copy_button2);

        // Set up Spinner arrays
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(getBaseContext(),
                R.array.array_all, R.layout.dropdown_item);
        mInputTypeSpinner.setAdapter(dataAdapter);
        mTargetTypeSpinner.setAdapter(dataAdapter);

        // Add OnItemSelected listener
        mInputTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String name;
                if (pos == ASCII_ID) {
                    name = "array_all";
                } else if (pos > ASCII_ID && pos < BASE64_ID) {
                    name = "array_nob64";
                } else {
                    name = "array_ascii_only";
                }
                int resource = getResources().getIdentifier(name, "array", getPackageName());
                if (mIsInitialSelection) {
                    mTargetTypeSpinner.setSelection(BINARY_ID);
                    mIsInitialSelection = false;
                } else {
                    mTargetTypeSpinner.setAdapter(ArrayAdapter.createFromResource(getBaseContext(),
                        resource, R.layout.dropdown_item));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Add OnItemSelected listener
        mInputTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String name;
                if (pos == ASCII_ID) {
                    name = "array_all";
                } else if (pos > ASCII_ID && pos < BASE64_ID) {
                    name = "array_nob64";
                } else {
                    name = "array_ascii_only";
                }
                int resource = getResources().getIdentifier(name, "array", getPackageName());
                mTargetTypeSpinner.setAdapter(ArrayAdapter.createFromResource(getBaseContext(),
                    resource, R.layout.dropdown_item));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Add TextChanged listener
        mInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) { }

            @Override
            public void afterTextChanged(Editable editable) {
                mInputTextString = editable.toString();
            }
        });

        // Add OnClick listeners
        convertButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startProcess();
            }
        });

        topCopyButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mClipboard.setText(mInputEditText.getText());
            }
        });

        bottomCopyButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mClipboard.setText(mOutputEditText.getText());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getSupportMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear_all:
                mInputEditText.setText("");
                mOutputEditText.setText("");
                return true;
            case R.id.rate_app:
                openPlayStore();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openPlayStore() {
        final String appPackageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.market_url)
                    + appPackageName)));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.http_url)
                    + appPackageName)));
        }
    }

    private void startProcess() {
        // Check if input type and output type are the same
        if (selectedTypesAreEqual()) {
            Toast.makeText(getBaseContext(), getString(R.string.equal_types_message), Toast.LENGTH_SHORT).show();
            return;
        }

        // Attempt conversion from input -> target type
        try {
            Class<?> clazz = Class.forName(mClassMap.get(mInputTypeSpinner.getSelectedItemPosition()));
            Object inputObject = clazz.getConstructor(
                    String.class, Context.class).newInstance(mInputTextString, getBaseContext());
            Method method = inputObject.getClass().getMethod("convert", Class.class);
            Object outputObject = method.invoke(inputObject, Class.forName(
                    mClassMap.get(mTargetTypeSpinner.getSelectedItemPosition())));
            updateOutputTextBox(outputObject.toString());
        } catch (Exception e) {
            while(e.getCause() != null)
                e = (Exception)e.getCause();
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean selectedTypesAreEqual() {
        return (mInputTypeSpinner.getSelectedItem().toString().equals(
                mTargetTypeSpinner.getSelectedItem().toString()));
    }

    private void updateOutputTextBox(String output) {
        mOutputEditText.setText(output);
    }
      
    private void loadHashMaps() {
        // Load Message subclass map
        mClassMap.put(ASCII_ID, AsciiMessage.class.getCanonicalName());
        mClassMap.put(BINARY_ID, BinaryMessage.class.getCanonicalName());
        mClassMap.put(DECIMAL_ID, DecimalMessage.class.getCanonicalName());
        mClassMap.put(HEX_ID, HexMessage.class.getCanonicalName());
        mClassMap.put(OCTAL_ID, OctalMessage.class.getCanonicalName());
        mClassMap.put(BASE64_ID, Base64Message.class.getCanonicalName());

        // Load first Hex map
        HexMessage.mapLongString.put(10L, "A");
        HexMessage.mapLongString.put(11L, "B");
        HexMessage.mapLongString.put(12L, "C");
        HexMessage.mapLongString.put(13L, "D");
        HexMessage.mapLongString.put(14L, "E");
        HexMessage.mapLongString.put(15L, "F");
        // Load second Hex map
        HexMessage.mapStringLong.put("A", 10L);
        HexMessage.mapStringLong.put("B", 11L);
        HexMessage.mapStringLong.put("C", 12L);
        HexMessage.mapStringLong.put("D", 13L);
        HexMessage.mapStringLong.put("E", 14L);
        HexMessage.mapStringLong.put("F", 15L);

    }
}

