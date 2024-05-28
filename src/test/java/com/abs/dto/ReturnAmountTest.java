package com.abs.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ReturnAmountTest {

    @Test
    void testAllArgsConstructor() {
       
        double maturityAmount = 1000.50;

        ReturnAmount returnAmount = new ReturnAmount(maturityAmount);

        assertThat(returnAmount.getMaturityAmount()).isEqualTo(maturityAmount);
    }

    @Test
    void testNoArgsConstructorAndSetter() {
        
        ReturnAmount returnAmount = new ReturnAmount();
        double maturityAmount = 1000.50;

        returnAmount.setMaturityAmount(maturityAmount);

        assertThat(returnAmount.getMaturityAmount()).isEqualTo(maturityAmount);
    }

    @Test
    void testBuilder() {
       
        double maturityAmount = 1000.50;
        ReturnAmount returnAmount = ReturnAmount.builder()
                                                .maturityAmount(maturityAmount)
                                                .build();
        assertThat(returnAmount.getMaturityAmount()).isEqualTo(maturityAmount);
    }

    @Test
    void testToString() {
        double maturityAmount = 1000.50;
        ReturnAmount returnAmount = new ReturnAmount(maturityAmount);
        String result = returnAmount.toString();
        assertThat(result).contains("maturityAmount=1000.5");
    }

    @Test
    void testEqualsAndHashCode() {
     
        double maturityAmount = 1000.50;
        ReturnAmount returnAmount1 = new ReturnAmount(maturityAmount);
        ReturnAmount returnAmount2 = new ReturnAmount(maturityAmount);

      
        assertThat(returnAmount1).isEqualTo(returnAmount2);
        assertThat(returnAmount1.hashCode()).isEqualTo(returnAmount2.hashCode());
    }
    
    @Test
    void test_Builder() {
    	new ReturnAmount().builder().toString();
    }
}
