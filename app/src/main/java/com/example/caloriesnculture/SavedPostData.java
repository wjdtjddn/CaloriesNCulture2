package com.example.caloriesnculture;

public class SavedPostData {
    private static final SavedPostData instance = new SavedPostData();

    public static SavedPostData getInstance() {
        return instance;
    }

    public boolean isNew = false;

    private Post post;

    public void Init()
    {
        post = new Post();
    }

    public  void SetPostData(Post _post)
    {

    }

    public Post GetPostData()
    {
        return post;
    }
}