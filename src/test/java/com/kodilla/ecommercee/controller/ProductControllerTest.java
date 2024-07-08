import com.kodilla.ecommercee.EcommerceeApplication;
import com.kodilla.ecommercee.controller.ProductController;
import com.kodilla.ecommercee.domain.dto.ProductDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = ProductController.class)
@ContextConfiguration(classes = EcommerceeApplication.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldFetchEmptyProductList() throws Exception {
        // Given & When
        mockMvc.perform(get("/v1/ecommercee/products")
                        .accept(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)))
                .andDo(print());
    }

    @Test
    public void shouldFetchProduct() throws Exception {
        // Given & When
        mockMvc.perform(get("/v1/ecommercee/products/1")
                        .accept(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("test_name")))
                .andExpect(jsonPath("$.description", is("test_description")))
                .andExpect(jsonPath("$.price", is(1)))
                .andExpect(jsonPath("$.groupId", is(1)))
                .andDo(print());
    }

    @Test
    public void shouldCreateProduct() throws Exception {
        // Given
        ProductDto productDto = new ProductDto(1L, "test_name", "test_description", new BigDecimal(1), 1L);
        String jsonContent = objectMapper.writeValueAsString(productDto);

        // When
        mockMvc.perform(post("/v1/ecommercee/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                // Then
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void shouldUpdateProduct() throws Exception {
        // Given
        ProductDto productDto = new ProductDto(1L, "updated_test_name", "updated_test_description", new BigDecimal(1), 1L);
        String jsonContent = objectMapper.writeValueAsString(productDto);

        // When
        mockMvc.perform(put("/v1/ecommercee/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("updated_test_name")))
                .andExpect(jsonPath("$.description", is("updated_test_description")))
                .andDo(print());
    }

    @Test
    public void shouldDeleteProduct() throws Exception {
        // Given & When
        mockMvc.perform(delete("/v1/ecommercee/products/1"))
                // Then
                .andExpect(status().isOk())
                .andDo(print());
    }
}
