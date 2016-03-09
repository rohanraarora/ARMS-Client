package com.forkthecode.arms_client;

/**
 * Created by Rohan on 3/10/2016.
 */
public class MenuItem {
    private String name;
    private String description;
    private String category;
    private String price;
    private String imageUrl;

    public MenuItem(){

    }

    public MenuItem(String name,String desc,String cat,String price,String imageUrl){
        this.name = name;
        description = desc;
        category = cat;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
