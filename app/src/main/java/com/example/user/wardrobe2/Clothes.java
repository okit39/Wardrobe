package com.example.user.wardrobe2;

import java.util.HashMap;
import java.util.Map;

public class Clothes {

    private String Category;
    private String Color;
    private String key;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    private boolean visible;

    public Clothes(){
    }
    public Clothes(String category, String color, String key){
        Category = category;
        Color = color;
        this.key = key;
    }

    //Getters

    public String getCategory() {
        return Category;
    }

    public String getColor() {
        return Color;
    }

    public String getKey() {
        return key;
    }

    // Setters

    public void setCategory(String category) {
        Category = category;
    }

    public void setColor(String color) {
        Color = color;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("category", Category);
        result.put("color", Color);
        result.put("key", key);

        return result;
    }

}
