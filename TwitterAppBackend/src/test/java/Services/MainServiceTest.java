package Services;

import com.rzeznicki.twitterapp.Repo.AuthorRepo;
import com.rzeznicki.twitterapp.Repo.KeywordRepo;
import com.rzeznicki.twitterapp.Repo.TweetRepo;
import com.rzeznicki.twitterapp.Services.MainService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class MainServiceTest {

    @Mock
    Environment environmentMock;

    @Mock
    AuthorRepo authorRepoMock;

    @Mock
    KeywordRepo keywordRepoMock;

    @Mock
    TweetRepo tweetRepoMock;

    @Test
    void performRequestToTwitterApi_shouldReturnResponseBody(){
        //given
        MainService mainService = new MainService(tweetRepoMock,keywordRepoMock,authorRepoMock,environmentMock);
        ResponseEntity<String> responseEntity = new ResponseEntity<String>("responseBody", HttpStatus.ACCEPTED);

        //when


        //then

    }
}
