package com.yurc.customerbus.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.widget.ImageView;

import com.yurc.customerbus.application.BusApplication;
import com.yurc.customerbus.util.LogUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


/**
 * 图片下载工具类
 * @ClassName DownloadImgUtils
 * @Description  图片下载工具类
 * @date 2015-6-5 下午3:48:57
 */
public class DownloadImgUtils {

	/**
	 * 根据url下载图片并根据imageView的宽高压缩
	 * @Title downloadImgByUrl
	 * @Description 根据url下载图片并根据imageView的宽高压缩
	 * @param urlStr url
	 * @param imageview
	 * @return Bitmap 压缩后的图片
	 * @date 2015-6-5 下午3:51:13
	 */
	public static Bitmap downloadImgByUrl(String urlStr, ImageView imageview) {
		FileOutputStream fos = null;
		InputStream is = null;
		try {
			String str = urlStr.substring(urlStr.lastIndexOf("/")+1,urlStr.lastIndexOf("."));
			String encodeStr = URLEncoder.encode(str, BusApplication.CHARSET);
			urlStr = urlStr.replace(str,encodeStr);

			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			is = new BufferedInputStream(conn.getInputStream());
			is.mark(is.available());

			Options opts = new Options();
			opts.inJustDecodeBounds = true;
			Bitmap bitmap = BitmapFactory.decodeStream(is, null, opts);

			// 获取imageview想要显示的宽和高
			ImageSizeUtil.ImageSize imageViewSize = ImageSizeUtil.getImageViewSize(imageview);
			opts.inSampleSize = ImageSizeUtil.caculateInSampleSize(opts,
					imageViewSize.width, imageViewSize.height);

			opts.inJustDecodeBounds = false;
			is.reset();
			bitmap = BitmapFactory.decodeStream(is, null, opts);

			conn.disconnect();
			return bitmap;

		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.e("Exception", e.getMessage());
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
			}

			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
			}
		}
		return null;
	}


	public static boolean downloadByUrl(String urlStr, File file) {
		FileOutputStream fos = null;
		InputStream is = null;
		try {
			String str = urlStr.substring(urlStr.lastIndexOf("/")+1,urlStr.lastIndexOf("."));
			String encodeStr = URLEncoder.encode(str, BusApplication.CHARSET);
			urlStr = urlStr.replace(str,encodeStr);
			LogUtil.v("downloadByUrl",urlStr);
			HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
			is = conn.getInputStream();
			fos = new FileOutputStream(file);
			byte[] buf = new byte[512];
			int len = 0;
			while ((len = is.read(buf)) != -1) {
				fos.write(buf, 0, len);
			}
			fos.flush();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.e("Exception",e.getMessage());
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
			}

			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
			}
		}

		return false;

	}
}
