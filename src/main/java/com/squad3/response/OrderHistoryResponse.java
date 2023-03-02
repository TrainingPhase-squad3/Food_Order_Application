package com.squad3.response;

import java.time.LocalDate;
import java.util.List;

import com.squad3.entity.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistoryResponse {
	
	
    private LocalDate orderDate;
    private Double totalPrice;
    
    private List<OrderItem> orderIem;

}
