package com.example.mynoteenglish.viewmodel;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mynoteenglish.model.OnselectchangeEdit;
import com.example.mynoteenglish.view.MainAddItem;

public class EditTextCursorWatcher extends androidx.appcompat.widget.AppCompatEditText {
    OnselectchangeEdit onSelectionChanged;
    public EditTextCursorWatcher(Context context) {
        super(context);

    }

    public EditTextCursorWatcher(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextCursorWatcher(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
      //  Toast.makeText(getContext(), "selStart is " + selStart + "selEnd is " + selEnd, Toast.LENGTH_LONG).show();
        onSelectionChanged.Getstartend(selStart,selEnd);
    }
    public void getStartEnd( OnselectchangeEdit onselectchangeEdit)
    {
        this.onSelectionChanged= onselectchangeEdit;
    }

}
