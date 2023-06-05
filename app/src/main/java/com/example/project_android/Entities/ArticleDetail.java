package com.example.project_android.Entities;

public class ArticleDetail {
    private String dataTitle;
    private String dataDesc;
    private String dataUser;
    private int dataImage;

    public ArticleDetail() {

    }
    public ArticleDetail(String dataTitle, String dataDesc, String dataUser, int dataImage) {
        this.dataTitle = dataTitle;
        this.dataDesc = dataDesc;
        this.dataUser = dataUser;
        this.dataImage = dataImage;
    }

    public String getDataTitle() {
        return dataTitle;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public String getDataUser() {
        return dataUser;
    }

    public int getDataImage() {
        return dataImage;
    }

    @Override
    public String toString() {
        return "ArticleDetail{" +
                "dataTitle='" + dataTitle + '\'' +
                ", dataDesc='" + dataDesc + '\'' +
                ", dataUser='" + dataUser + '\'' +
                ", dataImage=" + dataImage +
                '}';
    }
}
