package codegym.controller.Product;

import com.codegym.model.Product.Product;
import com.codegym.model.Product.ProductForm;
import com.codegym.model.Product.ProductHasAmount;
import com.codegym.service.Product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.codegym.controller.Product.ProductAjax.productListAddToCart;


@Controller
@PropertySource("classpath:global_config_app.properties")
public class ProductController {
    @Autowired
    IProductService productService;
    public static String keySearch;
    public static int numberOfPage=0;

    @Autowired
    private Environment environment;

    private String getPrincipal() {
        String role = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            role = String.valueOf(((UserDetails) principal).getAuthorities());
        } else {
            role = null;
        }
        return role;
    }

    private int size = 3;
    private int page = 0;

    @RequestMapping("/home/{page}")
    public String getPage(@PathVariable("page") int pageRequest, Model model) {
        page = pageRequest;
        int sizeNewProduct = 3;
        Page<Product> newProducts = this.productService.getAllProduct("", sizeNewProduct, 3);
        Page<Product> products = this.productService.getAllProduct("", size, page);
        model.addAttribute("products", products);
        model.addAttribute("user", getPrincipal());
        model.addAttribute("newProducts", newProducts);
        model.addAttribute("amountCart", productListAddToCart.size());
        String role = getPrincipal();
        if (role == null || role.equals("[ROLE_USER]")) {
            return "index";
        } else {
            return "admin";
        }
    }

    @RequestMapping("/product-detail/{id}")
    public String viewProductDetail(@PathVariable("id") Long id, Model model) {
        Product product = this.productService.findProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("amountCart", productListAddToCart.size());
        model.addAttribute("user", getPrincipal());

        return "single";
    }

    @GetMapping("/checkout")
    public String checkOut(Model model) {
        model.addAttribute("amountCart", productListAddToCart.size());
        model.addAttribute("listProduct", getProductAddToCart());
        model.addAttribute("user", getPrincipal());
        model.addAttribute("totalBill", getTotalBill());

        return "checkout";
    }

    public List<ProductHasAmount> getProductAddToCart() {
        List<ProductHasAmount> listProduct = new ArrayList<>();
        productListAddToCart.forEach((idProductKey, amountValue) -> {
            Product product = this.productService.findProductById(idProductKey);
            ProductHasAmount productHasAmount = new ProductHasAmount(product.getId(), product.getImage()
                    , product.getName(), product.getPrice()
                    , product.getDescribetion(), amountValue);
            listProduct.add(productHasAmount);
        });
        return listProduct;
    }

    @GetMapping("/admin/create-product")
    public String showFormCreate(Model model) {
        model.addAttribute("productForm", new ProductForm());
        model.addAttribute("user", getPrincipal());
        model.addAttribute("amountCart", productListAddToCart.size());
        return "create-product";
    }

    @PostMapping("/admin/create-product")
    public ModelAndView doCreateProduct(@ModelAttribute("productForm") ProductForm productForm, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("create-product");
            modelAndView.addObject("message", "Error! Please create again!");
            return modelAndView;
        }

        MultipartFile multipartFile = productForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = environment.getProperty("file_upload").toString();

        try {
            FileCopyUtils.copy(productForm.getImage().getBytes(), new File(fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.productService.addProduct(new Product("../properties/images/" + fileName, productForm.getName(), productForm.getPrice(), productForm.getDescribe()));
        ModelAndView modelAndView = new ModelAndView("create-product");
        modelAndView.addObject("user", getPrincipal());
        modelAndView.addObject("message", "Add success!");
        return modelAndView;
    }

    @GetMapping("/admin/edit-product/{id}")
    public String showFormEditProduct(@PathVariable("id") Long id, Model model) {
        Product product = this.productService.findProductById(id);
        model.addAttribute("amountCart", productListAddToCart.size());
        model.addAttribute("product", product);

        File fileImage = new File(product.getImage());
        model.addAttribute("fileImage", fileImage);
//        model.addAttribute("productForm",new ProductForm());
        model.addAttribute("message", "");

        return "edit-product";
    }

    @PostMapping("/admin/edit-product/{id}")
    public ModelAndView doEditProduct(@PathVariable("id") Long id,@ModelAttribute ProductForm productForm,BindingResult result){
        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("edit-product");
            modelAndView.addObject("message", "Error! Please edit again!");
            return modelAndView;
        }

        MultipartFile multipartFile = productForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = environment.getProperty("file_upload").toString();

        try {
            FileCopyUtils.copy(productForm.getImage().getBytes(), new File(fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Product product=new Product(id,"../properties/images/" + fileName, productForm.getName(), productForm.getPrice(), productForm.getDescribe());
        this.productService.editProduct(product);
        ModelAndView modelAndView = new ModelAndView("edit-product");
        modelAndView.addObject("user", getPrincipal());
        modelAndView.addObject("product", product);
        modelAndView.addObject("message", "Edit success!");
        return modelAndView;
    }

    @RequestMapping("/admin/delete/{id}")
    @ResponseBody()
    public String doDeleteProduct(@PathVariable("id") Long id) {
        Product product = this.productService.findProductById(id);
        this.productService.removeProduct(product);
        return "true";
    }

    @PostMapping("/search-product")
    public String showResultSearch(@RequestParam("key-search") String key,Model model){
        System.out.println(key);
        keySearch=key;
        List<Product> productsList = this.productService.getAllProduct(keySearch,100,0).getContent();
        Page<Product> products = this.productService.getAllProduct(keySearch,3,0);
        int numberPage=productsList.size()/3;
        if (productsList.size()%3!=0){
            numberPage++;
        }
        numberOfPage=numberPage;
        List<Integer> listPage=new ArrayList<>();
        for (int i = 0; i < numberOfPage; i++) {
            listPage.add(i);
        }
        model.addAttribute("numberPage",listPage);
        String role=getPrincipal();
        model.addAttribute("user", role);
        model.addAttribute("products", products);
        model.addAttribute("amountCart", productListAddToCart.size());
        if (role==null||role.equals("[ROLE_USER]")){
            return "search-user";
        }
        else {
            return "search-admin";
        }
    }

    @RequestMapping("/search-page/{page}")
    public String loadMoreResultSearch(@PathVariable("page") int pageRequest,Model model){
        page = pageRequest;
        Page<Product> products = this.productService.getAllProduct(keySearch, size, page);
        List<Integer> listPage=new ArrayList<>();
        for (int i = 0; i < numberOfPage; i++) {
            listPage.add(i);
        }
        model.addAttribute("products", products);
        model.addAttribute("user", getPrincipal());
        model.addAttribute("amountCart", productListAddToCart.size());
        model.addAttribute("numberPage",listPage);
        String role = getPrincipal();
        if (role == null || role.equals("[ROLE_USER]")) {
            return "search-user";
        } else {
            return "search-admin";
        }
    }

    public double getTotalBill() {
        final double[] totalBill = {0};
        productListAddToCart.forEach((idProductKey, amountValue) -> {
            Product product = this.productService.findProductById(idProductKey);
            totalBill[0] += product.getPrice() * amountValue;
        });
        return totalBill[0];
    }

}
