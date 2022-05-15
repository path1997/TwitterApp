package Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzeznicki.twitterapp.Controllers.MainController;
import com.rzeznicki.twitterapp.Repo.AuthorRepo;
import com.rzeznicki.twitterapp.Repo.KeywordRepo;
import com.rzeznicki.twitterapp.Repo.TweetRepo;
import com.rzeznicki.twitterapp.TwitterAppApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TwitterAppApplication.class)
@AutoConfigureMockMvc
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private KeywordRepo keywordRepo;

    @Autowired
    private TweetRepo tweetRepo;

    @Autowired
    private AuthorRepo authorRepo;

    @Test
    public void createKeyword_shouldAddKeyword() throws Exception {
        System.out.println(keywordRepo.findAll());
        mockMvc.perform(
                        post("/v1/keywords")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("bmw"))
                        .andExpect(status().is2xxSuccessful());

        assertThat(keywordRepo.findAll().size(), equalTo(1));
    }




}
