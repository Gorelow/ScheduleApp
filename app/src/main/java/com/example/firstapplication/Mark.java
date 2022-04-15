package com.example.firstapplication;

import android.graphics.Bitmap;

public class Mark {
    private String _name;
    private Bitmap _image;

    public Mark(String name, Bitmap image)
    {
        _name = name;
        _image = image;
    }


    public String get_name() {
        return _name;
    }

    public Bitmap get_image() {
        return _image;
    }

    public void set_image(Bitmap _image) {
        this._image = _image;
    }

    public void set_name(String _name) {
        this._name = _name;
    }
}
