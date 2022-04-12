package com.sbsi.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbsi.entity.Cart;
import com.sbsi.entity.CartProducts;
import com.sbsi.entity.Product;
import com.sbsi.service.CartService;
import com.sbsi.service.ProductService;

@Component
public class CartController implements Serializable {

	private static final long serialVersionUID = 1681465219145473019L;

	private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

	@Autowired
	private CartService cartService;

	@Autowired
	private ProductService productService;

	public Integer getCartSize(String username) {
		return cartService.getCartSize(username);
	}

	public Cart getCartByUsername(String username) {
		return cartService.getCartByUsername(username);
	}

	public List<CartProducts> getCartProducts(String username) {
		return cartService.getCartProducts(username);
	}

	public Double getCartTotal(String username) {
		return cartService.getCartTotal(username).doubleValue();
	}

	public String addProductToCart(Product productToCart, String username) {
		Cart cart = cartService.getCartByUsername(username);
		productToCart.setAvlblQnnty(productToCart.getAvlblQnnty().subtract(BigInteger.ONE));
		CartProducts cartProducts = new CartProducts();
		cartProducts.setCart(cart);
		cartProducts.setProduct(productToCart);
		cartProducts.setProductQntty(BigInteger.ONE);
		cartProducts.setSubTotal(productToCart.getPrice());
		if (Optional.ofNullable(cart.getCartProducts()).isPresent() && cart.getCartProducts().contains(cartProducts)) {
			cart.getCartProducts().stream().filter(c -> c.equals(cartProducts)).findAny().ifPresent(c -> {
				if (c.getProductQntty().intValue() < 5) {
					c.setProductQntty(c.getProductQntty().add(BigInteger.ONE));
					c.setSubTotal(
							BigDecimal.valueOf(c.getProductQntty().longValue()).multiply(c.getProduct().getPrice()));
				} else {
					FacesContext.getCurrentInstance().addMessage("addProductToCart",
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR, "Already 5 items with Product Code: "
											+ productToCart.getPrdctCd() + " in the Cart. More Items can't be added.",
									null));
				}
			});
		} else {
			cart.addCartProducts(cartProducts);
		}
		if (FacesContext.getCurrentInstance().getMessages("addProductToCart").hasNext()) {
			return "product";
		}
		this.updateCartPrice(cart);
		try {
			productService.updateProduct(productToCart);
			cartService.addProductToCart(cart);
			FacesContext.getCurrentInstance().addMessage("addProductToCart",
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Product with Code " + productToCart.getPrdctCd() + " successfully added to cart.", null));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("addProductToCart",
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Product with Code: " + productToCart.getPrdctCd() + " addition to cart failed", null));
		}
		return "product";
	}

	public void updateCartPrice(Cart cart) {
		cart.setTotalprice(cart.getCartProducts().parallelStream()
				.map(c -> BigDecimal.valueOf(c.getProductQntty().longValue()).multiply(c.getProduct().getPrice()))
				.reduce(BigDecimal.ZERO, BigDecimal::add));
	}

	public String removeProductFromCart(CartProducts cartProduct, String username) {
		Cart cart = cartService.getCartByUsername(username);
		Product product = productService.findProductByCode(cartProduct.getProduct().getPrdctCd());
		product.setAvlblQnnty(product.getAvlblQnnty().add(cartProduct.getProductQntty()));
		cart.removeCartProducts(cartProduct);
		this.updateCartPrice(cart);
		try {
			LOGGER.info("Removing product from cart");
			productService.updateProduct(product);
			cartService.removeProductFromCart(cart);
			FacesContext.getCurrentInstance().addMessage("removeProductFromCart",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Product Removed from cart.", null));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("removeProductFromCart", new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error Occured while removing product from cart.", null));
		}
		return "cart";
	}

	public String updateProductQttyInCart(CartProducts cartProduct, String username) {
		Cart cart = cartService.getCartByUsername(username);
		Product product = productService.findProductByCode(cartProduct.getProduct().getPrdctCd());
		if (Optional.ofNullable(cart.getCartProducts()).isPresent() && cart.getCartProducts().contains(cartProduct)) {
			cart.getCartProducts().stream().filter(c -> c.equals(cartProduct)).findAny().ifPresent(c -> {
				product.setAvlblQnnty(
						product.getAvlblQnnty().subtract(cartProduct.getProductQntty().subtract(c.getProductQntty())));
				c.setProductQntty(cartProduct.getProductQntty());
				c.setSubTotal(BigDecimal.valueOf(c.getProductQntty().longValue()).multiply(c.getProduct().getPrice()));
			});
		}
		this.updateCartPrice(cart);
		try {
			productService.updateProduct(product);
			cartService.updateProductQuantityInCart(cart);
			FacesContext.getCurrentInstance().addMessage("updateProductQntty",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Cart Updated with selected Quantity", null));
		} catch (Exception e) {
			LOGGER.error("Error Occured while updating quantity in cart");
			FacesContext.getCurrentInstance().addMessage("updateProductQntty",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Product Quantity updation failed in cart.", null));
		}
		return "cart";

	}

}
