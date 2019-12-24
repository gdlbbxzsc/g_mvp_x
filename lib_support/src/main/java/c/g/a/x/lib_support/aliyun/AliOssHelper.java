package c.g.a.x.lib_support.aliyun;

import android.app.Activity;
import android.content.Context;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import c.g.a.x.lib_support.BuildConfig;
import c.g.a.x.lib_support.android.utils.Logger;


public class AliOssHelper {

    private OSSClient ossClient;

    //需要配置的数据
    private static AliOssConfig aliOssConfig;

    public static void aliOssConfig(AliOssConfig config) {
        aliOssConfig = config;
    }

    public static UpLoadTask getUpLoadTask(Activity activity) {
        return getInstance(activity).createUpLoadTask(activity);
    }

    private static volatile AliOssHelper aliOssHelper;

    private static AliOssHelper getInstance(Context context) {
        if (aliOssHelper == null) {
            synchronized (AliOssHelper.class) {
                if (aliOssHelper == null) {
                    aliOssHelper = new AliOssHelper(context);
                }
            }
        }
        return aliOssHelper;
    }

    private AliOssHelper(Context context) {
        if (BuildConfig.app_mode) {
            if (aliOssConfig == null) {
                Logger.e("===============>>>>AliOss aliOssConfig is null,need to init it<<<<===============");
                return;
            }
        }

        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(aliOssConfig.ACCESS_KEY_ID, aliOssConfig.ACCESS_KEY_SECRET, aliOssConfig.SECURITY_TOKEN);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(10 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(10 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
//        OSSLog.enableLog(); //这个开启会支持写入手机sd卡中的一份日志文件位置在SD_path\OSSLog\logs.csv
        OSSLog.disableLog();
        String ENDPOINT = "oss-cn-beijing.aliyuncs.com";
        ossClient = new OSSClient(context.getApplicationContext(), ENDPOINT, credentialProvider, conf);
    }

    private UpLoadTask createUpLoadTask(Activity activity) {
        return new UpLoadTask(activity);
    }

    private void upLoadImageByOss(UpLoadTask upLoadTask) {

        for (int i = 0, size = upLoadTask.srcList.size(); i < size; i++) {
            if (!upLoadTask.uploadFail) break;
            final String path = upLoadTask.srcList.get(i);
            final String imgName = createUpLoadImgName(upLoadTask.imageNamePrefix);
            PutObjectRequest put = new PutObjectRequest(aliOssConfig.BUCKET_NAME, aliOssConfig.FOLDER + imgName, path);
            // 异步上传时可以设置进度回调
            put.setProgressCallback((request, currentSize, totalSize) -> {
            });
            OSSAsyncTask task = ossClient.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                @Override
                public void onSuccess(PutObjectRequest request, PutObjectResult putResult) {

                    upLoadTask.resultMap.put(path, getUploadImageUrl(imgName));

                    if (!upLoadTask.uploadFail) return;

                    if (size != upLoadTask.resultMap.size()) return;

                    upLoadTask.result();
                }

                @Override
                public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                    if (clientExcepion != null) clientExcepion.printStackTrace();
                    if (serviceException != null) serviceException.printStackTrace();

                    if (!upLoadTask.uploadFail) return;
                    upLoadTask.uploadFail = false;

                    upLoadTask.result();
                }
            });
        }
    }

    private String createUpLoadImgName(String imageNamePrefix) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return (imageNamePrefix == null ? "" : imageNamePrefix) + uuid + ".jpg";
    }

    private String getUploadImageUrl(String imgName) {
        return aliOssConfig.OSS_URL + "/" + aliOssConfig.FOLDER + imgName;
    }

    public static final class AliOssConfig {
        public String ACCESS_KEY_ID;
        public String ACCESS_KEY_SECRET;
        public String SECURITY_TOKEN;

        public String BUCKET_NAME;  // 測試阿里云API的bucket名称
        public String FOLDER;    // 阿里云API的文件夹名称

        public String OSS_URL;
    }

    private final class UpLoadTask {
        private final Activity activity;

        private volatile boolean uploadFail = false;
        private UploadResultListener uploadResultListener;

        private String imageNamePrefix = "";

        private final List<String> srcList = new ArrayList<>(); //上传图片path
        private final Map<String, String> resultMap = new HashMap<>(); //上传成功后服务器返回图片地址 key:本地图片path value 服务器返回url

        UpLoadTask(Activity activity) {
            this.activity = activity;
        }

        public final UpLoadTask setImageNamePrefix(String imageNamePrefix) {
            this.imageNamePrefix = imageNamePrefix;
            return this;
        }

        public final UpLoadTask setImagePathList(List<String> imageItems) {
            srcList.clear();
            resultMap.clear();

            srcList.addAll(imageItems);

            uploadFail = true;
            return this;
        }

        public final UpLoadTask setImagePath(String... paths) {
            srcList.clear();
            resultMap.clear();

            Collections.addAll(srcList, paths);

            uploadFail = true;
            return this;
        }

        public final void upload(UploadResultListener listener) {
            uploadResultListener = listener;
            upLoadImageByOss(this);
        }

        final void result() {
            try {
                if (activity == null) return;
                if (activity.isFinishing()) return;
                if (activity.isDestroyed()) return;

                activity.runOnUiThread(() -> {
                    if (!uploadFail) {
                        uploadResultListener.result(uploadFail, null);
                        return;
                    }
                    List<String> temps = new ArrayList<>(srcList.size());
                    for (String str : srcList) {
                        temps.add(resultMap.get(str));
                    }
                    uploadResultListener.result(uploadFail, temps);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface UploadResultListener {
        void result(boolean suc, List<String> resultList);
    }
}
