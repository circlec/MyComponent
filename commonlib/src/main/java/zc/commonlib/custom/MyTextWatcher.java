package zc.commonlib.custom;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * @作者 zhouchao
 * @日期 2019/4/22
 * @描述
 */
public abstract class MyTextWatcher implements TextWatcher {


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public abstract void afterTextChanged(Editable editable) ;
}
