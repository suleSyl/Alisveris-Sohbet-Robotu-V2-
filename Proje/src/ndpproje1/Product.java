package ndpproje1;

public abstract class Product {

    private String pCategory;
    private String pBrand;
    private String pModel;     

    public Product() {}

    public Product(String category, String brand, String model) {
        this.pCategory = category;
        this.pBrand = brand;
        this.pModel = model;      
    }

    public String getpCategory() {
        return pCategory;
    }

    public void setpCategory(String category) {
        this.pCategory = category;
    }

    public String getpBrand() {
        return pBrand;
    }

    public void setpBrand(String brand) {
        this.pBrand = brand;
    }

    public String getpModel() {
        return pModel;
    }

    public void setpModel(String model) {
        this.pModel = model;
    }

    public String showProductInfo() {
        return pCategory + " " + pBrand + " " + pModel;
    }
}
