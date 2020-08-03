package com.example.apothecary.views.mainscreen ;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.example.apothecary.BuildConfig;
import com.example.apothecary.R;
import com.example.apothecary.views.view.profile.ProfileFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import okhttp3.MultipartBody;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class UploadImage_BottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {

    private UploadImage_BottomSheetListener listener;
    
    @BindView(R.id.bottomsheet_ProfileImage)
    ImageView imageView;
    @BindView(R.id.bottomsheet_Upload_Button)
    Button upload;
    @BindView(R.id.bottomsheet_Update_Button)
    Button pickImage;

    private static final int REQUEST_TAKE_PHOTO = 0;
    private static final int REQUEST_PICK_PHOTO = 2;
    private Uri mMediaUri;
    private static final int CAMERA_PIC_REQUEST = 1111;

    private static final String TAG = ProfileFragment.class.getSimpleName();

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    public static final int MEDIA_TYPE_IMAGE = 1;

    private Uri fileUri;

    private String mediaPath=null;

    private Button btnCapturePicture;

    Bitmap bitmap =null;
    Bitmap bitmap2 = null;

    private String mImageFileLocation = "";
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
    ProgressDialog pDialog;
    private String postPath;

    Context context ;
    Activity activity;
    int viewId;

    String imageUrl;

    public UploadImage_BottomSheet(Context context, Activity activity , int ViewId) {
        this.context = context;
        this.activity = activity;
        this.viewId = ViewId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_uploadimage_layout,container,false);
        ButterKnife.bind(this,view);
        ClickEvent();
        setImage();


      /*  if(viewId==1){
            Picasso.get().load(imageUrl).placeholder(R.drawable.ic_circle2).into(imageView);
        }else{
            imageView.setImageResource(R.drawable.no_image_available);
        }*/

        return view;
    }

    public void setImageUrl(String url){
        this.imageUrl = url;
    }

    private void ClickEvent() {
        pickImage.setOnClickListener(this);
        upload.setOnClickListener(this);
        if(viewId==1){
           // imageView.setImageResource(R.drawable.no_profile_image2);
            Picasso.get().load(imageUrl).placeholder(R.drawable.ic_circle).into(imageView);
        }else{
            imageView.setImageResource(R.drawable.no_image_available);
        }
        //initDialog();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bottomsheet_Update_Button:
                new MaterialDialog.Builder(context)
                        .title(R.string.uploadImages)
                        .items(R.array.uploadImages)
                        .itemsIds(R.array.itemIds)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                switch (which) {
                                    case 0:
                                        SelectImageFromGallery();
                                        break;
                                   /* case 1:
                                        captureImage();

                                        break;*/
                                    case 1:
                                    {
                                        if(viewId==1){
                                            imageView.setImageResource(R.drawable.no_profile_image2);
                                        }else{
                                            imageView.setImageResource(R.drawable.no_image_available);
                                        }

                                    }

                                        break;
                                }
                            }
                        })
                        .show();
                break;
            case R.id.bottomsheet_Upload_Button:
                uploadFile();
                break;
        }

    }


    public void setMediaPath(String mediaPath){
        this.mediaPath=mediaPath;
    }

    private void setImage(){
        if(viewId!=1){
            if(mediaPath==null){
                imageView.setImageResource(R.drawable.no_image_available);
            }else{
                imageView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
            }
        }else{
            if(mediaPath==null){
                imageView.setImageResource(R.drawable.no_profile_image2);
            }
        }
    }

    private void SelectImageFromGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO);
    }

    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO || requestCode == REQUEST_PICK_PHOTO) {
                if (data != null) {
                    // Get the Image from data
                    Uri selectedImage = data.getData();
                    try {
                        bitmap2 = MediaStore.Images.Media.getBitmap(activity.getContentResolver(),selectedImage );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = activity.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    // Set the Image in ImageView for Previewing the Media
                    imageView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                    cursor.close();


                    postPath = mediaPath;
                }


            }else if (requestCode == CAMERA_PIC_REQUEST){
                if (Build.VERSION.SDK_INT > 21) {

                    Glide.with(context).load(mImageFileLocation).into(imageView);
                    postPath = mImageFileLocation;

                }else{
                    Glide.with(context).load(fileUri).into(imageView);
                    postPath = fileUri.getPath();

                }

            }

        }
        else if (resultCode != RESULT_CANCELED) {
           // context.Toast.makeText(this, "Sorry, there was an error!", Toast.LENGTH_LONG).show();
            Toasty.normal(context,"Sorry, there was an error!",Toasty.LENGTH_LONG).show();
        }
    }



    /**
     * Checking device has camera hardware or not
     * */
    private boolean isDeviceSupportCamera() {
        if (activity.getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_ANY)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }


    private void captureImage() {
        if (Build.VERSION.SDK_INT > 21) { //use this if Lollipop_Mr1 (API 22) or above
            Intent callCameraApplicationIntent = new Intent();
            callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

            // We give some instruction to the intent to save the image
            File photoFile = null;

            try {
                // If the createImageFile will be successful, the photo file will have the address of the file
                photoFile = createImageFile();
                // Here we call the function that will try to catch the exception made by the throw function
            } catch (IOException e) {
                Logger.getAnonymousLogger().info("Exception error in generating the file");
                e.printStackTrace();
            }
            // Here we add an extra file to the intent to put the address on to. For this purpose we use the FileProvider, declared in the AndroidManifest.
            Uri outputUri = FileProvider.getUriForFile(
                    context,
                    BuildConfig.APPLICATION_ID + ".provider",
                    photoFile);
            callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);

            // The following is a new line with a trying attempt
            callCameraApplicationIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Logger.getAnonymousLogger().info("Calling the camera App by intent");

            // The following strings calls the camera app and wait for his file in return.
            startActivityForResult(callCameraApplicationIntent, CAMERA_PIC_REQUEST);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            // start the image capture Intent
            startActivityForResult(intent, CAMERA_PIC_REQUEST);
        }


    }

    File createImageFile() throws IOException {
        Logger.getAnonymousLogger().info("Generating the image - method started");

        // Here we create a "non-collision file name", alternatively said, "an unique filename" using the "timeStamp" functionality
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmSS").format(new Date());
        String imageFileName = "IMAGE_" + timeStamp;
        // Here we specify the environment location and the exact path where we want to save the so-created file
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/Apothecary");
        Logger.getAnonymousLogger().info("Storage directory set");

        // Then we create the storage directory if does not exists
        if (!storageDirectory.exists()) storageDirectory.mkdir();

        // Here we create the file using a prefix, a suffix and a directory
        File image = new File(storageDirectory, imageFileName + ".jpg");
        // File image = File.createTempFile(imageFileName, ".jpg", storageDirectory);

        // Here the location is saved into the string mImageFileLocation
        Logger.getAnonymousLogger().info("File name and path set");

        mImageFileLocation = image.getAbsolutePath();
        // fileUri = Uri.parse(mImageFileLocation);
        // The file is returned to the previous intent across the camera application
        return image;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("file_uri", fileUri);
    }


    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + ".jpg");
        }  else {
            return null;
        }

        return mediaFile;
    }


    private void uploadFile() {

        if (postPath == null || postPath.equals("")) {
            Toasty.normal(context,"Please select an image",Toasty.LENGTH_LONG).show();
            return;
        } else {
            if(viewId==1){
                listener.onUploadButtonClicked(imageToString());
            }else{
                listener.onPrescriptionUploadButtonClicked(imageToString(),mediaPath);
            }
            dismiss();

        }
    }

    private String imageToString(){
        String image = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            bitmap2.compress(Bitmap.CompressFormat.JPEG,95,byteArrayOutputStream);
            byte[] imgByte = byteArrayOutputStream.toByteArray();
            image = Base64.encodeToString(imgByte,Base64.DEFAULT);

        return image;
    }

    public interface UploadImage_BottomSheetListener{
        void onUploadButtonClicked( String photo);
        void onPrescriptionUploadButtonClicked(String photo,String mediapath);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            if(viewId==1){
               // if(context instanceof  UploadImage_BottomSheet.UploadImage_BottomSheetListener){
                    listener = (UploadImage_BottomSheetListener) getTargetFragment();
               // }
            }else{
                 if(context instanceof  UploadImage_BottomSheet.UploadImage_BottomSheetListener){
                listener = (UploadImage_BottomSheetListener) context;
                 }
            }

        }catch (Exception e){
            throw new RuntimeException(context.toString()+" must implement UploadImage_BottomSheet_ClickListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }

    
}
