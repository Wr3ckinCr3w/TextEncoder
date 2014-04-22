package com.csab.TextEncoder;

import android.text.ClipboardManager;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import org.apache.commons.codec.DecoderException;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MainActivity extends SherlockActivity {

    private ClipboardManager mClipboard;
    private Spinner mInputTypeSpinner, mTargetTypeSpinner;
    private EditText mInputEditText, mOutputEditText;
    private String mInputTextString;
    private static final String DEFAULT_TARGET_TYPE = "Binary";
    private static final String EQUAL_TYPES_MESSAGE = "Input / output types are the same";
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
                R.array.types_array, R.layout.dropdown_item);
        mInputTypeSpinner.setAdapter(dataAdapter);
        mTargetTypeSpinner.setAdapter(dataAdapter);
        mTargetTypeSpinner.setSelection(dataAdapter.getPosition(DEFAULT_TARGET_TYPE));

        // Load HexMessage HashMaps
        loadHashMaps();

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

    private void startProcess() {
        // Check if input type and output type are the same
        if (selectedTypesAreEqual()) {
            Toast.makeText(getBaseContext(), EQUAL_TYPES_MESSAGE, Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO: Implement reflection to call conversion methods at runtime
        Message message;
        // Construct appropriate message type, call encoding method
        try {
            switch ((int) mInputTypeSpinner.getSelectedItemId()) {
                case ASCII_ID:
                    message = new AsciiMessage(mInputTextString);
                    convertFromAscii((AsciiMessage)message);
                    break;
                case BASE64_ID:
                    message = new Base64Message(mInputTextString);
                    convertFromBase64((Base64Message) message);
                    break;
                case BINARY_ID:
                    message = new BinaryMessage(mInputTextString);
                    convertFromBinary((BinaryMessage) message);
                    break;
                case DECIMAL_ID:
                    message = new DecimalMessage(mInputTextString);
                    convertFromDecimal((DecimalMessage) message);
                    break;
                case HEX_ID:
                    message = new HexMessage(mInputTextString);
                    convertFromHex((HexMessage) message);
                    break;
                case OCTAL_ID:
                    message = new OctalMessage(mInputTextString);
                    convertFromOctal((OctalMessage) message);
                    break;
            }
        } catch (MessageConstructException | NumberFormatException | DecoderException e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean selectedTypesAreEqual() {
        return (mInputTypeSpinner.getSelectedItem().toString().equals(
                mTargetTypeSpinner.getSelectedItem().toString()));
    }

    private void openPlayStore() {
        final String appPackageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" +
                    appPackageName)));
        }
    }

    private void updateOutputTextBox(String output) {
        mOutputEditText.setText(output);
    }
      
    private void convertFromAscii(AsciiMessage message) {
        try {
            switch ((int) mTargetTypeSpinner.getSelectedItemId()) {
                case BASE64_ID:
                    Base64Message b64m = message.toBase64Message();
                    updateOutputTextBox(b64m.toString());
                    break;
                case BINARY_ID:
                    BinaryMessage bm = message.toBinaryMessage();
                    updateOutputTextBox(bm.toString());
                    break;
                case DECIMAL_ID:
                    DecimalMessage dm = message.toDecimalMessage();
                    updateOutputTextBox(dm.toString());
                    break;
                case HEX_ID:
                    HexMessage hm = message.toHexMessage();
                    updateOutputTextBox(hm.toString());
                    break;
                case OCTAL_ID:
                    OctalMessage om = message.toOctalMessage();
                    updateOutputTextBox(om.toString());
                    break;
            }
        } catch (MessageConstructException e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void convertFromBase64(Base64Message message) {
        try {
            switch ((int) mTargetTypeSpinner.getSelectedItemId()) {
                case ASCII_ID:
                    AsciiMessage am = message.toAsciiMessage();
                    updateOutputTextBox(am.toString());
                    break;
                case BINARY_ID:
                    BinaryMessage bm = message.toBinaryMessage();
                    updateOutputTextBox(bm.toString());
                    break;
                case DECIMAL_ID:
                    DecimalMessage dm = message.toDecimalMessage();
                    updateOutputTextBox(dm.toString());
                    break;
                case HEX_ID:
                    HexMessage hm = message.toHexMessage();
                    updateOutputTextBox(hm.toString());
                    break;
                case OCTAL_ID:
                    OctalMessage om = message.toOctalMessage();
                    updateOutputTextBox(om.toString());
                    break;
            }
        } catch (MessageConstructException e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void convertFromBinary(BinaryMessage message) {
        try {
            switch ((int) mTargetTypeSpinner.getSelectedItemId()) {
                case ASCII_ID:
                    AsciiMessage am = message.toAsciiMessage();
                    updateOutputTextBox(am.toString());
                    break;
                case BASE64_ID:
                    Base64Message b64m = message.toBase64Message();
                    updateOutputTextBox(b64m.toString());
                    break;
                case DECIMAL_ID:
                    DecimalMessage dm = message.toDecimalMessage();
                    updateOutputTextBox(dm.toString());
                    break;
                case HEX_ID:
                    HexMessage hm = message.toHexMessage();
                    updateOutputTextBox(hm.toString());
                    break;
                case OCTAL_ID:
                    OctalMessage om = message.toOctalMessage();
                    updateOutputTextBox(om.toString());
                    break;
            }
        } catch (MessageConstructException e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void convertFromDecimal(DecimalMessage message) {
        try {
            switch ((int) mTargetTypeSpinner.getSelectedItemId()) {
                case ASCII_ID:
                    AsciiMessage am = message.toAsciiMessage();
                    updateOutputTextBox(am.toString());
                    break;
                case BASE64_ID:
                    Base64Message b64m = message.toBase64Message();
                    updateOutputTextBox(b64m.toString());
                    break;
                case BINARY_ID:
                    BinaryMessage bm = message.toBinaryMessage();
                    updateOutputTextBox(bm.toString());
                    break;
                case HEX_ID:
                    HexMessage hm = message.toHexMessage();
                    updateOutputTextBox(hm.toString());
                    break;
                case OCTAL_ID:
                    OctalMessage om = message.toOctalMesage();
                    updateOutputTextBox(om.toString());
                    break;
            }
        } catch (MessageConstructException e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void convertFromHex(HexMessage message) {
        try {
            switch ((int) mTargetTypeSpinner.getSelectedItemId()) {
                case ASCII_ID:
                    AsciiMessage am = message.toAsciiMessage();
                    updateOutputTextBox(am.toString());
                    break;
                case BASE64_ID:
                    Base64Message b64m = message.toBase64Message();
                    updateOutputTextBox(b64m.toString());
                    break;
                case BINARY_ID:
                    BinaryMessage bm = message.toBinaryMessage();
                    updateOutputTextBox(bm.toString());
                    break;
                case DECIMAL_ID:
                    DecimalMessage dm = message.toDecimalMessage();
                    updateOutputTextBox(dm.toString());
                    break;
                case OCTAL_ID:
                    OctalMessage om = message.toOctalMessage();
                    updateOutputTextBox(om.toString());
                    break;
            }
        } catch (MessageConstructException e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void convertFromOctal(OctalMessage message) {
        try {
            switch ((int) mTargetTypeSpinner.getSelectedItemId()) {
                case ASCII_ID:
                    AsciiMessage am = message.toAsciiMessage();
                    updateOutputTextBox(am.toString());
                    break;
                case BASE64_ID:
                    Base64Message b64m = message.toBase64Message();
                    updateOutputTextBox(b64m.toString());
                    break;
                case BINARY_ID:
                    BinaryMessage bm = message.toBinaryMessage();
                    updateOutputTextBox(bm.toString());
                    break;
                case DECIMAL_ID:
                    DecimalMessage dm = message.toDecimalMessage();
                    updateOutputTextBox(dm.toString());
                    break;
                case HEX_ID:
                    HexMessage hm = message.toHexMessage();
                    updateOutputTextBox(hm.toString());
                    break;
            }
        } catch (MessageConstructException e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadHashMaps() {
        // Load first map
        HexMessage.mapLongString.put(10L, "A");
        HexMessage.mapLongString.put(11L, "B");
        HexMessage.mapLongString.put(12L, "C");
        HexMessage.mapLongString.put(13L, "D");
        HexMessage.mapLongString.put(14L, "E");
        HexMessage.mapLongString.put(15L, "F");
        // Load second map
        HexMessage.mapStringLong.put("A", 10L);
        HexMessage.mapStringLong.put("B", 11L);
        HexMessage.mapStringLong.put("C", 12L);
        HexMessage.mapStringLong.put("D", 13L);
        HexMessage.mapStringLong.put("E", 14L);
        HexMessage.mapStringLong.put("F", 15L);

    }
}

