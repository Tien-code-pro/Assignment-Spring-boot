package com.example.orderdemo.seeder;

import com.example.orderdemo.entity.*;
import com.example.orderdemo.repository.OrderDetailRepository;
import com.example.orderdemo.repository.UserRepository;
import com.example.orderdemo.util.LocalDatetimehelper;
import com.github.javafaker.Faker;
import com.example.orderdemo.entity.enums.OrderSimpleStatus;
import com.example.orderdemo.entity.enums.ProductSimpleStatus;
import com.example.orderdemo.repository.OrderRepository;
import com.example.orderdemo.repository.ProductRepository;
import com.example.orderdemo.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class ApplicationSeeder implements CommandLineRunner {

    boolean createSeedData = false;
    final OrderRepository orderRepository;
    final ProductRepository productRepository;
    final UserRepository userRepository;
    final OrderDetailRepository orderDetailRepository;
    Faker faker;
    Random random = new Random();
    int numberOfProduct = 200;
    int numberOfOrder = 1000;
    int numberOfUser = 10;

    public ApplicationSeeder(
            OrderRepository orderRepository,
            ProductRepository productRepository, UserRepository userRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.faker = new Faker();
    }

    @Override
    public void run(String... args) throws Exception {
        if(createSeedData){
            // Reset dữ liệu
            orderRepository.deleteAll();
            orderDetailRepository.deleteAll();
            productRepository.deleteAll();
            seedProduct();
            seedUser();
            seedOrder();
        }
    }

    private void seedUser(){
        List<User> listUsers = new ArrayList<>();
        for (int i = 0; i < numberOfUser; i++) {
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setFullName(faker.name().fullName());
            user.setPhone(faker.phoneNumber().phoneNumber());
            user.setEmail(faker.name().title());
            user.setStatus(ProductSimpleStatus.ACTIVE);
            listUsers.add(user);
        }
        userRepository.saveAll(listUsers);
    }

    private void seedOrder() {
        List<Product> products = productRepository.findAll();
        List<User> users = userRepository.findAll();
        // Sinh danh sách order để lưu
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < numberOfOrder; i++) {
            // Thông tin một order
            Order order = new Order();
            User user = users.get(random.nextInt(users.size()));
            order.setUser(user);
            order.setId(UUID.randomUUID().toString());
            order.setCreatedAt(LocalDatetimehelper.generateLocalDate());
            // Generate order details.
            Set<OrderDetail> orderDetails = new HashSet<>();
            // sinh ngẫu nhiên số lượng order detail cho một đơn hàng.
            int randomOrderDetailNumber = faker.number().numberBetween(1, 5);
            HashSet<String> existingProductId = new HashSet<>();
            for (int j = 0; j < randomOrderDetailNumber; j++) {
                // Generate 1 item
                OrderDetail orderDetail = new OrderDetail();
                // lấy random sản phẩm từ trong danh sách.
                Product randomProduct = products.get(
                        faker.number().numberBetween(0, products.size() - 1));
                // tránh tình trạng sản phẩm bị trùng trong đơn hàng.
                if (existingProductId.contains(randomProduct.getId())) {
                    // bỏ qua nếu trùng sản phẩm hoặc có thể lấy random lại một product khác.
                    continue;
                }
                existingProductId.add(randomProduct.getId());
                // tạo khoá chính từ order id và product id
                orderDetail.setId(new OrderDetailId(order.getId(), randomProduct.getId()));
                // set quan hệ với order
                orderDetail.setOrder(order);
                // set quan hệ với product
                orderDetail.setProduct(randomProduct);
                orderDetail.setUnitPrice(randomProduct.getPrice());
                orderDetail.setQuantity(faker.number().numberBetween(1, 5));
                // tính lại tổng tiền.
                order.addTotalPrice(orderDetail);
                // add vào danh sách order Detail
                orderDetails.add(orderDetail);
            }
            // set orderDetails vào order
            order.setOrderDetails(orderDetails);
            order.setStatus(OrderSimpleStatus.PENDING);
            // Add order vào danh sách orders bên ngoài, để có thể save all.
            orders.add(order);
        }
        orderRepository.saveAll(orders);
    }

    private void seedProduct() {
        List<Product> listProduct = new ArrayList<>();
        for (int i = 0; i < numberOfProduct; i++) {
            System.out.println(i + 1);
            Product product = new Product();
            product.setId(UUID.randomUUID().toString());
            product.setName(faker.name().title());
            product.setSlug(StringHelper.toSlug(product.getName()));
            product.setDescription(faker.lorem().sentence()); // text
            product.setPrice(
                    new BigDecimal(faker.number().numberBetween(100, 200) * 10000));
            product.setCreatedBy("0");
            product.setUpdatedBy("0");
            product.setDetail(faker.lorem().sentence());
            product.setThumbnails(faker.avatar().image());
            product.setStatus(ProductSimpleStatus.ACTIVE);
            listProduct.add(product);
            System.out.println(product.toString());
        }
        productRepository.saveAll(listProduct);
    }

    public static void main(String[] args) {

    }
}
