package com.arif.cabex.helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class GenericTextWatcher implements TextWatcher {
	private final EditText etSelf,etNext,etPrev;

	public GenericTextWatcher(EditText etPrev, EditText etSelf, EditText etNext) {
		this.etSelf = etSelf;
		this.etPrev = etPrev;
		this.etNext = etNext;
	}

	@Override
	public void afterTextChanged(Editable editable) {
		String text = editable.toString();
		if (text.length() == 1) {
			etNext.requestFocus();
			etNext.setSelection(etNext.length());

		}
		else if(text.length()==2){
			etSelf.setText(Character.toString(text.charAt(0)));
			etNext.append(Character.toString(text.charAt(1)));
			etNext.requestFocus();
			etNext.setSelection(etNext.length());

		}
		else {
			etPrev.requestFocus();
			etPrev.setSelection(etPrev.length());

		}
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		//TODO In future, we can add new algorithm to replace the current digit while typing in a digit box
	}
}