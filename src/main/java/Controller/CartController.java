package Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/v1/shop/carts")
public class CartController {

    private List<Cart> carts = new ArrayList<>();
    private AtomicLong cartIdCounter = new AtomicLong();
    private AtomicLong cartItemIdCounter = new AtomicLong();

    @GetMapping
    public List<Cart> getAllCarts() {
        return carts;
    }

    @GetMapping("/{v1/shop/carts}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long id) {
        Optional<Cart> cart = carts.stream().filter(c -> c.getId().equals(id)).findFirst();
        return cart.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cart createCart(@RequestBody Cart cart) {
        cart.setId(cartIdCounter.incrementAndGet());
        cart.getItems().forEach(item -> item.setId(cartItemIdCounter.incrementAndGet()));
        carts.add(cart);
        return cart;
    }

    @PutMapping("/{v1/shop/carts}")
    public ResponseEntity<Cart> updateCart(@PathVariable Long id, @RequestBody Cart cartDetails) {
        Optional<Cart> existingCart = carts.stream().filter(c -> c.getId().equals(id)).findFirst();
        if (existingCart.isPresent()) {
            Cart cart = existingCart.get();
            cart.setItems(cartDetails.getItems());
            cart.getItems().forEach(item -> {
                if (item.getId() == null) {
                    item.setId(cartItemIdCounter.incrementAndGet());
                }
            });
            return ResponseEntity.ok(cart);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{v1/shop/carts}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        Optional<Cart> existingCart = carts.stream().filter(c -> c.getId().equals(id)).findFirst();
        if (existingCart.isPresent()) {
            carts.remove(existingCart.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
