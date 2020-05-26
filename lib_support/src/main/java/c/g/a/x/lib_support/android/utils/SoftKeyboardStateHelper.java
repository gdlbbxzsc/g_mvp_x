/*
 * Copyright (c) 2015, 张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package c.g.a.x.lib_support.android.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * 软键盘监听器助手
 *
 * @author kymjs (http://www.kymjs.com)
 */
public class SoftKeyboardStateHelper implements ViewTreeObserver.OnGlobalLayoutListener {

    private View mContentView;
    private int mOriginHeight;
    private int mPreHeight;
    private onKeyboardChangeListener listener;

    public void setKeyBoardListener(onKeyboardChangeListener keyBoardListen) {
        this.listener = keyBoardListen;
    }

    public SoftKeyboardStateHelper(Activity contextObj) {
        if (contextObj == null) {
            return;
        }
        mContentView = contextObj.findViewById(android.R.id.content);
        if (mContentView == null) return;

        mContentView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    public void close() {
        mContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }


    @Override
    public void onGlobalLayout() {
        int currHeight = mContentView.getHeight();
        if (currHeight == 0) {
            return;
        }

        if (mPreHeight == 0) {
            mOriginHeight = mPreHeight = currHeight;
            return;
        }

        if (mPreHeight == currHeight) return;

        mPreHeight = currHeight;


        if (listener == null) return;

        boolean isShow = !(mOriginHeight == currHeight);
        int keyboardHeight = mOriginHeight - currHeight;

        listener.onKeyboardChange(isShow, keyboardHeight);

    }


    public interface onKeyboardChangeListener {
        void onKeyboardChange(boolean isShow, int keyboardHeight);
    }

}
