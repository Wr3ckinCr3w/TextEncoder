package com.csab.TextEncoder;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class MainActivity extends Activity {

    private Spinner mInputTypeSpinner, mTargetTypeSpinner;
    private EditText mOutputEditText;
    private String mInputTextString;
    private static final CharSequence DEFAULT_TARGET_TYPE = "Binary";
    private static final int ASCII_ID = 0;
    private static final int DECIMAL_ID = 1;
    private static final int HEX_ID = 2;
    private static final int BINARY_ID = 3;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // TODO: Holo Color resources
        // Initialize UI objects
        mInputTypeSpinner = (Spinner) findViewById(R.id.input_spinner);
        mTargetTypeSpinner = (Spinner) findViewById(R.id.target_spinner);
        mOutputEditText = (EditText) findViewById(R.id.output_text);
        EditText inputEditText = (EditText) findViewById(R.id.input_text);
        Button convertButton = (Button) findViewById(R.id.convert_button);
        Button copyButton1 = (Button) findViewById(R.id.copy_button1);
        Button copyButton2 = (Button) findViewById(R.id.copy_button2);

        // Set up Spinner arrays
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(getBaseContext(),
                R.array.types_array, R.layout.dropdown_item);
        mInputTypeSpinner.setAdapter(dataAdapter);
        mTargetTypeSpinner.setAdapter(dataAdapter);
        mTargetTypeSpinner.setSelection(dataAdapter.getPosition(DEFAULT_TARGET_TYPE));

        // Add TextChanged listener
        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) { }

            @Override
            public void afterTextChanged(Editable editable) {
                mInputTextString = editable.toString();
            }
        });

        // TODO: Button onClick listener to check valid conversion and convert
        // 1. Kick off start() method
        // 2. If inputType = outputType, set the resultText to the inputText
        // 3. Otherwise, real conversion. Check if input text syntax is valid (boolean)
        // 4. -> if true, create objects, update textbox. if false, generate error toast message

        // Add onClick listener
        convertButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Message message = new HexMessage(mInputTextString);
                AsciiMessage am = ((HexMessage)message).toAsciiMessage();
                //Toast.makeText(getBaseContext(), message.toString(), Toast.LENGTH_LONG).show();
                //startProcess();
                updateOutputTextBox(am.toString());
                }
        });
    }

    private void startProcess() {
        // Check if input type and output type are the same
        if (selectedTypesAreEqual()) {
            updateOutputTextBox(mInputTextString);
        }

        Message message;
        // Construct appropriate message type
        switch ((int) mInputTypeSpinner.getSelectedItemId()) {
            case ASCII_ID: message = new AsciiMessage(mInputTextString); break;
            case DECIMAL_ID: message = new DecimalMessage(mInputTextString); break;
            case HEX_ID: message = new HexMessage(mInputTextString); break;
            case BINARY_ID: message = new BinaryMessage(mInputTextString); break;
        }

        // Attempt appropriate encoding
        switch ((int) mTargetTypeSpinner.getSelectedItemId()) {
            case ASCII_ID: break;
            case DECIMAL_ID: // message = ((type)message).toDecimalMessage(); break;
            case HEX_ID: break;
            case BINARY_ID: break;
        }
    }

    private boolean selectedTypesAreEqual() {
        return (mInputTypeSpinner.getSelectedItem().toString().equals(
                mTargetTypeSpinner.getSelectedItem().toString()));
    }

    private void updateOutputTextBox(String output) {
        mOutputEditText.setText(output);
    }


}

