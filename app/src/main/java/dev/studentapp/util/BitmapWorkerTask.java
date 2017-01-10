package dev.studentapp.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

public class BitmapWorkerTask extends AsyncTask<Void, Void, Bitmap> {

            private final WeakReference<ImageView> imageViewReference;
            private String imagePath;
            private Bitmap bitmap;
            private int reqWidth, reqHeight;

            public BitmapWorkerTask(ImageView imageView, String imagePath, int reqWidth, int reqHeight) {
                // Use a WeakReference to ensure the ImageView can be garbage collected
                imageViewReference = new WeakReference<ImageView>(imageView);
                this.imagePath = imagePath;

                this.reqWidth = reqWidth;
                this.reqHeight = reqHeight;
            }

            public BitmapWorkerTask(ImageView imageView, Bitmap bitmap, int reqWidth, int reqHeight) {
                // Use a WeakReference to ensure the ImageView can be garbage collected
                imageViewReference = new WeakReference<ImageView>(imageView);
                this.bitmap = bitmap;

                this.reqWidth = reqWidth;
                this.reqHeight = reqHeight;
            }

            public BitmapWorkerTask(ImageView imageView, String imagePath) {
                // Use a WeakReference to ensure the ImageView can be garbage collected
                imageViewReference = new WeakReference<ImageView>(imageView);
                this.imagePath = imagePath;
            }

            public BitmapWorkerTask(ImageView imageView, Bitmap bitmap) {
                // Use a WeakReference to ensure the ImageView can be garbage collected
                imageViewReference = new WeakReference<ImageView>(imageView);
                this.bitmap = bitmap;
            }

            // Decode image in background.
            @Override
            protected Bitmap doInBackground(Void... params) {

                if (reqHeight > 1 && reqHeight > 1) {
                    if (bitmap==null)
                        return decodeBitmapFromResource(imagePath, reqWidth, reqHeight);
                    else
                        return decodeBitmap(bitmap, reqWidth, reqHeight);
                }
                else {
                    if (bitmap==null)
                        return BitmapFactory.decodeFile(imagePath);
                    else
                        return bitmap;
                }
            }

            // Once complete, see if ImageView is still around and set bitmap.
            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (imageViewReference != null && bitmap != null) {
                    final ImageView imageView = imageViewReference.get();
                    if (imageView != null) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            }

            public Bitmap decodeBitmapFromResource(String path, int reqWidth, int reqHeight) {

                // First decode with inJustDecodeBounds=true to check dimensions
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;

                BitmapFactory.decodeFile(path,options);

                // Calculate inSampleSize
                options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

                // Decode bitmap with inSampleSize set
                options.inJustDecodeBounds = false;
                return BitmapFactory.decodeFile(path,options);
            }

            public Bitmap decodeBitmap(Bitmap bitmap, int reqWidth, int reqHeight) {
                if (bitmap.getWidth() > reqWidth || bitmap.getHeight() > reqHeight)
                    return bitmap;
                else
                    return Bitmap.createScaledBitmap(bitmap, reqWidth, reqHeight, false);
            }

            public int calculateInSampleSize(
                    BitmapFactory.Options options, int reqWidth, int reqHeight) {
                // Raw height and width of image
                final int height = options.outHeight;
                final int width = options.outWidth;
                int inSampleSize = 1;

                if (height > reqHeight || width > reqWidth) {

                    final int halfHeight = height / 2;
                    final int halfWidth = width / 2;

                    // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                    // height and width larger than the requested height and width.
                    while ((halfHeight / inSampleSize) >= reqHeight
                            && (halfWidth / inSampleSize) >= reqWidth) {
                        inSampleSize *= 2;
                    }
                }

                return inSampleSize;
            }
        }