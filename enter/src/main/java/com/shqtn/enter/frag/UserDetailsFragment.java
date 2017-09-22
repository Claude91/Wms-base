package com.shqtn.enter.frag;

import android.os.Bundle;

import com.shqtn.base.BaseFragment;

/**
 * Created by android on 2017/9/21.
 */

public class UserDetailsFragment extends BaseFragment  {
    public static UserDetailsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        UserDetailsFragment fragment = new UserDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
