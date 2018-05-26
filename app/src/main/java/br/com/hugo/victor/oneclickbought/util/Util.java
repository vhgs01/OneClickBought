package br.com.hugo.victor.oneclickbought.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

import java.util.List;

import static android.support.v4.app.ActivityCompat.requestPermissions;

public class Util {

    private static final int REQUEST_PHONE_CALL = 1;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;

    public static void openLinkedinProfile(Context context, String perfilId) throws Exception {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("linkedin://add/%@" + perfilId));

        final PackageManager packageManager = context.getPackageManager();
        final List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);

        if (list.isEmpty()) {
            intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.linkedin.com/profile/view?id=" + perfilId));
        }

        context.startActivity(intent);
    }

    public static void checkPermissionPhoneCall(Activity activity, Context context, String phone) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_PHONE_CALL);
        } else {
            activity.startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" +
                    phone)));
        }
    }

    public static void checkPermissionCamera(Activity activity, Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(activity, new String[]{Manifest.permission.CAMERA},
                    MY_CAMERA_PERMISSION_CODE);
        } else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            activity.startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    public static String getPhoneNumberFormatted(String number) {
        return number.replace("(", "").replace(")", "")
                .replace("–", "").replaceAll("\\s", "");
    }

}