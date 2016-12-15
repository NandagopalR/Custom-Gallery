package com.orgware.customgallery.util;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import com.orgware.customgallery.model.ImageDataModel;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class GalleryHelper {

  public static Map<String, ArrayList<ImageDataModel>> imageFolderMap = new HashMap<>();

  public static ArrayList<String> keyList;

  public static ArrayList<ImageDataModel> allImages = new ArrayList<ImageDataModel>();
  ;

  /**
   * Getting All Images Path.
   *
   * @param activity the activity
   * @return ArrayList with images Path
   */
  public static Map<String, ArrayList<ImageDataModel>> getImageFolderMap(Activity activity) {

    imageFolderMap.clear();

    Uri uri;
    Cursor cursor;
    int column_index_data, column_index_folder_name;

    String absolutePathOfImage = null, folderName;
    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

    String[] projection = {
        MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME
    };

    cursor = activity.getContentResolver().query(uri, projection, null, null, null);

    column_index_data = cursor.getColumnIndexOrThrow(MediaColumns.DATA);

    column_index_folder_name =
        cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

    while (cursor.moveToNext()) {

      absolutePathOfImage = cursor.getString(column_index_data);

      folderName = cursor.getString(column_index_folder_name);

      ImageDataModel imDataModel = new ImageDataModel(folderName, absolutePathOfImage);

      if (imageFolderMap.containsKey(folderName)) {

        imageFolderMap.get(folderName).add(imDataModel);
      } else {

        ArrayList<ImageDataModel> listOfAllImages = new ArrayList<ImageDataModel>();

        listOfAllImages.add(imDataModel);

        imageFolderMap.put(folderName, listOfAllImages);
      }
    }

    // Get all Internal images
    uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;

    cursor = activity.getContentResolver().query(uri, projection, null, null, null);

    column_index_data = cursor.getColumnIndexOrThrow(MediaColumns.DATA);

    column_index_folder_name =
        cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

    while (cursor.moveToNext()) {

      absolutePathOfImage = cursor.getString(column_index_data);

      folderName = cursor.getString(column_index_folder_name);

      ImageDataModel imDataModel = new ImageDataModel(folderName, absolutePathOfImage);

      if (imageFolderMap.containsKey(folderName)) {

        imageFolderMap.get(folderName).add(imDataModel);
      } else {

        ArrayList<ImageDataModel> listOfAllImages = new ArrayList<ImageDataModel>();

        listOfAllImages.add(imDataModel);

        imageFolderMap.put(folderName, listOfAllImages);
      }
    }

    keyList = new ArrayList(imageFolderMap.keySet());

    return imageFolderMap;
  }

  /**
   * Getting All Images Path.
   *
   * @param activity the activity
   * @return ArrayList with images Path
   */
  public static ArrayList<ImageDataModel> gettAllImages(Activity activity) {

    ArrayList<Hashtable<String, ArrayList<String>>> processedFilesArray =
        new ArrayList<Hashtable<String, ArrayList<String>>>();

    allImages.clear();
    Uri uri;
    Cursor cursor;
    int column_index_data, column_index_folder_name;

    String absolutePathOfImage = null, imageName;
    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

    String[] projection = {
        MediaColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME
    };

    cursor = activity.getContentResolver().query(uri, projection, null, null, null);

    column_index_data = cursor.getColumnIndexOrThrow(MediaColumns.DATA);

    column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);

    while (cursor.moveToNext()) {

      absolutePathOfImage = cursor.getString(column_index_data);

      imageName = cursor.getString(column_index_folder_name);

      allImages.add(new ImageDataModel(imageName, absolutePathOfImage));
    }

    // Get all Internal images
    uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;

    cursor = activity.getContentResolver().query(uri, projection, null, null, null);

    column_index_data = cursor.getColumnIndexOrThrow(MediaColumns.DATA);

    column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);

    while (cursor.moveToNext()) {

      absolutePathOfImage = cursor.getString(column_index_data);

      imageName = cursor.getString(column_index_folder_name);

      allImages.add(new ImageDataModel(imageName, absolutePathOfImage));
    }

    return allImages;
  }

  public void getFromSdcard(List<String> filePaths) {
    File file = new File(android.os.Environment.getExternalStorageDirectory(), "DCIM");

    if (file.isDirectory()) {
      File[] listFile = file.listFiles();

      for (int i = 0; i < listFile.length; i++) {

        filePaths.add(listFile[i].getAbsolutePath());
      }
    }
  }

  /**
   * Getting All Images Path.
   *
   * @param context the activity
   * @return ArrayList with images Path
   */
  public List<ImageDataModel> gettAllImages(Context context) {

    //Remove older images to avoid copying same image twice

    allImages.clear();
    Uri uri;
    Cursor cursor;
    int column_index_data, column_index_folder_name;

    String absolutePathOfImage = null, imageName;

    //get all images from external storage

    uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

    String[] projection = {
        MediaStore.MediaColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME
    };

    cursor = context.getContentResolver().query(uri, projection, null, null, null);

    column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

    column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);

    while (cursor.moveToNext()) {

      absolutePathOfImage = cursor.getString(column_index_data);

      imageName = cursor.getString(column_index_folder_name);

      allImages.add(new ImageDataModel(imageName, absolutePathOfImage));
    }

    // Get all Internal storage images

    uri = android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI;

    cursor = context.getContentResolver().query(uri, projection, null, null, null);

    column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

    column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);

    while (cursor.moveToNext()) {

      absolutePathOfImage = cursor.getString(column_index_data);

      imageName = cursor.getString(column_index_folder_name);

      allImages.add(new ImageDataModel(imageName, absolutePathOfImage));
    }

    return allImages;
  }

  public ArrayList<String> getFilePaths(Activity activity) {

    Uri u = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    String[] projection = { MediaStore.Images.ImageColumns.DATA };
    Cursor c = null;
    SortedSet<String> dirList = new TreeSet<String>();
    ArrayList<String> resultIAV = new ArrayList<String>();

    String[] directories = null;
    if (u != null) {
      c = activity.managedQuery(u, projection, null, null, null);
    }

    if ((c != null) && (c.moveToFirst())) {
      do {
        String tempDir = c.getString(0);
        tempDir = tempDir.substring(0, tempDir.lastIndexOf("/"));
        try {
          dirList.add(tempDir);
        } catch (Exception e) {

        }
      } while (c.moveToNext());
      directories = new String[dirList.size()];
      dirList.toArray(directories);
    }

    for (int i = 0; i < dirList.size(); i++) {
      File imageDir = new File(directories[i]);
      File[] imageList = imageDir.listFiles();
      if (imageList == null) continue;
      for (File imagePath : imageList) {
        try {

          if (imagePath.isDirectory()) {
            imageList = imagePath.listFiles();
          }
          if (imagePath.getName().contains(".jpg")
              || imagePath.getName().contains(".JPG")
              || imagePath.getName().contains(".jpeg")
              || imagePath.getName().contains(".JPEG")
              || imagePath.getName().contains(".png")
              || imagePath.getName().contains(".PNG")
              || imagePath.getName().contains(".gif")
              || imagePath.getName().contains(".GIF")
              || imagePath.getName().contains(".bmp")
              || imagePath.getName().contains(".BMP")) {

            String path = imagePath.getAbsolutePath();
            resultIAV.add(path);
          }
        }
        //  }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

    return resultIAV;
  }
}
