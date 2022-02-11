package com.arif.cabex.helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class GenericTextWatcher implements TextWatcher {
	private EditText self;
	private EditText etPrev;
	private EditText etNext;

	public GenericTextWatcher(EditText self, EditText etNext, EditText etPrev) {
		this.self = self;
		this.etPrev = etPrev;
		this.etNext = etNext;
	}

	@Override
	public void afterTextChanged(Editable editable) {
		String text = editable.toString();
		if (text.length() == 1) {
			etNext.requestFocus();
		}
		else if (text.length() == 0)
			etPrev.requestFocus();
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		//TODO In future, we can add new algorithm to replace the current digit while typing in a digit box
	}
}