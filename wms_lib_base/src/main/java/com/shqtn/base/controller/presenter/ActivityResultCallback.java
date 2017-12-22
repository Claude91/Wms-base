package com.shqtn.base.controller.presenter;

import android.content.Intent;

/**
 * Created by android on 2017/9/29.
 */

public interface ActivityResultCallback {
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
