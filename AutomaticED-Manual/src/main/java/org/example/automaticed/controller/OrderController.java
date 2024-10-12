package org.example.automaticed.controller;

import org.example.automaticed.entity.OrderEntity;
import org.example.automaticed.service.IOrderService;
import org.example.automaticed.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @GetMapping("/encrypt")
    public String encrypt(@RequestParam String plaintext, @RequestParam String algorithm) {
        try {
            return EncryptionUtil.encrypt(plaintext, algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error encrypting data";
        }
    }

    @GetMapping("/decrypt")
    public String decrypt(@RequestParam String ciphertext, @RequestParam String algorithm) {
        try {
            return EncryptionUtil.decrypt(ciphertext, algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error decrypting data";
        }
    }

    @GetMapping("/saveAll")
    public List<OrderEntity> save(@RequestParam Integer count) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        long startTime = System.currentTimeMillis();
        System.out.println("Start Time: " + sdf.format(new Date(startTime)));

        List<OrderEntity> save = orderService.save(count);

        long endTime = System.currentTimeMillis();
        System.out.println("End Time: " + sdf.format(new Date(endTime)));

        long durationMillis = endTime - startTime;

        long minutes = TimeUnit.MILLISECONDS.toMinutes(durationMillis);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(durationMillis) - TimeUnit.MINUTES.toSeconds(minutes);
        long millis = durationMillis % 1000;

        System.out.println("Execution Time: " + minutes + " min " + seconds + " sec " + millis + " ms");

        return save;
    }

    @GetMapping("/get")
    public String get(@RequestParam Long orderId) {
        String username = EncryptionUtil.encrypt(orderService.getUsernameByOrderId(orderId), "DES");
        return username;
    }

    @GetMapping("/getOrderEntity")
    public OrderEntity getOrderEntity(@RequestParam Long orderId) {
        OrderEntity orderEntity = orderService.getOrderEntity(orderId);
        return decryptUsername(orderEntity);
    }

    private OrderEntity decryptUsername(OrderEntity order) {
        // Encrypt only the username field
        try {
            String decryptedUsername = EncryptionUtil.decrypt(order.getUsername(), "DES");
            order.setUsername(decryptedUsername);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    @GetMapping("/getAllByPage")
    public Page<OrderEntity> findPaginated(int pageNo, int pageSize) {
        Page<OrderEntity> paginated = orderService.findPaginated(pageNo, pageSize);
        return paginated.map(this::decryptUsername);
    }

    @PostMapping("/saveByOneself")
    public List<OrderEntity> saveByOneself(@RequestBody List<OrderEntity> orderList) {
        List<OrderEntity> encryptedOrders = orderList.stream().map(this::encryptUsername).collect(Collectors.toList());
        List<OrderEntity> savedOrders = orderService.saveByOneself(encryptedOrders);
        return savedOrders;
    }

    private OrderEntity encryptUsername(OrderEntity order) {
        // Encrypt only the username field
        String encryptedUsername = EncryptionUtil.encrypt(order.getUsername(), "DES");
        order.setUsername(encryptedUsername);
        return order;
    }


}
