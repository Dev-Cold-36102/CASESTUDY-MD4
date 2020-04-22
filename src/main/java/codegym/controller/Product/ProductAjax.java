package codegym.controller.Product;

import com.codegym.model.Product.Product;
import com.codegym.service.Product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class ProductAjax {

    @Autowired
    IProductService productService;

    public static HashMap<Long, Integer> productListAddToCart = new HashMap<>();


    @RequestMapping("/addtocart/{id}")
    @ResponseBody()
    public int addToCart(@PathVariable("id") Long id) {
//        Product product=this.productService.findProductById(id);
        if (productListAddToCart.containsKey(id)) {
            int amount = productListAddToCart.get(id);
            productListAddToCart.replace(id, amount + 1);
        } else {
            productListAddToCart.put(id, 1);
        }
        return productListAddToCart.size();
    }

    @RequestMapping("/change-amount-product/{id}/{amount}")
    @ResponseBody()
    public double changeAmountProduct(@PathVariable("id") Long id, @PathVariable("amount") int amount, Model model) {
        productListAddToCart.replace(id, amount);
        double totalAfter = this.productService.findProductById(id).getPrice() * amount;
        return totalAfter;
    }

    @RequestMapping("/remove/{id}")
    @ResponseBody()
    public int removeProduct(@PathVariable("id") Long id) {
        productListAddToCart.remove(id);
        return productListAddToCart.size();
    }

    @RequestMapping("/search/{key}")
    @ResponseBody()
    public String[] doSearch(@PathVariable("key") String key, Model model) {
        List<Product> products =  this.productService.getAllProduct(key, 100, 0).getContent();
//        String[] product=new String[10];
        List<String> nameProduct=new ArrayList<>();
        for (Product p:products) {
            nameProduct.add(p.getName());
        }
        String[] name= new String[nameProduct.size()];
              name=  nameProduct.toArray(name);
        return name;
    }


}
