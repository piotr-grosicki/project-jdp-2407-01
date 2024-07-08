import com.kodilla.ecommercee.EcommerceeApplication;
import com.kodilla.ecommercee.controller.GroupController;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = GroupController.class)
@ContextConfiguration(classes = EcommerceeApplication.class)
public class GroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldFetchEmptyGroupList() throws Exception {
        // Given & When
        mockMvc.perform(get("/v1/ecommercee/groups")
                        .accept(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)))
                .andDo(print());
    }

    @Test
    public void shouldFetchGroup() throws Exception {
        // Given & When
        mockMvc.perform(get("/v1/ecommercee/groups/group/1")
                        .accept(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("test group name")))
                .andDo(print());
    }

    @Test
    public void shouldCreateGroup() throws Exception {
        // Given
        String groupName = "new_group";

        // When
        mockMvc.perform(post("/v1/ecommercee/groups")
                        .param("name", groupName))
                // Then
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void shouldUpdateGroup() throws Exception {
        // Given
        Long groupId = 1L;
        Long productId = 1L;

        // When
        mockMvc.perform(put("/v1/ecommercee/groups/{groupId}", groupId)
                        .param("productId", String.valueOf(productId)))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("updated test group name")))
                .andDo(print());
    }
}

