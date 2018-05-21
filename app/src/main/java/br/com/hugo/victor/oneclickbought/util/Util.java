package br.com.hugo.victor.oneclickbought.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.util.List;

public class Util {

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
}
