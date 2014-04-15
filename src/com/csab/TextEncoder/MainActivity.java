package com.csab.TextEncoder;

import android.app.Activity;
import android.text.ClipboardManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import org.apache.commons.codec.DecoderException;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity {

    private ClipboardManager mClipboard;
    private Spinner mInputTypeSpinner, mTargetTypeSpinner;
    private EditText mInputEditText, mOutputEditText;
    private String mInputTextString;
    private static final CharSequence DEFAULT_TARGET_TYPE = "Binary";
    private static final int ASCII_ID = 0;
    private static final int DECIMAL_ID = 1;
    private static final int HEX_ID = 2;
    private static final int BINARY_ID = 3;


    @Override
    @SuppressWarnings("deprecation")
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
        getMenuInflater().inflate(R.menu.activity_main, menu);
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
                // TODO: fill in stub
                return true;
            case R.id.donate:
                // TODO: fill in stub
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startProcess() {
        // Check if input type and output type are the same
        if (selectedTypesAreEqual()) {
            updateOutputTextBox(mInputTextString);
        }

        Message message;
        // Construct appropriate message type, call encoding method
        try {
            switch ((int) mInputTypeSpinner.getSelectedItemId()) {
                case ASCII_ID:
                    message = new AsciiMessage(mInputTextString);
                    convertFromAscii((AsciiMessage)message);
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
            }
        } catch (MessageConstructException | DecoderException e) {
            Toast.makeText(getBaseContext(), Message.EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        // TODO: add Base64Message, OctalMessage
    }

    private boolean selectedTypesAreEqual() {
        return (mInputTypeSpinner.getSelectedItem().toString().equals(
                mTargetTypeSpinner.getSelectedItem().toString()));
    }

    private void updateOutputTextBox(String output) {
        mOutputEditText.setText(output);
    }
      
    private void convertFromAscii(AsciiMessage message) {
        try {
            switch ((int) mTargetTypeSpinner.getSelectedItemId()) {
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
            Toast.makeText(getBaseContext(), Message.EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void convertFromBinary(BinaryMessage message) {
        switch ((int) mTargetTypeSpinner.getSelectedItemId()) {
            case ASCII_ID:
                AsciiMessage am = message.toAsciiMessage();
                updateOutputTextBox(am.toString());
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
    }

    private void convertFromDecimal(DecimalMessage message) {
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
                case HEX_ID:
                    HexMessage hm = message.toHexMessage();
                    updateOutputTextBox(hm.toString());
                    break;
            }
        } catch (MessageConstructException e) {
            Toast.makeText(getBaseContext(), Message.EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void convertFromHex(HexMessage message) {
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
            }
        } catch (MessageConstructException e) {
            Toast.makeText(getBaseContext(), Message.EXCEPTION_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }
}

