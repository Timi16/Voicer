package com.mycompany.voicer;

import android.app.*;
import android.os.*;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.*;
import android.content.*;
import android.widget.AdapterView.*;
import android.speech.*;
import java.util.*;
import com.mycompany.myapp2.*;
public class MainActivity extends Activity 
{
	private TextView textView;
	private Button button;
	private SpeechRecognizer speechRecognizer;
	private Intent speechRecognizerIntent;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		textView = findViewById(R.id.textView);
		button = findViewById(R.id.microphoneButton);
		speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
		speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
		
		SpeechRecognitionListener listener = new SpeechRecognitionListener();
		speechRecognizer.setRecognitionListener(listener);
		button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					speechRecognizer.startListening(speechRecognizerIntent);
				}
			});
		textView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String text = textView.getText().toString();
					if (!text.isEmpty()) {
						android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
						ClipData clip = ClipData.newPlainText("Copied Text", text);
						clipboard.setPrimaryClip(clip);
						Toast.makeText(MainActivity.this, "Text copied to clipboard", Toast.LENGTH_SHORT).show();
					}
				}
			});
    }
	private class SpeechRecognitionListener implements RecognitionListener {
		@Override
		public void onReadyForSpeech(Bundle params) {
			// Speech recognition is ready
		}

		@Override
		public void onBeginningOfSpeech() {
			// Speech recognition has started
		}

		@Override
		public void onRmsChanged(float rmsdB) {
			// RMS (Root Mean Square) value has changed
		}

		@Override
		public void onBufferReceived(byte[] buffer) {
			// Audio buffer received
		}

		@Override
		public void onEndOfSpeech() {
			// Speech recognition has ended
		}

		@Override
		public void onError(int error) {
			// Speech recognition error occurred
		}

		@Override
		public void onResults(Bundle results) {
			ArrayList<String> speechResults = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
			if (speechResults != null && speechResults.size() > 0) {
				String recognizedSpeech = speechResults.get(0);
				textView.setText(recognizedSpeech);
			}
		}

		@Override
		public void onPartialResults(Bundle partialResults) {
			// Partial speech recognition results received
		}

		@Override
		public void onEvent(int eventType, Bundle params) {
			// Speech recognition event occurred
		}
	}
	
}
