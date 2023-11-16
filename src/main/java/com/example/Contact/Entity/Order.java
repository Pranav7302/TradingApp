package com.example.Contact.Entity;

import com.example.Contact.Constants.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo userOrdersId;

    @NotBlank(message = Constants.Stock_Symbol_Not_Blank)
    @Size(min = 10, max = 25, message = Constants.Stock_Symbol_Length)
    private String stockSymbol;

    @Pattern(regexp = "(?i)buy|sell", message = Constants.Order_Type)
    private String orderType;

    @Positive(message =Constants.Price_Positive)
    @DecimalMin(value = "0.0", message = Constants.Price_Constraint)
    private double price;

    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = Constants.Quantity_Constraint)
    private int quantity;

    private String status;

    private String timestamp;
}
