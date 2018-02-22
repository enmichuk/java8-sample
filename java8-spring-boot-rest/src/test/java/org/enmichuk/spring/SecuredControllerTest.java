package org.enmichuk.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecuredControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getHello() throws Exception {
        mockMvc.perform(get("/new/data"))
                .andExpect(unauthenticated())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void requestWithInvalidUser() throws Exception {
        this.mockMvc.perform(get("/new/data").secure(true).with(httpBasic("invalid", "invalid")))
                .andExpect(unauthenticated())
                .andExpect(status().is4xxClientError())
        ;
    }

    @Test
    public void requestUserWithValidUser() throws Exception {
        this.mockMvc.perform(get("/user/data").secure(true).with(httpBasic("user", "password")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(startsWith("Secured data")))
        ;
    }

    @Test
    public void requestNewWithValidUser() throws Exception {
        this.mockMvc.perform(get("/new/data").secure(true).with(httpBasic("user", "password")))
                .andExpect(status().is4xxClientError())
        ;
    }

    @Test
    public void requestAdminWithValidUser() throws Exception {
        this.mockMvc.perform(get("/admin/data").secure(true).with(httpBasic("user", "password")))
                .andExpect(status().is4xxClientError())
        ;
    }

    @Test
    public void requestUserWithValidAdmin() throws Exception {
        this.mockMvc.perform(get("/user/data").secure(true).with(httpBasic("admin", "password")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(startsWith("Secured data")))
        ;
    }

    @Test
    public void requestNewWithValidAdmin() throws Exception {
        this.mockMvc.perform(get("/new/data").secure(true).with(httpBasic("admin", "password")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(startsWith("Secured data")))
        ;
    }

    @Test
    public void requestAdminWithValidAdmin() throws Exception {
        this.mockMvc.perform(get("/admin/data").secure(true).with(httpBasic("admin", "password")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(startsWith("Secured data")))
        ;
    }
}
