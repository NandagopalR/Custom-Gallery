package com.orgware.customgallery.model;

import java.io.File;

/**
 * Created by nandagopal on 12/14/16.
 */
public class FolderDetailItem {

  private String fileId;
  private File file;
  private String fileName;
  private String fileSize;
  private String filePath;

  public FolderDetailItem(String fileId, File file, String fileName, String fileSize,
      String filePath) {
    this.fileId = fileId;
    this.file = file;
    this.fileName = fileName;
    this.fileSize = fileSize;
    this.filePath = filePath;
  }

  public String getFileId() {
    return fileId;
  }

  public void setFileId(String fileId) {
    this.fileId = fileId;
  }

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFileSize() {
    return fileSize;
  }

  public void setFileSize(String fileSize) {
    this.fileSize = fileSize;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }
}
