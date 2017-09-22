package com.shqtn.base.controller.view;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by android on 2017/9/22.
 */

public interface IContext {
    void startActivity(Class clazz);

    void startActivity(Class clazz, Bundle bundle);

    void startActivity(Class clazz, int requestCode);

    void startActivity(Class clazz, Bundle bundle, int requestCode);

    void closeActivity();

    Context getContext();

    String getString(int id);
}
