/*
 * Copyright 2014 Hieu Rocker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package c.g.a.x.module_chat.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import c.g.a.x.module_chat.R;

/**
 * @author Hieu Rocker (rockerhieu@gmail.com).
 */
public class MoreFragment extends Fragment {

    private LinearLayout layout1;
    private LinearLayout layout2;

    private OnImageChooseListener listener;

    public static MoreFragment newInstance() {
        MoreFragment fragment = new MoreFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        layout1 = (LinearLayout) view.findViewById(R.id.chat_menu_images);
        layout2 = (LinearLayout) view.findViewById(R.id.chat_menu_photo);

        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PictureSelector.create(MoreFragment.this)
//                        .openGallery(PictureMimeType.ofImage())
//                        .maxSelectNum(1)// 最大图片选择数量 int
//                        .minSelectNum(1)// 最小选择数量 int
//                        .selectionMode(PictureConfig.SINGLE)
//                        .isPreviewImage(true)// 是否可预览图片 true or false
//                        .isCamera(false)// 是否显示拍照按钮 true or false
//                        .imageEngine(GlideEngine.createGlideEngine())
//                        .forResult(new OnResultCallbackListener<LocalMedia>() {
//                            @Override
//                            public void onResult(List<LocalMedia> result) {
//                                // onResult Callback
//                                String path = result.get(0).getPath();
//                                upload(path);
//                            }
//
//                            @Override
//                            public void onCancel() {
//                            }
//                        });
            }
        });
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                PictureSelector.create(MoreFragment.this)
//                        .openCamera(PictureMimeType.ofImage())
//                        .imageEngine(GlideEngine.createGlideEngine())
//                        .forResult(new OnResultCallbackListener<LocalMedia>() {
//                            @Override
//                            public void onResult(List<LocalMedia> result) {
//                                String path = result.get(0).getPath();
//                                upload(path);
//                            }
//
//                            @Override
//                            public void onCancel() {
//                                // onCancel Callback
//                            }
//                        });
            }
        });
        return view;
    }


    private void upload(String path) {
//        UrlAction.context(getContext()).upload(Constant.IMAGE_UPLOAD, new File(path)).toObservable()
//                .subscribe((Consumer<ResponseBody>) responseBody -> {
//                            String ss = responseBody.string();
//                            listener.onImageChoose(ss);
//                        }, (throwable) -> {
//                            Logger.e(throwable.toString());
//                        },
//                        () -> {
//                        });
    }

    public void setListener(OnImageChooseListener listener) {
        this.listener = listener;
    }

    public interface OnImageChooseListener {
        void onImageChoose(String url);
    }
}
